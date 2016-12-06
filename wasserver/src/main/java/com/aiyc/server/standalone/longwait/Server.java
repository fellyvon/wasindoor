package com.aiyc.server.standalone.longwait;

import java.net.Socket;
import java.util.List;

import com.aiyc.server.standalone.exp.NotifyException;

public interface Server {
	void start();// /启动

	void destory();// /停止

	boolean isStart();

	List<Client> getClients();

 

	void addSokect(Client socket);

	void removeSokect(Client socket);

	void removeSokect(String ip, int port);

	void removeSokect(int id);

	Client getSokect(int id);

	Client getSokect(String ip, int port);
	Client getSokect(String userNo);
 

	void sendMsg(Socket socket, byte[] b);

	void sendAll(byte b[]);
 
	
	 int getPort();
	 
	 void addNotify(Notifyer er);////���������Ŀ
	 
	 void startNotify()  throws NotifyException;////��ʼ����

}
