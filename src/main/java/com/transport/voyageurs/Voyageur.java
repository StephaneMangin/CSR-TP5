package com.transport.voyageurs;

import com.transport.billeterie.Billet;
import com.transport.billeterie.CentralServer;
import com.transport.billeterie.Trajet;
import com.transport.gare.EspaceVente;
import com.transport.gare.Gare;
import com.transport.log.Log;


public class Voyageur extends Thread {

	private Log log;
	private Billet billet;
	private Trajet trajet;
	
	public Voyageur(Gare gareInit, Trajet trajet){
		log = new Log(this);
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
		if (billet.getTrajet() == this.trajet) {
			log.severe("le trajet du billet n'est pas le bon.");
		}
		log.finest("a son billet pour " + billet.getTrain().getTrajet().toString());
		this.billet = billet;
	}

	public String toString() {
		return "<Voyageur " + getId() + ">"; 
	}
	
	public void run() {
		while (true) {
			trajet.gareDepart().getEspaceVente().faireQueue(this);
			if (billet != null) {
				billet.getTrajet().gareDepart().entrer(this);
				billet.getTrajet().gareDepart().getEspaceQuai().faireQueue(this);
			} else {
				log.warning("plus de billet pour " + trajet.toString());
			}
			setTrajet(CentralServer.getTrajet(billet.getTrajet().gareArrivee(), null));
		}
	}
}
