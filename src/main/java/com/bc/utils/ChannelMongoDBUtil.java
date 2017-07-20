package com.bc.utils;

import com.bc.lbsdn.util.MongoDbUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ChannelMongoDBUtil {

	public static DBObject getChannelDate(String channelName,String date,String startingHour,String startingMin,
											String endHour,String endMin){
		
		MongoDbUtil.initMongoLab();
		DB dbObject = MongoDbUtil.getMongoLabDB();
		DBCollection dbCollection = dbObject.getCollection("Channels");
		
		DBObject queryObject = new BasicDBObject("channel",channelName).append("date", date).append("Hour", new BasicDBObject("$gte",startingHour))
				.append("Minute", new BasicDBObject("$gte",startingMin))
				.append("Hour", new BasicDBObject("$lte",endHour))
				.append("Minute", new BasicDBObject("$lte",endMin));
		
		DBCursor cursor = dbCollection.find(queryObject);
		DBObject responseDBObject = null;
		while(cursor.hasNext()){
			responseDBObject = cursor.next();
			System.out.println("Response:"+responseDBObject);
		}
		
		
		
		return responseDBObject;
		
	}
	
}
