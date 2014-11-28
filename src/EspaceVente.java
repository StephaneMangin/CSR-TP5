import java.util.Stack;


public class EspaceVente {

	private Gare gare;
	private Integer IMPRESSION_TICKET = 1000;
	private Stack<Billet> billets = new Stack<Billet>();
	
	public EspaceVente(Gare gare) {
		this.gare = gare;
	}

	synchronized public void declarerTrain(Train train){
		System.out.println("? Train " + train.getId() + " a déclaré " + train.nbPlaces() + " places.");
		for (int i=0;i< train.nbPlaces();i++) {
			Billet billet = new Billet(train);
			billets.add(billet);
			notifyAll();
		}
	}
	
	synchronized public void acheterBillet(Voyageur voyageur) throws InterruptedException{
		while (this.billets.size() == 0) {
			wait();
		}
		Thread.sleep(IMPRESSION_TICKET);
		voyageur.setBillet(billets.pop());
		notifyAll();
	}
	
}
