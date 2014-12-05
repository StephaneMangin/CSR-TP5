package com.transport.gare;

import java.util.ArrayList;
import java.util.List;
import com.transport.billeterie.CentralServer;
import com.transport.log.Log;
import com.transport.trains.Train;
import com.transport.voyageurs.Voyageur;

/**
 * Simule un espace de vente contenant des guichets
 * 
 * @author blacknight
 *
 */
public class EspaceVente  {

	private Log logger;
	private Gare gare;
	/**
	 * Nombre max de guichet à créer
	 */
	private int nbGuichets = 3;
	/**
	 * Liste des guichets
	 */
	private List<Guichet> guichets = new ArrayList<Guichet>();
	
	/**
	 * Chaque espace de vente est attribué à une gare
	 * 
	 * @param gare
	 */
	public EspaceVente(Gare gare) {
		super();
		this.gare = gare;
		logger = new Log(this);
		for (int i=0;i< nbGuichets;i++) {
			guichets.add(new Guichet(i, this));
		}
	}
 
	/**
	 * Déclare un train comme disponible et créer autant de billets correspondant
	 * à son nombre de place disponible
	 * 
	 * @param train
	 */
	synchronized public void declarerTrain(Train train){
		train.setTrajet(CentralServer.getTrajet(gare, null));
		train.logger.info("déclare " + train.nbPlaces() + " place(s) disponible(s).");
		CentralServer.ajouterBillets(train);
		logger.info("billet créé pour le " + train.toString() + " avec le " + train.getTrajet().toString());
		notifyAll();
	}
	
	/**
	 * Tant qu'aucun billet n'est disponible pour le trajet du voyageur, celui-ci est mis en attente.
	 * Une fois disponible, le voyageur fait la queue à un guichet selectionné aléatoirement
	 * 
	 * @param voyageur
	 */
	synchronized public void faireQueue(Voyageur voyageur){
		while (CentralServer.nbBillets(voyageur.getTrajet()) == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Guichet guichet = guichets.get((int) (Math.random()*(nbGuichets-1)));
		guichet.faireQueue(voyageur);
		notifyAll();
	}

	/**
	 * Retire tous les billets pour le train donné
	 * 
	 * @param train
	 */
	synchronized public void retirerTrain(Train train) {
		CentralServer.retirerBillets(train);
		notifyAll();
	}
	
	public String toString() {
		return gare.toString() + "::EspaceVente";
	}
}
