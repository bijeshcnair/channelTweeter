package com.bc.lbsdn.util;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class MongoDbUtil {
	private static Mongo conn = null;
	private static final String mongoDBserver  = "127.12.127.130";
	private static final int mongoDbPort = 27017;
	private static DB db;

	private static final String mongoLabServer = "ds039487.mongolab.com";
	private  static final int mongoLabServerPort = 39487;
	private static DB mongoLabDB;

	public static void initMongoLab(){

		try{


			String textUri = "mongodb://bijeshcnair:intruder@ds039487.mongolab.com:39487/checkinservice";
			MongoURI uri = new MongoURI(textUri);
			Mongo m = new Mongo(uri);
			//Mongo conn1 = new Mongo(mongoLabServer,mongoLabServerPort);
			mongoLabDB = m.getDB("checkinservice");
			
			System.out.println(mongoLabDB.getName());//db.setReadPreference(ReadPreference.SECONDARY);

			if (!mongoLabDB.authenticate("bijeshcnair", "intruder".toCharArray())) {
				throw new MongoException("unable to authenticate");
			}
			BasicDBObject geoIndex = new BasicDBObject("location", "2d");

		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (MongoException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void init(){
		try{
			conn = new Mongo(mongoDBserver,mongoDbPort);
			db = conn.getDB("simplecheckin");
			//db.setReadPreference(ReadPreference.SECONDARY);

			if (db.authenticate("admin", "cfrtleNTIjH3".toCharArray())) {
				throw new MongoException("unable to authenticate");
			}
			BasicDBObject geoIndex = new BasicDBObject("location", "2d");

		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (MongoException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static  DB getDB(){
		return db;
	}
	public static DB getMongoLabDB(){
		return mongoLabDB;
	}
	public static DBObject getChannelFor(String date,String hour,String minute){
		DB db = mongoLabDB;
		DBCollection dbCollection = mongoLabDB.getCollection("Channels");
		
		//DBObject queryObject = new BasicDBObject(
		return null;
		
	}
}
