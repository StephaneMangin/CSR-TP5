package com.gare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class CentralServer {
	
	Log logger;
	static int nb_train_max = 5;
	static int nb_voyageur_max = 50;
	static ArrayList<Gare> gares = new ArrayList<Gare>();
	static ArrayList<Trajet> trajets = new ArrayList<Trajet>();
	private static Stack<Billet> billets = new Stack<Billet>();
	
	public CentralServer() throws Exception {
		logger = new Log(this);
		Gare gareA = new Gare("A", this);
		Gare gareB = new Gare("B", this);
		Gare gareC = new Gare("C", this);
		trajets.add(new Trajet(gareA, gareB));
		trajets.add(new Trajet(gareA, gareC));
		trajets.add(new Trajet(gareB, gareA));
		trajets.add(new Trajet(gareB, gareC));
		trajets.add(new Trajet(gareC, gareA));
		trajets.add(new Trajet(gareB, gareA));
		gares.add(gareA);
		gares.add(gareB);
		gares.add(gareC);
	}

	public Gare getGare(String name) {
		for (Gare gare: gares) {
			if (gare.getName() == name) {
				return gare;
			}
		}
		return null;
	}

	synchronized public void retirerTrain(Train train) {
		removeBillets(train);
		notifyAll();
	}
	
	synchronized public void addBillets(Train train) {
		for (int i=0;i<train.nbPlaces();i++) {
			billets.add(new Billet(train.getTrajet()));
		}
		logger.config("billet créé pour le train " + train.getId());
	}

	synchronized public Billet popBillet(Gare gareDepart, Gare gareArrivee) {
		for (Billet billet: billets) {
			if (billet.getGareDepart() == gareDepart && billet.getGareArrivee() == gareArrivee) {
				billets.remove(billet);
				return billet;
			}
		}
		return null;
	}

	synchronized public void removeBillets(Train train) {
		for (Billet billet: billets) {
			if (billet.getTrain() == train) {
				billets.remove(billet);
			}
		}
		notifyAll();
	}

	synchronized public int getNbBillets() {
		return billets.size();
	}

	synchronized public Iterator<Trajet> getTrajets() {
		return trajets.iterator();
	}
	
	synchronized public int getNbBillets(Train train) {
		int i = 0;
		for (Billet billet: billets) {
			if (billet.getTrain() == train) {
				i++;
			}
		}
		return i;
	}
	
	public String toString() {
		return "MAIN";
	}
	
	public static void main(String[] args) throws Exception {
		new CentralServer();
		Thread launch1 = new VoyageursLauncher(nb_voyageur_max);
		Thread launch = new TrainLauncher(nb_train_max);
		launch.start();
		launch1.start();
		for (Gare gare: gares) {
			for (Voyageur voyageur: gare.voyageurs) {
				voyageur.start();
			}
		}
		for (Gare gare: gares) {
			for (Voyageur voyageur: gare.voyageurs) {
				voyageur.join();
			}
		}
		launch.join();
		launch1.join();
	}

}
