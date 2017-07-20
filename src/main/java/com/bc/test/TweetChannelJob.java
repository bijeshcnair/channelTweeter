package com.bc.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TweetChannelJob implements Job {
	
	public static void main(String[] args) {
		String currentTime = "22:30:00";
		String currentTimeHour = currentTime.substring(3,5);
		System.out.println(currentTimeHour);
	}
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub

	}

}
