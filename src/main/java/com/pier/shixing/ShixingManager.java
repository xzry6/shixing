package com.pier.shixing;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pier.shixing.util.ShixingConfig;
import com.pier.shixing.util.ShixingUtil;



public class ShixingManager {
	
	private static long ONEDAY = ShixingConfig.daily_gap_time;
	
	private static String TIME_M = ShixingConfig.crawler_begin_time;// daily job time
	
	private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(100000);
	
	private static LinkedBlockingQueue<Integer> pagequeue = new LinkedBlockingQueue<Integer>(150000);

	private static ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(5);
	
			
	public static void executeShixingJob() {
		try {
			for(int i=ShixingConfig.crawler_begin_page; i<=ShixingConfig.crawler_end_page; ++i)
				pagequeue.put(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		long initDelay = ShixingUtil.getTimeMillis(TIME_M) - System.currentTimeMillis();
		initDelay = initDelay > 0 ? initDelay : ONEDAY + initDelay;
		
		executor.scheduleAtFixedRate(new ShixingDailyProducer(queue), initDelay, ONEDAY, TimeUnit.MILLISECONDS);
		executor.execute(new ShixingFullTimeProducer(pagequeue,queue));
		executor.execute(new ShixingFullTimeProducer(pagequeue,queue));
		executor.execute(new ShixingConsumer(queue));
		executor.execute(new ShixingConsumer(queue));
	}
	
	
	
}
