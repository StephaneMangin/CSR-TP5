package com.transport.trains;

import com.transport.billeterie.CentralServer;

/**
 * Classe de lancement des Threads de train.
 * 
 * Associe un trajet aléatoirement à chaque train.
 * Faits une pause de 1 seconde entre chaque instanciation.
 * 
 * @author blacknight
 *
 */
public class TrainLauncher extends Thread {
	/**
	 * Nombre maximum de trains à instancier
	 */
	private int nb_train_max;

	public TrainLauncher(int nb_train_max) {
		this.nb_train_max = nb_train_max;
	}
	
	public void run() {
		for (int i=0; i<nb_train_max;i++) {
			new Train(CentralServer.getRandomGare()).start();
		}
	}

}
