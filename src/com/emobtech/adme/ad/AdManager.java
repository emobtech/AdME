package com.emobtech.adme.ad;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.emobtech.adme.io.ConnectionListener;
import com.emobtech.adme.io.HttpManager;
import com.emobtech.adme.util.StringUtil;

/**
 * Classe responsável por baixar os Ads.
 * @author Ernandes Jr (ernandes@gmail.com)
 */
public final class AdManager implements ConnectionListener {
	/**
	 * Gerenciador de downloads. 
	 */
	private HttpManager adDownloader;
	
	/**
	 * Gerenciador de downloads para baixar as imagens.
	 */
	private HttpManager contentDownloader;

	/**
	 * Ad handler.
	 */
	private AbstractAdHandler handler;
	
	/**
	 * Escutar dos eventos dos ads.
	 */
	private AdListener listener;
	
	/**
	 * Último ad baixado.
	 */
	private Ad prevAd;

	/**
	 * Ad baixado.
	 */
	private Ad ad;
	
	/**
	 * Construtor.
	 * @param handler Ad handler.
	 */
	public AdManager(AbstractAdHandler handler) {
		this.handler = handler;
		//
		adDownloader = new HttpManager();
		contentDownloader = new HttpManager();
		adDownloader.setConnectionListener(this);
		contentDownloader.setConnectionListener(this);
		//
		if (!StringUtil.isEmpty(handler.getUserAgent())) {
			adDownloader.setRequestProperty(
				"User-Agent", handler.getUserAgent());
			contentDownloader.setRequestProperty(
				"User-Agent", handler.getUserAgent());
		}
		adDownloader.setRequestProperty(
			"Content-Type", " application/x-www-form-urlencoded");
		contentDownloader.setRequestProperty(
			"Content-Type", " application/x-www-form-urlencoded");
	}
	
	/**
	 * Atribue o ad handler.
	 * @param handler Handler.
	 */
	public void setAdHandler(AbstractAdHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * Atribue o escutador dos eventos dos ads.
	 * @param l Escutador.
	 */
	public void setListener(AdListener l) {
		listener = l;
	}
	
	/**
	 * Requisita o download do ad.
	 */
	public void startGetAd() {
		adDownloader.start(handler.getServiceURL());
		//
		ad = null;
	}
	
	/**
	 * Ad baixado.
	 * @return Ad.
	 */
	public Ad getAd() {
		return ad;
	}

	/**
	 * @see com.emobtech.adme.io.ConnectionListener#onFail(java.lang.String, java.lang.Throwable)
	 */
	public void onFail(String url, Throwable exception) {
		if (listener != null) {
			listener.onFailedAd(exception);
		}
	}
	
	/**
	 * @see com.emobtech.adme.io.ConnectionListener#onSuccess(java.lang.String)
	 */
	public void onSuccess(String url) {
		try {
			if (ad == null) {
				ByteArrayInputStream in =
					new ByteArrayInputStream(adDownloader.getData());
				//
				try {
					ad = handler.parseResponse(in);
				} catch (IOException e) {}
				//
				if (ad != null) {
					if (ad.equals(prevAd)) {
						notifyReceivedAd(prevAd);
					} else if (ad.getImageURL() == null
							       && ad.getText() != null) {
						notifyReceivedAd(ad);
					} else {
						contentDownloader.start(ad.getImageURL());
					}
				} else {
					notifyReceivedAd(null);
				}
			} else {
				ad.setImage(contentDownloader.getData());
				//
				notifyReceivedAd(ad);
			}
		} catch (OutOfMemoryError e) {
			System.gc();
			//
			onFail(url, e);
		}
	}
	
	/**
	 * Notifica que o ad foi recebido.
	 * @param ad Ad.
	 */
	protected void notifyReceivedAd(Ad ad) {
		if (listener != null) {
			listener.onReceived(ad);
		}
		//
		if (ad != null) {
			prevAd = ad;
		}
	}
}
