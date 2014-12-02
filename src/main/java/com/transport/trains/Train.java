package com.transport.trains;

import java.util.ArrayList;

import com.transport.gare.Gare;
import com.transport.gare.Trajet;
import com.transport.log.Log;
import com.transport.voyageurs.Voyageur;


public class Train extends Thread {

	public Log logger;
	private Gare  gare;
	private Trajet trajet;
	private Integer CAPACITE_TRAIN = 150;
	private Integer NB_PLACES = (int) (Math.random()*(CAPACITE_TRAIN));
	private Integer VITESSE_TRAIN = (int) (Math.random()*(3000-500))+500;
	private Integer ARRET_TRAIN = (int) (Math.random()*(10000-2000))+2000;
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();

	
	public Train(Gare gare){
		this.gare = gare;
		logger = new Log(this);
	}
	
	public int getAttente() {
		return ARRET_TRAIN / 1000;
	}
	
	public boolean estPlein() {
		return nbVoyageurs() == 0;
	}
	
	public void ajoutVoyageur(Voyageur voyageur){
		voyageurs.add(voyageur);
	}
	
	public Integer nbVoyageurs(){
		return voyageurs.size();
	} 
	
	public Integer nbPlaces(){
		return NB_PLACES;
	}

	private void viderTrain(Gare gare) {
		for (Voyageur voyageur: voyageurs) {
			gare.entrer(voyageur);
		}
	}

	public void faireQueue(Voyageur voyageur) {
		ajoutVoyageur(voyageur);
		trajet.gareDepart().sortir(voyageur);
	}
	
	public String toString() {
		return "<Train " + getId() + " en " + gare.toString() + ">"; 
	}

	public Trajet getTrajet() {
		return trajet;
	}

	public void setTrajet(Trajet trajet) {
		assert trajet.gareDepart() != gare;
		this.trajet = trajet;
		
	}

	public Gare getGareActuelle() {
		return gare;
	}
	
	public void run() {
		try {
			Thread.sleep(10000/VITESSE_TRAIN);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		gare.getEspaceQuai().entrerQuai(this);
		gare.getEspaceVente().declarerTrain(this);
		try {
			Thread.sleep(ARRET_TRAIN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gare.getEspaceVente().retirerTrain(this);
		gare.getEspaceQuai().sortirQuai(this);
		
		assert trajet != null;
		gare = trajet.gareArrivee();
		gare.getEspaceQuai().entrerQuai(this);
		viderTrain(trajet.gareArrivee());
		gare.getEspaceQuai().sortirQuai(this);
	}
}
