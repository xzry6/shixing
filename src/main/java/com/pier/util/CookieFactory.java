package com.pier.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class CookieFactory extends Factory{
	private HttpGet get;
	private HttpPost post;
	@Override
	public CookieFactory set(Type cookie, HttpGet request) {
		switch(cookie) {
			case LinkedIn: {
				Parameters.setLinkedIn(request);
				break;
			}
			case BOC: {
				Parameters.setBOC(request);
				break;
			}
			case Shixing: {
				Parameters.setShixing(request);
				break;
			}
		}
		get = request;
		return this;
	}
	public CookieFactory set(Type cookie, HttpPost request) {
		switch(cookie) {
			case LinkedIn: {
				Parameters.setLinkedIn(request);
				break;
			}
			case BOC: {
				Parameters.setBOC(request);
				break;
			}
			case Shixing: {
				Parameters.setShixing(request);
			}
		}
		post = request;
		return this;
	}
	public HttpGet getGet() {
		return get;
	}
	
	public HttpPost getPost() {
		return post;
	}
}
