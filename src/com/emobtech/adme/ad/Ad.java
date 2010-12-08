/*
 * Ad.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.ad;

/**
 * <p>
 * This class defines an entity that represents an Ad.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 * @see AdManager
 */
public final class Ad {
	/**
	 * Link. 
	 */
	private String link;

	/**
	 * Image URL.
	 */
	private String imageURL;

	/**
	 * Image bytes.
	 */
	private byte[] image;
	
	/**
	 * Text.
	 */
	private String text;
	
	/**
	 * <p>
	 * Ad in HTML.
	 * </p>
	 */
	private String html;
	
	/**
	 * <p>
	 * Create an instance of Ad class.
	 * </p>
	 */
	public Ad() {
	}
	
	/**
	 * <p>
	 * Create an instance of Ad class.
	 * </p>
	 * @param link Link.
	 * @param text Text.
	 * @param imageURL Image URL.
	 * @param image Image bytes.
	 * @param html HTML content.
	 */
	public Ad(String link, String text, String imageURL, byte[] image,
		String html) {
		this.link = link;
		this.text = text;
		this.imageURL = imageURL;
		this.image = image;
		this.html = html;
	}

	/**
	 * <p>
	 * Returns the ad's link.
	 * </p>
	 * @return Link.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * <p>
	 * Sets the ad's link.
	 * </p>
	 * @param link Link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * <p>
	 * Returns the ad's image URL.
	 * </p>
	 * @return URL.
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * <p>
	 * Sets the ad's image URL.
	 * </p>
	 * @param imageURL URL.
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * <p>
	 * Returns the ad's image.
	 * </p>
	 * @return Image.
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * <p>
	 * Sets the ad's image.
	 * </p>
	 * @param image Image.
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * <p>
	 * Returns the ad's text.
	 * </p>
	 * @return Text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * <p>
	 * Sets the ad's text.
	 * </p>
	 * @param text Text.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * <p>
	 * Returns the ad's HTML.
	 * </p>
	 * @return HTML.
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * <p>
	 * Sets the ad's HTML.
	 * </p>
	 * @param html HTML.
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Ad) {
			if (this == obj) {
				return true;
			} if (link != null) {
				return link.equals(((Ad)obj).link);
			} else if (text != null) {
				return text.equals(((Ad)obj).text);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return link.hashCode();
	}
}
