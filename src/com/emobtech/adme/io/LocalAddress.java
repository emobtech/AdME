/*
 * LocalAddress.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.io;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;

/**
 * <p>
 * This class retrieves the device's local address.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 */
public class LocalAddress {
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		ServerSocketConnection c = null;
		//
		try {
			c = (ServerSocketConnection)Connector.open("socket://:1234");
			//
			return c.getLocalAddress();
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (IOException e) {}
			}
		}
	}
}
