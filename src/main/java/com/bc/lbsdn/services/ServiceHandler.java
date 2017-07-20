package com.bc.lbsdn.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bc.lbsdn.util.MongoDbUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ServiceHandler {

	protected DBCollection dbCollection;
	protected BasicDBObject fields = new BasicDBObject();

	public ServiceHandler(String type) {
		dbCollection = MongoDbUtil.getMongoLabDB().getCollection(type);
		System.out.println("Got DB collection"+dbCollection.getName());
	
	}
	public void handleRequest(HttpServletRequest requset,HttpServletResponse response){
		
	}
}
