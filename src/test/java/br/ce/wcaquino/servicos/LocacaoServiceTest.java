package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static matchers.LocadoraMatchers.caiEm;
import static matchers.LocadoraMatchers.caiEmUmaSegunda;
import static matchers.LocadoraMatchers.isHoje;
import static matchers.LocadoraMatchers.isHojeMaisDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excessoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excessoes.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import matchers.DiaSemanaMatcher;
import matchers.LocadoraMatchers;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 		
		List<Filme> filmes = Arrays.asList(new Filme("Matrix", 3, 12.98D));
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		error.checkThat(locacao.getValor(), is(equalTo(12.98)));
		// error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(), isHoje());
		// error.checkThat(isMesmaData(locacao.getDataRetorno(), adicionarDias(new Date(), 1)), is(true));
		error.checkThat(locacao.getDataRetorno(), isHojeMaisDiferencaDias(1));
	}
	
	@Test()
	public void deveImpedirDeAlugarFilmeSemEstoque() throws Exception	 {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 		
		List<Filme> filmes = Arrays.asList(new Filme("Matrix", 0, 12.98D));
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
	}
	
	@Test()
	public void deveImpedirDeAlugarSemUsuario() throws FilmeSemEstoqueException {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo");
		List<Filme> filmes = Arrays.asList(new Filme("Matrix", 3, 12.98D));

				
		//Acao
		try {
			Locacao locacao = locacaoService.alugarFilme(null, filmes);
			Assert.fail("Experado excessão de filme sem estoque");
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio"));
		} 
	}
	
	@Test()
	public void deveImpedirDeAlugarFilmeSemFilme() throws Exception	 {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, null);
	}
	
	@Test
	public void deveAplicarDesconto3filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo");
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(new Filme("Matrix", 3, 10.00D));
		filmes.add(new Filme("Star Wars", 3, 10.00D));
		filmes.add(new Filme("O circulo", 3, 10.00D));
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(27.50)));
	}
	
	@Test
	public void deveAplicarDesconto4filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo");
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(new Filme("Matrix", 3, 10.00D));
		filmes.add(new Filme("Star Wars", 3, 10.00D));
		filmes.add(new Filme("O circulo", 3, 10.00D));
		filmes.add(new Filme("Exterminador", 3, 10.00D));
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(32.50)));
	}
	
	@Test
	public void deveAplicarDesconto5filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo");
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(new Filme("Matrix", 3, 10.00D));
		filmes.add(new Filme("Star Wars", 3, 10.00D));
		filmes.add(new Filme("O circulo", 3, 10.00D));
		filmes.add(new Filme("Exterminador", 3, 10.00D));
		filmes.add(new Filme("Alien", 3, 10.00D));
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(35d)));
	}
	
	@Test
	public void deveAplicarDesconto6filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo");
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(new Filme("Matrix", 3, 10.00D));
		filmes.add(new Filme("Star Wars", 3, 10.00D));
		filmes.add(new Filme("O circulo", 3, 10.00D));
		filmes.add(new Filme("Exterminador", 3, 10.00D));
		filmes.add(new Filme("Alien", 3, 10.00D));
		filmes.add(new Filme("Predador", 3, 10.00D));
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(35d)));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws LocadoraException, FilmeSemEstoqueException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//Cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("João Verissimo"); 		
		List<Filme> filmes = Arrays.asList(new Filme("Matrix", 3, 12.98D));
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		//Assert.assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		//Assert.assertThat(locacao.getDataRetorno(), caiEm(Calendar.MONDAY));
		Assert.assertThat(locacao.getDataRetorno(), caiEmUmaSegunda());
	}
}
