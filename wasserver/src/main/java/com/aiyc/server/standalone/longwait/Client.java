package com.aiyc.server.standalone.longwait;

import java.net.Socket;

public class Client {

	private Socket socket = null;
	private String id;// 设置id
	private int n = 0;

	public int getN() {
		return n;
	}

	public void clearN() {
		this.n = 0;
	}

	public void addN() {
		this.n = this.n + 1;
	}

	public Client(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
	}

	public boolean equls(Client o) {

		return o.id == this.id
				&& socket.getInetAddress().getHostAddress().equals(
						o.socket.getInetAddress().getHostAddress())
				&& o.getSocket().getPort() == socket.getPort();
	}

	public Socket getSocket() {
		return socket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
