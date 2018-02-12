package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalculadoraTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private Calculadora calc;

	@Before
	public void setup() {
		 calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//cenario
		int a = 6;
		int b = 2;
		
		Calculadora calc = new Calculadora();		
		
		//acao
		int resultado = calc.somar(a, b); 
		
		
		//verificacao
		Assert.assertThat(resultado, is(equalTo(8)));
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//cenario
		int a = 8;
		int b = 6;
		
		Calculadora calc = new Calculadora();		
		
		//acao
		int resultado = calc.subtrair(a, b); 
		
		
		//verificacao
		Assert.assertThat(resultado, is(equalTo(2)));
	}
	
	@Test
	public void deveDividirDoisValores() throws Exception {
		//cenario
		int a = 8;
		int b = 2;
		
		//acao
		int resultado = calc.dividir(a, b); 
		
		
		//verificacao
		Assert.assertThat(resultado, is(equalTo(4)));
	}
	
	@Test
	public void deveDividirDoisValoresSendoZero() throws Exception {
		//cenario
		int a = 8;
		int b = 0;
		
		exception.expect(Exception.class);
		exception.expectMessage("Não é possível dividir por zero");
		
		//acao
		int resultado = calc.dividir(a, b); 
	}
	
}
