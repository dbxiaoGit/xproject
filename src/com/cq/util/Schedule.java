/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class Schedule {
	Logger log;
	Calendar currentCalendar,targetCalendar;
	public Schedule(int h,int m ,int s, Excuter e) {
		log = Logger.getLogger(Schedule.class);
		currentCalendar = Calendar.getInstance();
		targetCalendar = Calendar.getInstance();
		log.info("currentCalendar = " + currentCalendar.getTime());
		log.info("targetCalendar = " + targetCalendar.getTime());
		log.info("change targetCalendar ");
		targetCalendar.set(Calendar.HOUR_OF_DAY,h);
		targetCalendar.set(Calendar.MINUTE,m);
		targetCalendar.set(Calendar.SECOND,s);
		log.info("currentCalendar = " + currentCalendar.getTime());
		log.info("targetCalendar = " + targetCalendar.getTime());
		if (targetCalendar.before( currentCalendar)) {
			long newTime = targetCalendar.getTimeInMillis() + 24*3600*1000;
			log.info("targetCalendar + 1 day");
			targetCalendar.setTimeInMillis(newTime);
			log.info("targetCalendar change to " + targetCalendar.getTime());
		}
		log.info("create schedule " + targetCalendar.getTime());
		new Timer().schedule(new TimerTask(){
			public void run(){
				e.start();
			}
		},targetCalendar.getTime());
		
	}
}
