package br.ce.wcaquino.servicos;

import java.util.Date;

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
		Assert.assertTrue(locacao.getValor() == 12.98);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.adicionarDias(new Date(), 1)));
	}
}
