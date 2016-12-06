package com.waspring.wasservice.net.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Test {

	
	public static void main(String fdf[]) throws Exception{
		 Gson gson = new Gson();
		 LoginReqMessage s=new LoginReqMessage();
		 s.HEAD.setCHKSUM("1");
		 s.MESSAGE.USER_NO="YYY";
		 String json=gson.toJson(s);
		 System.out.println(json);
 
         LoginReqMessage jsonBean = gson.fromJson(json, LoginReqMessage.class);
         
         System.out.println
         (jsonBean.HEAD.getCHKSUM());
         
    	 LoginRepMessage p=new LoginRepMessage();
    	 p.CLIENTNO="123";
    	 json=gson.toJson(p);
    	 System.out.println(json);
     
    	 LoginRepMessage bean = gson.fromJson(json, LoginRepMessage.class);
         
       
         
     	JsonParser parser = new JsonParser();
     	JsonElement root = parser.parse(json);
     	JsonObject rootobj = root.getAsJsonObject();
		JsonElement action = rootobj.get("CLIENTNO");
		String  type = gson.fromJson(action, String.class);	
	 System.out.println(type);
         
	}
}
