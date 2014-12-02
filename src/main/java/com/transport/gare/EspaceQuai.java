package com.transport.gare;

import java.util.ArrayList;

import com.transport.log.Log;
import com.transport.trains.Train;
import com.transport.voyageurs.Voyageur;


public class EspaceQuai {

	Log logger;
	private Gare gare;
	private Integer NB_VOIES = 3;
	private ArrayList<Train> trains = new ArrayList<Train>();
	
	public EspaceQuai(Gare gare) {
		this.gare = gare;
		
		logger = new Log(this);
	}
	
	synchronized public int getNbVoiesLibres() {
		return NB_VOIES - trains.size();
	}

	synchronized public void entrerQuai(Train train){
		while (getNbVoiesLibres() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		train.logger.info("entré en gare pour " + train.getAttente() + " secondes ...");
		trains.add(train);
		notifyAll();
	}

	synchronized public void sortirQuai(Train train){
		train.logger.info("quitte la gare avec " + train.nbVoyageurs() + " voyageur(s).");
		trains.remove(train);
		notifyAll();
	}
	
	synchronized public void faireQueue(Voyageur voyageur) {
		while (true) {
			for (Train train: trains){
				if (train.getId() == voyageur.getBillet().getTrain().getId()){
					train.faireQueue(voyageur);
					train.logger.finer(voyageur.toString() + " est entré.");
					notifyAll();
					return;
				}
			}
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String toString() {
		return gare.toString() + "::EspaceQuai";
	}

}
