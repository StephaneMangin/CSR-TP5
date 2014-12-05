package com.transport.gare;

import com.transport.billeterie.Billet;
import com.transport.billeterie.CentralServer;
import com.transport.billeterie.Trajet;
import com.transport.log.Log;
import com.transport.voyageurs.Voyageur;

/**
 * Simule un guichet associé à un espace de vente
 * 
 * @author blacknight
 *
 */
class Guichet {

	private Log logger;
	/**
	 * Identifiant unique du guichet
	 */
	private int id;
	/**
	 * Espace de vente associé
	 */
	private EspaceVente espaceVente;
	/**
	 * Temps d'impression d'un billet
	 */
	private Integer IMPRESSION_TICKET = 10;

	public Guichet(int id, EspaceVente espaceVente) {
		this.id = id;
		this.espaceVente = espaceVente;
		this.logger = new Log(this);
	}
	
	/*
	 * Accès principal des voyageurs au guichet
	 */
	public void faireQueue(Voyageur voyageur) {
		logger.finest(voyageur.toString() + " en attente d'un billet...");
		voyageur.setBillet(imprimeBillet(voyageur.getTrajet()));
	}

	/**
	 * Retrait des billet au serveur central
	 * 
	 * @param trajet
	 * @return
	 */
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
