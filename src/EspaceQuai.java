import java.util.ArrayList;


public class EspaceQuai {

	private Gare gare;
	private Integer NB_VOIES = 3;
	private ArrayList<Train> trains = new ArrayList<Train>();
	
	public EspaceQuai(Gare gare) {
		this.gare = gare;
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
		System.out.println(">Train " + train.getId() + " entre en gare avec " + train.nbPlaces() + " places dispo. Attente prévue : " + train.getAttente());
		trains.add(train);
		notifyAll();
	}

	synchronized public void sortirQuai(Train train){
		trains.remove(train);
		System.out.println("<Train " + train.getId() + " quitte la gare avec " + train.nbPlaces() + " places dispo.");
		System.out.println("Voyageurs restant: " + gare.getVoyageurs_restants());
		notifyAll();
	}
	
	synchronized public void faireQueue(Voyageur voyageur) throws InterruptedException{
		while (true) {
			for (Train train: trains){
				if (train.getId() == voyageur.getBillet().getTrain().getId()){
					train.faireQueue(voyageur);
					notifyAll();
					return;
				} else {
					wait();
				}
			}
		}
	}
	
}
