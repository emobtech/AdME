/*
 * StringUtil.java
 * 05/12/2010
 * AdME - Advertising Micro Edition
 * Copyright(c) Ernandes Mourao Junior (ernandes@gmail.com)
 * All rights reserved
 */
package com.emobtech.adme.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * <p>
 * This class implements a set of util string methods.
 * </p>
 * 
 * @author Ernandes Mourao Junior (ernandes@gmail.com)
 * @since 1.0
 */
public final class StringUtil {
    /**
     * <p>
     * Returns a string from a given stream.
     * </p>
     * @param in Stream.
     * @return String.
     */
    public static final String getStringFromStream(InputStream in) {
		int c;
		StringBuffer str = new StringBuffer();
		//
		try {
			while ((c = in.read()) != -1) {
				str.append((char)c);
			}
		} catch (IOException e) {}
		//
		return str.toString();
    }
    
	/**
	 * <p>
	 * Verifies whether a given string is empty (length = 0 or null).
	 * </p>
	 * @param str String.
	 * @return Empty (true).
	 */
	public static final boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
    
	/**
	 * <p>
	 * Replaces a given substring to another substring.
	 * <p/>
	 * @param text String.
	 * @param searchStr Substring to be replaced.
	 * @param replacementStr Replacement substring.
	 * @return String replaced.
	 * @throws IllegalArgumentException If any parameter is null.
	 */
	public static final String replace(String text, String searchStr,
		String replacementStr) {
		if (text == null) {
			throw new IllegalArgumentException("Text must not be null.");
		}
		if (searchStr == null) {
			throw new IllegalArgumentException(
				"Search string must not be null.");
		}
		if (replacementStr == null) {
			throw new IllegalArgumentException(
				"Replacement string must not be null.");
		}
		//
		if (isEmpty(text) || isEmpty(searchStr)) {
			return text;
		}
		//
		StringBuffer sb = new StringBuffer();
		int searchStringPos = text.indexOf(searchStr);
		int startPos = 0;
		int searchStringLength = searchStr.length();
		//
		while (searchStringPos != -1) {
			sb.append(
				text.substring(startPos, searchStringPos)).append(
					replacementStr);
			startPos = searchStringPos + searchStringLength;
			searchStringPos = text.indexOf(searchStr, startPos);
		}
		//
		sb.append(text.substring(startPos, text.length()));
		//
		return sb.toString();
	}
	
	/**
     * <p>
     * Split a string based on a given delimiter.
     * </p>
     * @param str String.
     * @param delimiter Delimiter.
     * @return String tokens.
     * @throws IllegalArgumentException If str is null.
     */
    public static final String[] split(String str, char delimiter) {
    	if (str == null) {
    		throw new IllegalArgumentException("Str must not be null.");
    	}
    	//
        Vector v = new Vector();
        int start = 0;
        int iof;
        //
        while ((iof = str.indexOf(delimiter, start)) != -1) {
            v.addElement(str.substring(start, iof).trim());
            start = iof +1;
        }
        //
        v.addElement(str.substring(start, str.length()).trim());
        String[] codes = new String[v.size()];
        v.copyInto(codes);
        //
        return codes;
    }
    
	/**
	 * <p>
	 * Remove any tag occurrence from the given string.
	 * </p>
	 * @param str String to be parsed.
	 * @return String with no tags.
	 * @throws IllegalArgumentException If str is null.
	 */
	public static String removeTags(String str) {
		if (str == null) {
			throw new IllegalArgumentException("Str must not be null.");
		}
		//
		StringBuffer out = new StringBuffer();
		char cs[] = str.toCharArray();
		boolean tagFound = false;
		int i1 = 0;
		int l = 0;
		//
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '<' && !tagFound) {
				out.append(cs, i1, l);
				//
				i1 = i;
				l = 0;
				tagFound = true;
				l++;
			} else if (cs[i] == '>' && tagFound) {
				i1 = i +1;
				l = 0;
				tagFound = false;
			} else {
				l++;
			}
		}
		if (l > 0) {
			out.append(cs, i1, l);
		}
		//
		return out.toString().trim();
	}
	
    /**
	 * <p>
	 * Create an instance of StringUtil class.
	 * </p>
	 * <p>
	 * Private constructor to avoid object instantiation.
	 * </p>
	 */
	private StringUtil() {}
}
