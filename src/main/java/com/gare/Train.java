package com.gare;

import java.util.ArrayList;


public class Train extends Thread {

	Log logger;
	private Trajet trajet;
	private Integer CAPACITE_TRAIN = 10;
	private Integer NB_PLACES = (int) (Math.random()*(CAPACITE_TRAIN-1));
	private Integer VITESSE_TRAIN = (int) (Math.random()*(300-50))+50;
	private Integer ARRET_TRAIN = (int) (Math.random()*(30000-3000))+3000;
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();

	
	public Train(Trajet trajet){
		this.trajet = trajet;
		logger = new Log(this);
	}
	
	public int getAttente() {
		return ARRET_TRAIN / 1000;
	}
	
	public boolean estPlein() {
		return nbVoyageurs() == 0;
	}
	
	synchronized public void ajoutVoyageur(Voyageur voyageur){
		voyageurs.add(voyageur);
	}
	
	synchronized public Integer nbVoyageurs(){
		return voyageurs.size();
	} 
	
	synchronized public Integer nbPlaces(){
		return NB_PLACES;
	}
	
	public void run() {
		logger.config(" initialisé.");
		try {
			Thread.sleep(10000/VITESSE_TRAIN);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		trajet.gareDepart().getEspaceQuai().entrerQuai(this);
		trajet.gareDepart().getEspaceVente().declarerTrain(this);
		try {
			Thread.sleep(ARRET_TRAIN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		trajet.gareDepart().getEspaceVente().retirerTrain(this);
		trajet.gareDepart().getEspaceQuai().sortirQuai(this);
		
		trajet.gareArrivee().getEspaceQuai().entrerQuai(this);
		viderTrain(trajet.gareArrivee());
	}

	private void viderTrain(Gare gare) {
		for (Voyageur voyageur: voyageurs) {
			gare.addVoyageur(voyageur);
		}
	}

	public void faireQueue(Voyageur voyageur) {
		ajoutVoyageur(voyageur);
		trajet.gareDepart().removeVoyageur(voyageur);
	}
	
	public String toString() {
		return trajet.gareDepart().getEspaceQuai().toString() + "::Train(" + getId() + ")"; 
	}

	public Trajet getTrajet() {
		return trajet;
	}

}
