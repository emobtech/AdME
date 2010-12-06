package com.emobtech.adme.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * Classe responsável por baixar contéudos a partir de uma URL.
 * @author Ernandes Jr (ernandes@gmail.com)
 */
public class HttpManager implements Runnable {
	/**
	 * Escutador dos estados da conexão.
	 */
	protected ConnectionListener connListener;
	
	/**
	 * Propriedades do request.
	 */
	protected Hashtable requestProperties;
	
	/**
	 * URL a ser acessada.
	 */
	protected String url;
	
	/**
	 * Dados baixados.
	 */
	protected byte[] data;
	
	/**
	 * Para o download corrente.
	 */
	protected boolean toStop;
	
	/**
	 * Construtor.
	 */
	public HttpManager() {
		requestProperties = new Hashtable();
		//
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * Atribue uma propriedade do request.
	 * @param property Propriedade.
	 * @param value Valor.
	 */
	public synchronized void setRequestProperty(String property, String value) {
		requestProperties.put(property, value);
	}
	
	/**
	 * Inicia o download.
	 * @param url URL a ser acessada.
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
	 * Para o download corrente.
	 */
	public synchronized void stop() {
		toStop = true;
		//
		notifyAll();
	}

	/**
	 * Retorna os dados baixados.
	 * @return Dados.
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * Atribui um escutador dos estados da conexão.
	 * @param listener
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
