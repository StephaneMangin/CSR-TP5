package com.transport.gare;

import com.transport.log.Log;


public class Trajet {

	Log logger;
	private Gare gareDepart;
	private Gare gareArrivee;
	
	public Trajet(Gare gareDepart, Gare gareArrivee) {
		this.gareDepart = gareDepart;
		this.gareArrivee = gareArrivee;
		logger = new Log(this);
	}

	public Gare gareArrivee() {
		return gareArrivee;
	}

	public Gare gareDepart() {
		return gareDepart;
	}
	
	public String toString() {
		return "<Trajet " + gareDepart.toString() + " vers " + gareArrivee.toString() + ">";
	}
	
}