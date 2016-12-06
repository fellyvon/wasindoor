package com.aiyc.framework.monitor;

import java.util.Iterator;
import java.util.Map;

/**
 * session管理器
 * 
 * @author felly
 * 
 */
public class SessionManager {
	private MonitorSessionThread mon = new MonitorSessionThread(this);

	private static class SessionManagerImpl {
		static SessionManager man = new SessionManager();
	}

	public static SessionManager instance() {
		return SessionManagerImpl.man;
	}

	private SessionManager() {

	}

	public void listener() {
		if (!mon.isAlive()) {
			mon.start();
		}
	}

	private Map<String, Session> sessions = new java.util.concurrent.ConcurrentHashMap<String, Session>(
			10000);

	public void addSession(Session session) {
		if (session == null) {
			return;
		}

		sessions.put(session.getFlowId(), session);

		// ////////
	}

	public void removeSession(Session session) {
		if (session == null)
			return;
		Session se = sessions.remove(session.getFlowId());
		se = null;
	}

	public Session getSession(String flowId) {
		return sessions.get(flowId);
	}

	class MonitorSessionThread extends Thread {
		private SessionManager sessionManager;

		MonitorSessionThread(SessionManager sessionManager) {
			this.sessionManager = sessionManager;
		}

		public void run() {

			while (true) {

				try {
					Iterator keys = sessions.keySet().iterator();

					while (keys.hasNext()) {
						Session ses = sessions.get(keys.next());
						System.out.println(ses.getFlowId()+":"+ses.getLastTime());
						if (ses.isOutTime()) {
							sessionManager.removeSession(ses);
						}
						Thread.sleep(100);// ///////
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						Thread.sleep(60000);// /1分钟检查一次
					} catch (Exception er) {
						er.printStackTrace();
						System.out.println("线程异常down");
						System.exit(-1);
					}
				}
			}

		}

	}

}
