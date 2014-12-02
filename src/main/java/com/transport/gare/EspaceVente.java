package com.transport.gare;

import java.util.ArrayList;
import java.util.List;

import com.transport.billeterie.Billet;
import com.transport.log.Log;
import com.transport.trains.Train;
import com.transport.voyageurs.Voyageur;


public class EspaceVente  {

	Log logger;
	private Gare gare;
	private int nbGuichets = 3;
	private List<Guichet> guichets = new ArrayList<Guichet>();

	
	public EspaceVente(Gare gare) {
		super();
		this.gare = gare;
		logger = new Log(this);
		for (int i=0;i< nbGuichets;i++) {
			guichets.add(new Guichet(i, this));
		}
	}
 
	synchronized public void declarerTrain(Train train){
		train.logger.info("déclare " + train.nbPlaces() + " place(s) disponible(s).");
		gare.getCentralServer().ajouterBillets(train);
		logger.info("billet créé pour le " + train.toString() + " avec le " + train.getTrajet().toString());
		notifyAll();
	}
	
	synchronized public Billet faireQueue(Voyageur voyageur){
		while (gare.getCentralServer().nbBillets(voyageur.getTrajet()) == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Guichet guichet = guichets.get((int) (Math.random()*(nbGuichets-1)));
		notifyAll();
		return guichet.faireQueue(voyageur);
	}

	public Gare getGare() {
		return gare;
	}
	
	public String toString() {
		return gare.toString() + "::EspaceVente";
	}

	synchronized public void retirerTrain(Train train) {
		gare.getCentralServer().retirerTrain(train);
		notifyAll();
	}
}
