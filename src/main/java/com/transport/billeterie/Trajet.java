package com.transport.billeterie;

import com.transport.gare.Gare;


public class Trajet {

	private Gare gareDepart;
	private Gare gareArrivee;
	
	public Trajet(Gare gareDepart, Gare gareArrivee) {
		this.gareDepart = gareDepart;
		this.gareArrivee = gareArrivee;
	}

	/**
	 * Retourne la gare d'arrivée.
	 * 
	 * @return
	 */
	public Gare gareArrivee() {
		return gareArrivee;
	}
	/**
	 * Retourne la gare de départ.
	 * 
	 * @return
	 */
	public Gare gareDepart() {
		return gareDepart;
	}
	
	public String toString() {
		return "<Trajet " + gareDepart.toString() + " vers " + gareArrivee.toString() + ">";
	}
	
}