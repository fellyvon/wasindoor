package com.waspring.wasservice.net.busii.comm;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.comm.AddFriendReqMessage;
/**
 * 
 * @author felly
 *
 */
@Requestable(serverName = "ADD_FREND_REQ")
public class AddFriendHandler implements IHandler {
	private CommDao dao = new CommDao();

	
	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		AddFriendReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, AddFriendReqMessage.class);
		CommonRepMessage rep = new CommonRepMessage();
        String sendNo=model.MESSAGE.SEND_USER_NO;
        String rcvNo=model.MESSAGE.RCVER_NO;
        if(StringUtils.isNullOrBank(sendNo)){
        	rep.RTN_FLAG = "0";
    		rep.RTN_MSG = "发送人不能为空！";
    		return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
        }
        if(StringUtils.isNullOrBank(rcvNo)){
        	rep.RTN_FLAG = "0";
    		rep.RTN_MSG = "不存在这个用户哦";
    		return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
        }
        
        /////判断发送人是否合法用户
        
        if(!dao.haveUser(sendNo)){
        	rep.RTN_FLAG = "0";
    		rep.RTN_MSG = "发送人不合法！";
    		return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
        }
        if(!dao.haveUser(rcvNo)){
        	rep.RTN_FLAG = "0";
    		rep.RTN_MSG = "好友不存在！";
    		return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
        }
        /**
         * 判断是否已经是好友了
         */
        
        if(dao.isFriend(sendNo, rcvNo)){
        	rep.RTN_FLAG = "0";
    		rep.RTN_MSG = "你们已经是好友了！";
    		return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
        }
        
		dao.addFriend(model);
	
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
