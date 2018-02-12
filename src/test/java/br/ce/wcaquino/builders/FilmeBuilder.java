package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {}

	public static FilmeBuilder umFilme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme("Matrix", 3, 10.00D);
		return builder;
	}
	
	public static FilmeBuilder umFilmeSemEstoque() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme("Matrix", 0, 10.00D);
		return builder;
	}

	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder comValor(Double precoLocacao) {
		filme.setPrecoLocacao(precoLocacao);
		return this;
	}
	
	public Filme get() {
		return filme;
	}
}
