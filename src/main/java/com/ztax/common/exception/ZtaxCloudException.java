package com.ztax.common.exception;

public class ZtaxCloudException extends RuntimeException {

	private static final long serialVersionUID = -7285211528095468156L;

	public ZtaxCloudException() {
	}

	public ZtaxCloudException(String msg) {
		super(msg);
	}

}