package com.emobtech.adme.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe com alguns métodos utilitários para trabalhar com strings.
 * @author Ernandes Jr (ernandes@gmail.com)
 */
public final class StringUtil {
    /**
     * Retorna uma string a partir do conteúdo do stream.
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
	 * Verifica se a string está vazia ou nula.
	 * @param str String.
	 * @return Vazia ou nula (true).
	 */
	public static final boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
    
	/**
	 * <p>
	 * Substitui cada substring por uma outra substring no texto informado.
	 * </p>
	 * @param text Texto.
	 * @param searchStr Substring a ser substituída.
	 * @param replacementStr Substring substituta.
	 * @return Texto substituído.
	 */
	public static final String replace(String text, String searchStr,
		String replacementStr) {
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
     * Returna uma string com zeros a esquerda.
     * </p>
     * @param n Número.
     * @param len Número de zeros.
     * @return String.
     */
    public static final String zeroPad(int n, int len) {
    	String s = n + "";
    	//
    	for (int i = len - s.length(); i > 0; i--) {
            s = '0' + s;
    	}
    	//
        return s;
    }
    
	/**
	 * <p>
	 * Verify if a given char is a space, tab, line return, etc.
	 * </p> 
	 * @param c Char.
	 * @return is space char or not.
	 */
	public static final boolean isSpaceChar(char c) {
		//\u0020
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
	
	/**
	 * Construtor privado. Evitar criar instâncias desta classe.
	 */
	private StringUtil() {}
}
