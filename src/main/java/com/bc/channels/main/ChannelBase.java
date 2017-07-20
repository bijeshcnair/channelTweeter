package com.bc.channels.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.bc.lbsdn.util.MongoDbUtil;
import com.bc.utils.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ChannelBase {

	protected String appspotUrl = "http://indian-television-guide.appspot.com/indian_television_guide?";

	public String channleDatainJson;
	public List<String> channelList = new ArrayList<String>();
	public Map<String, JSONObject> channelData = new HashMap<String, JSONObject>();
	public Twitter twitterInstance;
	public String channeName;

	public String cosumerKey;
	public String consumerSecret;
	public String oauthAccessToken;
	public String oauthAccessTokenSecret;
	public String programType;
	public HashMap<String, String> twitterHandle = new HashMap<String, String>();
	public ChannelBase() {
		super();

	}
	public void init(){
		twitterInstance = getTwitterInstance(cosumerKey, consumerSecret, oauthAccessToken, oauthAccessTokenSecret);
		updateData(DateUtil.getIndianDate());
		System.out.println("Got twitter instance and updated channel details for"+programType);
	}

	protected Twitter getTwitterInstance(String consumerKey,String consumerSecret,String oauthAccessToken,String oauthAccessTokenSecret){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setUseSSL(true);
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(oauthAccessToken).setOAuthAccessTokenSecret(oauthAccessTokenSecret);

		TwitterFactory tFactory = new TwitterFactory(cb.build());
		return tFactory.getInstance();

	}


	public void updateData(Date newDate){
		try{
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		DB mongoLabDb = MongoDbUtil.getMongoLabDB();
		MongoDbUtil.initMongoLab();
		String _date = dateFormat.format(newDate);
		
		for(String channels:channelList){
			String _appspotUrl = appspotUrl+"channel="+channels+"&date="+_date;
			//System.out.println("updating channel info from :"+_appspotUrl);
			try{
				System.out.println("Getting data for channel"+channels);
				HttpURLConnection conn  = (HttpURLConnection) new URL(_appspotUrl).openConnection();
				conn.setRequestProperty("Host", "indian-television-guide.appspot.com");
				conn.setRequestProperty("Referer", "http://indian-television-guide.appspot.com/");

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder data = new StringBuilder();
				String s = "";

				while((s = br.readLine()) != null)
					data.append(s);
				
				try{
					JSON.parse(data.toString());
				}catch(Exception e)	{
					System.err.println("Not a valid json string from chanel:"+channels);
					continue ;
				}
				//this.channleDatainJson = data.toString();
				Object obj = JSONValue.parse(data.toString());
				System.out.println(data.toString());
				JSONObject jObject = (JSONObject)obj;
				this.channelData.put(channels, jObject);

				System.out.println("Schedules updated for channel: "+channels+" at "+new SimpleDateFormat("dd-MM-yyyy").format(newDate));

				
				//String date = jObject.get("date").toString();
				JSONArray listOfShowsArray = (JSONArray)jObject.get("listOfShows");

				try{
					//System.out.println(currentTime);
					Iterator<JSONObject> it  = listOfShowsArray.iterator();
					List<DBObject> programsList = new ArrayList<DBObject>();


					DBCollection channelsCollection = mongoLabDb.getCollection("Channels");
					//channelsCollection.drop();
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
						if(this instanceof HollywoodMovies){
							String director = showDetails.get("Directed By")!=null ? showDetails.get("Directed By").toString():" ";
							String imdbRating = showDetails.get("IMDB Rating")!=null ? showDetails.get("IMDB Rating").toString():"0";
							String language = showDetails.get("Language:")!=null ? showDetails.get("Language:").toString():" ";
							String imdbRatings[] = imdbRating.split("/");
							float rating = Float.parseFloat(imdbRatings[0]);

							DBObject program = new BasicDBObject();
							program.put("_id", channels+_date+count);
							program.put("date",_date);
							program.put("timeStamp", newDate.getTime());
							program.put("channel", channels);
							program.put("showtitle", showTitle);
							program.put("IMDB Rating", rating);
							program.put("ShowType",showType);
							program.put("Hour", showHour);
							program.put("Minute", showMinute);
							program.put("Director", director);
							program.put("Language", language);
							++count;
							//	DBObject fullProgram = (DBObject)JSON.parse(showObject.toJSONString());
							programsList.add(program);

							//System.out.println("Added full program to the list"+program);
						}else{
							//System.out.println("program not a movie so not adding to the list");
						}
					}
					channelsCollection.insert(programsList); 
					System.out.println("Get last error :"+mongoLabDb.getLastError());

				}catch (Exception e) {
					System.out.println("Exception while addng to DB"+e);
				}


			}catch(IOException e){
				System.err.println("Failed to update channel schedule for :"+channels);

			}finally{
				System.out.println("Closing connection to mongolab");
				//mongoLabDb.getMongo().close();
			}
		}
		System.out.println("Closing connection to mongolab");
		mongoLabDb.getMongo().close();	
		System.out.println("New schedule for program type "+this.programType+" on "+new SimpleDateFormat("dd-MM-yyyy").format(newDate));
		}
		catch(RuntimeException rte){
			System.err.println("Runtime exception while adding data to DB"+rte.toString());
		}
		}

	public void updateStatus(JSONObject channelData){

	}
}
