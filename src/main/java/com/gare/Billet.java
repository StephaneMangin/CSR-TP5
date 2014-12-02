package com.gare;

public class Billet {

	private Trajet trajet;
	private Train train;
	
	Billet(Trajet trajet) {
		this.trajet = trajet;
	}

	public void setTrain(Train train) {
		this.train = train;
		this.trajet = train.getTrajet();
	}
	
	public Train getTrain() {
		return train;
	}

	public Gare getGareArrivee() {
		return trajet.gareArrivee();
	}
	
	public Gare getGareDepart() {
		return trajet.gareDepart();
	}
}
