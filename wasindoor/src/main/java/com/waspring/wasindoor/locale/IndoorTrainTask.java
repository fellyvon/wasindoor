package com.waspring.wasindoor.locale;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aiyc.server.standalone.db.DaoUtil;
import com.aiyc.server.standalone.net.CalcTasker;
import com.aiyc.server.standalone.util.Configuration;
import com.aiyc.server.standalone.util.Log;
import com.waspring.wasindoor.locale.libsvm.SVMTrainer;

/**
 * 定时任务
 * 
 * @author felly
 * 
 */
public class IndoorTrainTask extends TimerTask implements CalcTasker {
	Logger log = Log.getLogger();
	public final static long DEFAULT_TRAIN_RATE = 60; // minutes

	public final static int NUM_OF_FOLDS = 1;// /360度分为几个折数

	public static final int TRAIN_ROW_NUM = 5;// 训练文件中每行有几个数据

	public static void main(String arfs[]) throws Exception {
		IndoorTrainTask s = new IndoorTrainTask();
		s.run();
	}

	/**
	 * 启动任务
	 */
	public void start() {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 0, getTrainRate());
	}

	private final static long getTrainRate() {
		long rate = Configuration.SVMTrainRate;

		if (rate < 1.0) {
			rate = DEFAULT_TRAIN_RATE;
		}

		return rate * 1000 * 60*24;
	}

	private List trainList = new Vector();
	private List runList = new Vector();

	@Override
	public void run() {
		System.out.println("geo train start....");
		log.log(Level.INFO, "geo train start....");
		// /对每一幢建筑进行计算
		String sql = "select distinct  `BUILDING_NO` BUILDING_NO  from    d_building ";
		try {
			ResultSet rs = DaoUtil.queryData(sql, new Object[] {});
			while (rs.next()) {
				String buildNo = rs.getString("BUILDING_NO");
				trainList.add(buildNo);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.log(Level.WARNING, e.getLocalizedMessage());

		}

		Train train = new Train();
		train.start();// ////启动训练线程，异步进行

		HU hu = new HU();// //线程用于定时添加资源
		hu.start();
	}

	class HU extends Thread {
		public void run() {
			try {
				// //一次性加5个建筑
				while (trainList.size() > 0) {

					int len = trainList.size() > 10 ? 10 : trainList.size();
					for (int i = 0; i < len; i++) {
						runList.add(trainList.remove(0));
					}
					synchronized (lock) {

						if (runList.size() > 0) {
							lock.notify();
							// System.out.println("有数据，训练线程开始干活！");
						}
					}
					synchronized (hulock) {
						if (runList.size() > 0) {
							// System.out.println("训练线程正在干活，添加资源线程停止加资源！");
							hulock.wait();
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				log.log(Level.WARNING, e.getLocalizedMessage());
			}
		}
	}

	Object lock = new Object();
	Object hulock = new Object();

	class Train extends Thread {

		public void run() {
			while (true) {
				try {
					synchronized (lock) {
						if (runList.size() == 0) {
							// System.out.println("没数据，训练线程没办法干活！");
							lock.wait();
						}

					}
					synchronized (hulock) {
						if (runList.size() == 0) {
							// System.out.println("没数据，添加资源线程开始加数据呀！");
							hulock.notify();
						}
					}
					String buildNo = (String) runList.remove(0);

					buildBuild(buildNo);

					Thread.sleep(100);// ///让整个执行过程踹口气
				} catch (java.lang.ArrayIndexOutOfBoundsException ee) {
					ee.printStackTrace();
					log.log(Level.WARNING, ee.getLocalizedMessage());
				}

				catch (Exception e) {
					e.printStackTrace();
					log.log(Level.WARNING, e.getLocalizedMessage());
				}
			}
		}

	}

	public void buildBuild(String buildNo) throws Exception {
		ResultSet rs = getFloors(buildNo);
		while (rs.next()) {

			String floorNo = rs.getString("floor_no");
			buildBuildFloor(buildNo, floorNo);
		}

	}

	public void buildBuildFloor(String buildNo, String floorNo)
			throws Exception {

		ITrainGen gen = new TrainFileGen(buildNo, floorNo);

		gen.init();

		gen.gen();// /产生模型文件

		Thread.sleep(1);// ///让过程踹口气

		int b = gen.getBuildNo();
		int f = gen.getFloorNo();
		int los = gen.getBuildFloorLocation();
		// 先训练 生成8(折数)个model

		try {
			SVMTrainer trainer = new SVMTrainer(b, f, TRAIN_ROW_NUM, los,
					NUM_OF_FOLDS);
			trainer.buildTrainModel(Configuration.indoor_location
					+ File.separator);

			trainer = null;
		} catch (Exception e) {
			e.printStackTrace();

		}
		gen.destory();
		gen = null;

		Thread.sleep(1);// ///让过程踹口气

	}

	/**
	 * 得到全部楼层
	 * 
	 * @param buildNo
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getFloors(String buildNo) throws SQLException {
		String sql = "select  floor_no from d_floor  r "
				+ " where r.build_no=?";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { buildNo });
		return rs;
	}
}
