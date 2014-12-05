package com.transport.billeterie;

import com.transport.log.Log;
import com.transport.trains.Train;

public class Billet {

	private Log log;
	private Trajet trajet;
	private Train train;
	private static int nb = 0;
	private int id;
	
	Billet(Trajet trajet) {
		this.id = Billet.nb++;
		this.trajet = trajet;
		log = new Log(this);
	}

	/**
	 * Retourne l'id du billet.
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Défini le train
	 * 
	 * @param train
	 */
	public void setTrain(Train train) {
		if (train.getTrajet() != trajet) {
			log.severe(" trajet incorrect !");
		}
		this.train = train;
	}
	
	/**
	 * Retourne le train associée
	 * 
	 * @return
	 */
	public Train getTrain() {
		return train;
	}

	/**
	 * Retourne le trajet associé
	 * 
	 * @return
	 */
	public Trajet getTrajet() {
		return trajet;
	}
	
	public String toString() {
		return "<Billet " + trajet.toString() + ">";
	}
	
}
