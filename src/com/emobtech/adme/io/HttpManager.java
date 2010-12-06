/*
 * HttpManager.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * <p>
 * This class is responsible for managing Http connections.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see ConnectionListener
 */
public final class HttpManager implements Runnable {
	/**
	 * <p>
	 * Connection listener.
	 * </p>
	 */
	protected ConnectionListener connListener;
	
	/**
	 * <p>
	 * Request properties.
	 * </p>
	 */
	protected Hashtable requestProperties;
	
	/**
	 * <p>
	 * URL.
	 * </p>
	 */
	protected String url;
	
	/**
	 * <p>
	 * Data accessed.
	 * </p>
	 */
	protected byte[] data;
	
	/**
	 * <p>
	 * Stop download (true).
	 * </p>
	 */
	protected boolean toStop;
	
	/**
	 * <p>
	 * Create an instance of HttpManager class.
	 * </p>
	 */
	public HttpManager() {
		requestProperties = new Hashtable();
		//
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * <p>
	 * Sets a request property.
	 * </p>
	 * @param property Property.
	 * @param value Value.
	 */
	public synchronized void setRequestProperty(String property, String value) {
		requestProperties.put(property, value);
	}
	
	/**
 	 * <p>
	 * Starts connection.
	 * </p>
	 * @param url URL.
	 */
	public synchronized void start(String url) {
		this.url = url;
		data = null;
		//
		if (toStop) {
			toStop = false;
			//
			Thread t = new Thread(this);
			t.start();
		} else {
			notifyAll();
		}
	}
	
	/**
	 * <p>
	 * Stops connection.
	 * </p>
	 */
	public synchronized void stop() {
		toStop = true;
		//
		notifyAll();
	}

	/**
	 * <p>
	 * Returns connection's data.
	 * </p>
	 * @return Data.
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * <p>
	 * Sets the connection listener.
	 * </p>
	 * @param listener Listener.
	 */
	public void setConnectionListener(ConnectionListener listener) {
		this.connListener = listener;
	}
	
	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (!toStop) {
			HttpConnection conn = null;
			InputStream in = null;
			//
			try {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {}
					//
					if (toStop) {
						return;
					}
					//
					System.out.println(url);
					//
					conn = (HttpConnection)Connector.open(url);
					conn.setRequestMethod(HttpConnection.GET);
					//
					Enumeration mdKeys = requestProperties.keys();
					//
					while (mdKeys.hasMoreElements()) {
						String prop = mdKeys.nextElement().toString();
						//
						conn.setRequestProperty(
							prop, requestProperties.get(prop).toString());
					}
					//
					if (conn.getResponseCode() != HttpConnection.HTTP_OK) {
						throw new IOException("" + conn.getResponseCode());
					}
					//
					final int length = (int)conn.getLength();
					ByteArrayOutputStream buf =
						new ByteArrayOutputStream(length != -1 ? length : 2048);
		            int ch;
					//
					in = conn.openInputStream();
		            //
		            while ((ch = in.read()) != -1) {
		            	buf.write(ch);
		            	//
		    			if (toStop) {
		    				return;
		    			}
		            }
		            //
		            data = buf.toByteArray();
		            //
					if (connListener != null) {
						connListener.onSuccess(url);
					}
				}
			} catch (IllegalArgumentException e) {
				if (connListener != null) {
					connListener.onFail(url, e);
				}
			} catch (IOException e) {
				if (connListener != null) {
					connListener.onFail(url, e);
				}
			} catch (SecurityException e) {
				if (connListener != null) {
					connListener.onFail(url, e);
				}
			} catch (Exception e) {
				if (connListener != null) {
					connListener.onFail(url, e);
				}
			} catch (OutOfMemoryError e) {
				System.gc();
				//
				if (connListener != null) {
					connListener.onFail(url, e);
				}
			} catch (Throwable e) {
				if (connListener != null) {
					connListener.onFail(url, e);
				}
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}
