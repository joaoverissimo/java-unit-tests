package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Test
	public void teste() {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 
		Filme filme = new Filme("Matrix", 3, 12.98D);
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		
		//Verificacao
		assertThat(locacao.getValor(), is(equalTo(12.98)));
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), adicionarDias(new Date(), 1)), is(true));
	}
}
