package com.waspring.wasindoor.locale;

import java.sql.SQLException;

public interface ITrainGen {
	void init() throws Exception;

	void gen() throws Exception;

	void destory() throws Exception;

	String getTrainFile();

	int getBuildNo();

	int getFloorNo();

	int getBuildFloorLocation() throws SQLException;
	public  void loadDirFromDB() throws Exception;
	
	int getStartPos();
	int getEndPos();
}
