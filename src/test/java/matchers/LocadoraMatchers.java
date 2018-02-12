package matchers;

import java.util.Calendar;
import java.util.Date;

public class LocadoraMatchers {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiEmUmaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DiasComparadorMatcher isHojeMaisDiferencaDias(Integer dias) {
		return new DiasComparadorMatcher(dias);
	}
	
	public static DiasComparadorMatcher isHoje() {
		return new DiasComparadorMatcher(0);
	}
	
}
