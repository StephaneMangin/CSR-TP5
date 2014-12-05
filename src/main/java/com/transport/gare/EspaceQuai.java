package com.transport.gare;

import java.util.ArrayList;

import com.transport.log.Log;
import com.transport.trains.Train;
import com.transport.voyageurs.Voyageur;


public class EspaceQuai {

	private Log logger;
	private Gare gare;
	private static Integer NB_VOIES = 3;
	private ArrayList<Train> trains = new ArrayList<Train>();
	
	public EspaceQuai(Gare gare) {
		this.gare = gare;
		logger = new Log(this);
	}
	
	/**
	 * Retourne le nombre de voie libre.
	 * 
	 * @return
	 */
	synchronized public int getNbVoiesLibres() {
		notifyAll();
		return NB_VOIES - trains.size();
	}

	/**
	 * Ajoute un train aux quai.
	 * 
	 * @param train
	 */
	synchronized public void entrerQuai(Train train){
		while (getNbVoiesLibres() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info(train.toString() + " entré en gare pour " + train.getAttente() + " secondes ...");
		trains.add(train);
		notifyAll();
	}

	/**
	 * Retire un train du quai.
	 * 
	 * @param train
	 */
	synchronized public void sortirQuai(Train train){
		logger.info(train.toString() + " quitte la gare avec " + train.nbVoyageurs() + " voyageur(s).");
		trains.remove(train);
		notifyAll();
	}
	
	/**
	 * Ajouter un voyageur aux files d'attentes des trains
	 * 
	 * @param voyageur
	 */
	synchronized public void faireQueue(Voyageur voyageur) {
		while (true) {
			for (Train train: trains){
				if (train.getId() == voyageur.getBillet().getTrain().getId()){
					train.faireQueue(voyageur);
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
