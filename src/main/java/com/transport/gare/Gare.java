package com.transport.gare;

import java.util.ArrayList;
import java.util.Iterator;

import com.transport.billeterie.CentralServer;
import com.transport.log.Log;
import com.transport.voyageurs.Voyageur;



/**
 * Classe de modélisation d'une gare
 * 
 * @author blacknight
 *
 */
public class Gare {

	private Log log;
	private String name;
	private CentralServer centralServer;
	private static EspaceQuai quai;
	private EspaceVente vente;
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	
	public Gare(String name, CentralServer centralServer) {
		this.name = name;
		this.centralServer = centralServer;
		log = new Log(this);
		vente = new EspaceVente(this);
		quai = new EspaceQuai(this);
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
		return "GARE " + name;
	}

	public Iterator<Voyageur> getVoyageurs() {
		return voyageurs.iterator();
	}
	
	public int getNbVoyageurs() {
		return voyageurs.size();
	}
	
	synchronized public void sortir(Voyageur voyageur) {
		log.finest("sortie du " + voyageur.toString());
		voyageurs.remove(voyageur);
		notifyAll();
	}

	synchronized public void entrer(Voyageur voyageur) {
		log.finest("entrée du " + voyageur.toString());
		voyageurs.add(voyageur);
		notifyAll();
	}

	public String getName() {
		return name;
	}
}
