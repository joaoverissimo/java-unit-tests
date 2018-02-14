package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excessoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excessoes.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	private static final int QNTD_FILMES_3 = 2;
	private static final int QNTD_FILMES_4 = 3;
	private static final int QNTD_FILMES_5 = 4;
	private static final int QNTD_FILMES_6 = 5;
	
	private static final double DESCONTO_30_PORCENTO = 1d - 0.25d;
	private static final double DESCONTO_50_PORCENTO = 1d - 0.50d;
	private static final double DESCONTO_75_PORCENTO = 1d - 0.75d;
	private static final double DESCONTO_100_PORCENTO = 0d;

	private LocacaoDAO dao;
	private SPCService spcService;
	private EmailService emailService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private Double getPrecoLocacao(List<Filme> filmes) {
		Double retorno = 0D;
		
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme =  filme.getPrecoLocacao();
			
			if (i == QNTD_FILMES_3) {
				valorFilme = valorFilme * DESCONTO_30_PORCENTO;
			} 
			
			if (i == QNTD_FILMES_4) {
				valorFilme = valorFilme * DESCONTO_50_PORCENTO;
			} 
			
			if (i == QNTD_FILMES_5) {
				valorFilme = valorFilme * DESCONTO_75_PORCENTO;
			} 
			
			if (i == QNTD_FILMES_6) {
				valorFilme = valorFilme * DESCONTO_100_PORCENTO;
			} 
			
			retorno += valorFilme;
		}
		
		return retorno;		
	}
	
	public Date getDataRetorno(Date dataLocacao) {
		Date dataRetorno = new Date();
		dataRetorno = adicionarDias(dataLocacao, 1);

		if (DataUtils.verificarDiaSemana(dataRetorno, Calendar.SUNDAY)) {
			dataRetorno = adicionarDias(dataRetorno, 1);
		}
		
		return dataRetorno;
	}
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException, FilmeSemEstoqueException  {
		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		
		if (usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}
		
		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}
		
		try {
			if (spcService.possuiNegativacao(usuario)) {
				throw new LocadoraException("Usuário negativado no SPC");
			}
		} catch (RuntimeException e) {
			throw new LocadoraException("Erro com o serviço do SPC");
		}
		
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(getPrecoLocacao(filmes));

		//Entrega no dia seguinte
		locacao.setDataRetorno(getDataRetorno(locacao.getDataLocacao()));
		
		//Salvando a locacao...	
		dao.salvar(locacao);
		
		return locacao;
	}
	
	public void notificarAtraso() {
		List<Locacao> locacoes = dao.findEmAtraso();
		for (Locacao locacao : locacoes) {
			if (locacao.getDataRetorno().before(new Date())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}
}