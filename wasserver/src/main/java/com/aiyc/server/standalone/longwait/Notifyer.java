package com.aiyc.server.standalone.longwait;

import com.aiyc.server.standalone.exp.NotifyException;

/**
 * ������
 * 
 * @author felly
 * 
 */
public interface Notifyer {

	void send() throws NotifyException;
}
