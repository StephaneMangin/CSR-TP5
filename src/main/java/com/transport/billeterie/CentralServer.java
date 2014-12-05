package com.transport.billeterie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

import com.transport.gare.Gare;
import com.transport.trains.Train;
import com.transport.trains.TrainLauncher;
import com.transport.voyageurs.VoyageursLauncher;

/**
 * Classe centrale d'initialisation des objets.
 * 
 * Instancie les lanceurs de threads et le serveur REST.
 * 
 * @author blacknight
 *
 */
public class CentralServer {
	
	/**
	 * Nombre maximum de train à instancier
	 */
	private static int nb_train_max = 80;
	/**
	 * Nombre maximum de voyageur à instancier
	 */
	private static int nb_voyageur_max = 300;
	/**
	 * Collection des gares disponibles.
	 */
	private static ArrayList<Gare> gares = new ArrayList<Gare>();
	/**
	 * Collection des trajets disponibles.
	 */
	private static ArrayList<Trajet> trajets = new ArrayList<Trajet>();
	/**
	 * Pile des billets disponibles.
	 */
	private ArrayList<Billet> billets = new ArrayList<Billet>();
	
	public CentralServer() throws Exception {
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

	/**
	 * Retourne la gare associée
	 * 
	 * @param name
	 * @return
	 */
	public synchronized static Gare getGare(String name) {
		for (Gare gare: gares) {
			if (gare.getName().equals(name)) {
				return gare;
			}
		}
		return null;
	}

	/**
	 * Retourne un itérateur sur la collection des gares
	 * 
	 * @return
	 */
	public synchronized static Iterator<Gare> getGares() {
		return gares.iterator();
	}
	
	/**
	 * Retourne un itérateur sur la pile des billets
	 * 
	 * @return
	 */
	public synchronized Iterator<Billet> getBillets() {
		return billets.iterator();
	}
	
	/**
	 * Retourne le trajet associé
	 * 
	 * @param gareDepart
	 * @param gareArrivee
	 * @return
	 */
	public synchronized static Trajet getTrajet(Gare gareDepart, Gare gareArrivee) {
		for (Trajet trajet: trajets) {
			if (gareArrivee == null && trajet.gareDepart() == gareDepart) {
				return trajet;
			}
			else if (gareDepart == null && trajet.gareArrivee() == gareArrivee) {
				return trajet;
			}
			else if (trajet.gareDepart() == gareDepart && trajet.gareArrivee() == gareArrivee) {
				return trajet;
			}
		}
		return null;
	}
	
	/**
	 * Créé les billets pour le train fourni en paramètre
	 * 
	 * @param train
	 */
	public synchronized void ajouterBillets(Train train) {
		if (train.nbPlaces() != 0) {
			for (int i=0;i<train.nbPlaces();i++) {
				Billet billet = new Billet(train.getTrajet());
				billet.setTrain(train);
				billets.add(billet);
			}
		}
	}

	/**
	 * Retourne le billet pour ce trajet après suppression de la liste des billets
	 * 
	 * @param trajet
	 * @return
	 */
	public synchronized Billet retirerBillet(Trajet trajet) {
		for (Billet billet: billets) {
			if (billet.getTrajet() == trajet) {
				billets.remove(billet);
				return billet;
			}
		}
		return null;
	}
	
	/**
	 * Retourne la liste de tous les billets pour ce train
	 * après leur retrait de la liste des billets.
	 * 
	 * @param train
	 * @return
	 */
	public synchronized ArrayList<Billet> retirerBillets(Train train) {
		ArrayList<Billet> billetsSupprimes = new ArrayList<Billet>();
		for (Billet billet: billets) {
			if (billet.getTrain() == train) {
				billets.remove(billet);
				billetsSupprimes.add(billet);
			}
		}
		return billetsSupprimes;
	}

	/**
	 * Retourne le nombre total de billets.
	 * 
	 * @return
	 */
	public synchronized int nbBillets() {
		return billets.size();
	}

	/**
	 * Retourne un itérateur sur la collection des trajets
	 * 
	 * @return
	 */
	public synchronized static Iterator<Trajet> getTrajets() {
		return trajets.iterator();
	}

	/**
	 * Retourne une gare au hasard
	 * 
	 * @return
	 */
	public synchronized static Gare getRandomGare() {
		return gares.get((int) (Math.random()*(gares.size()-1)));
	}

	/**
	 * Retourne un trajet au hasard
	 * 
	 * @return
	 */
	public synchronized static Trajet getRandomTrajet() {
		return trajets.get((int) (Math.random()*(trajets.size()-1)));
	}
	
	/**
	 * Retourne le nombre de billets restants pour ce trajet
	 * 
	 * @param trajet
	 * @return
	 */
	public synchronized int nbBillets(Trajet trajet) {
		int i = 0;
		for (Billet billet: billets) {
			if (billet.getTrajet() == trajet) {
				i++;
			}
		}
		return i;
	}
	
	public String toString() {
		return "CentralServer::";
	}
    
	public static void main(String[] args) throws Exception {
        // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8124);
                
        // Create an application
        Application application = new CentralApplication(context);
        component.getDefaultHost().attach(application);
        
        // Start the component
        component.start();
        
		new CentralServer();
		Thread launch1 = new VoyageursLauncher(nb_voyageur_max);
		Thread launch = new TrainLauncher(nb_train_max);
		launch.start();
		launch1.start();
		launch.join();
		launch1.join();
	}

}
