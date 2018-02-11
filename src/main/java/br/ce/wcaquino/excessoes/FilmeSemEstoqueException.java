package br.ce.wcaquino.excessoes;

public class FilmeSemEstoqueException extends Exception {

	private static final long serialVersionUID = 277125821803677054L;

	public FilmeSemEstoqueException(String message) {
		super(message);
	}

}
