/*
 * VservAdHandler.java
 * 03/01/2011
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad.vserv;

import java.io.IOException;
import java.io.InputStream;

import com.emobtech.adme.ad.AbstractAdHandler;
import com.emobtech.adme.ad.Ad;
import com.emobtech.adme.ad.AdManager;
import com.emobtech.adme.util.StringUtil;

/**
 * <p>
 * This class defines ad handler for the ad network Vserv.mobi (www.vserv.mobi).
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.1
 * @see AdManager
 */
public class VservAdHandler extends AbstractAdHandler {
	/**
	 * <p>
	 * Inner-active base URL.
	 * </p>
	 */
	private final String VS_URL = "http://us.vserv.mobi/delivery/adapi.php";

	/**
	 * <p>
	 * Create an instance of VservAdHandler class.
	 * </p>
	 */
	public VservAdHandler() {
		setUrl(VS_URL);
	}

	/**
	 * <p>
	 * Create an instance of VservAdHandler class.
	 * </p>
	 * @param zoneid Zone ID.
	 * @param userAgent User-agent.
	 */
	public VservAdHandler(String zoneid, String userAgent) {
		setUrl(VS_URL);
		setParameter("zoneid", zoneid);
		setUserAgent(userAgent);
		setParameter("a", "1");
	}

	/**
	 * @see com.emobtech.adme.ad.AdHandler#getUserAgent()
	 */
	public String getUserAgent() {
		return (String)parameters.get("ua");
	}
	
	/**
	 * <p>
	 * Sets the user agent.
	 * </p>
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		if (StringUtil.isEmpty(userAgent)) {
			removeParameter("ua");
		} else {
			setParameter("ua", userAgent);
		}
	}

	/**
	 * @see com.emobtech.adme.ad.AdHandler#parseResponse(java.io.InputStream)
	 */
	public Ad parseResponse(InputStream response) throws IOException {
		String content = StringUtil.getStringFromStream(response);
		//
		if (StringUtil.isEmpty(content)) {
			return null;
		}
		//
		String[] data = StringUtil.split(content, '\n');
		//
		if (data.length < 3 || data[0].equals("text")) {
			return null;
		}
		//
		Ad ad = new Ad();
		//
		ad.setImageURL(data[1]);
		ad.setLink(data[2]);
		if (data.length > 3) {
			ad.setText(StringUtil.removeTags(data[3]));
		}
		//
		return ad;
	}
}
