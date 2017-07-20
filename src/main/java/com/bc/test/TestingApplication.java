package com.bc.test;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bc.channels.main.ChannelJob;
import com.bc.lbsdn.util.MongoDbUtil;

public class TestingApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoDbUtil.initMongoLab();

		JobDetail job = new JobDetail();
		job.setName("dummyJobName");
		job.setJobClass(ChannelJob.class);

		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("dummyTriggerName");
		trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(1800000);
		System.out.println("Triggering Channel job");



		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
