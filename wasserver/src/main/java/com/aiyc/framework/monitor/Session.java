package com.aiyc.framework.monitor;

import java.util.Date;

/**
 * 存放数据
 * 
 * @author felly
 * 
 */
public class Session extends java.util.concurrent.ConcurrentHashMap {
	public Session() {
		super();
		reflushTime();
	}

	public Session(int len) {
		super(len);
		reflushTime();
	}

	private int waitOutTime = 3600000;// 1个小时

	private long lastTime = 0l;

	public long getLastTime() {
		return lastTime;
	}

	public void reflushTime() {
		lastTime = (new Date()).getTime();
	}

	public boolean isOutTime() {
		if ((new Date().getTime() - lastTime) > waitOutTime) {
			return true;
		}
		return false;
	}

	private String flowId = "";

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

}
