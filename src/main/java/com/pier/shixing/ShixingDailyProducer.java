package com.pier.shixing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.pier.shixing.util.ShixingProducer;
import com.pier.shixing.util.ShixingUtil;
import com.pier.util.SqlConnector;
import com.pier.util.SqlServer254;
import com.pier.util.Type;

public class ShixingDailyProducer extends ShixingProducer implements Runnable{
	
	private String emailSubject = "Shixin Daily job";
	
	public ShixingDailyProducer(BlockingQueue<String> queue, String id) {
		super(queue,id);
	}
	
	@Override
	public void run() {
		int page = 1;
		int dailycount = 0;
		while(bool){
			if(manage(Type.Shixing,"http://shixin.court.gov.cn/personMore.do",Integer.toString(page))) {
				page++;
				dailycount += 15;
			}
		} 
		String info = "daily job finished, "+dailycount+" instances have been put into queue";
		ShixingUtil.print(id,info);
		ShixingUtil.email(emailSubject,info);
		bool = true;
	}


	@Override
	protected boolean checkid(String id) throws Exception{
		SqlConnector sc = new SqlConnector(SqlServer254.getInstance());
		Map <String,String> map = new HashMap<String,String>();
		map.put("SHIXIN_ID", id);
		if(sc.checkExist("BLACKLIST", map, "")) {
			ShixingUtil.print(this.id,"daily instance "+id+" already existed, daily job finished");
			return true;
		}
		return false;
	}
}
