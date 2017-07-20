package com.bc.channels.main;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

public class ChannelJob implements Job {

	public static boolean isStarted = false;

	public static List<ChannelBase> channelList = new ArrayList<ChannelBase>();
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		//Execute the channel job
		try{
			if(!isStarted){
				System.out.println("SySTEM INIT");
				fillList();
				isStarted  =true;

			}

			String currentTime = getCurrentTime();
			String currentTimeHour = currentTime.substring(0,2);
			String currentTimeMinute = currentTime.substring(3,5);
			for(ChannelBase channel :channelList){
				//Checks whether the channel is having a show at current time . 

				Map<String,JSONObject> channelDataMap = channel.channelData;
				Iterator iteratr = channelDataMap.entrySet().iterator();
				while(iteratr.hasNext()){
					System.out.println("                 ");
					System.out.println("------------------------------------------------------");
					System.out.println("                       ");
					Map.Entry mapEntry = (Map.Entry) iteratr.next();
					String channelName = (String)mapEntry.getKey();
					channelName = (channel.twitterHandle.get(channelName) == null)?
							channelName:(channel.twitterHandle.get(channelName));

					JSONObject jArray = (JSONObject)mapEntry.getValue();
					//System.out.println("array:"+jArray.toJSONString());
					JSONArray listOfShows = (JSONArray)jArray.get("listOfShows");

					System.out.println(currentTime);
					Iterator<JSONObject> itr  = listOfShows.iterator();
					while(itr.hasNext()){
						JSONObject channelData = itr.next();
						JSONObject showDetails = (JSONObject)channelData.get("showDetails");
						String imgURL = (String)channelData.get("showThumb");
						String showType = null;
						String showTime = (String)channelData.get("showTime");
						String showHour = showTime.substring(0,2);
						//Need to get show running this hour and next hour as the tool tweets programs in next 30 mins range.  	

						String currentTimeNextHour = updateHour(currentTimeHour);

						if(showHour.equals(currentTimeHour) || showHour.equals(currentTimeNextHour)){

							System.out.println("Show time:"+showTime);
							System.out.println("Current time hour:"+currentTime);
							System.out.println("Current time next hour:"+currentTimeNextHour);

							System.out.println("WE HAVE A SHOW NOW with url"+imgURL);
							if(showDetails.get("Show Type:") != null)
								showType = showDetails.get("Show Type:").toString();	
							if(showTime.equals(currentTime) && ((channel.programType).equals(showType)
									||channel instanceof HollywoodMovies)){
								tweetStatus(channelData, showDetails, showTime, channelName, channel, imgURL);

							}else{
								String newTIme = currentTime;
								System.out.println("Show time : "+showTime);
								System.out.println("No show for :"
										+channelName +" at "+newTIme+
										" checking for the programs coming up in next half hour");

								for(int i=0;i<29;i++){

									newTIme = updateMinute(newTIme);
									if(showTime.equals(newTIme) &&  ((channel.programType).equals(showType)
											||channel instanceof HollywoodMovies)){
										System.out.println("There is a program for channel . New time:"+newTIme);
										tweetStatus(channelData, showDetails, newTIme, channelName, channel, null);
										break;
									}
								}
								System.out.println("No program for channel "+channelName+"@ this time  Something went wrong");
							}
						}
					}
					System.out.println("Completed processing :"+channelName);

				}
				int currentTimeHourinInt = Integer.parseInt(currentTimeHour);
				int currentTimeMininInt = Integer.parseInt(currentTimeMinute);
				if(currentTimeHourinInt >= 23 && currentTimeMininInt >= 30){
					System.out.println("Its time to update chanel data");
					Calendar c =  Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.DATE,1);
					c.getTime();
					/*try{
						channel.updateData(c.getTime());
					}
					catch(RuntimeException rte) {
						System.out.println("RUN TIME EXCEPTION while adding channed data to DB"+rte.toString());
					}
					catch (Exception e){
						System.out.println("Exception while adding data to DB"+e.toString());
					}*/
					//System.out.println("Channel data updted at :"+ c.getTime());		
					/*try {

						channel.twitterInstance.updateStatus("Good Night... ");
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
*/
				}/*if(currentTime.equals("09:00:00")){
					try {
						channel.twitterInstance.updateStatus("New day new programs. Keep following");
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}*/


			}
			System.out.println("All channels processed");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something WRONG ..."+e.toString());		}

	}
	private void tweetStatus(JSONObject channelData,JSONObject showDetails,String currentTime,String channelName,ChannelBase channel, String imgURL){


		//if(channelData.get("showTime").equals(currentTime) && (channel.programType).equals(showType)){

		String img = channelData.get("showThumb").toString();
		System.out.println("Tweeting a show now "+img);
		String showTitle = " ";
		if(channelData.get("showTitle") != null)
			showTitle = channelData.get("showTitle").toString();

		System.out.println("Show title:"+showTitle);
		String showDescription = (String)((String)showDetails.get("Show Description")!=null ?showDetails.get("Show Description"):"");
		//JSONObject showDetails = (JSONObject)channelData.get("showDetails");
		System.out.println("Show description :"+showDescription);
		//System.out.println("Show type:"+showType);

		try {
			String status = "Next on "+channelName.toUpperCase()+" '"+showTitle+"' at:"+currentTime.substring(0, currentTime.length()-3)+" IST";
			System.out.println("show details "+showDetails);

			if(showDetails.get("Genre:")!= null)
				status = status+".Genre:"+(String)showDetails.get("Genre:");

			if(showDetails.get("IMDB Rating")!= null)
				status = status+". IMDB Rating-"+(String)showDetails.get("IMDB Rating");	

			if(showDetails.get("Genre")!= null)
				status = status+".Genre:"+(String)showDetails.get("Genre");
			/*if(showDetails.get("Cast")!= null)
				status = status+" staring "+ showDetails.get("Cast");
			 */
			System.out.println(1);
			int moreStatus = 140 - status.length();

			String cast = null;
			if(showDetails.get("Actor")!=null){

				cast = showDetails.get("Actor").toString();
			}

			/*if(cast!=null && cast.length() >moreStatus)
				cast = cast.substring(0, moreStatus-2);
			else*/
		
			//
			if(cast!=null)
			status = status+".Cast:"+cast;
		
			if(status.length() >= 118){
				status = status.substring(0, 117);
			}
			System.out.println(5);
			System.out.println(" 	"+status);
			//channel.twitterInstance.updateStatus("In "+channelName.toUpperCase()+" '"+showTitle+"' - "+showType+" at: "+currentTime.substring(0, currentTime.length()-3));
			//channel.twitterInstance.updateStatus(status);
			StatusUpdate sUpdate = new StatusUpdate(status);
			if(img!=null){
				InputStream input = new URL(img).openStream();
				sUpdate.setMedia("Thumb",input);
				System.out.println("Uploading image :"+img);

			}
			channel.twitterInstance.updateStatus(sUpdate);

			//System.out.println("Coming up in "+channelName.toUpperCase()+" '"+showTitle+"' - "+showType+" at: "+currentTime.substring(0, currentTime.length()-2));

		} catch (Exception e) {
			System.out.println("Twitter Exception , failed to update twitter status"+e.getStackTrace().toString());
			e.printStackTrace();
		}
		//}
	}
	public static String getCurrentTime(){

		/*
		String time = null;
		Date indianDate = DateUtil.getIndianDate();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(indianDate);

		//int unroundedMinutes = calendar.get(Calendar.MINUTE);
		//int mod = unroundedMinutes % 30;
		//System.out.println("u"+mod);
		//calendar.add(Calendar.MINUTE, mod < 8 ? +mod : (30-mod));
		calendar.set(Calendar.SECOND, 0);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		time =  dateFormat.format(calendar.getTime());
		 */
		//	DateFormat timeFormatter= new SimpleDateFormat("HH:mm:SS");

		TimeZone tz = TimeZone.getTimeZone("GMT+5:30");
		Calendar c = Calendar.getInstance(tz);

		String curTime = String.format("%02d:%02d",  c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))+":00";
		System.out.println("Current Indian Time: "+curTime);
		return curTime;


	}
	public static void fillList(){

		//channelList.add(new SportsChannelMain());
		channelList.add(new MalayalamMovies());
		channelList.add(new HollywoodMovies());
		channelList.add(new TamilMovies());
		channelList.add(new TeluguMovies());
	}

	public static String updateMinute(String minute){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date d = null;
		try {
			d = df.parse(minute);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 1);
		return df.format(cal.getTime());

	}
	public String updateHour(String time){
		SimpleDateFormat df = new SimpleDateFormat("HH");
		Date d = null;
		try {
			d = df.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR, 1);
		return df.format(cal.getTime());

	}
	public static void main(String[] args) {
		//System.out.println(getCurrentTime());
		/*TimeZone timeZone1 = TimeZone.getTimeZone("America/Godthab");
		Calendar calendar = new GregorianCalendar();

		long timeCPH = calendar.getTimeInMillis();
		System.out.println("timeCPH  = " + timeCPH);
		System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));

		calendar.setTimeZone(timeZone1);


		long timeLA = calendar.getTimeInMillis();
		System.out.println(new Date(timeLA));
		System.out.println("timeLA   = " + timeLA);
		System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));*/
		System.out.println(getCurrentTime());
		System.out.println(updateMinute(getCurrentTime()));
		/*
		String time = null;
		Date indianDate = DateUtil.getIndianDate();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(indianDate);

		//int unroundedMinutes = calendar.get(Calendar.MINUTE);
		//int mod = unroundedMinutes % 30;
		//System.out.println("u"+mod);
		//calendar.add(Calendar.MINUTE, mod < 8 ? +mod : (30-mod));
		calendar.set(Calendar.SECOND, 0);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		time =  dateFormat.format(calendar.getTime());

		System.out.println("TIme:"+time);
		TimeZone tz = java.util.TimeZone.getTimeZone("GMT+5:30");
		Calendar c = java.util.Calendar.getInstance(tz);
		String indianTime = c.get(java.util.Calendar.HOUR_OF_DAY)+":"+c.get(java.util.Calendar.MINUTE);
		System.out.println(indianTime);
		System.out.println(updateMinute(indianTime));
		 */	}
}
