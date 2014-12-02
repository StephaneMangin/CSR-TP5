package com.gare;

import java.util.ArrayList;



public class Gare {

	Log log;
	private String name;
	private CentralServer centralServer;
	private static EspaceQuai quai;
	private EspaceVente vente;
	ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	
	public Gare(String name, CentralServer centralServer) {
		this.name = name;
		this.centralServer = centralServer;
		log = new Log(this);
		vente = new EspaceVente(this);
		quai = new EspaceQuai(this);
	}

	public String getName() {
		return name;
	}
	
	public EspaceQuai getEspaceQuai() {
		return quai;
	}
	
	public CentralServer getCentralServer() {
		return centralServer;
	}
	
	public EspaceVente getEspaceVente() {
		return vente;
	}

	public String toString() {
		return "GARE(" + name + ")";
	}

	synchronized public void sortir(Voyageur voyageur) {
		log.finest("sortie du " + voyageur.toString());
		voyageurs.remove(voyageur);		
	}

	synchronized public void entrer(Voyageur voyageur) {
		log.finest("entrée du " + voyageur.toString());
		voyageurs.add(voyageur);
	}
}
