/*
 * AdListener.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad;

/**
 * <p>
 * This interface defines the events that are triggered during an ad request.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see AdManager
 * @see Ad
 */
public interface AdListener {
	/**
	 * Ad received.
	 * @param ad Ad.
	 */
	public void onReceived(Ad ad);
	
	/**
	 * Error by retrieving the ad.
	 * @param exception Error.
	 */
	public void onFailedAd(Throwable exception);
}
