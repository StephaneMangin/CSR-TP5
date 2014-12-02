package com.gare;


public class Voyageur extends Thread {

	private Log log;
	private Billet billet;
	private Trajet trajet;
	private EspaceVente espaceVente;
	
	public Voyageur(Gare gareInit, Trajet trajet){
		log = new Log(this);
		this.espaceVente = gareInit.getEspaceVente();
		this.trajet = trajet;
	}
	
	public Billet getBillet() {
		return billet;
	}
	
	public Trajet getTrajet() {
		return trajet;
	}
	
	public void setBillet(Billet billet) {
		if (this.billet == null) {
			log.severe("a déjà un billet");
		}
		if (billet.getTrain().getTrajet() == this.trajet) {
			log.severe("le trajet du billet n'est pas le bon.");
		}
		log.finest("a son billet pour " + billet.getTrain().getTrajet().toString());
		this.billet = billet;
	}
	
	public void run() {
		billet = espaceVente.acheterBillet(this);
		if (billet.getTrajet().gareDepart() != espaceVente.getGare()) {
			espaceVente.getGare().sortir(this);
			billet.getTrajet().gareDepart().entrer(this);
		}
		billet.getTrajet().gareDepart().getEspaceQuai().faireQueue(this);
	}

	public String toString() {
		return "Voyageur(" + getId() + ")"; 
	}
}
