package com.pier.shixing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.pier.shixing.util.ShixingProducer;
import com.pier.util.SqlConnector;
import com.pier.util.SqlServer254;
import com.pier.util.Type;

public class ShixingDailyProducer extends ShixingProducer implements Runnable{
		
	public ShixingDailyProducer(BlockingQueue<String> queue) {
		super(queue);
	}

	
	@Override
	public void run() {
		int page = 1;
		while(bool){
			if(manage(Type.Shixing,"http://shixin.court.gov.cn/personMore.do",Integer.toString(page)))
				page++;
		} 
		bool = true;
	}


	@Override
	protected boolean checkid(String id) throws Exception{
		SqlConnector sc = new SqlConnector(SqlServer254.getInstance());
		Map <String,String> map = new HashMap<String,String>();
		map.put("SHIXIN_ID", id);
		if(sc.checkExist("BLACKLIST", map, "")) {
			System.out.println("daily instance already existed, daily job finished");
			return true;
		}
		return false;
	}
}
