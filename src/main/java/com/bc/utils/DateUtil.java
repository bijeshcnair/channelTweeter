package com.bc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil extends java.util.Date {
	public DateUtil() {
		// TODO Auto-generated constructor stub
	}
	public static java.util.Date getIndianDate(){

		DateFormat timeFormatter= new SimpleDateFormat("MM/dd/yyyy HH:mm:SS");

		timeFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		System.out.println("Formated date"+timeFormatter.format(new DateUtil()));

		String timeZoneChangedDate =  timeFormatter.format(new DateUtil());
		System.out.println("Indian"+timeZoneChangedDate);

		SimpleDateFormat sdF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

		java.util.Date indianDate = null;
		try {
			indianDate = sdF.parse(timeZoneChangedDate);
		}catch (ParseException e) {
			// TODO: handle exception
		}
		System.out.println("Indian date:"+indianDate);
		return indianDate;
	}
	public static String updateTime30Minutes(String time){

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
		cal.add(Calendar.MINUTE, 180);
		return df.format(cal.getTime());
	}
	
	public static String getCurrentIndianTime(){
		TimeZone tz = TimeZone.getTimeZone("GMT+5:30");
		Calendar c = Calendar.getInstance(tz);

		String curTime = String.format("%02d:%02d",  c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))+":00";
		System.out.println("Current Indian Time: "+curTime);
		return curTime;
	}
}
