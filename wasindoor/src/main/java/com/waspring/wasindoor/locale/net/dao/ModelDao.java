package com.waspring.wasindoor.locale.net.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasindoor.locale.net.model.CreateGenModelReqMessage;

public class ModelDao {

	public void clearModel(String buildNo, String floorNo, String posNo,
			double x, double y) throws Exception {
		List list = new ArrayList();
		String sql = "delete from  `x_fingerprint` where pos_id in"
				+ " (select pos_id from `x_tmnlpos` " + "  where  build_no=? "
				+ "" + "";

		list.add(buildNo);
		if (StringUtils.nullToEmpty(posNo).equals("")) {
			if (x != Double.MIN_VALUE && y != Double.MIN_VALUE) {
				posNo = (x + y) + "";
			}
		}

		if (!StringUtils.nullToEmpty(floorNo).equals("")) {
			sql += " and floor_no=? ";
			list.add(floorNo);
		}

		if (!StringUtils.nullToEmpty(posNo).equals("")) {
			sql += " and pos_no=? ";
			list.add(posNo);
		}

		sql += "" + "  )   ";

		DaoUtil.executeQuery(sql, list.toArray());

		list = new ArrayList();
		sql = "delete from `x_tmnlpos` " + "  where  build_no=? ";

		list.add(buildNo);
		if (!StringUtils.nullToEmpty(floorNo).equals("")) {
			sql += " and floor_no=? ";
			list.add(floorNo);
		}
		if (!StringUtils.nullToEmpty(posNo).equals("")) {
			sql += " and pos_no=? ";
			list.add(posNo);
		}
		DaoUtil.executeQuery(sql, list.toArray());

	}

	/**
	 * ��¼ģ�Ϳ�
	 */

	public synchronized void saveGenModel(CreateGenModelReqMessage model)
			throws Exception {
		Iterator<CreateGenModelReqMessage.Pos> it = model.MESSAGE.POS_INFO
				.iterator();

		while (it.hasNext()) {

			CreateGenModelReqMessage.Pos pos = it.next();
			String buildNo = pos.BUILD_NO;
			String floorNo = pos.FLOOR_NO;

			long posId = 0;
			String sql = "select pos_id   from "
					+ "  x_tmnlpos  		   where   build_no=? and floor_no=? and x=?"
					+ " and y=?  ";

			ResultSet rs = DaoUtil.queryData(sql, new Object[] {

			buildNo, floorNo, pos.X, pos.Y });
			boolean havePos = false;
			if (rs.next()) {
				posId = rs.getLong("pos_id");
				havePos = true;
			} else {
				sql = "select max(pos_id) pos_id from " + "  x_tmnlpos ";
				rs = DaoUtil.queryData(sql, new Object[] {});
				if (rs.next()) {
					posId = rs.getLong("pos_id") + 1;
				} else {
					posId = 1;
				}
				havePos = false;
			}

			if (!havePos) {
				sql = "insert into x_tmnlpos\n"
						+ "  (pos_id, x, y, tmnl_num, floor_no, build_no, area_no, map_no, pos_no)\n"
						+ "\n"
						+ "  select ?, ?, ?, 0, ?, ?, area_no, map_no, ?\n"
						+ "    from d_floor f\n" + "   where f.build_no = ?\n"
						+ "     and floor_no = ? limit 1";
				String posNo = pos.POS_NO;
				if (StringUtils.nullToEmpty(posNo).equals("")) {
					posNo = (pos.X + pos.Y) + "";
				}

				List list = new ArrayList();
				list.add(posId);
				list.add(pos.X);
				list.add(pos.Y);
				list.add(floorNo);
				list.add(buildNo);
				list.add(posNo);
				list.add(buildNo);
				list.add(floorNo);
				DaoUtil.executeQuery(sql, list.toArray());
			}

			List<CreateGenModelReqMessage.Geo> geo = pos.GEO_INFO;

			Iterator<CreateGenModelReqMessage.Geo> gt = geo.iterator();
			int index = 0;
			while (gt.hasNext()) {
				CreateGenModelReqMessage.Geo ge = gt.next();
				sql = "insert into x_fingerprint\n" + "  (pos_id,\n"
						+ "   xAngle,\n" + "   yAngle,\n" + "   zAngle,\n"
						+ "   xMagnetic,\n" + "   yMagnetic,\n"
						+ "   zMagnetic,\n" + "   gen_time)\n" + "values\n"
						+ "  (?, ?, ?, ?, ?, ?, ?, now())";

				List list = new ArrayList();
				list.add(posId);
				list.add(ge.xAngle);
				list.add(ge.yAngle);
				list.add(ge.zAngle);
				list.add(ge.xMagnetic);
				list.add(ge.yMagnetic);
				list.add(ge.zMagnetic);
				DaoUtil.executeQuery(sql, list.toArray());
				index++;
			}

			sql = "update x_tmnlpos set tmnl_num=tmnl_num+?  where  pos_id=?  ";

			DaoUtil.executeQuery(sql, new Object[] { index, posId });

		}
	}

	/**
	 * �õ�λ����Ϣ
	 */

	public ResultSet getPosInfo(int posId) throws Exception {
		String sql = "select pos_id, x, y, tmnl_num, floor_no, build_no, area_no, map_no, pos_no\n"
				+ "  from x_tmnlpos\n" + " where pos_id = ?";

		return DaoUtil.queryData(sql, new Object[] { posId });

	}

}
