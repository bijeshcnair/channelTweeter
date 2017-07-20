package com.bc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.bc.lbsdn.util.MongoDbUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ChannelCalls {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws TwitterException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException, TwitterException {
		// TODO Auto-generated method stub
		String channel = "hbo";
		String date  = "22012014";
		String url = "http://indian-television-guide.appspot.com/indian_television_guide?channel="+channel+"&date="+date;
		System.out.println(url);
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Host", "indian-television-guide.appspot.com");
		conn.setRequestProperty("Referer", "http://indian-television-guide.appspot.com/");

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder data = new StringBuilder();
		String s = "";
		System.out.println("reading");
		while((s = br.readLine()) != null)
			data.append(s);
		String pageData = data.toString();
		System.err.println("page data");
		System.out.println(data);
		System.out.println("printed");

		//String pageData = new Scanner(new File("E:\\paas\\openshift\\openshift-checkins\\src\\json.js")).useDelimiter("\\Z").next();
		System.out.println(pageData);

		Object obj = JSONValue.parse(pageData);
		JSONObject jArray = (JSONObject)obj;
		//String date = jArray.get("date").toString();
		System.out.println("array:"+jArray.toJSONString());
		JSONArray jsonArray = (JSONArray)jArray.get("listOfShows");

		System.out.println((jsonArray.size()));

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		//Date date = new Date();

		Date roundedDate =null;// DateUtils.round(date,Calendar.HOUR);
//		String currentTime = dateFormat.format(roundedDate);

		//System.out.println(currentTime);
		Iterator<JSONObject> it  = jsonArray.iterator();
		List<DBObject> programsList = new ArrayList<DBObject>();
		MongoDbUtil.initMongoLab();
		DB mongoLabDb = MongoDbUtil.getMongoLabDB();
		DBCollection channelsCollection = mongoLabDb.getCollection("Channels");
		channelsCollection.drop();
		int count = 0;
		while(it.hasNext()){
			//System.out.println(it.next().get("showTime"));
			JSONObject showObject = it.next();
			String showTitle = showObject.get("showTitle").toString();
			String showTime = showObject.get("showTime").toString();
			String showHour = showTime.substring(0,2);
			String showMinute = showTime.substring(3,5);
			
			
			JSONObject showDetails = (JSONObject)showObject.get("showDetails");
			JSONObject showTypeObj = (JSONObject) showDetails.get("Show Type");
			
			String 	showType = 	(showDetails.get("Show Type:")!=null)?(showDetails.get("Show Type:").toString()):" ";
			String director = showDetails.get("Directed By")!=null ? showDetails.get("Directed By").toString():" ";
			String imdbRating = showDetails.get("IMDB Rating")!=null ? showDetails.get("IMDB Rating").toString():"0";
			
			String imdbRatings[] = imdbRating.split("/");
			float rating = Float.parseFloat(imdbRatings[0]);
			
			DBObject program = new BasicDBObject();
			program.put("date",date);
			program.put("channel", channel);
			program.put("showtitle", showTitle);
			program.put("IMDB Rating", rating);
			program.put("ShowType",showType);
			program.put("Hour", showHour);
			program.put("Minute", showMinute);
			program.put("Director", director);
		//	DBObject fullProgram = (DBObject)JSON.parse(showObject.toJSONString());
			programsList.add(program);

			System.out.println("Added full program to the list"+program);
			
			
			/*if(it.next().get("showTime").equals(currentTime)){
				System.out.println("Got the show ");
				String showTitle = it.next().get("showTitle").toString();
				System.out.println("Show title:"+showTitle);
				JSONObject showDetails = (JSONObject)it.next().get("showDetails");
				String showType = showDetails.get("Show Type:").toString();
				System.out.println("Show type:"+showType);


				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true).setOAuthConsumerKey("GTUlSLmwwJF7agDrY7GGuQ").setOAuthConsumerSecret("0In56BzhM1MtFIK0eZikkA6K9nsZsL3P2LvxngoTvrI")
				.setOAuthAccessToken("2219461088-jiabyv7wV6vNDNB7lbbH17WWKaoiGXgOSbopPfz").setOAuthAccessTokenSecret("02o4fXTzsMKQpnYyBHR57emMQXroibmD4oSXCIabsPRg7");

				TwitterFactory tFactory = new TwitterFactory(cb.build());
				Twitter twitter = tFactory.getInstance();
				System.out.println(twitter.verifyCredentials().getId());

				System.out.println(twitter.updateStatus("Coming up in Star Sports- "+showTitle+" Type:"+showType+" at :"+currentTime)); 

			}*/
		}
		
		Mongo conn2 = new Mongo("localhost",27017);
		DB mDB = conn2.getDB("test");
		DBCollection programsCollection = mDB.getCollection("programs");
		System.out.println("DD"+mongoLabDb.getName());
		//channelsCollection.insert(programsList);
		System.out.println("added all programs");
		//channelsCollection.insert(programsList);
		System.out.println(mongoLabDb.getLastError());
		
		/*
		for(JSONObject jObject:jsonArray.){
			if(jObject.get("showTime").equals(currentTime))
			{
				System.out.println("Next show found at :"+currentTime);

			}
		}
		 */



	}

}
