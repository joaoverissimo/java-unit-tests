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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Test
	public void testeLocacao() throws Exception {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 
		Filme filme = new Filme("Matrix", 3, 12.98D);
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		
		//Verificacao
		error.checkThat(locacao.getValor(), is(equalTo(12.98)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), adicionarDias(new Date(), 1)), is(true));
	}
	
	@Test(expected=Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 
		Filme filme = new Filme("Matrix", 0, 12.98D);
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
	}
	
	@Test()
	public void testeLocacao_filmeSemEstoque2() {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 
		Filme filme = new Filme("Matrix", 0, 12.98D);
		
		//Acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filme);
			Assert.fail("Experado excessão de filme sem estoque");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test()
	public void testeLocacao_filmeSemEstoque3() throws Exception	 {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 
		Filme filme = new Filme("Matrix", 0, 12.98D);
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
	}
}
