package com.pier.util;

import com.pier.shixing.util.ShixingConfig;

public class SqlServer254 extends BasicSqlServer{

	private static BasicSqlServer bss = new SqlServer254();
	
	private String driver = ShixingConfig.db_driver;
	private String url = ShixingConfig.db_url;
	private String user = ShixingConfig.db_username;
	private String password = ShixingConfig.db_password;
	
	private SqlServer254() {
		
	}
	
	public static BasicSqlServer getInstance() {
		return bss;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
}
