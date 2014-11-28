import java.util.ArrayList;



public class Gare {

	private static EspaceQuai quai;
	private EspaceVente vente;
	private int nb_voyageur_max = 10;
	private int nb_train_max = 2;
	private int voyageurs_restants = nb_voyageur_max;
	private ArrayList<Train> trains = new ArrayList<Train>();
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	ThreadGroup voyageursThread = new ThreadGroup("Voyageurs");
	ThreadGroup trainsThread = new ThreadGroup("Trains");
	
	public Gare() throws InterruptedException {
		quai = new EspaceQuai(this);
		vente = new EspaceVente(this);
		for (int i=0; i<nb_train_max;i++) {
			trains.add(new Train(voyageursThread, i, this));
		}
		for (int i=0; i<nb_voyageur_max;i++) {
			voyageurs.add(new Voyageur(trainsThread, i, this));
		}
		for (Train train: trains) {
			train.start();
		}
		for (Voyageur voyageur: voyageurs) {
			voyageur.start();
		}
	}

	public EspaceQuai getQuai() {
		return quai;
	}

	public EspaceVente getEspaceVente() {
		return vente;
	}

	synchronized public int getVoyageurs_restants() {
		return voyageurs_restants;
	}

	synchronized public void removeVoyageur() {
		this.voyageurs_restants--;
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Gare();
	}

}
