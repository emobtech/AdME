package com.emobtech.adme.ad;

import java.io.IOException;
import java.io.InputStream;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;

import com.emobtech.adme.util.StringUtil;

/**
 * @author Ernandes Jr
 *
 */
public final class InneractiveAdHandler extends AbstractAdHandler {
	/**
	 * @param accountID
	 */
	public InneractiveAdHandler(String accountID) {
		super(accountID);
		setAuid("-1");
		setUserAgent(null);
	}

	/**
	 * @see com.emobtech.adme.ad.AbstractAdHandler#getServiceURL()
	 */
	public String getServiceURL() {
		StringBuffer url =
			new StringBuffer("http://m2m1.inner-active.com/simpleM2M/");
		//
//		url.append("clientRequestHtmlAd?");
		url.append("clientRequestAd?");
		url.append("aid=" + getAccountID());
		url.append("&cid=" + getAuid());
		url.append("&v=Sm2m-1.5.1");
		url.append("&po=639");
		url.append(getMetadataAsQueryString());
//		url.append("&test=true");
		//
		return url.toString();
	}

	/**
	 * @see com.emobtech.adme.ad.AdHandler#parseResponse(java.io.InputStream)
	 */
	public Ad parseResponse(InputStream response) throws IOException {
		String respStr = StringUtil.getStringFromStream(response).trim();
		//
//		System.out.println(respStr);
		//
		if (respStr.startsWith("<tns:Response")) {
			response.reset();
			//
			return parseXMLResponse(response);
		} else if (respStr.startsWith("<a href")) {
			response.reset();
			//
			return parseHTMLResponse(response);
		} else {
			return null;
		}
	}
	
	/**
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected Ad parseXMLResponse(InputStream response) throws IOException {
		try {
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
						setAuid(parser.getAttributeValue(0));
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
				return new Ad(link, text, url, null);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected Ad parseHTMLResponse(InputStream response) throws IOException {
		try {
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
					return new Ad(link, null, imgURL, null);
				}
			}
			//
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
