package com.gare;

import java.util.ArrayList;



public class Gare {

	Log logger;
	private String name;
	private CentralServer main;
	private static EspaceQuai quai;
	private EspaceVente vente;
	private int voyageurs_restants = CentralServer.nb_voyageur_max;
	ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	
	public Gare(String name, CentralServer main) {
		this.name = name;
		this.main = main;
		logger = new Log(this);
		vente = new EspaceVente(this);
		quai = new EspaceQuai(this);
	}

	public String getName() {
		return name;
	}
	
	public EspaceQuai getEspaceQuai() {
		return quai;
	}

	public Gare getGare(String name) {
		return main.getGare(name);
	}
	
	public CentralServer getMain() {
		return main;
	}
	
	public EspaceVente getEspaceVente() {
		return vente;
	}

	public String toString() {
		return "GARE(" + name + ")";
	}

	synchronized public void removeVoyageur(Voyageur voyageur) {
		voyageurs.remove(voyageur);		
	}

	synchronized public void addVoyageur(Voyageur voyageur) {
		voyageurs.add(voyageur);
	}
	
	synchronized public int getVoyageurs_restants() {
		return voyageurs_restants;
	}

	synchronized public void removeVoyageur() {
		this.voyageurs_restants--;
	}
}
