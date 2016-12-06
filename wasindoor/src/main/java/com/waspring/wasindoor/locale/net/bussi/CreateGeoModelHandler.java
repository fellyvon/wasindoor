package com.waspring.wasindoor.locale.net.bussi;

import java.util.Iterator;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasindoor.locale.net.dao.ModelDao;
import com.waspring.wasindoor.locale.net.model.CommonRepMessage;
import com.waspring.wasindoor.locale.net.model.CreateGenModelReqMessage;


@Requestable(serverName = "CREATE_GEO_MODEL_REQ")
public class CreateGeoModelHandler implements IHandler {

	public Response handle(JsonElement data) throws Exception {
		CreateGenModelReqMessage model = GsonFactory.getGsonInstance()
				.fromJson(data, CreateGenModelReqMessage.class);
		CommonRepMessage rep = new CommonRepMessage();
		if (model.MESSAGE.POS_INFO.size() == 0) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "必须录入地磁信号！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		Iterator<CreateGenModelReqMessage.Pos> it = model.MESSAGE.POS_INFO
				.iterator();
		int index=1;
		while (it.hasNext()) {

			CreateGenModelReqMessage.Pos pos = it.next();
			String buildNo = pos.BUILD_NO;
			String floorNo = pos.FLOOR_NO;

			 
			double  x=pos.X;
			double y=pos.Y;

		   if(x==Double.MIN_VALUE||   y==Double.MIN_VALUE){
			   rep.RTN_FLAG = "0";
				rep.RTN_MSG = "第"+index+"行数据，坐标必须输入！";
				return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		   }
		   
		  
		   
			
			
			if (StringUtils.nullToEmpty(buildNo).equals("")
					|| StringUtils.nullToEmpty(floorNo).equals("")) {
				rep.RTN_FLAG = "0";
				rep.RTN_MSG = "第"+index+"行数据，楼宇编号和楼层编号必须输入！";
				return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
			}

		 

			if (pos.GEO_INFO.size() == 0) {
				rep.RTN_FLAG = "0";
				rep.RTN_MSG = "第"+index+"行数据，必须录入地磁信号！";
				return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
			}
			
			
			
			index++;
		}
		ModelDao dao = new ModelDao();

		dao.saveGenModel(model);
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "录入成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
