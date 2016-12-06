package com.aiyc.server.standalone.longwait;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * ����
 * 
 * @author felly
 * 
 */
public class SokectCallBack extends Thread {
	private Socket socket;
	private Server server = null;

	private static Log log = LogFactory.getLog(SokectCallBack.class);//  

	public SokectCallBack(Server server, Socket socket) {
		this.socket = socket;
		this.server = server;
		this.setName("SokectCallBack" + socket);
		// server.addSokect(socket);
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			dis = new DataInputStream(new BufferedInputStream(socket
					.getInputStream()));
			dos = new DataOutputStream(socket.getOutputStream());

			while (socket != null && !socket.isClosed()) {
				String inData = dis.readLine();

				Gson gson = GsonFactory.getGsonInstance();
				JsonParser parser = new JsonParser();

	 

				try {

					JsonElement root = parser.parse(inData);

					if (root.isJsonObject()) {
						JsonObject rootobj = root.getAsJsonObject();
						JsonElement action = rootobj
								.get(RequestHandler.ACTION_TOKEN);
						JsonElement data = rootobj
								.get(RequestHandler.DATA_TOKEN);
						String type = gson.fromJson(action, String.class);

						if ("HEART_JUMP".equals(type)) {
							dos.writeBytes(inData);
						} else {
							RequestHandler rhandler = new RequestHandler();
							String res = rhandler.request(inData);
							
							dos.writeBytes(res);
							
							if ("LOGIN_REQ".equals(type)) {
								// ////
//								LoginReqMessage loc = GsonFactory
//										.getGsonInstance().fromJson(data,
//												LoginReqMessage.class);
								Client c = new Client(socket,
									"");

								server.addSokect(c);
								
							}
						}

					} else {

					}

				} catch (Exception ee) {
					ee.printStackTrace();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {

				if (dos != null) {
					dos.close();
				}
				if (dis != null) {
					dis.close();
				}
				if (socket != null) {
					// //写入客户端离�?记录
					AloneServer.instance().removeSokect(
							socket.getInetAddress().getHostAddress(),
							socket.getPort());

					socket.close();
					socket = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			}
		}
	}
}
