import java.util.ArrayList;
import java.util.logging.Level;



public class Gare {

	Log logger;
	private static EspaceQuai quai;
	private EspaceVente vente;
	private int nb_voyageur_max = 50;
	private int nb_train_max = 5;
	private int voyageurs_restants = nb_voyageur_max;
	private ArrayList<Train> trains = new ArrayList<Train>();
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	
	public Gare() throws InterruptedException {
		logger = new Log(this);
		logger.setLevel(Level.INFO);
		quai = new EspaceQuai(this);
		vente = new EspaceVente(this);
		for (int i=0; i<nb_train_max;i++) {
			trains.add(new Train(i, this));
		}
		for (int i=0; i<nb_voyageur_max;i++) {
			voyageurs.add(new Voyageur(i, this));
		}
		for (Train train: trains) {
			train.start();
		}
		for (Voyageur voyageur: voyageurs) {
			voyageur.start();
		}
		for (Train train: trains) {
			train.join();
		}
		for (Voyageur voyageur: voyageurs) {
			voyageur.join();
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
	
	public String toString() {
		return "GARE";
	}
	public static void main(String[] args) throws InterruptedException {
		new Gare();
	}

}
