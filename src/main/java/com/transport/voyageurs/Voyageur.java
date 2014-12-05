package com.transport.voyageurs;

import com.transport.billeterie.Billet;
import com.transport.billeterie.CentralServer;
import com.transport.billeterie.Trajet;
import com.transport.gare.EspaceVente;
import com.transport.gare.Gare;
import com.transport.log.Log;


public class Voyageur extends Thread {

	private Log log;
	private Gare gareInit;
	private Billet billet;
	private Trajet trajet;
	
	public Voyageur(Gare gareInit, Trajet trajet){
		log = new Log(this);
		this.gareInit = gareInit;
		setTrajet(trajet);
	}

	private void setTrajet(Trajet trajet) {
		this.trajet = trajet;
	}
	
	public Billet getBillet() {
		return billet;
	}
	
	public Trajet getTrajet() {
		return trajet;
	}
	
	public void setBillet(Billet billet) {
		log.finest("a son billet pour " + billet.getTrain().getTrajet().toString());
		this.billet = billet;
	}

	public String toString() {
		return "<Voyageur " + getId() + ">";
	}
	
	public void run() {
		while (true) {
			gareInit.getEspaceVente().faireQueue(this);
			billet.getTrajet().gareDepart().entrer(this);
			billet.getTrajet().gareDepart().getEspaceQuai().faireQueue(this);
			setTrajet(gareInit.getCentralServer().getTrajet(billet.getTrajet().gareArrivee(), null));
			gareInit = billet.getTrajet().gareDepart();
		}
	}
}
