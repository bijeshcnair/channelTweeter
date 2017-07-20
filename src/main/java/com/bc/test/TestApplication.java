package com.bc.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore;

import com.bc.utils.ChannelMongoDBUtil;
import com.bc.utils.DateUtil;
import com.mongodb.DBObject;

public class TestApplication {



	public static void main(String[] args) {
		/*TestMongoDbUtil.init();
			DB db = TestMongoDbUtil.getDB();
			DBCollection dCollection  = db.getCollection("BloodGroupService");


			DBObject myDoc = dCollection.findOne();
			System.out.println(myDoc);*/


		/*	DateFormat formatter= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
			String dhateverDateYouWant = formatter.format(new Date());

			Date dd = new Date(dhateverDateYouWant);
			System.out.println(dd);

		 */

/*		DateFormat formatter1= new SimpleDateFormat("MM/dd/yyyy HH:mm:SS");

		formatter1.setTimeZone(TimeZone.getTimeZone("Asia/Qatar"));
		System.out.println("Formated date"+formatter1.format(new Date()));
		
		String qatarDate =  formatter1.format(new Date());
		System.out.println("QD:"+qatarDate);
		
		SimpleDateFormat sdF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		try {
			Date _qatarDate = sdF.parse(qatarDate);
			System.out.println("qatar date:"+_qatarDate);
			Date whateverDateYouWant = _qatarDate;//sdF.parse(formatter1.format(new Date()));
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(whateverDateYouWant);

			int unroundedMinutes = calendar.get(Calendar.MINUTE);
			int mod = unroundedMinutes % 30;
			System.out.println("u"+mod);
			calendar.add(Calendar.MINUTE, mod < 8 ? +mod : (30-mod));
			calendar.set(Calendar.SECOND, 0);
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			System.out.println(dateFormat.format(calendar.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		Date d;
		
		HashMap<String, String> test = new HashMap<String, String>();
		test.put("a", "one");
		test.put("a", "two");
		
		System.out.println(test.get("a"));
		/*try {
				//d = DateFormat.getInstance().parse(formatter1.format(new Date()));
				//System.out.println(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 */
		System.out.println(updateHour("23:33"));
		String date = "10022014";
		String channel = "hbo";
		String startTime = "11:35";
		String endTime = DateUtil.updateTime30Minutes(startTime);
		
		String startTimes[] = startTime.split(":");
		String startTimeHour = startTimes[0];
		String StartTimeMinute = startTimes[1];

		System.out.println("start time:"+startTime);
		System.out.println("End time:"+endTime);
		
		String endTimes[] = endTime.split(":");
		String endTimeHour = endTimes[0];
		String endTimeMinute = endTimes[1];
		DBObject responseObject = ChannelMongoDBUtil.getChannelDate(channel, date, startTimeHour, StartTimeMinute, endTimeHour, endTimeMinute);
		System.out.println(responseObject);

	}
	public static String updateHour(String time){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = null;
		try {
			d = df.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 30);
		return df.format(cal.getTime());

	}
}
