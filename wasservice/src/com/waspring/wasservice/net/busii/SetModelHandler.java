package com.waspring.wasservice.net.busii;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.svm.MapSVMSupport;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.ModelDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.SetModelReqMessage;
@Requestable(serverName = "SET_MODEL_REQ")
public class SetModelHandler implements IHandler {

	public static void main(String fdsf[]) throws Exception {
		SetModelReqMessage m = new SetModelReqMessage();
		m.HEAD.setCMD("1");
		m.MESSAGE.MAP_NO = "1";
		List<SetModelReqMessage.Message.AP_INFO> ap = new ArrayList<SetModelReqMessage.Message.AP_INFO>();
		SetModelReqMessage.Message.AP_INFO aa = new SetModelReqMessage.Message.AP_INFO();
		aa.AP_MAC = "1";
		aa.AP_NAME = "1";
		aa.AP_VALUE = "1";
		aa.ISINFRASTRUCTURE = "1";
		aa.WEPENABLED = "0";
		ap.add(aa);
		aa = new SetModelReqMessage.Message.AP_INFO();
		aa.AP_MAC = "11";
		aa.AP_NAME = "11";
		aa.AP_VALUE = "11";
		aa.ISINFRASTRUCTURE = "11";
		aa.WEPENABLED = "10";
		ap.add(aa);
		m.MESSAGE.AP_INFO = ap;
		m.MESSAGE.LOCATION.X = "100";
		m.MESSAGE.LOCATION.Y = "2220.0";
		System.out.println(m.toJson());
	}

	private ModelDao dao = new ModelDao();

	public Response handle(JsonElement data) throws Exception {
		CommonRepMessage rm = new CommonRepMessage();

		final SetModelReqMessage model = GsonFactory.getGsonInstance()
				.fromJson(data, SetModelReqMessage.class);
		if (StringUtils.nullToEmpty(model.MESSAGE.MAP_NO).equals("")) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "地图编号为空！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		if (model.MESSAGE.AP_INFO.size() == 0) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "不存在AP信息！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}

		List<SetModelReqMessage.Message.AP_INFO> readings = model.MESSAGE.AP_INFO;
		Iterator<SetModelReqMessage.Message.AP_INFO> it = readings.iterator();

		while (it.hasNext()) {
			SetModelReqMessage.Message.AP_INFO ap = it.next();
			if (StringUtils.nullToEmpty(ap.AP_MAC).equals("")) {
				rm.RTN_FLAG = "0";
				rm.RTN_MSG = "读数中存在AP地址为空！";
				return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
			}

			if (StringUtils.nullToEmpty(ap.AP_VALUE).equals("")) {
				rm.RTN_FLAG = "0";
				rm.RTN_MSG = "读数中存在AP强度为空！";
				return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
			}

		}
		if (StringUtils.nullToEmpty(model.MESSAGE.LOCATION.X).equals("")) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "x坐标为空！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		if (StringUtils.nullToEmpty(model.MESSAGE.LOCATION.Y).equals("")) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "y坐标为空！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		// /地图不存在
		if (!dao.haveMap(model.MESSAGE.MAP_NO)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "地图不存在，无法录入";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}

		// /////////////////////////////////////开始记录
		int result = dao.saveModel(model);
		if (result == 0) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "处理失败";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		// ////// 记录完成//////////////////////////////////
		int count = dao.getCountLocation(model.MESSAGE.MAP_NO,
				model.MESSAGE.LOCATION.X, model.MESSAGE.LOCATION.Y);
		// ///如果数量小于最大模型库数量，开始训练该地图
		if (count < dao.getMaxTrinPointLoc(model.MESSAGE.MAP_NO)) {
			// //开始按地图为单位进行训练
			Log.getLogger().fine(
					"Training model (fp count for loc " + model.MESSAGE.LOCATION.X
							+ ":" + model.MESSAGE.LOCATION.Y + ": " + count);
			Thread trainer = new Thread(new Runnable() {

				public void run() {
					MapSVMSupport.train(model.MESSAGE.MAP_NO);////异步训练
				}
			});
			trainer.start();

		}
		// ///////////////////////////////

		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "操作成功！";
		Response res = new Response(Status.ok, rm.RTN_MSG, rm.toJson());

		return res;
	}

}
