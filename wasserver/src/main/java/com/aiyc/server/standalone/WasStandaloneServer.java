package com.aiyc.server.standalone;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aiyc.framework.monitor.DaoMonitor;
import com.aiyc.framework.monitor.SessionManager;
import com.aiyc.server.standalone.dzdp.NotifyEnterPlace;
import com.aiyc.server.standalone.longwait.AloneServer;
import com.aiyc.server.standalone.net.CalcTasker;
import com.aiyc.server.standalone.net.ConnectionHandler;
import com.aiyc.server.standalone.svm.TrainSVMTimerTask;
import com.aiyc.server.standalone.util.Configuration;
import com.aiyc.server.standalone.util.ConsoleData;
import com.aiyc.server.standalone.util.Log;

/**
 * Basic class of the standalone server
 * 
 * 
 * @author felly
 * 
 */
public class WasStandaloneServer implements Runnable {

	/**
	 * Does start the standalone server
	 * 
	 * @param args
	 *            configuration
	 */
	public static void main(String[] args) {
		initConfig(args);
		startServer();

	}

	private final ServerSocket serverSocket;
	private final ExecutorService threadPool;
	private static Logger log;
	private boolean running;

	/**
	 * creates a new server instance
	 * 
	 * @param port
	 *            Port number
	 * @throws IOException
	 */
	public WasStandaloneServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("HTTP SERVER SERVICE...PORT:" + port);
		threadPool = Executors.newCachedThreadPool();
		log = Log.getLogger();
	}

	/**
	 * sets the configuration according to the arguments
	 * 
	 * @param args
	 *            configuration
	 */
	public static void initConfig(String[] args) {
		Object list = ConsoleData.instance().getCachedValue(ConsoleData.HNADER);
		if (args.length > 0) {
			Configuration.ServerPort = new Integer(args[0]);
		}
		getServiceClassLoader();
		getCalcClassLoader();
	}

	private static URLClassLoader calcLoader = null;// /////

	private static URLClassLoader serviceLoader = null;// /////

	public static URLClassLoader getServiceClassLoader() {
		if (serviceLoader == null) {
			File cl = new File(Configuration.hander_indoor_libdir);// ��Jar��
			URL indoor = null;

			try {
				indoor = cl.toURL();
			} catch (Exception e) {
				e.printStackTrace();
			}
			serviceLoader = (new URLClassLoader(new URL[] { indoor }, Thread
					.currentThread().getContextClassLoader()));
		}

		return serviceLoader;
	}

	public static URLClassLoader getCalcClassLoader() {
		if (calcLoader == null) {
			File cl = new File(Configuration.calc_indoor_libdir);// ��Jar��
			URL indoor = null;

			try {
				indoor = cl.toURL();
			} catch (Exception e) {
				e.printStackTrace();
			}
			calcLoader = (new URLClassLoader(new URL[] { indoor }, Thread
					.currentThread().getContextClassLoader()));
		}

		return calcLoader;
	}

	/**
	 * �ش��㷨����
	 */

	public static void startClac() throws Exception {
		if (Configuration.calcSort == 2 || Configuration.calcSort == 3) {
			System.out.println("geo calc  start ....");
			Class task = calcLoader.loadClass(Configuration.calc_indoor_path);
			CalcTasker calc = (CalcTasker) task.newInstance();
			calc.start();
		}
		if (Configuration.calcSort == 1 || Configuration.calcSort == 3) {
			System.out.println("wifi calc  start ....");
			TrainSVMTimerTask.start();// ///wifi�㷨��
		}
	}

	/**
	 * starts the server by creating an instance an run it
	 */
	public static void startServer() {
		try {
			WasStandaloneServer server = new WasStandaloneServer(
					Configuration.ServerPort);
			new Thread(server).start();

			AloneServer server2 = AloneServer.instance();

			server2.start();
			System.out.println("SOKECT SERVER SERVICE...PORT:"
					+ Configuration.LongServerPort);

			NotifyEnterPlace.instance().start();
			System.out.println("Notify SERVICE start.....");

			// ///�㷨��
			try {
				startClac();
			} catch (Exception e) {
				e.printStackTrace();
			}

			SessionManager.instance().listener();
			System.out.println("Session service start.....");

		} catch (IOException e) {

			Log.getLogger().log(Level.SEVERE, "Failed to start server", e);
			e.printStackTrace();
		}
	}

	/**
	 * waits for incoming connection, accepts and passes them to the connection
	 * handler
	 */
	public void run() {
		log.info("Started server at "
				+ serverSocket.getInetAddress().getHostName() + ":"
				+ serverSocket.getLocalPort());
		running = true;
		try {

			while (running) {
				threadPool
						.execute(new ConnectionHandler(serverSocket.accept()));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			if (running) {
				log.log(Level.SEVERE, "caught io execpton: " + ex.getMessage(),
						ex);
			} else {
				log.fine(ex.getMessage());
			}
		}

		threadPool.shutdown();
		log.fine("Shutting down thread pool...");

		while (!threadPool.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		log.fine("Thread pool shut down");

		synchronized (this) {
			notifyAll();
		}

		NotifyEnterPlace.instance().destory();// ///

		System.out.println("Stopped server at "
				+ serverSocket.getInetAddress().getHostName() + ":"
				+ serverSocket.getLocalPort());

	}

	/**
	 * stops the server.
	 */
	public void stopServer() {
		log.info("Stopping server...");
		running = false;

		try {
			serverSocket.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public class ShutdownHandler implements Runnable {

		WasStandaloneServer server = null;
		DaoMonitor dm = null;

		public ShutdownHandler(WasStandaloneServer server, DaoMonitor dm) {
			this.server = server;
			this.dm = dm;
		}

		// TODO: check synchronization issue (not all messages get logged).
		// system.out.println gets always printed

		public void run() {
			log.fine("Control-C caught. Shutting down gracefully...");
			// System.out.println("Shutdown Handler: Shutting down
			// gracefully...");
			server.stopServer();
			// dm.end();
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
			}
		}

	}

}
