package com.waspring.wasservice.net.dao.area;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aiyc.framework.utils.StringUtils;
import com.waspring.wasdbtools.DaoUtil;
import com.waspring.wasservice.net.model.area.SetAreaReqMessage;
import com.waspring.wasservice.net.model.area.SetBuildReqMessage;
import com.waspring.wasservice.net.model.area.SetDoorReqMessage;
import com.waspring.wasservice.net.model.area.SetFloorReqMessage;
import com.waspring.wasservice.net.model.area.SetGraphReqMessage;

public class GraphDao {
	/**
	 * ͨ���ͼ��ѯ�������
	 */

	public ResultSet queryArea(String mapNo) throws Exception {
		String sql = " select      `ID`, " + "`FLOOR_NO` , " + "`BUILD_NO` , "
				+ "`AREA_NO`, " + "`IMG_URL`, " + "`DOORS` , "
				+ "`MAP_NO`    from d_floor where map_no=?";
		return DaoUtil.queryData(sql, new Object[] { mapNo });
	}

	/**
	 * ����ͼ��
	 */

	public void saveGraph(SetGraphReqMessage model) throws Exception {
		String sql = "insert into  map (   mapId  , mapName   ,mapURL  ,"
				+ "mapx0,mapy0,mapx1,mapy1,centerx,centery,maplevel,minlevel,maxlevel )"
				+ "" + "values(" + DaoUtil.makePix(12) + ")";
		String[] bounds = model.MESSAGE.MAP_BOUNDS.split(",");
		String[] ceters = model.MESSAGE.MAP_CENTER.split(",");
		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.MAP_NO,
				model.MESSAGE.MAP_NAME, model.MESSAGE.MAP_URL, bounds[0],
				bounds[1], bounds[2], bounds[3], ceters[0], ceters[1],
				model.MESSAGE.MAP_LEVEL, model.MESSAGE.MIN_LEVEL,
				model.MESSAGE.MAX_LEVEL });
	}

	/**
	 * ��ѯseller
	 */

	public boolean isSeller(String areaNo, String buildNo, String floorNo,
			String doorNo) throws Exception {
		String sql = "select 1 from s_seller r where  `AREA_NO`=? ";
		List list = new ArrayList();
		list.add(areaNo);

		if (!StringUtils.isNullOrBank(buildNo)) {
			sql += " and  `BUILD_NO`=? ";

			list.add(buildNo);
		}

		if (!StringUtils.isNullOrBank(floorNo)) {
			sql += " and  `FLOOR_NO`=? ";

			list.add(floorNo);
		}

		if (!StringUtils.isNullOrBank(doorNo)) {
			sql += " and  `DOOR_NO`=? ";

			list.add(doorNo);
		}
		return DaoUtil.queryData(sql, list.toArray()).next();

	}

	/**
	 * ��ѯ��ͼ�Ƿ���ʹ��
	 */

	public boolean ismapUse(String mapNo) throws Exception {
		String sql = "select 1 from d_floor r where r.map_no=? ";

		return DaoUtil.queryData(sql, new Object[] { mapNo }).next();
	}

	/**
	 * ɾ��ͼ��
	 */

	public void delGraph(String mapNo) throws Exception {
		String sql = "delete from  map where  mapid=?   ";
		DaoUtil.executeUpdate(sql, new Object[] { mapNo });
	}

	/**
	 * ɾ������
	 */

	public void delArea(String areaNo, boolean sub) throws Exception {
		String sql = "delete from  d_area where  AREA_NO=?   ";

		DaoUtil.executeUpdate(sql, new Object[] { areaNo });

		if (sub) {
			sql = "delete from  d_building  where  AREA_NO=? ";

			DaoUtil.executeUpdate(sql, new Object[] { areaNo });

			sql = "delete from  d_floor  where  AREA_NO=? ";

			DaoUtil.executeUpdate(sql, new Object[] { areaNo });

			sql = "delete from  d_door  where  AREA_NO=? ";

			DaoUtil.executeUpdate(sql, new Object[] { areaNo });
		}
	}

	/**
	 * ɾ��¥��
	 */

	public void delBuild(String areaNo, String buildNo, boolean sub)
			throws Exception {

		String sql = "delete from  d_building  where AREA_NO=?  and   BUILDING_NO=? ";

		DaoUtil.executeUpdate(sql, new Object[] { areaNo, buildNo });

		if (sub) {
			sql = "delete from  d_floor  where  AREA_NO=?  and  BUILDING_NO=? ";

			DaoUtil.executeUpdate(sql, new Object[] { areaNo, buildNo });

			sql = "delete from  d_door  where  AREA_NO=?  and  BUILDING_NO=? ";

			DaoUtil.executeUpdate(sql, new Object[] { areaNo, buildNo });
		}
	}

	/**
	 * ɾ��¥��
	 */

	public void delFloor(String areaNo, String buildNo, String floorNo,
			boolean sub) throws Exception {

		String sql = "delete from  d_floor  where  AREA_NO=?  and   BUILDING_NO=? and  FLOOR_NO=? ";

		DaoUtil.executeUpdate(sql, new Object[] { areaNo, buildNo, floorNo });
		if (sub) {
			sql = "delete from  d_door  where  AREA_NO=?  and   BUILDING_NO=? and FLOOR_NO=? ";

			DaoUtil
					.executeUpdate(sql,
							new Object[] { areaNo, buildNo, floorNo });
		}
	}

	/**
	 * ɾ���
	 */

	public void delDoor(String areaNo, String buildNo, String floorNo,
			String doorNo) throws Exception {

		String sql = "delete from  d_door  where  AREA_NO=?  and   BUILDING_NO=? and  FLOOR_NO=? and   doorNo=? ";

		DaoUtil.executeUpdate(sql, new Object[] { areaNo, buildNo, floorNo,
				doorNo });

		sql = "delete from   d_bussipos where shopno=? and   BUILDING_NO=? and AREA_NO=? "
				+ "   and floor_no=?   ";

		DaoUtil.executeUpdate(sql, new Object[] { doorNo, buildNo, areaNo,
				floorNo });
	}

	/**
	 * ����������Ϣ
	 */
	public void saveAreaInfo(SetAreaReqMessage model) throws Exception {
		String sql = " insert into d_area(   `AREA_NO` , `AREA_NAME` ,"
				+ "`AREA_ADDR` , `BUILD_NUM` , `AREA_TYPE` ,"
				+ "`P_AREA_NO` , `IMG_URL`) values(" + DaoUtil.makePix(7) + ")";

		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.AREA_NO,
				model.MESSAGE.AREA_NAME, model.MESSAGE.AREA_ADDR,
				model.MESSAGE.BUILD_NUM, model.MESSAGE.AREA_TYPE,
				model.MESSAGE.P_AREA_NO, model.MESSAGE.IMG_URL,

		});
	}

	/**
	 * ����¥��
	 */

	public void saveBuildInfo(SetBuildReqMessage model) throws Exception {
		String sql = " insert into `d_building`( `BUILDING_NO` ,"
				+ "`AREA_NO` ," + "`BUILDING_NAME` ," + "`BUILDING_DESC` ,"
				+ "`FLOORS` ," + "`FLOOR_PIX` ," + "`JD` ," + " `WD` ,"
				+ "`IMG_URL`) values(" + DaoUtil.makePix(9) + ")";

		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.BUILD_NO,
				model.MESSAGE.AREA_NO, model.MESSAGE.BUILDING_NAME,
				model.MESSAGE.BUILDING_DESC, model.MESSAGE.FLOORS,
				model.MESSAGE.FLOOR_PIX, model.MESSAGE.JD, model.MESSAGE.WD,
				model.MESSAGE.IMG_URL

		});
	}

	/**
	 * ����¥��
	 */

	public void saveFloorInfo(SetFloorReqMessage model) throws Exception {
		String sql = " insert into `d_floor`(  `FLOOR_NO` , " + "`BUILD_NO` , "
				+ "`AREA_NO`, " + "`IMG_URL`, " + "`DOORS` , "
				+ "`MAP_NO` ) values(" + DaoUtil.makePix(6) + ")";

		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.FLOOR_NO,

		model.MESSAGE.BUILD_NO, model.MESSAGE.AREA_NO, model.MESSAGE.IMG_URL,
				model.MESSAGE.DOORS, model.MESSAGE.MAP_NO });
	}

	/**
	 * ���淿��
	 */
	public void saveDoorInfo(SetDoorReqMessage model) throws Exception {
		String sql = " insert into `d_door`(  `DOOR_NO` , " + "`FLOOR_NO` , "
				+ "`BUILD_NO` , " + "`AREA_NO`  ) values(" + DaoUtil.makePix(4)
				+ ")";

		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.DOOR_NO,

		model.MESSAGE.FLOOR_NO, model.MESSAGE.BUILD_NO, model.MESSAGE.AREA_NO,

		});

		String mapNo = "";
		ResultSet rs = this.getFloorInfo(model.MESSAGE.AREA_NO,
				model.MESSAGE.BUILD_NO, model.MESSAGE.FLOOR_NO);
		if (rs.next()) {
			mapNo = rs.getString("MAP_NO");
		}
		sql = "\n" + "insert into d_bussipos\n" + "  (OBJECTID,\n"
				+ "   CODE,\n" + "   NAME,\n" + "   HOTLINE,\n"
				+ "   SHOPNO,\n" + "   SHOPENGLIS,\n" + "   SHOPLOGO,\n"
				+ "   BUILDNO,\n" + "   MAPNO,\n" + "   BUSINESSID,\n"
				+ "   floor_no,\n" + "   area_no)\n" + "  select ?,\n"
				+ "         categories,\n" + "         name,\n"
				+ "         telephone,\n" + "         ?,\n" + "         ?,\n"
				+ "         photo_url,\n" + "         ?,\n" + "         ?,\n"
				+ "         business_id,\n" + "         ?,\n" + "         ?\n"
				+ "    from s_businesses_detail\n"
				+ "   where s_businesses_detail. business_id = ?";
		List list = new ArrayList();
		list.add(model.MESSAGE.OBJECTID);
		list.add(model.MESSAGE.DOOR_NO);
		list.add(model.MESSAGE.SHOPENGLIS);
		list.add(model.MESSAGE.BUILD_NO);
		list.add(mapNo);
		list.add(model.MESSAGE.FLOOR_NO);
		list.add(model.MESSAGE.AREA_NO);
		list.add(model.MESSAGE.BUSINESSID);

		DaoUtil.executeUpdate(sql, list.toArray());
	}

	/**
	 * 
	 * @param mapNo
	 * @return
	 * @throws Exception
	 */
	public ResultSet getGraph(String mapNo) throws Exception {
		String sql = "select   mapId MAP_NO, mapName MAP_NAME ,mapURL MAP_URL,"
				+ "mapx0,mapy0,mapx1,mapy1,centerx,centery,maplevel,minlevel,maxlevel  from map where mapId=?";

		return DaoUtil.queryData(sql, new Object[] { mapNo });
	}

	/**
	 * 
	 * @param mapNo
	 * @return
	 * @throws Exception
	 */
	public ResultSet getGraphForBean(String mapNo) throws Exception {
		String sql = "select   mapId MAP_NO, mapName MAP_NAME ,mapURL MAP_URL,"
				+ "mapx0,mapy0,mapx1,mapy1,centerx,centery,maplevel,minlevel,maxlevel  from map where mapId=?";

		return DaoUtil.queryData(sql, new Object[] { mapNo });
	}

	/**
	 * �����ѯ
	 */
	public ResultSet getAreaInfo(String areaNo) throws Exception {
		String sql = "select    `ID` ,  `AREA_NO` , `AREA_NAME` ,"
				+ "`AREA_ADDR` , `BUILD_NUM` , `AREA_TYPE` ,"
				+ "`P_AREA_NO` , `IMG_URL`  from d_area where `AREA_NO`=?";

		return DaoUtil.queryData(sql, new Object[] { areaNo });

	}

	/**
	 * �õ�ȫ�����ظ��Ľ���
	 */

	public ResultSet getAllBuilding() throws Exception {
		String sql = "select `AREA_NO`,  `BUILDING_NO` BUILD_NO  ,  `JD` , `WD`   from d_building";

		return DaoUtil.queryData(sql, new Object[] {});
	}

	/**
	 * ¥���ѯ
	 * 
	 * @param areaNo
	 * @param buildNo
	 * @return
	 * @throws Exception
	 */
	public ResultSet getBuildInfo(String areaNo, String buildNo)
			throws Exception {
		String sql = "select `ID` , " + "`BUILDING_NO` BUILD_NO  ,"
				+ "`AREA_NO` ," + "`BUILDING_NAME` ," + "`BUILDING_DESC` ,"
				+ "`FLOORS` ," + "`FLOOR_PIX` ," + "`JD` ," + " `WD` ,"
				+ "`IMG_URL`  from d_building where   AREA_NO=?       ";
		List list = new ArrayList();
		list.add(areaNo);
		if (!StringUtils.isNullOrBank(buildNo)) {
			sql += " and BUILDING_NO=? ";
			list.add(buildNo);
		}
		return DaoUtil.queryData(sql, list.toArray());

	}

	/**
	 * ¥���ѯ
	 */

	public ResultSet getFloorInfo(String areaNo, String buildNo, String floorNo)
			throws Exception {
		String sql = " select      `ID`, "
				+ "`FLOOR_NO` , "
				+ "`BUILD_NO` , "
				+ "`AREA_NO`, "
				+ "`IMG_URL`, "
				+ "`DOORS` , "
				+ "`MAP_NO`    from d_floor  where `AREA_NO`=? and d_floor.BUILD_NO=?  ";

		List list = new ArrayList();
		list.add(areaNo);
		list.add(buildNo);
		if (!StringUtils.isNullOrBank(floorNo)) {
			sql += " and   d_floor.FLOOR_NO=? ";
			list.add(floorNo);
		}
		return DaoUtil.queryData(sql, list.toArray());

	}

	/**
	 * 5.1.5.4.1 ������Ϣ��ȡ����
	 */

	public ResultSet getDoorInfo(String areaNo, String buildNo, String floorNo,
			String doorNo) throws Exception {
		String sql = "select a. ID,\n" + "       a. DOOR_NO,\n"
				+ "       a. FLOOR_NO,\n" + "       a. BUILD_NO,\n"
				+ "       a. AREA_NO,\n" + "       b. MAPNO,\n" + "\n"
				+ "       b.BUSINESSID,\n" + "       b.SHOPLOGO,\n"
				+ "       b.SHOPENGLIS,\n" + "       b.HOTLINE,\n"
				+ "       b.name       BUSSI_NAME,\n"
				+ "       b.code       BUSSI_SORT,\n" + "       b.OBJECTID,\n"
				+ "       b.X,\n" + "       b.Y\n" + "\n"
				+ "  from d_door a, d_bussipos b\n"
				+ " where a.door_no = b.SHOPNO\n"
				+ "   and a.BUILD_NO = b.BUILD_NO\n" + "\n"
				+ "   and a.AREA_NO = ?\n" + "   and a.BUILD_NO = ?\n"
				+ "   and a.FLOOR_NO = ? and a.AREA_NO=b.AREA_NO "
				+ "  and   a.FLOOR_NO=b.FLOOR_NO  ";

		List list = new ArrayList();
		list.add(areaNo);
		list.add(buildNo);
		list.add(floorNo);
		if (!StringUtils.isNullOrBank(doorNo)) {
			sql += " and   a.DOOR_NO=?  ";
			list.add(doorNo);
		}
		return DaoUtil.queryData(sql, list.toArray());

	}

}
