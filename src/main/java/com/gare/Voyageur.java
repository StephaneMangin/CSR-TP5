package com.gare;


public class Voyageur extends Thread {

	private Billet billet;
	private EspaceVente espaceVente;
	
	public Voyageur(Gare gareInit){
		this.espaceVente = gareInit.getEspaceVente();
	}
	
	public Billet getBillet() {
		return billet;
	}

	public void setBillet(Billet billet) {
		if (this.billet == null) {
			this.billet = billet;
		}
	}

	public Gare getGareArrivee() {
		return billet.getGareArrivee();
	}
	
	public Gare getGareDepart() {
		return billet.getGareDepart();
	}
	
	public void run() {
		billet = espaceVente.acheterBillet(this);
		getGareDepart().getEspaceQuai().faireQueue(this);
	}

	public String toString() {
		return "Voyageur(" + getId() + ")"; 
	}
}
