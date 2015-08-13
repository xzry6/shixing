package com.pier.shixing;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class Listener implements ServletContextListener{

	protected static Logger logger = Logger.getLogger(Listener.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("starting up to Shixing full time job");
		ShixingManager.executeShixingJob();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("destroyed JobListener");
	}

}
