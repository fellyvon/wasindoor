package com.aiyc.framework.monitor;

import com.aiyc.server.standalone.db.DaoUtil;

public class ReConnectionMonitor implements DaoMonitor {
	private String dbType;
	private Tester teser = null;
	private int delay = 30000;

	public ReConnectionMonitor(String dbType, int delay) {
		super();
		this.dbType = dbType;
		this.delay = delay;
	}

	private class Tester extends Thread {
		public void start() {
			super.start();
			System.out.println("Tester start...");
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(delay);
					testDB();

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		}
	}

	public void end() {
		teser = null;
		System.out.println("DB test stop!");
	}

	private Tester test = null;

	public void start() {
		test = new Tester();
		test.start();

	}

	public ReConnectionMonitor() {
		this("MYSQL", 30000);
	}

	public void testDB() {
		boolean conn = false;
		try {
			DaoUtil.queryData("select 1", new Object[] {});
			conn = true;
		} catch (Exception e) {
			e.printStackTrace();
			conn = false;
		} finally {
			if (conn) {
				System.out.println("DB connectioned");
			} else {
				System.out.println("DB reconnectioned");
			}
		}
	}

}
