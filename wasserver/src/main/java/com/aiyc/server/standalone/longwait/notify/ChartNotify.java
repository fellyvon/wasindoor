package com.aiyc.server.standalone.longwait.notify;

import com.aiyc.server.standalone.exp.NotifyException;
import com.aiyc.server.standalone.longwait.AloneServer;
import com.aiyc.server.standalone.longwait.Notifyer;
import com.aiyc.server.standalone.longwait.Server;

/**
 * ����������Ϣ�߳�
 * 
 * @author felly
 * 
 */
public class ChartNotify implements Notifyer, Runnable {
	public void run() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Server server = null;

	public ChartNotify() {
		server = AloneServer.instance();
	}

	/**
	 * 
	 */
	public void send() throws NotifyException {
		Thread t = new Thread(this);
		t.start();
	}

}
