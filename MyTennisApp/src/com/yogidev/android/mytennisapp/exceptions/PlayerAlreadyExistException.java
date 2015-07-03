package com.yogidev.android.mytennisapp.exceptions;

public class PlayerAlreadyExistException extends Exception {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	public PlayerAlreadyExistException() {
	}

	public PlayerAlreadyExistException(String detailMessage) {
		super(detailMessage);
	}

	public PlayerAlreadyExistException(Throwable throwable) {
		super(throwable);
	}

	public PlayerAlreadyExistException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
