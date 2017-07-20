package com.bc.test;

import java.net.UnknownHostException;

import com.bc.utils.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TestMongoDbUtil {
	private static Mongo conn = null;
	private static final String mongoDBserver  = "127.12.127.130";
	private static final int mongoDbPort = 27017;
	private static DB db;


	public static void init(){
		/*try{
			conn = new Mongo("mongodb://bijeshcnair:intruder@ds039487.mongolab.com:39487/checkinservice");
			db = conn.getDB("simplecheckinservice");
			//db.setReadPreference(ReadPreference.SECONDARY);

			if (db.authenticate("bijeshcnair", "intruder".toCharArray())) {
				throw new MongoException("unable to authenticate");
			}
			BasicDBObject geoIndex = new BasicDBObject("location", "2d");

		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (MongoException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}*/
		System.out.println(DateUtil.getIndianDate());
	}
	
	public static  DB getDB(){
		return db;
	}
}
