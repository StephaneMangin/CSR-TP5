import java.util.ArrayList;



public class Gare {

	private static EspaceQuai quai;
	private static EspaceVente vente;
	private static int nb_voyageur_max = 150;
	private static int nb_train_max = 6;
	private int voyageurs_restants = nb_voyageur_max;
	static ArrayList<Train> trains = new ArrayList<Train>();
	static ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	
	public Gare() {
		
		quai = new EspaceQuai(this);
		vente = new EspaceVente();

	}

	public EspaceQuai getQuai() {
		return quai;
	}

	public EspaceVente getEspaceVente() {
		return vente;
	}
	
	synchronized public void addVoyageur(Voyageur voyageur) {
		voyageurs.add(voyageur);
	}
	
	synchronized public void addTrain(Train train) {
		trains.add(train);
	}
	
	public static void main(String[] args) throws InterruptedException {

		Gare gare = new Gare();

		quai = gare.getQuai();
		
		for (int i=0; i<nb_voyageur_max;i++) {
			gare.addVoyageur(new Voyageur(i, gare));
		}
		for (int i=0; i<nb_train_max;i++) {
			gare.addTrain(new Train(i, gare));
		}
		for (Voyageur voyageur: voyageurs){
			voyageur.start();
		}
		for (Train train: trains){
			train.start();
		}
		for (Voyageur voyageur: voyageurs){
			voyageur.join();
		}
		for (Train train: trains){
			train.join();
		}
	}

	synchronized public int getVoyageurs_restants() {
		return voyageurs_restants;
	}

	synchronized public void removeVoyageur() {
		this.voyageurs_restants--;
	}

}
