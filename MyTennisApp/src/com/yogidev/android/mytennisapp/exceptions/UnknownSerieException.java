package com.yogidev.android.mytennisapp.exceptions;

public class UnknownSerieException extends Exception {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	public UnknownSerieException() {
	}

	public UnknownSerieException(String detailMessage) {
		super(detailMessage);
	}

	public UnknownSerieException(Throwable throwable) {
		super(throwable);
	}

	public UnknownSerieException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
