 
package com.aiyc.server.standalone.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Handler for a connection.
 * 
 * It does handle an incoming connection and reads requests and pass each of
 * them to the request handler
 * 
 * @author felly
 * 
 */

public class ConnectionHandler implements Runnable {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	public ConnectionHandler(Socket s) throws IOException {
		socket = s;
		in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
		out = new DataOutputStream(
				new BufferedOutputStream(s.getOutputStream()));

	}

	/**
	 * reads each request and passes it to the request handler. closes a
	 * connection if requested
	 */
	@SuppressWarnings("deprecation")
	public void run() {
		long n1 = System.currentTimeMillis();

		String ip = socket.getLocalAddress().getHostAddress();
		String port = String.valueOf(socket.getPort());
		try {

			RequestHandler rhandler = new RequestHandler();
			ImageHandler imgHandler = new ImageHandler(in, out, ip, port);

			String line = in.readLine();
			System.out.println("request line="+line);
			long n2 = System.currentTimeMillis();
			System.out.println("read cost" + (n2 - n1) / 1000 + "ms");

			if (line != null) {
				if ((line.indexOf("GET") == 0) || (line.indexOf("POST") == 0)) {
					imgHandler.handle(line);
				} else {

					String lines = new BufferedReader(new InputStreamReader(
							new java.io.StringBufferInputStream(line), "UTF-8"))
							.readLine();

					long n3 = System.currentTimeMillis();

					String res = rhandler.request(lines);

					long n4 = System.currentTimeMillis();
					System.out.println("response cost" + (n4 - n3) / 1000 + "ms");

					out.write((res + "\r\n").getBytes());

				}
			}

			out.flush();

			socket.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
		double n5 = System.currentTimeMillis();

		System.out.println("all cost" + (n5 - n1) / 1000 + "ms");

	}

}
