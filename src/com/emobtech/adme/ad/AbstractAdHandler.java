/*
 * AbstractAdHandler.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad;

import java.util.Enumeration;
import java.util.Hashtable;

import com.emobtech.adme.util.URLEncoder;

/**
 * <p>
 * This class defines an abstract ad handler. To create a new ad handler, it is
 * recommended to start by extending this class.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see InneractiveAdHandler
 */
public abstract class AbstractAdHandler implements AdHandler {
	/**
	 * <p>
	 * Account ID.
	 * </p>
	 */
	private String accountID;
	
	/**
	 * <p>
	 * User agent.
	 * </p>
	 */
	private String userAgent;
	
	/**
	 * <p>
	 * Local address.
	 * </p>
	 */
	private String localAddress;
	
	/**
	 * <p>
	 * Application unique id.
	 * </p>
	 */
	private String auid;
	
	/**
	 * <p>
	 * Metadata table.
	 * </p>
	 */
	private Hashtable metadata;
	
	/**
	 * <p>
	 * Create an instance of AbstractAdHandler class.
	 * </p>
	 */
	public AbstractAdHandler() {
	}
	
	/**
	 * <p>
	 * Create an instance of AbstractAdHandler class.
	 * </p>
	 * @param accountID Account ID.
	 */
	public AbstractAdHandler(String accountID) {
		this.accountID = accountID;
		userAgent = "Profile/MIDP-2.0 Configuration/CLDC-1.0";
		auid = System.currentTimeMillis() + "";
		metadata = new Hashtable();
		try {
			Object o =
				Class.forName(
					"com.emobtech.adme.io.LocalAddress").newInstance();
			//
			localAddress = o.toString();
		} catch (Exception e) {}
	}
	
	/**
	 * <p>
	 * Returns all metadata as query string format.
	 * </p>
	 * @return Query string.
	 */
	public String getMetadataAsQueryString() {
		StringBuffer queryStr = new StringBuffer();
		Enumeration mdKeys = metadata.keys();
		//
		while (mdKeys.hasMoreElements()) {
			String key = mdKeys.nextElement().toString();
			//
			queryStr.append('&' + key + '=');
			queryStr.append(
				URLEncoder.encode(metadata.get(key).toString(), "UTF-8"));
		}
		//
		return queryStr.toString();

	}

	/**
	 * <p>
	 * Returns the account ID.
	 * </p>
	 * @return Account ID.
	 */
	public String getAccountID() {
		return accountID;
	}

	/**
	 * <p>
	 * Returns the user agent.
	 * </p>
	 * @return User agent.
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * <p>
	 * Returns the local address.
	 * </p>
	 * @return Local address.
	 */
	public String getLocalAddress() {
		return localAddress;
	}

	/**
	 * <p>
	 * Returns the application unique ID.
	 * </p>
	 * @return AUID.
	 */
	public String getAuid() {
		return auid;
	}
	
	/**
	 * <p>
	 * Sets the account ID.
	 * </p>
	 * @param accountID Account ID.
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	/**
	 * <p>
	 * Sets the user agent.
	 * </p>
	 * @param userAgent User agent.
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * <p>
	 * Sets the local address.
	 * </p>
	 * @param localAddress Local address.
	 */
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	/**
	 * <p>
	 * Sets the application unique ID.
	 * </p>
	 * @param auid AUID.
	 */
	public void setAuid(String auid) {
		this.auid = auid;
	}
	
	/**
	 * <p>
	 * Sets a metadata property.
	 * </p>
	 * @param property Property.
	 * @param value Value.
	 */
	public void setMetadataProperty(String property, String value) {
		metadata.put(property, value);
	}
}
