package com.bc.lbsdn.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.bc.lbsdn.util.LbsdnConstants;
import com.bc.lbsdn.util.MongoDbUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BloodGroup extends ServiceHandler {

	public BloodGroup(String type) {
		super(type);
		BasicDBObject geoIndex = new BasicDBObject("location", "2d");
		dbCollection.ensureIndex(geoIndex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleRequest(HttpServletRequest requset,
			HttpServletResponse response) {

		String methodType = requset.getParameter(LbsdnConstants.METHOD_TYPE);
		if(LbsdnConstants.METHOD_TYPE_SETDATA.equals(methodType)){
			//Writing blood group information . 
			String mobileNumber = requset.getParameter(LbsdnConstants.MOBILE_NUMBER);
			String name  = requset.getParameter(LbsdnConstants.NAME);
			String bloodGroup = requset.getParameter(LbsdnConstants.BLOOD_GROUP);
			String place = requset.getParameter(LbsdnConstants.PLACE);
			String state = requset.getParameter(LbsdnConstants.State);
			String country = requset.getParameter(LbsdnConstants.Country);

			double longitude = Double.valueOf(requset.getParameter(LbsdnConstants.LONGITUDE)).doubleValue();
			double latitude = Double.valueOf(requset.getParameter(LbsdnConstants.LATITUDE)).doubleValue();

			BasicDBObject bDBObject = new BasicDBObject();
			bDBObject.put(LbsdnConstants.MOBILE_NUMBER,mobileNumber);
			bDBObject.put(LbsdnConstants.NAME, name);
			bDBObject.put(LbsdnConstants.BLOOD_GROUP,bloodGroup);
			bDBObject.put(LbsdnConstants.PLACE,place);
			bDBObject.put(LbsdnConstants.State,state);
			bDBObject.put(LbsdnConstants.Country,country);

			bDBObject.put("location", new double[] {longitude,latitude});
			dbCollection.insert(bDBObject);
			try {
				response.getWriter().write("Blood group added for :"+name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (LbsdnConstants.METHOD_TYPE_GETDATA.equals(methodType)){
			//Getting the blood group
			double longitude = Double.valueOf(requset.getParameter(LbsdnConstants.LONGITUDE)).doubleValue();
			double latitude = Double.valueOf(requset.getParameter(LbsdnConstants.LATITUDE)).doubleValue();
			String bloodGroup = requset.getParameter(LbsdnConstants.BLOOD_GROUP);
			String distance = requset.getParameter(LbsdnConstants.Distance);
			BasicDBObject queryObject = new BasicDBObject("location",new BasicDBObject("$near", new Double[]{longitude,latitude}));
			if(bloodGroup!= null){
				queryObject.append(LbsdnConstants.BLOOD_GROUP, bloodGroup);
			}			response.setContentType("plain/text");
			PrintWriter out = null;

			if(distance != null && "true".equals(distance)){
			/*try{
				BasicDBObject myCmd = new BasicDBObject();
				myCmd.append("geoNear", "BloodGroupService");
				double[] loc = {longitude,latitude};
				myCmd.append("near", loc);
				myCmd.append("spherical", true);
				//myCmd.append("maxDistance", (double)2500/6378137);
				myCmd.append("distanceMultiplier", 10000);
				System.out.println(myCmd);
				CommandResult myResult = MongoDbUtil.getDB().command(myCmd);
				
				out.write(myResult.toString());
			}catch (NullPointerException e) {
				// TODO: handle exception
				System.out.println("NPE"+ExceptionUtils.getRootCauseStackTrace(e).toString());
			}*/
			}
			else{		
				DBCursor dCursor = dbCollection.find(queryObject,new BasicDBObject("_id", 0));



				try {
					out = response.getWriter();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}




				JSONArray list = new JSONArray();

				while(dCursor.hasNext()){


					list.add(dCursor.next());
				}

				out.write(list.toJSONString());
				out.close();

			}
		}
	}
	public static void main(String[] args) {
		MongoDbUtil.initMongoLab();
		read(76.99,11.04);
		
		
	}
	static void read(double longitude,double latitude){
		BloodGroup bloodGroup = new BloodGroup("BloodGroupService");
	
		BasicDBObject queryObject = new BasicDBObject("location",new BasicDBObject("$geoNear", new Double[]{longitude,latitude}));
		queryObject.append("spherical", true);
		queryObject.append("maxDistance", (double)2500/6378137);
		queryObject.append("distanceMultiplier", 6378137);
		DBCursor cursor = bloodGroup.dbCollection.find(queryObject,new BasicDBObject("_id", 0));
		System.out.println("Response");
		while(cursor.hasNext()){
			System.out.println("data");  
			DBObject object = cursor.next();
			System.out.println(object.get("Name")+" - "+object);
		}

		
	}
	static void write(){
		BloodGroup bloodGroup = new BloodGroup("BloodGroupService");

		BasicDBObject bDBObject = new BasicDBObject();
		bDBObject.put(LbsdnConstants.MOBILE_NUMBER,"9043704964");
		bDBObject.put(LbsdnConstants.NAME, "testUser");
	

		bDBObject.put("location", new double[] {12.22,33.33});
		bloodGroup.dbCollection.insert(bDBObject);
		System.out.println(bDBObject.get("_id"));
		
	}
}
