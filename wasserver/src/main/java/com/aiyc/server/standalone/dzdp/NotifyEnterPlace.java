package com.aiyc.server.standalone.dzdp;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 
import com.aiyc.server.standalone.db.DaoUtil;
import com.aiyc.server.standalone.util.Configuration;

/**
 * 同步任务入口
 * 
 * @author felly
 * 
 */
public class NotifyEnterPlace extends TimerTask implements Enter {
	private static class NotifyEnterPlaceImpl {
		private static NotifyEnterPlace alone = new NotifyEnterPlace();
	}

	public static NotifyEnterPlace instance() {
		return NotifyEnterPlaceImpl.alone;
	}

	private NotifyEnterPlace() {

	}

	private TimerManager timer = null;

	private class TimerManager {
		private NotifyEnterPlace n;

		// 时间间隔
		private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
		private Timer timer = null;

		public TimerManager(NotifyEnterPlace n) {
			this.n = n;
			timer = new Timer();

			// 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
			timer.schedule(n, PERIOD_DAY);
		}

		public void destory() {
			timer.cancel();
		}

		// 增加或减少天数
		public Date addDay(Date date, int num) {
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(date);
			startDT.add(Calendar.DAY_OF_MONTH, -num);
			return startDT.getTime();
		}

	}

	/**
	 * 得到全部不重复的建筑
	 */

	public ResultSet getAllBuilding() throws Exception {
		String sql = "select `AREA_NO`,  `BUILDING_NO` BUILD_NO  ,  `JD` , `WD`   from d_building";

		return DaoUtil.queryData(sql, new Object[] {});
	}
	public void run() {

		try {
			ResultSet rs =  getAllBuilding();
			while (rs.next()) {
				executorService.execute(new NotifyDaz(rs.getString("AREA_NO"),
						rs.getString("BUILD_NO"), rs.getString("JD"), rs
								.getString("WD"), Configuration.search_radais

				)

				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private ExecutorService executorService; // 线程池
	private final int POOL_SIZE = 4; // 单个CPU的线程池大小
 

	public void start() {
		if (executorService == null)
			init();

		if (timer == null)
			timer = new TimerManager(this);
	}

	public void init() {
		try {
			if (executorService == null) {
				executorService = Executors.newFixedThreadPool(Runtime
						.getRuntime().availableProcessors()
						* POOL_SIZE);
				System.out.println("开辟线程数 ： "
						+ Runtime.getRuntime().availableProcessors()
						* POOL_SIZE);
			}
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}
	}

	public void destory() {
		if (timer != null) {
			timer.destory();
		}

		timer = null;
		if (!executorService.isShutdown())
			executorService.shutdownNow();
		executorService = null;

	}

	public static void main(String dfsf[]) throws Exception {
		NotifyEnterPlace.instance().init();
		NotifyEnterPlace.instance().run();
	}

}