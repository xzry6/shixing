package com.pier.util;

import org.apache.log4j.Logger;

public class BaseCallback implements Callback {

	protected Logger logger = Logger.getLogger(getClass());

	@Override
	public void success() {
		
	}

	@Override
	public void error() {
		
	}
}
