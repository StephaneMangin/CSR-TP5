package com.transport.gare;

import com.transport.billeterie.Billet;
import com.transport.log.Log;
import com.transport.voyageurs.Voyageur;

class Guichet {

	Log logger;
	private int id;
	private EspaceVente espaceVente;
	private Integer IMPRESSION_TICKET = 10;

	public Guichet(int id, EspaceVente espaceVente) {
		this.id = id;
		this.espaceVente = espaceVente;
		this.logger = new Log(this);
	}
	
	synchronized public Billet faireQueue(Voyageur voyageur) {
		logger.finest(voyageur.toString() + " en attente d'un billet...");
		return imprimeBillet(voyageur.getTrajet());
	}

	private Billet imprimeBillet(Trajet trajet) {
		Billet billet = espaceVente.getGare().getCentralServer().retirerBillet(trajet);
		logger.finest( "impression du billet...");
		try {
			Thread.sleep(IMPRESSION_TICKET);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		notifyAll();
		return billet;
	}
	
	public String toString() {
		return espaceVente.toString() + "::Guichet(" + id + ")";
	}
}
