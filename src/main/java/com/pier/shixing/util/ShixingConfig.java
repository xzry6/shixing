package com.pier.shixing.util;

import com.pier.util.ConfigUtil;

public class ShixingConfig {
	
	public final static String db_driver = ConfigUtil.getString("db.produce.driverClassName", null);

	public final static String db_url = ConfigUtil.getString("db.produce.url", null);

	public final static String db_username = ConfigUtil.getString("db.produce.username", null);

	public final static String db_password = ConfigUtil.getString("db.produce.password", null);

	public final static String log_path = ConfigUtil.getString("log.file.path", null);
	
	public final static int crawler_begin_page = Integer.parseInt(ConfigUtil.getString("crawler.begin.page",null));
	
	public final static int crawler_end_page = Integer.parseInt(ConfigUtil.getString("crawler.end.page",null));

	public final static int crawler_gap_second = Integer.parseInt(ConfigUtil.getString("crawler.gap.second",null));

	public final static int crawler_sleep_second = Integer.parseInt(ConfigUtil.getString("crawler.sleep.second",null));

	public final static int crawler_timeout_millisecond = Integer.parseInt(ConfigUtil.getString("crawler.timeout.millisecond",null));

	public final static String crawler_begin_time = ConfigUtil.getString("crawler.begin.time", null);
	
	public final static int consumer_sleep_second = Integer.parseInt(ConfigUtil.getString("consumer.sleep.second",null));

	public final static int consumer_timeout_millisecond = Integer.parseInt(ConfigUtil.getString("consumer.timeout.millisecond",null));

	public final static long daily_gap_time = 24*60*60*1000;
	
	public final static long hour_gap_time = 60*60*1000;
	
	public final static long fivemin_gap_time = 5*60*1000;
	
	public final static long minute_gap_time = 60*1000;
}
