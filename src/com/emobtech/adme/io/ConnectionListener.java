/*
 * ConnectionListener.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.io;

/**
 * <p>
 * This interface defines the events that are triggered during a connection.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see HttpManager
 */
public interface ConnectionListener {
	/**
	 * <p>
	 * Connection failed.
	 * </p>
	 * @param url URL.
	 * @param exception Error.
	 */
	public void onFail(String url, Throwable exception);

	/**
	 * <p>
	 * Connection succeeded.
	 * </p>
	 * @param url URL.
	 */
	public void onSuccess(String url);
}
