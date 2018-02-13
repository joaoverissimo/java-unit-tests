package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.dao.LocacaoDAOFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excessoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excessoes.LocadoraException;

@RunWith(Parameterized.class)
public class LocacaoValoresTest {
	
	private LocacaoService locacaoService ;

	@Parameter
	public List<Filme> filmes;

	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	private static Filme filme1 = FilmeBuilder.umFilme().get();
	private static Filme filme2 = FilmeBuilder.umFilme().get();
	private static Filme filme3 = FilmeBuilder.umFilme().get();
	private static Filme filme4 = FilmeBuilder.umFilme().get();
	private static Filme filme5 = FilmeBuilder.umFilme().get();
	private static Filme filme6 = FilmeBuilder.umFilme().get();
	private static Filme filme7 = FilmeBuilder.umFilme().get();
	
	@Parameters(name="Teste parametrizado {index} - cenário: {2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1), 10d, "1 filme sem desconto"},
			{Arrays.asList(filme1, filme2), 20d, "2 filmes sem desconto"},
			{Arrays.asList(filme1, filme2, filme3), 27.5d, "2 filmes desconto 25%"},
			{Arrays.asList(filme1, filme2, filme3), 27.5d, "3 filmes desconto 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 32.5d, "4 filmes desconto 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 35d, "5 filmes desconto 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 35d, "6 filmes desconto 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 45d, "7 filmes"}
		});
	}
	
	@Before
	public void setup() {
		locacaoService = new LocacaoService();
		locacaoService.setDao(new LocacaoDAOFake());
	}
	
	@Test
	public void deveAplicarDescontoConformeParametros() throws LocadoraException, FilmeSemEstoqueException {
		//Cenario
		Usuario usuario = new Usuario("João Verissimo");
		
		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//Verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(valorLocacao)));
	}
	
}
