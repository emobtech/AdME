package com.emobtech.adme.io;

/**
 * Interface com métodos que descreve os eventos de uma conexão de dados.
 * @author Ernandes Jr (ernandes@gmail.com)
 */
public interface ConnectionListener {
	/**
	 * Conexão falhou.
	 * @param url URL que falhou.
	 * @param exception Erro.
	 */
	public void onFail(String url, Throwable exception);

	/**
	 * Conexão bem sucessidade.
	 * @param url URL acessada.
	 */
	public void onSuccess(String url);
}
