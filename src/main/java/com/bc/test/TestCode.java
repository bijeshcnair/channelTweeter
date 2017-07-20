
package com.bc.test;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bc.channels.main.ChannelJob;


public class TestCode {

	public static void main(String[] args) {
		/*	
		Date d  = new Date();
		System.out.println(d.getTime());
		Date roundedDate = DateUtils.round(d,Calendar.HOUR);

		//

		Calendar c =  Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE,1);
		System.out.println(c.getTime());

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

		String _date = dateFormat.format(c.getTime());
		System.out.println("Updating new date:"+_date);*/

		
		JobDetail job = new JobDetail();
    	job.setName("dummyJobName");
    	job.setJobClass(ChannelJob.class);

		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("dummyTriggerName");
		trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(1800000);
		

    	
    	
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
