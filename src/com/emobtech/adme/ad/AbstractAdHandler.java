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

import com.emobtech.adme.ad.inneractive.InneractiveAdHandler;
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
	 * URL.
	 * </p> 
	 */
	protected String url;
	
	/**
	 * <p>
	 * Parameters table.
	 * </p>
	 */
	protected Hashtable parameters;
	
	/**
	 * <p>
	 * Create an instance of AbstractAdHandler class.
	 * </p>
	 */
	public AbstractAdHandler() {
		parameters = new Hashtable();
	}
	
	/**
	 * <p>
	 * Returns the ad network URL.
	 * </p>
	 * @return URL.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <p>
	 * Sets the ad network URL.
	 * </p>
	 * @param url URL.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * <p>
	 * Sets a parameter value.
	 * </p>
	 * @param parameter Parameter.
	 * @param value Value.
	 */
	public void setParameter(String parameter, String value) {
		parameters.put(parameter, value);
	}
	
	/**
	 * <p>
	 * Removes a given parameter.
	 * </p>
	 * @param parameter Parameter.
	 */
	public void removeParameter(String parameter) {
		parameters.remove(parameter);
	}

	/**
	 * @see com.emobtech.adme.ad.AdHandler#getServiceURL()
	 */
	public String getServiceURL() {
		StringBuffer adUrl = new StringBuffer(url);
		//
		adUrl.append('?');
		adUrl.append(getParametersAsQueryString());
		//
		return adUrl.toString();
	}

	/**
	 * <p>
	 * Returns all parameters as query string format.
	 * </p>
	 * @return Query string.
	 */
	protected String getParametersAsQueryString() {
		StringBuffer queryStr = new StringBuffer();
		Enumeration mdKeys = parameters.keys();
		//
		while (mdKeys.hasMoreElements()) {
			String key = mdKeys.nextElement().toString();
			//
			queryStr.append('&' + key + '=');
			queryStr.append(
				URLEncoder.encode(parameters.get(key).toString(), "UTF-8"));
		}
		//
		return queryStr.toString();
	}
}
