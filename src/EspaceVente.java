import java.util.ArrayList;


public class EspaceVente {

	private Integer IMPRESSION_TICKET = 1000;

	private static ArrayList<Train> trains = new ArrayList<Train>();
	
	synchronized public void ajoutTrain(Train train){
		trains.add(train);
	}
	
	public EspaceVente() {
	}

	synchronized public void acheterBillet(Voyageur voyageur){
		try {
			Thread.sleep(IMPRESSION_TICKET);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		voyageur.setBillet();
		notifyAll();
	}
	
}
