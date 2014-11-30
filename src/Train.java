import java.util.ArrayList;



public class Train extends Thread {

	private Integer id;
	Log logger;
	private Integer CAPACITE_TRAIN = 10;
	private Integer NB_PLACES = (int) (Math.random()*(CAPACITE_TRAIN-0));
	private Integer VITESSE_TRAIN = (int) (Math.random()*(300-50))+50;
	private Integer ARRET_TRAIN = (int) (Math.random()*(30000-3000))+3000;
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
	EspaceQuai quai;
	EspaceVente vente;

	
	public Train(Integer id, Gare gare){
		this.id = id;
		quai = gare.getQuai();
		vente = gare.getEspaceVente();
		logger = new Log(this);
	}
	
	public long getId() {
		return id;
	}
	
	public int getAttente() {
		return ARRET_TRAIN / 1000;
	}
	
	public boolean estPlein() {
		return nbVoyageurs() == 0;
	}
	
	synchronized public void ajoutVoyageur(Voyageur voyageur){
		voyageurs.add(voyageur);
	}
	
	synchronized public Integer nbVoyageurs(){
		return voyageurs.size();
	} 
	
	synchronized public Integer nbPlaces(){
		return NB_PLACES;
	}
	
	public void run() {
		try {
			Thread.sleep(10000/VITESSE_TRAIN);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		quai.entrerQuai(this);
		try {
			Thread.sleep(ARRET_TRAIN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		vente.retirerTrain(this);
		quai.sortirQuai(this);
	}

	public void faireQueue(Voyageur voyageur) {
		ajoutVoyageur(voyageur);
		quai.removeVoyageur();
		
	}
	
	public String toString() {
		return quai.toString() + "::Train(" + id + ")"; 
	}

}
