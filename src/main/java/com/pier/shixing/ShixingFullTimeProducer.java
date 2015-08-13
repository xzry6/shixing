package com.pier.shixing;

import java.util.concurrent.BlockingQueue;

import com.pier.shixing.util.ShixingProducer;
import com.pier.util.Type;


public class ShixingFullTimeProducer extends ShixingProducer implements Runnable{	
	
	//private static int page = ShixingConfig.crawler_begin_page;

	protected BlockingQueue<Integer> pagequeue;
	
	public ShixingFullTimeProducer(BlockingQueue<Integer> pagequeue, BlockingQueue<String> queue) {
		super(queue);
		this.pagequeue = pagequeue;
	}

	
	@Override
	public void run() {
		int page = pagequeue.poll();
		while(bool){
			if(manage(Type.Shixing,"http://shixin.court.gov.cn/personMore.do",Integer.toString(page)))
				page = pagequeue.poll();
		} 
	}


	@Override
	protected boolean checkid(String id) {
		return false;
	}
	
}
