import java.util.ArrayList;
import java.util.Stack;


public class EspaceVente {

	private Integer IMPRESSION_TICKET = 1000;
	private Stack<Billet> billets = new Stack<Billet>();
	
	synchronized public void ajoutTrain(Train train){
		for (int i=0;i< train.nbPlaces();i++) {
			billets.add(new Billet(train));
		}
	}
	
	synchronized public void acheterBillet(Voyageur voyageur){
		try {
			Thread.sleep(IMPRESSION_TICKET);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		voyageur.setBillet(billets.pop());
		notifyAll();
	}
	
}
