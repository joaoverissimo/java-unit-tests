package br.ce.wcaquino.builders;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Arrays;
import java.util.Date;

import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoBuilder {

	private Locacao locacao;
	
	private LocacaoBuilder() {}
	
	public static LocacaoBuilder umaLocacao() {
		LocacaoBuilder builder = new LocacaoBuilder();
		
	    Locacao obj = new Locacao();
	    obj.setFilmes(Arrays.asList(umFilme().get()));
	    obj.setUsuario(umUsuario().get());
	    obj.setValor(12.00);
	    obj.setDataLocacao(new Date());
	    obj.setDataRetorno(adicionarDias(new Date(), 1));
		
		builder.locacao = obj;
		return builder;
	}
	
	public LocacaoBuilder comUsuario(Usuario usuario) {
		locacao.setUsuario(usuario);
		return this;
	}

	public LocacaoBuilder comDataRetorno(Date dataRetorno) {
		locacao.setDataRetorno(dataRetorno);
		return this;
	}
	
	public LocacaoBuilder atrasada() {
		Date dataRetorno = DataUtils.adicionarDias(new Date(), -2);
		locacao.setDataRetorno(dataRetorno);
		return this;
	}
	
	public Locacao get() {
		return locacao; 
	}
}
