package com.pier.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public abstract class Factory {
	
	public PostFactory set(Type cookie, String url, String user, String pwd, String additional){
		return null;
	}
	
	public CookieFactory set(Type cookie, HttpGet request){
		return null;
	}
	
	public CookieFactory set(Type cookie, HttpPost request) {
		return null;
	}
	
	public HttpGet getGet(){
		return null;
	}
	
	public HttpPost getPost(){
		return null;
	}
}
