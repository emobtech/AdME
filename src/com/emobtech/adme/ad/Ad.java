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
	 * Create an instance of Ad class.
	 * </p>
	 * @param link Link.
	 * @param text Text.
	 * @param imageURL Image URL.
	 * @param image Image bytes.
	 */
	public Ad(String link, String text, String imageURL, byte[] image) {
		this.link = link;
		this.text = text;
		this.imageURL = imageURL;
		this.image = image;
	}

	/**
	 * Returns the ad's link.
	 * @return Link.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the ad's link.
	 * @param link Link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Returns the ad's image URL.
	 * @return URL.
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * Sets the ad's image URL.
	 * @param imageURL URL.
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * Returns the ad's image.
	 * @return Image.
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Sets the ad's image.
	 * @param image Image.
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * Returns the ad's text.
	 * @return Text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the ad's text.
	 * @param text Text.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Ad) {
			if (imageURL != null) {
				return imageURL.equals(((Ad)obj).imageURL);
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
