package de.tud.et.ifa.agtele.emf.exceptions;

public class EMFIsStupidException extends RuntimeException {
	private static final long serialVersionUID = 1172774702427205865L;

	@Override
	public String toString() {
		return "EMF is Stupid! - " + super.toString();
	}
}
