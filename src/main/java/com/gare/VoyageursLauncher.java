package com.gare;


public class VoyageursLauncher extends Thread {


	private int nb_voyageur_max;

	public VoyageursLauncher(int nb_voyageur_max) {
		this.nb_voyageur_max = nb_voyageur_max;
	}
	
	public void run() {
		for (Gare gare: CentralServer.gares) {
			for (int i=0; i<nb_voyageur_max;i++) {
				Trajet trajet = CentralServer.trajets.get((int) (Math.random()*(CentralServer.trajets.size()-1)));
				Voyageur voyageur = new Voyageur(gare, trajet);
				voyageur.start();
				try {
					voyageur.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
