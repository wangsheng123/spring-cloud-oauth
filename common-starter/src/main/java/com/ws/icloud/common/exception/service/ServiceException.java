package com.ws.icloud.common.exception.service;


import com.ws.icloud.common.exception.BaseException;

/**
 * service处理异常
 */
public class ServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2437160791033393978L;

	public ServiceException(String msg) {
	  super(msg);
	}

	public ServiceException(Exception e){
	  this(e.getMessage());
	}
}
