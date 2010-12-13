/*
 * InneractiveAdHandler.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad.inneractive;

import java.io.IOException;
import java.io.InputStream;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.emobtech.adme.ad.AbstractAdHandler;
import com.emobtech.adme.ad.Ad;
import com.emobtech.adme.ad.AdManager;
import com.emobtech.adme.util.StringUtil;

/**
 * <p>
 * This class defines ad handler for the ad network Inner-active
 * (www.inner-active.com).
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see AdManager
 */
public final class InneractiveAdHandler extends AbstractAdHandler {
	/**
	 * <p>
	 * Inner-active base URL.
	 * </p>
	 */
	private final String IA_URL = "http://m2m1.inner-active.com/simpleM2M/";
	
	/**
	 * <p>
	 * User agent.
	 * </p>
	 */
	private String userAgent;
	
	/**
	 * <p>
	 * Create an instance of InneractiveAdHandler class.
	 * </p>
	 * @param accountID Account ID.
	 */
	public InneractiveAdHandler(String accountID) {
		setUrl(IA_URL);
		setResponseFormat("xml");
		setParameter("aid", accountID);
		setParameter("cid", "-1");
		setParameter("v", "Sm2m-1.5.1");
	}
	
	/**
	 * <p>
	 * Sets test mode enabled. It means your app will receive test ads from
	 * Inner-active. Use this mode during your integration tests.
	 * </p>
	 * @param enabled Enabled (true).
	 */
	public void setTestModeEnabled(boolean enabled) {
		if (enabled) {
			setParameter("test", "true");
		} else {
			removeParameter("test");
		}
	}
	
	/**
	 * <p>
	 * Sets the response format return by ad network. Valid values are "xml"
	 * and "html".
	 * </p>
	 * @param format Format.
	 */
	public void setResponseFormat(String format) {
		if (StringUtil.isEmpty(format)) {
			throw new IllegalArgumentException("Format must not be empty.");
		}
		//
		format = format.toLowerCase().trim();
		//
		if (format.equals("xml")) {
			setUrl(IA_URL + "clientRequestAd");
		} else if (format.equals("html")) {
			setUrl(IA_URL + "clientRequestHtmlAd");
		} else {
			throw new IllegalArgumentException("Invalid format: " + format);
		}
	}

	/**
	 * @see com.emobtech.adme.ad.AdHandler#getUserAgent()
	 */
	public String getUserAgent() {
		return userAgent;
	}
	
	/**
	 * <p>
	 * Sets the user agent.
	 * </p>
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
		//
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
		String respStr = StringUtil.getStringFromStream(response).trim();
		//
		if (respStr.startsWith("<tns:Response")) {
			response.reset();
			//
			try {
				return parseXMLResponse(response);
			} catch (XmlPullParserException e) {
				return null;
			}
		} else if (respStr.startsWith("<a href")) {
			response.reset();
			//
			return parseHTMLResponse(response);
		} else {
			return null;
		}
	}
	
	/**
	 * <p>
	 * Parses the ad's XML content.
	 * </p>
	 * @param response Response.
	 * @return Ad.
	 * @throws IOException If occurs any I/O error by parsing the ad.
	 * @throws XmlPullParserException If occurs any parser error.
	 */
	protected Ad parseXMLResponse(InputStream response) throws IOException,
		XmlPullParserException {
		KXmlParser parser = new KXmlParser();
		parser.setInput(response, "UTF-8");
		//
		int etype = parser.next();
		String tag = "";
		String url = null;
		String link = null;
		String text = null;
		//
		while (etype != XmlPullParser.END_DOCUMENT) {
			if (etype == XmlPullParser.START_TAG) {
				tag = parser.getName().toLowerCase();
				//
				if (tag.equals("tns:response")) {
					String res = parser.getAttributeValue(0);
					//
					if (!("OK".equals(res) || "House Ad".equals(res))) {
						return null;
					}
				} else if (tag.equals("tns:client")) {
					setParameter("cid", parser.getAttributeValue(0));
				}
			} else if (etype == XmlPullParser.TEXT) {
				if (tag.equals("tns:image")) {
					url = parser.getText();
					if (StringUtil.isEmpty(url)) {
						url = null;
					}
				} else if (tag.equals("tns:url")) {
					link = parser.getText();
					if (StringUtil.isEmpty(link)) {
						link = null;
					}
				} else if (tag.equals("tns:text")) {
					text = parser.getText();
					if (StringUtil.isEmpty(text)) {
						text = null;
					}
				}
			} else if (etype == XmlPullParser.END_TAG) {
				tag = "";
			}
			//
			etype = parser.next();
		}
		//
		if (url != null || text != null) {
			return new Ad(link, text, url, null, null);
		} else {
			return null;
		}
	}
	
	/**
	 * <p>
	 * Parses the ad's HTML content.
	 * </p>
	 * @param response Response.
	 * @return Ad.
	 * @throws IOException If occurs any I/O error by parsing the ad.
	 */
	protected Ad parseHTMLResponse(InputStream response) throws IOException {
		String html = StringUtil.getStringFromStream(response).trim();
		int ix = html.indexOf("<a href=\"");
		//
		if (ix != -1) {
			String link = html.substring(9, html.indexOf("\"", 9));
			ix = html.indexOf("<img src=\"");
			//
			if (ix != -1) {
				String imgURL =
					html.substring(ix + 10, html.indexOf("\"", ix + 10));
				link = StringUtil.replace(link, "&amp;", "&");
				imgURL = StringUtil.replace(imgURL, "&amp;", "&");
				//
				return new Ad(link, null, imgURL, null, html);
			}
		}
		//
		return null;
	}
}
