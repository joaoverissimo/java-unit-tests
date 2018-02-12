package br.ce.wcaquino.servicos;

public class Calculadora {

	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int dividir(int a, int b) throws Exception {
		if (b == 0) {
			throw new Exception("Não é possível dividir por zero");
		}
		return a / b;
	}

}
