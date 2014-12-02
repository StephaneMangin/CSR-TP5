package com.transport.trains;

import com.transport.billeterie.CentralServer;
import com.transport.gare.Gare;


public class TrainLauncher extends Thread {

	private int nb_train_max;

	public TrainLauncher(int nb_train_max) {
		this.nb_train_max = nb_train_max;
	}
	
	public void run() {
		for (int i=0; i<nb_train_max;i++) {
			// Attribution aléatoire des trajets
			Gare gare = CentralServer.gares.get((int) (Math.random()*(CentralServer.gares.size()-1)));
			Train train = new Train(gare);
			train.start();
		}
	}

}
