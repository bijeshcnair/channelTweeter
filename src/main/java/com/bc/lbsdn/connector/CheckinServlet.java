package com.bc.lbsdn.connector;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bc.channels.main.ChannelJob;
import com.bc.lbsdn.services.BloodGroup;
import com.bc.lbsdn.services.ServiceHandler;
import com.bc.lbsdn.util.LbsdnConstants;
import com.bc.lbsdn.util.Methods;
import com.bc.lbsdn.util.MongoDbUtil;

public class CheckinServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;



	@Override
	public void init() throws ServletException {
		//MongoDbUtil.init();
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


	@Override
	@SuppressWarnings("static-access")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {  

		try{

			ServiceHandler sHandler = null;
			String methodName = request.getParameter(LbsdnConstants.METHOD_NAME);
			Methods eMethod = Methods.valueOf(methodName);
			switch (eMethod) {
			case bloodgroupservice:
				sHandler = new BloodGroup("BloodGroupService");
				break;

			default:
				response.getWriter().write("Please provide a valid method name");
				break;
			}
			sHandler.handleRequest(request, response);
		}catch (Exception e) {
			response.getWriter().write("Exception:"+e);
		}

	}

	@Override
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		/*		double x = 0.0;
		double y = 0.0;

		String comment = null;

		try {
			x = Double.valueOf(request.getParameter("x")).doubleValue();
			y = Double.valueOf(request.getParameter("y")).doubleValue();
			comment = request.getParameter("comment");
			if (comment == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			response.sendError(response.SC_BAD_REQUEST,
					"missing arguments (double x, double y, string comment)");
		}

		BasicDBObject doc = new BasicDBObject();
		doc.put("comment", comment);
		doc.put("location", new double[] { x, y });
		checkins.insert(doc);

		response.setStatus(response.SC_OK);
	}*/
	}

}