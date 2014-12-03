package com.transport.billeterie;

import com.transport.gare.Trajet;
import com.transport.trains.Train;

public class Billet {

	private Trajet trajet;
	private Train train;
	static int nb = 0;
	private int id;
	
	Billet(Trajet trajet) {
		this.id = Billet.nb++;
		this.trajet = trajet;
	}

	public int getId() {
		return id;
	}
	
	public void setTrain(Train train) {
		assert train.getGareActuelle() != trajet.gareDepart();
		this.train = train;
	}
	
	public Train getTrain() {
		return train;
	}

	public Trajet getTrajet() {
		return trajet;
	}
	
	public String toString() {
		return "<Billet " + train.toString() + " | " + trajet.toString() + ">";
	}
	
}
