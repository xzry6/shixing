package com.pier.shixing.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.pier.util.RequestBuilder;
import com.pier.util.Type;

public abstract class ShixingProducer{

	protected int count = 0;
	
	protected boolean bool = true;
		  
	protected BlockingQueue<String> queue;
	
	protected CloseableHttpClient httpClient;
	
	public ShixingProducer(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}
	
	
	public boolean manage(Type t, String url, String page) {
		boolean bool = true;
		try {
			parse(login(t,url,page));
			System.out.println(ShixingUtil.getTime()+"finish crawling on page "+page);
		} catch(Exception e) {
			//e.printStackTrace();
			bool = false;
			System.out.println(ShixingUtil.getTime()+"crawling "+page+" failed");
			count++;
		}
		finally {
			try {
				if(count%3==0) {
					System.out.println("begin to sleep "+ShixingConfig.crawler_sleep_second+" seconds");
					TimeUnit.SECONDS.sleep(ShixingConfig.crawler_sleep_second);
					if(count%5==0) {
						ShixingUtil.writefile(page,"failed crawling on page");
						bool = true;
					}
				}
				TimeUnit.SECONDS.sleep(ShixingConfig.crawler_gap_second);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return bool;
	}
	
	
	protected String login(Type t, String url, String page) throws Exception {
		HttpPost post = new RequestBuilder(url,null,null,page).buildPost(t);
		post.setConfig(ShixingUtil.getRequestConfig(ShixingConfig.crawler_timeout_millisecond));
		System.out.println(ShixingUtil.getTime()+"begin to crawl on page "+page);
		httpClient = HttpClients.custom()
										.setDefaultRequestConfig(ShixingUtil.getRequestConfig(ShixingConfig.crawler_timeout_millisecond))
										.build(); 
		CloseableHttpResponse redirect = httpClient.execute(post);
		
		HttpEntity myEntity = redirect.getEntity();
	  String fulltext = EntityUtils.toString(myEntity,"UTF-8"); 
	  
	  return fulltext;
	}
	
	
	protected void parse(String fulltext) throws Exception {
		Document doc = Jsoup.parse(fulltext);
		//System.out.println(doc.html());
	  Elements elements = doc.select("tbody").first().select("tr");
		for(int i=1; i<elements.size(); ++i) {
			String id = elements.get(i).getElementsByAttribute("id").first().attr("id");
		  String jsonurl = "http://shixin.court.gov.cn/detail?id="+id;
		  if(checkid(id)) {
			  bool = false;
			  return;
		  	}
		  queue.put(jsonurl);
		  System.out.println("put one json into queue. queue size is now "+queue.size());
		 }
	}
	
	
	abstract protected boolean checkid(String id) throws Exception;
}
