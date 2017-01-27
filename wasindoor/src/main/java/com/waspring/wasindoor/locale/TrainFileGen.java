package com.waspring.wasindoor.locale;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aiyc.server.standalone.util.Configuration;
import com.waspring.wasdbtools.DaoUtil;

/**
 * 产生训练文件
 * 
 * @author felly
 * 
 */
public class TrainFileGen implements ITrainGen {

	private int startPos, endPos;

	public int getEndPos() {

		return endPos;
	}

	public int getStartPos() {
		return startPos;
	}

	public static final String TRAIN_FILE_NAME = "train";
	public static final String MAP_FILE_NAME = "map";
	public static final int MIN_TMNL_NUM = 20;// //训练点最少多少个采集点

	public void destory() throws Exception {
		if (trainFile != null) {
			trainFile = null;
		}
		if (trianPath != null) {
			trianPath = null;
		}
		buildNo = -1;
		floorNo = -1;
		startPos = -1;
		endPos = -1;

	}

	private int locationNum = -1;

	public int getBuildFloorLocation() throws SQLException {
		if (locationNum == -1) {
			// //确保每个采集点的指纹点都大于20
			String sql = "select  count(1) num  from   "
					+ " x_tmnlpos where     x_tmnlpos.floor_no=? and "
					+ "  x_tmnlpos. build_no=?   ";

			ResultSet rs = DaoUtil.queryData(sql, new Object[] { floorNoString,
					buildNoString });

			if (rs.next()) {
				locationNum = rs.getInt("num");
			}

		}

		return locationNum;
	}

	public void gen() throws Exception {
		if (!initable)
			throw new Exception("必须初始化！");

		// //确保每个采集点的指纹点都大于20
		String sql = "select zAngle,xMagnetic,yMagnetic,zMagnetic, x_fingerprint.pos_id,`tmnl_num` "
				+ " tmnl_num   from  x_fingerprint,"
				+ " x_tmnlpos where x_fingerprint.pos_id= x_tmnlpos.pos_id "
				+ " and  x_tmnlpos.tmnl_num>=? and  x_tmnlpos.floor_no=? and "
				+ "  x_tmnlpos. build_no=?   ";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { MIN_TMNL_NUM,
				floorNoString, buildNoString });
		StringBuilder train = new StringBuilder(1024);

		while (rs.next()) {

			String posId = rs.getString("pos_id");
			train.append(rs.getString("zAngle") + "\t"
					+ rs.getString("xMagnetic") + "\t"
					+ rs.getString("yMagnetic") + "\t"
					+ rs.getString("zMagnetic") + "\t" + posId + "\r\n"

			);

		}
		StringBuilder map = new StringBuilder(1024);

		sql = "select   x_tmnlpos.pos_id,x_tmnlpos.x,x_tmnlpos.y  from   "
				+ " x_tmnlpos where   x_tmnlpos.tmnl_num>=? and  x_tmnlpos.floor_no=? and "
				+ "  x_tmnlpos. build_no=?   ";

		rs = DaoUtil.queryData(sql, new Object[] { MIN_TMNL_NUM, floorNoString,
				buildNoString });
		while (rs.next()) {
			String posId = rs.getString("pos_id");
			map.append(posId + "\t" + rs.getString("x") + "\t"
					+ rs.getString("y") + "\r\n");
		}

		FileWriter writer = null;
		try {
			writer = new FileWriter(trainFile);// /生成train
			writer.write(train.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			train = null;

			if (writer != null) {
				writer.close();
			}
			writer = null;
		}

		writer = null;
		try {
			writer = new FileWriter(mapFile);// 生成map
			writer.write(map.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			map = null;
			if (writer != null) {
				writer.close();
			}
			writer = null;
		}
	}

	private boolean initable = false;

	public void init() throws Exception {
		loadDirFromDB();
		String path = getTrainFile();
		makeDirs();
		if (trianPath == null) {
			trianPath = new File(path);
			if (!trianPath.exists()) {
				trianPath.mkdir();
			}
		}

		if (trainFile == null) {
			trainFile = new File(path + File.separator + TRAIN_FILE_NAME
					+ ".txt");
			if (!trainFile.exists()) {
				trainFile.createNewFile();
			}
		}

		if (mapFile == null) {
			mapFile = new File(path + File.separator + MAP_FILE_NAME + ".txt");
			if (!mapFile.exists()) {
				mapFile.createNewFile();
			}
		}

		initable = true;
	}

	private File trianPath = null;
	private File trainFile = null;
	private File mapFile = null;
	private String buildNoString, floorNoString;

	private int buildNo = -1, floorNo = -1;

	public TrainFileGen(String buildNo, String floorNo) throws Exception {
		this.buildNoString = buildNo;
		this.floorNoString = floorNo;
	}

	public String getTrainFile() {
		return Configuration.indoor_location + File.separator + buildNo
				+ File.separator + floorNo + File.separator;
	}

	/**
	 * 目录制定
	 */
	private void makeDirs() {
		File parent = new File(Configuration.indoor_location + File.separator
				+ buildNo);
		if (!parent.exists()) {
			parent.mkdir();
		}
	}

	/**
	 * 目录产生
	 * 
	 * @param fs
	 * @throws Exception
	 */

	public void loadDirFromDB() throws Exception {
		String sql = "select min(pos_id) start_pos,max(pos_id) end_pos from "
				+ "	x_tmnlpos  where x_tmnlpos.floor_no=? and x_tmnlpos.build_no=? ";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { floorNoString,
				buildNoString });
		if (rs.next()) {
			startPos = rs.getInt("start_pos");
			endPos = rs.getInt("end_pos");
		}
		sql = "select id from `d_building` where   `BUILDING_NO`=?  ";

		rs = DaoUtil.queryData(sql, new Object[] { buildNoString });
		if (rs.next()) {
			buildNo = rs.getInt("id");
		}

		sql = "select id from `d_floor` where   `FLOOR_NO`=?  and BUILD_NO=?  ";

		rs = DaoUtil.queryData(sql,
				new Object[] { floorNoString, buildNoString });
		if (rs.next()) {
			floorNo = rs.getInt("id");
		}
		if (buildNo == -1 || floorNo == -1) {
			throw new Exception("传入楼宇编号或楼层编号不存在！");
		}

	}

	public static void main(String fs[]) throws Exception {

		TrainFileGen s = new TrainFileGen("xx", "#3");

		s.init();
		s.gen();
		System.out.println(s.getTrainFile());

	}

	public int getBuildNo() {
		return buildNo;
	}

	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}

	public int getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
}
