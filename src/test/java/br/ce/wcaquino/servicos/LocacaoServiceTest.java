package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.LocadoraMatchers.caiEmUmaSegunda;
import static br.ce.wcaquino.matchers.LocadoraMatchers.isHoje;
import static br.ce.wcaquino.matchers.LocadoraMatchers.isHojeMaisDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excessoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excessoes.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService locacaoService;
	private SPCService spc;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		locacaoService = new LocacaoService();
		
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		locacaoService.setDao(dao);
		
		spc = Mockito.mock(SPCService.class);
		locacaoService.setSCPService(spc);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//Cenario
		Usuario usuario = umUsuario().get();	
		List<Filme> filmes = Arrays.asList(umFilme().comValor(12.98).get());
		
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
		Usuario usuario = umUsuario().get(); 		
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().get());
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
	}
	
	@Test()
	public void deveImpedirDeAlugarSemUsuario() throws FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = umUsuario().get();
		List<Filme> filmes = Arrays.asList(umFilme().get());

				
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
		Usuario usuario = umUsuario().get(); 
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, null);
	}
	
	@Test
	public void deveAplicarDesconto3filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = umUsuario().get();
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(27.50)));
	}
	
	@Test
	public void deveAplicarDesconto4filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = umUsuario().get();
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(32.50)));
	}
	
	@Test
	public void deveAplicarDesconto5filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = umUsuario().get();
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(35d)));
	}
	
	@Test
	public void deveAplicarDesconto6filmes() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = umUsuario().get();
		
		List<Filme> filmes = new ArrayList<Filme>();
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		filmes.add(umFilme().get());
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(35d)));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws LocadoraException, FilmeSemEstoqueException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//Cenario
		Usuario usuario = umUsuario().get(); 		
		List<Filme> filmes = Arrays.asList(umFilme().get());
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		//Assert.assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		//Assert.assertThat(locacao.getDataRetorno(), caiEm(Calendar.MONDAY));
		Assert.assertThat(locacao.getDataRetorno(), caiEmUmaSegunda());
	}
	
	@Test
	public void deveImpedirAlugarFilmeParaNegativado() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = umUsuario().get(); 		
		List<Filme> filmes = Arrays.asList(umFilme().get());
		
		Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Usuário negativado no SPC");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
	}
}
