package com.transport.voyageurs;

import com.transport.billeterie.CentralServer;

/**
 * Classe de lancement des Threads de voyageurs.
 * 
 * Associe une gare et un trajet aléatoirement à chaque voyageur.
 * 
 * @author blacknight
 *
 */
public class VoyageursLauncher extends Thread {
	/**
	 * Nombre maximum de voyageur à instancier par gare.
	 */
	private int nb_voyageur_max;

	public VoyageursLauncher(int nb_voyageur_max) {
		this.nb_voyageur_max = nb_voyageur_max;
	}
	
	public void run() {
		for (int i=0; i<nb_voyageur_max;i++) {
			new Voyageur(
				CentralServer.getRandomGare(),
				CentralServer.getRandomTrajet()
			).start();
		}
	}
}