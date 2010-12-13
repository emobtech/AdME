/*
 * AdManager.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.emobtech.adme.ad.inneractive.InneractiveAdHandler;
import com.emobtech.adme.io.ConnectionListener;
import com.emobtech.adme.io.HttpManager;
import com.emobtech.adme.util.StringUtil;

/**
 * <p>
 * This class is responsible for managing the request between the application
 * and the ad network, in order to retrieve an ad.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see Ad
 * @see AdHandler
 * @see AdListener
 * @see AbstractAdHandler
 * @see InneractiveAdHandler
 */
public final class AdManager implements ConnectionListener {
	/**
	 * <p>
	 * Http connection to access the ad's content.
	 * </p> 
	 */
	private HttpManager adDownloader;
	
	/**
	 * <p>
	 * Http connection to download the ad's image.
	 * </p> 
	 */
	private HttpManager contentDownloader;

	/**
	 * <p>
	 * Ad handler.
	 * </p>
	 */
	private AdHandler handler;
	
	/**
	 * <p>
	 * Ad listener.
	 * </p>
	 */
	private AdListener listener;

	/**
	 * <p>
	 * Current ad retrieved.
	 * </p>
	 */
	private Ad ad;
	
	/**
	 * <p>
	 * Create an instance of AdManager class.
	 * </p>
	 * @param handler Ad handler.
	 */
	public AdManager(AdHandler handler) {
		this.handler = handler;
		//
		adDownloader = new HttpManager();
		contentDownloader = new HttpManager();
		adDownloader.setConnectionListener(this);
		contentDownloader.setConnectionListener(this);
		//
		if (!StringUtil.isEmpty(handler.getUserAgent())) {
			adDownloader.setRequestProperty(
				"User-Agent", handler.getUserAgent());
			contentDownloader.setRequestProperty(
				"User-Agent", handler.getUserAgent());
		}
		adDownloader.setRequestProperty(
			"Content-Type", " application/x-www-form-urlencoded");
		contentDownloader.setRequestProperty(
			"Content-Type", " application/x-www-form-urlencoded");
	}
	
	/**
	 * <p>
	 * Sets the ad handler object.
	 * </p>
	 * @param handler Handler.
	 */
	public void setAdHandler(AbstractAdHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * <p>
	 * Sets the ad listener object.
	 * </p>
	 * @param listener Listener.
	 */
	public void setAdListener(AdListener listener) {
		this.listener = listener;
	}
	
	/**
	 * <p>
	 * Starts an ad request.
	 * </p>
	 */
	public void requestAd() {
		adDownloader.start(handler.getServiceURL());
		//
		ad = null;
	}
	
	/**
	 * <p>
	 * Returns the last ad retrieved.
	 * </p>
	 * @return Ad.
	 */
	public Ad getAd() {
		return ad;
	}

	/**
	 * @see com.emobtech.adme.io.ConnectionListener#onFail(java.lang.String, java.lang.Throwable)
	 */
	public void onFail(String url, Throwable exception) {
		if (listener != null) {
			listener.onFailedAd(exception);
		}
	}
	
	/**
	 * @see com.emobtech.adme.io.ConnectionListener#onSuccess(java.lang.String)
	 */
	public void onSuccess(String url) {
		if (ad == null) {
			ByteArrayInputStream in =
				new ByteArrayInputStream(adDownloader.getData());
			//
			try {
				ad = handler.parseResponse(in);
			} catch (IOException e) {}
			//
			if (ad != null) {
				if (ad.getImageURL() == null && ad.getText() != null) {
					notifyReceivedAd(ad);
				} else if (ad.getImageURL() != null) {
					contentDownloader.start(ad.getImageURL());
				} else {
					onFail(url, new Exception("Invalid Ad!"));
				}
			} else {
				onFail(url, new Exception("Ad not found!"));
			}
		} else {
			ad.setImage(contentDownloader.getData());
			//
			notifyReceivedAd(ad);
		}
	}
	
	/**
	 * <p>
	 * Notifies the ad listener that a ad has just been received.
	 * </p>
	 * @param ad Ad.
	 */
	protected void notifyReceivedAd(Ad ad) {
		if (listener != null) {
			listener.onReceived(ad);
		}
	}
}
