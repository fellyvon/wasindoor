package com.waspring.wasindoor.locale.net.bussi;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.monitor.Session;
import com.aiyc.framework.monitor.SessionManager;
import com.aiyc.framework.utils.DateUtil;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.core.Vector;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Configuration;
import com.google.gson.JsonElement;
import com.waspring.wasindoor.locale.GeomagneticEntity;
import com.waspring.wasindoor.locale.ITrainGen;
import com.waspring.wasindoor.locale.IndoorTrainTask;
import com.waspring.wasindoor.locale.TrainFileGen;
import com.waspring.wasindoor.locale.knn.HdbInitial;
import com.waspring.wasindoor.locale.knn.Hdblocator;
import com.waspring.wasindoor.locale.knn.KnnLocator;
import com.waspring.wasindoor.locale.knn.KnnScaler;
import com.waspring.wasindoor.locale.libsvm.SVMLocator;
import com.waspring.wasindoor.locale.net.dao.ModelDao;
import com.waspring.wasindoor.locale.net.model.GeoPosRepMessage;
import com.waspring.wasindoor.locale.net.model.GeoPosReqMessage;

 

@Requestable(serverName = "GEO_GEO_POS_REQ")
public class GeoPosHandler implements IHandler {
	
	
	
	public static void main(String dfs []) throws Exception{
		String   buildNo="cxyt";
		String floorNo="#2";
		ITrainGen gen = new TrainFileGen(buildNo, floorNo);

		gen.loadDirFromDB();
		
		System.out.println(gen.getStartPos()+":"+gen.getEndPos());
	}
	private boolean isKnn = false;
	private String dirFileAddr = Configuration.indoor_location;
	KnnScaler scaler = new KnnScaler();
	KnnLocator singlelocate = new KnnLocator();
	HdbInitial Hdb = new HdbInitial();
	Hdblocator locator = new Hdblocator();
	public GeoPosHandler() {
		isKnn = "knn".equals(Configuration.p.getProperty("calc.type", ""));
	}

	public Response handle(JsonElement data) throws Exception {

		GeoPosReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GeoPosReqMessage.class);

		GeoPosRepMessage rep = new GeoPosRepMessage();

		String buildNo = model.MESSAGE.BUILD_NO;
		String floorNo = model.MESSAGE.FLOOR_NO;

		if (StringUtils.nullToEmpty(buildNo).equals("")
				|| StringUtils.nullToEmpty(floorNo).equals("")) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "定位失败，楼宇编号和楼层编号必须输入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.nullToEmpty(model.MESSAGE.xAngle).equals("")) {
			model.MESSAGE.xAngle = "0";
		}
		if (StringUtils.nullToEmpty(model.MESSAGE.yAngle).equals("")) {
			model.MESSAGE.yAngle = "0";
		}
		if (StringUtils.nullToEmpty(model.MESSAGE.zAngle).equals("")) {
			model.MESSAGE.zAngle = "0";
		}

		if (StringUtils.nullToEmpty(model.MESSAGE.xMagnetic).equals("")) {
			model.MESSAGE.xMagnetic = "0";
		}
		if (StringUtils.nullToEmpty(model.MESSAGE.yMagnetic).equals("")) {
			model.MESSAGE.yMagnetic = "0";
		}
		if (StringUtils.nullToEmpty(model.MESSAGE.zMagnetic).equals("")) {
			model.MESSAGE.zMagnetic = "0";
		}

		ITrainGen gen = new TrainFileGen(buildNo, floorNo);

		gen.loadDirFromDB();

		Vector<GeomagneticEntity> gens = new Vector<GeomagneticEntity>();
		int b = gen.getBuildNo();
		int f = gen.getFloorNo();
		GeomagneticEntity entity = new GeomagneticEntity(b, f, Double
				.parseDouble(model.MESSAGE.xAngle), Double
				.parseDouble(model.MESSAGE.yAngle), Double
				.parseDouble(model.MESSAGE.zAngle), Double
				.parseDouble(model.MESSAGE.xMagnetic), Double
				.parseDouble(model.MESSAGE.yMagnetic), Double
				.parseDouble(model.MESSAGE.zMagnetic), DateUtil
				.convertDateToString(new Date()), null);
		gens.add(entity);
		List<Integer> results = new ArrayList<Integer>();

		long end = System.currentTimeMillis();

		try {

			if (isKnn) {// //knn定位

				// step 1 训练数据标准化
				boolean success = scaler.scaleTrainFile(dirFileAddr, b, f, 5);// 首先要有这一个，队训练数据标准化完毕
				if (success) {
					System.out.println("训练标准化成功！");
				} else {
					System.out.println("训练标准化失败！");
					rep.RTN_FLAG = "0";

					rep.RTN_MSG = "build=" + buildNo + ",floor=" + floorNo
							+ "训练标准化失败";
					return new Response(Status.failed, rep.RTN_MSG, rep
							.toJson());
				}				// step 2: 获取出时历史数据
				String flowId = model.HEAD.getFLOWID();
				Session ses = SessionManager.instance().getSession(flowId);
				if (ses.get("GEN_POS") == null) {
					ses.put("GEN_POS", new LinkedList());
				}
				LinkedList srcPoint = (LinkedList) ses.get("GEN_POS");
				int NeiborNumber = 8;
				if (srcPoint.size() < NeiborNumber) {
					int result = singlelocate.locate(entity, dirFileAddr, 5,
							NeiborNumber, gen.getStartPos(), gen.getEndPos());
					srcPoint.addLast(result);
					rep.RTN_FLAG = "0";
					rep.RTN_MSG = "请等待开始定位！";
					return new Response(Status.failed, rep.RTN_MSG, rep
							.toJson());
				}  				LinkedList outputHistoryData = Hdb.InitialVale(dirFileAddr, b,
						f, srcPoint,  gen.getStartPos(), gen.getEndPos());
				// step 3 定位
				int result = locator.hdblocate(entity, dirFileAddr, 5,
						NeiborNumber, gen.getStartPos(), gen.getEndPos(), outputHistoryData);
				results.add(result);
				srcPoint=locator.GetUpdatedHbdata();
				ses.put("GEN_POS",srcPoint);
			} else {// //////svm定位
				SVMLocator loc = new SVMLocator();
				results = loc.locate(gens, Configuration.indoor_location
						+ File.separator, IndoorTrainTask.NUM_OF_FOLDS);
			}

		} catch (IOException e) {
			rep.RTN_FLAG = "0";
			e.printStackTrace();
			rep.RTN_MSG = e.getMessage();
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if (results != null && !results.isEmpty()) {
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "定位成功！";
			int posId = results.get(0).intValue();
			ResultSet rs = (new ModelDao()).getPosInfo(posId);

			if (rs.next()) {
				rep.BUILD_NO = rs.getString("build_no");
				rep.FLOOR_NO = rs.getString("floor_no");
				rep.AREA_NO = rs.getString("area_no");
				rep.MAP_NO = rs.getString("map_no");
				rep.POS_NO = rs.getString("pos_no");
				rep.X = rs.getString("x");
				rep.Y = rs.getString("y");
			} else {
				rep.RTN_FLAG = "0";
				rep.RTN_MSG = "定位失败！定位出来的位置标识号：" + posId + "在模型中不存在！";
				return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
			}

		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "定位结果返回为空！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
