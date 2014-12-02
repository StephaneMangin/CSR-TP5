package com.gare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class CentralServer {
	
	Log logger;
	static int nb_train_max = 10;
	static int nb_voyageur_max = 500;
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
	
	public Trajet getTrajet(Gare gareDepart, Gare gareArrivee) {
		for (Trajet trajet: trajets) {
			if (gareArrivee == null && trajet.gareDepart().getName() == gareDepart.getName()) {
				return trajet;
			}
			else if (gareDepart == null && trajet.gareArrivee().getName() == gareArrivee.getName()) {
				return trajet;
			}
			else if (trajet.gareDepart().getName() == gareDepart.getName() && trajet.gareArrivee().getName() == gareArrivee.getName()) {
				return trajet;
			}
		}
		return null;
	}

	synchronized public void retirerTrain(Train train) {
		retirerBillets(train);
		notifyAll();
	}
	
	synchronized public void ajouterBillets(Train train) {
		if (train.nbPlaces() != 0) {
			Trajet trajet = getTrajet(train.getGareActuelle(), null);
			train.setTrajet(trajet);
			for (int i=0;i<train.nbPlaces();i++) {
				Billet billet = new Billet(trajet);
				billet.setTrain(train);
				billets.add(billet);
			}
			logger.info("billet créé pour le " + train.toString() + " avec le " + trajet.toString());
		}
	}

	synchronized public Billet retirerBillet(Trajet trajet) {
		for (Billet billet: billets) {
			if (billet.getTrajet() == trajet) {
				billets.remove(billet);
				return billet;
			}
		}
		return null;
	}

	synchronized public ArrayList<Billet> retirerBillets(Train train) {
		ArrayList<Billet> billetsSupprimes = new ArrayList<Billet>();
		for (Billet billet: billets) {
			if (billet.getTrain() == train) {
				billets.remove(billet);
				billetsSupprimes.add(billet);
			}
		}
		notifyAll();
		return billetsSupprimes;
	}

	synchronized public int nbBillets() {
		return billets.size();
	}

	synchronized public Iterator<Trajet> getTrajets() {
		return trajets.iterator();
	}
	
	synchronized public int nbBillets(Train train) {
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
		launch.join();
		launch1.join();
		for (Gare gare: gares) {
			for (Voyageur voyageur: gare.voyageurs) {
				voyageur.start();
			}
		}
	}

}
