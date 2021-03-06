package com.pier.shixing.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.config.RequestConfig;

import com.pier.util.MailBean;
import com.pier.util.MailUtil;


public class ShixingUtil {
	
	public static String output = ShixingConfig.log_path;
	
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static RequestConfig getRequestConfig(int mill) {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				  .setSocketTimeout(mill)
				  .setConnectTimeout(mill)
				  .setConnectionRequestTimeout(mill)
				  .setStaleConnectionCheckEnabled(true)
				  .build();
		return defaultRequestConfig;
	}
	
	public static synchronized void writefile(String info, String attention) {
		try {
			FileWriter file = new FileWriter(output,true);
			BufferedWriter out = new BufferedWriter(file);
			out.write(attention+": "+info+"\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " "
					+ time);
			return curDate.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void print(String id, String info) {
		System.out.println(ShixingUtil.getTime()+id+", "+info);
	}
	
	public static void email(String subject, String msg) {
		MailBean bean = new MailBean(subject, msg);
		MailUtil.sendMail(bean);
	}
	
	public static String getTime() {
		return "["+df.format(new Date())+"]: ";
	}
}
