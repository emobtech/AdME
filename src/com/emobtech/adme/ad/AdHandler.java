/*
 * AdHandler.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * This class defines an ad handler, which is responsible for providing the URL
 * to be accessed in order to retrieve an ad from a given network.
 * </p>
 * 
 * @author Ernandes Jr (ernandes@gmail.com)
 * @since 1.0
 * @see AbstractAdHandler
 */
public interface AdHandler {
	/**
	 * <p>
	 * Returns the URL to access the ad.
	 * </p>
	 * @return Ad URL.
	 */
	public String getServiceURL();
	
	/**
	 * <p>
	 * Parses the ad information returned by ad network.
	 * </p>
	 * @param response Ad content.
	 * @return Ad.
	 * @throws IOException If occurs any I/O error by parsing the ad.
	 */
	public Ad parseResponse(InputStream response) throws IOException;
}
