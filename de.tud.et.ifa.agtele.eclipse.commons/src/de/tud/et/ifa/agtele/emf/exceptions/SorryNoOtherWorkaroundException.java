package de.tud.et.ifa.agtele.emf.exceptions;

public class SorryNoOtherWorkaroundException extends EMFIsStupidException {
	private static final long serialVersionUID = 3045557510623315131L;

	@Override
	public String toString() {
		return "Sorry, but " + super.toString();
	}
}
