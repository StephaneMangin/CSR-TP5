package com.transport.gare;

import com.transport.billeterie.Billet;
import com.transport.billeterie.CentralServer;
import com.transport.billeterie.Trajet;
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
	
	public void faireQueue(Voyageur voyageur) {
		logger.finest(voyageur.toString() + " en attente d'un billet...");
		voyageur.setBillet(imprimeBillet(voyageur.getTrajet()));
	}

	private Billet imprimeBillet(Trajet trajet) {
		logger.finest( "impression du billet...");
		try {
			Thread.sleep(IMPRESSION_TICKET);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CentralServer.retirerBillet(trajet);
	}
	
	public String toString() {
		return espaceVente.toString() + "::Guichet(" + id + ")";
	}
}
