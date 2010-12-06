package com.emobtech.adme.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

/**
 * Classe utilizada para codificar URLs.
 * @author Ernandes Jr (ernandes@gmail.com)
 */
public final class URLEncoder {
    /**
	 * <p>
	 * Codifica uma dada string.
	 * </p>
	 * @param s String a ser codificada.
	 * @param enc Codificação.
	 * @return String codificada.
	 * @throws IllegalArgumentException Se a string for nula.
	 */
	public static String encode(String s, String enc) {
		if (s == null) {
			throw new IllegalArgumentException("String must not be null");
		}
		if (enc == null) {
			enc = "UTF-8";
		}
		//
		ByteArrayInputStream bIn;
		//
		try {
			bIn = new ByteArrayInputStream(s.getBytes(enc));
		} catch (UnsupportedEncodingException e) {
			bIn = new ByteArrayInputStream(s.getBytes());
		}
		//
		int c = bIn.read();
		StringBuffer ret = new StringBuffer();
		//
		while (c >= 0) {
			if ((c >= 'a' && c <= 'z')
					|| (c >= 'A' && c <= 'Z')
					|| (c >= '0' && c <= '9')
					|| c == '.'
					|| c == '-'
					|| c == '*'
					|| c == '_') {
				ret.append((char) c);
			} else if (c == ' ') {
				ret.append("%20");
			} else {
				if (c < 128) {
					ret.append(getHexChar(c));
				} else if (c < 224) {
					ret.append(getHexChar(c));
					ret.append(getHexChar(bIn.read()));
				} else if (c < 240) {
					ret.append(getHexChar(c));
					ret.append(getHexChar(bIn.read()));
					ret.append(getHexChar(bIn.read()));
				}
			}
			//
			c = bIn.read();
		}
		//
		return ret.toString();
	}
	
	/**
	 * <p>
	 * Pega o valor hexadecimal de um caractere.
	 * </p>
	 * @param c Caractere.
	 */
	private static String getHexChar(int c) {
		return (c < 16 ? "%0" : "%") + Integer.toHexString(c).toUpperCase();
	}
	
	/**
	 * Construtor privado. Evitar criar instâncias desta classe.
	 */
	private URLEncoder() {}
}
