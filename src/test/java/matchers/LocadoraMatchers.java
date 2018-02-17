package matchers;

import java.util.Calendar;

public class LocadoraMatchers {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiEmUmaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
}
