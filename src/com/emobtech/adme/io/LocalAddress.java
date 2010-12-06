package com.emobtech.adme.io;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;

/**
 * Classe para pegar o endereço IP.
 * @author Ernandes Jr (ernandes@gmail.com)
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
