package com.ws.icloud.common.exception.controller;


import com.ws.icloud.common.exception.BaseException;

/**
 * <p>
 * Title: ControllerException
 * </p>
 */

public class ControllerException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1412104290896291466L;

	public ControllerException(String msg) {
		super(msg);
	}

	public ControllerException(Exception e) {
		this(e.getMessage());
	}

}
