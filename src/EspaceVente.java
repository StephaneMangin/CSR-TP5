import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class EspaceVente {

	Log logger;
	private Gare gare;
	private int nbGuichets = 3;
	private Integer IMPRESSION_TICKET = 10;
	private Stack<Billet> billets = new Stack<Billet>();
	private List<EspaceVente.Guichet> guichets = new ArrayList<Guichet>();
	
	private class Guichet {

		private int id;
		Log logger;

		public Guichet(int id) {
			this.id = id;
			this.logger = new Log(this);
		}
		
		public void acheterBillet(Voyageur voyageur) {
			voyageur.setBillet(imprimeBillet());
			this.logger.finer(voyageur.toString());
		}
		
		public String toString() {
			return EspaceVente.this.toString() + "::Guichet(" + id + ")"; 
		}
	}
	
	public EspaceVente(Gare gare) {
		this.gare = gare;
		logger = new Log(this);
		for (int i=0;i< nbGuichets;i++) {
			guichets.add(new Guichet(i));
		}
	}

	synchronized public void declarerTrain(Train train){
		train.logger.info("déclare " + train.nbPlaces() + " place(s) disponible(s).");
		for (int i=0;i< train.nbPlaces();i++) {
			Billet billet = new Billet(train);
			billets.add(billet);
			notifyAll();
		}
		logger.config(train.nbPlaces() + "billet créé pour le train " + train.getId());
	}
	
	synchronized public void faireQueue(Voyageur voyageur){
		while (billets.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Guichet guichet = guichets.get((int) (Math.random()*(nbGuichets-1)));
		guichet.acheterBillet(voyageur);
	}

	synchronized public void retirerTrain(Train train) {
		for (Billet billet: billets) {
			if (billet.getTrain() == train) {
				billets.remove(billet);
			}
		}
		notifyAll();
	}
	
	synchronized public Billet imprimeBillet() {
		try {
			Thread.sleep(IMPRESSION_TICKET);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return billets.pop();
	}
	
	public String toString() {
		return gare.toString() + "::EspaceVente";
	}
}
