package com.aiyc.server.standalone.longwait;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aiyc.server.standalone.exp.NotifyException;
import com.aiyc.server.standalone.util.Configuration;

/**
 * 服务入口程序
 * 
 * @author felly
 * 
 */
public class AloneServer implements Server, Runnable {
	private List<Notifyer> ln = new Vector<Notifyer>();

	/***
	 * 添加推送服务
	 */
	public synchronized void addNotify(Notifyer er) {
		if (!ln.contains(er)) {
			ln.add(er);
		}

	}

	public void startNotify()  throws NotifyException {
		 Iterator<Notifyer> it=ln.iterator();
		 while(it.hasNext()){
			 Notifyer next=it.next();
			 next.send();
		 }
		
	}

	public void run() {
		try {
			Socket socket = null;
			while (true) {

				isStart = true;
				socket = serverSocket.accept();
				System.out.println("连接客户端："
						+ socket.getInetAddress().getHostAddress());
				executorService.execute(new SokectCallBack(this, socket));
				// Client c = new Client(socket, socket.getLocalAddress()
				// .getHostAddress()
				// + socket.getPort());
				// this.addSokect(c);
				// new RegChecker(c, 120l).start();// /

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			isStart = false;
		} finally {
			try {

				destory();
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 */
	public Client getSokect(int id) {
		Iterator<Client> it = this.getClients().iterator();
		while (it.hasNext()) {
			Client c = it.next();
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	public Client getSokect(String ip, int port) {
		Iterator<Client> it = this.getClients().iterator();
		while (it.hasNext()) {
			Client c = it.next();
			Socket s = c.getSocket();
			if (s.getInetAddress().getHostAddress().equals(ip)
					&& s.getPort() == port) {
				return c;
			}
		}
		return null;
	}

	public void removeSokect(int id) {
		Client c = this.getSokect(id);
		if (c != null)
			this.removeSokect(c);

	}

	public void removeSokect(String ip, int port) {
		Client c = this.getSokect(ip, port);
		if (c != null)
			this.removeSokect(c);

	}

	private int port = 7887;
	private ServerSocket serverSocket = null;
	private ExecutorService executorService; // 线程池
	private final int POOL_SIZE = 4; // 单个CPU的线程池大小
	private List<Client> sokects = new Vector<Client>(1024);

	public List<Client> getClients() {
		return sokects;
	}

	public void destory() {
		try {
			if (serverSocket != null)
				serverSocket.close();

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

		} finally {
			serverSocket = null;
			isStart = false;
		}
	}

	private static Log log = LogFactory.getLog(AloneServer.class);//  

	/**
	 * 入口程序
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		AloneServer server = AloneServer.instance();

		server.start();
		if (args.length > 0) {
			if (args[0].indexOf("-c") != -1) {// //界面启动
				// new ServerMonitor(server);
			} else if (args[0].indexOf("-i") != -1) {// /安装
				// new ServiceRegCommand().install();
			} else if (args[0].indexOf("-u") != -1) {// 卸载
				// new ServiceRegCommand().uninstall();
			}

			else if (args[0].indexOf("-b") != -1) {// 后台运行

			}
		}

	}

	private AloneServer() {
		port = getPort();

		try {
			this.bingToServerPort(port);
			executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
					.availableProcessors()
					* POOL_SIZE);
			// System.out.println("开辟线程数 ： "
			// + Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		} catch (Exception e) {
			System.out.println("绑定端口不成功!" + e.getMessage());
			e.printStackTrace();
			log.error(e);
		} finally {

		}
	}

	private void bingToServerPort(int port) {
		try {
			serverSocket = new ServerSocket(port);
			// System.out.println("服务启动成功!端口:" + port);
			log.debug("服务启动!端口:" + port);
		} catch (Exception e) {

			log.error("启动服务失败！" + e);
			e.printStackTrace();

		}
	}

	public int getPort() {
		return Configuration.LongServerPort;
	}

	private static class AloneServerImpl {
		private static AloneServer alone = new AloneServer();
	}

	public static AloneServer instance() {
		return AloneServerImpl.alone;
	}

	private boolean isStart;

	public void start() {

		if (isStart) {
			log.warn("服务已经启动！");
			return;
		}

		new Thread(this).start();
		// Runtime.getRuntime().addShutdownHook(
		// new Thread(new ShutdownHandler(this)));
	}

	public synchronized void addSokect(Client socket) {
		if (sokects.contains(socket)) {
			return;
		}
		sokects.add(socket);

	}

	public synchronized void removeSokect(Client socket) {
		sokects.remove(socket);

	}

	public boolean isStart() {
		return isStart;
	}

	private List<String> msgs = new ArrayList<String>(1024);

	public List<String> qetCallBackMsg() {
		return msgs;
	}

	public synchronized void addCallBack(String msg) {
		if (msgs.size() > 1024) {// 大于1024释放内存
			msgs.clear();
		}
		msgs.add(msg);
		if (log.isDebugEnabled()) {
			log.debug(msg);
		}
	}

	private Object lock = new Object();

	/**
	 * 给指定socket发送消息
	 */
	public void sendMsg(Socket socket, byte[] b) {
		try {

			synchronized (lock) {
				java.io.OutputStream writer = socket.getOutputStream();

				writer.write(b);
				writer.flush();
			}

		} catch (Exception ex) {
			ex.printStackTrace();

			log.error("服务器发送消息失败：" + socket.getInetAddress().getHostAddress()
					+ "(" + socket.getPort() + ")"
					+ ExcepitonTrace.getExceptionTraceInfo(ex));
		}
	}

	/**
	 * 发送给全部客户端
	 * 
	 * @param b
	 */
	public void sendAll(byte[] b) {
		Iterator<Client> it = this.getClients().iterator();
		while (it.hasNext()) {
			this.sendMsg(it.next().getSocket(), b);
		}
	}

	public class ShutdownHandler implements Runnable {

		private Server server = null;

		public ShutdownHandler(Server server) {
			this.server = server;

		}

		// TODO: check synchronization issue (not all messages get logged).
		// system.out.println gets always printed

		public void run() {
			log.warn("Control-C caught. Shutting down gracefully...");
			// System.out.println("Shutdown Handler: Shutting down
			// gracefully...");
			server.destory();

			try {
				synchronized (server) {
					log.info("Waiting for server shutdown...");
					// System.out.println("Shutdown Handler: Wait for server
					// shutdown...");
					server.wait();
					log.info("Server shut down, now quitting...");

					// System.out.println("Shutdown Handler: Server shut down,
					// now quitting");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e);
			}
		}

	}

	/**
	 * 
	 */

	public Client getSokect(String userNo) {
		Iterator<Client> it = this.getClients().iterator();
		while (it.hasNext()) {
			Client c = it.next();

			if (c.getId() != null && c.getId().equals(userNo)) {
				return c;
			}
		}
		return null;
	}
}

