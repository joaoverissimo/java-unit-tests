package br.ce.wcaquino.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.AssertTest;
import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;
import br.ce.wcaquino.servicos.LocacaoValoresTest;

@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	AssertTest.class,
	LocacaoServiceTest.class,
	LocacaoValoresTest.class
})
public class LocadoraMainSuite {
	
}
