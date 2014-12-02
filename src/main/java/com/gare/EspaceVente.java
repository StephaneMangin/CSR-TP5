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
		
		synchronized private Billet faireQueue(Voyageur voyageur) {
			logger.finest(voyageur.toString() + " en attente d'un billet...");
			return imprimeBillet(voyageur.getTrajet());
		}

		synchronized private Billet imprimeBillet(Trajet trajet) {
			logger.finest( "impression du billet...");
			try {
				Thread.sleep(IMPRESSION_TICKET);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return gare.getCentralServer().retirerBillet(trajet);
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
		gare.getCentralServer().ajouterBillets(train);
	}
	
	synchronized public Billet acheterBillet(Voyageur voyageur){
		while (gare.getCentralServer().nbBillets() == 0) {
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
		gare.getCentralServer().retirerTrain(train);
		
	}

	public Gare getGare() {
		return gare;
	}
}
