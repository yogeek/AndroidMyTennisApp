package com.yogidev.android.mytennisapp.exceptions;

public class MandatoryParameterException extends Exception {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryParameterException() {
	}

	public MandatoryParameterException(String detailMessage) {
		super(detailMessage);
	}

	public MandatoryParameterException(Throwable throwable) {
		super(throwable);
	}

	public MandatoryParameterException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
