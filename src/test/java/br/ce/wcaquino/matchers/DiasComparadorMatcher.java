package br.ce.wcaquino.matchers;

import java.util.Date;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DiasComparadorMatcher extends TypeSafeMatcher<Date> {

	private Integer dias;
	
	public DiasComparadorMatcher(Integer dias) {
		this.dias = dias;
	}

	public void describeTo(Description desc) {
		desc.appendText(new Date().toString() + " + " + dias.toString() + "dias");
	}

	@Override
	protected boolean matchesSafely(Date data2) {
		Date data1 = DataUtils.adicionarDias(new Date(), dias);
		return DataUtils.isMesmaData(data1, data2);
	}

}
