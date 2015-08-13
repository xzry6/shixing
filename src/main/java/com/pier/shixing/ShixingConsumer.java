package com.pier.shixing;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.pier.shixing.util.ShixingConfig;
import com.pier.shixing.util.ShixingList;
import com.pier.shixing.util.ShixingUtil;
import com.pier.util.JsonUtils;
import com.pier.util.RequestBuilder;
import com.pier.util.SqlConnector;
import com.pier.util.SqlServer254;
import com.pier.util.Type;

public class ShixingConsumer implements Runnable{
	
	private volatile BlockingQueue<String> queue;
	
	public ShixingConsumer(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		while(true) {
			if(!queue.isEmpty()) {
				String url = queue.poll();
				try {
					login(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else try {
				TimeUnit.SECONDS.sleep(ShixingConfig.consumer_sleep_second);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	private void login(String url) throws Exception {
		
		System.out.println(ShixingUtil.getTime()+"start to grab information from "+url);
		
		HttpGet get = new RequestBuilder("http://shixin.court.gov.cn/personMore.do",null,null,null).buildGet(Type.Shixing, url);
		get.setConfig(ShixingUtil.getRequestConfig(ShixingConfig.consumer_timeout_millisecond));
		CloseableHttpClient httpClient = HttpClients.custom()
																		.setDefaultRequestConfig(ShixingUtil.getRequestConfig(ShixingConfig.consumer_timeout_millisecond))
																		.build();
    CloseableHttpResponse json = httpClient.execute(get);
    HttpEntity newEntity = json.getEntity();
	  String webtext = EntityUtils.toString(newEntity,"UTF-8");
	  
	  connect(webtext.replaceAll("\\\\n", ""));
	  System.out.println(ShixingUtil.getTime()+"job done");
	}
	
	
	private synchronized void connect(String webtext){
		
		try {
			//parse json
			ShixingList sx = JsonUtils.json2bean(webtext,ShixingList.class);
			String[] arr = sx.info2array();
			//connect to sql server
			SqlConnector sc = new SqlConnector(SqlServer254.getInstance());
			String[] columns = {"SHIXIN_ID","NAME","CASE_CODE","AGE","GENDER","IDENTITY_CARD","COURT","PROVINCE","PARTY_TYPE_NAME","GIST_ID","REG_DATE","UNIT","DUTY","PERFORMANCE","DISRUPT_TYPE","PUBLISH_DATE"};
			Map <String,String> map = new HashMap<String,String>();
			map.put("SHIXIN_ID", arr[0]);
			if(!sc.checkExist("BLACKLIST", map, "")) {
				sc.replaceInstance("BLACKLIST", columns, arr);
				System.out.println("success insertion");
			}
			else 
				System.out.println("instance already exists!");
			sc.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			ShixingUtil.writefile(webtext, "parse failed");
		}
	}
}
