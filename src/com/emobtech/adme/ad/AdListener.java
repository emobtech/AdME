package com.emobtech.adme.ad;

/**
 * Interface que define os estados do download de um ad.
 * @author Ernandes Jr (ernandes@gmail.com)
 */
public interface AdListener {
	/**
	 * Ad recebido.
	 * @param ad Ad.
	 */
	public void onReceived(Ad ad);
	
	/**
	 * Falha ao baixar o ad.
	 * @param exception Error.
	 */
	public void onFailedAd(Throwable exception);
}
