package com.gare;

import java.util.ArrayList;
import java.util.List;


public class EspaceVente  {

	Log logger;
	private Gare gare;
	private int nbGuichets = 3;
	private Integer IMPRESSION_TICKET = 10;
	private List<EspaceVente.Guichet> guichets = new ArrayList<Guichet>();
	
	private class Guichet {

		private int id;
		Log logger;

		public Guichet(int id) {
			this.id = id;
			this.logger = new Log(this);
		}
		
		private Billet faireQueue(Voyageur voyageur) {
			this.logger.finer(voyageur.toString());
			return imprimeBillet(gare, gare);
		}

		private Billet imprimeBillet(Gare gareDepart, Gare gareArrivee) {
			try {
				Thread.sleep(IMPRESSION_TICKET);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return gare.getMain().popBillet(gareDepart, gareArrivee);
		}
		
		public String toString() {
			return EspaceVente.this.toString() + "::Guichet(" + id + ")"; 
		}
	}
	
	public EspaceVente(Gare gare) {
		super();
		this.gare = gare;
		logger = new Log(this);
		for (int i=0;i< nbGuichets;i++) {
			guichets.add(new Guichet(i));
		}
	}
 
	public void declarerTrain(Train train){
		train.logger.info("déclare " + train.nbPlaces() + " place(s) disponible(s).");
		gare.getMain().addBillets(train);
	}
	
	synchronized public Billet acheterBillet(Voyageur voyageur){
		while (gare.getMain().getNbBillets() == 0) {
			logger.finest(voyageur.toString() + " en attente d'un billet...");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Guichet guichet = guichets.get((int) (Math.random()*(nbGuichets-1)));
		return guichet.faireQueue(voyageur);
	}
	
	public String toString() {
		return gare.toString() + "::EspaceVente";
	}

	public void retirerTrain(Train train) {
		gare.getMain().retirerTrain(train);
		
	}
}
