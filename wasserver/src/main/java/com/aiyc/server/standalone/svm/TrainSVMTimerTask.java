 
package com.aiyc.server.standalone.svm;

import java.sql.ResultSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.aiyc.server.standalone.db.DaoUtil;
import com.aiyc.server.standalone.util.Configuration;

public final class TrainSVMTimerTask extends TimerTask {

	public final static long DEFAULT_TRAIN_RATE = 60; // minutes

	public static void start() {
		TimerTask trainSVM = new TrainSVMTimerTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(trainSVM, 0, getTrainRate());
	}

	private final static long getTrainRate() {
		long rate = Configuration.SVMTrainRate;

		if (rate < 1.0) {
			rate = DEFAULT_TRAIN_RATE;
		}

		return rate * 1000 * 60;
	}

	private List trainList = new Vector();
	private List runList = new Vector();

	@Override
	public void run() {
		// /SVMSupport.train();
		// /��ÿһ�ŵ�ͼ����ѵ��
		String sql = "select distinct  mapId from    svmmodel ";
		try {
			ResultSet rs = DaoUtil.queryData(sql, new Object[] {});
			while (rs.next()) {
				String mapId = rs.getString("mapId");
				trainList.add(mapId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		Train train = new Train();
		train.start();// ////����ѵ���̣߳��첽����

		HU hu = new HU();// //�߳����ڶ�ʱ�����Դ
		hu.start();
	}

	class HU extends Thread {
		public void run() {
			try {
				// //һ���Լ�10��
				while (trainList.size() > 0) {

					int len = trainList.size() > 10 ? 10 : trainList.size();
					for (int i = 0; i < len; i++) {
						runList.add(trainList.remove(0));
					}
					synchronized (lock) {

						if (runList.size() > 0) {
							lock.notify();
							// System.out.println("����ݣ�ѵ���߳̿�ʼ�ɻ");
						}
					}
					synchronized (hulock) {
						if (runList.size() > 0) {
							// System.out.println("ѵ���߳����ڸɻ�����Դ�߳�ֹͣ����Դ��");
							hulock.wait();
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	Object lock = new Object();
	Object hulock = new Object();

	class Train extends Thread {

		public  void run() {
			while (true) {
				try {
					synchronized (lock) {
						if (runList.size() == 0) {
							// System.out.println("û��ݣ�ѵ���߳�û�취�ɻ");
							lock.wait();
						}

					}
					synchronized (hulock) {
						if (runList.size() == 0) {
							// System.out.println("û��ݣ������Դ�߳̿�ʼ�����ѽ��");
							hulock.notify();
						}
					}
					String mapId = (String) runList.remove(0);
					 
						MapSVMSupport.train(mapId);
				 
				
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
