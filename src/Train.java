import java.util.ArrayList;



public class Train extends Thread{

	private Integer id;
	private Gare gare;
	private Integer CAPACITE_TRAIN = 200;
	private Integer NB_PLACES = (int) (Math.random()*(CAPACITE_TRAIN-0));
	private Integer VITESSE_TRAIN = (int) (Math.random()*(300-50))+50;
	private Integer ARRET_TRAIN = (int) (Math.random()*(30000-3000))+3000;
	private ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();

	
	public Train(Integer id, Gare gare){
		this.id = id;
		this.gare = gare;
	}

	public int getAttente() {
		return ARRET_TRAIN;
	}
	
	public boolean estPlein() {
		return nbVoyageurs() == 0;
	}
	
	synchronized public void ajoutVoyageur(Voyageur voyageur){
		System.out.println(" >Train " + this.id + " prend le voyageur " + voyageur.getId());
		voyageurs.add(voyageur);
	}
	
	synchronized public Integer nbVoyageurs(){
		return (CAPACITE_TRAIN - NB_PLACES) + voyageurs.size();
	} 
	
	synchronized public Integer nbPlaces(){
		return NB_PLACES - voyageurs.size();
	}
	
	public void run() {
		try {
			Thread.sleep(10000/VITESSE_TRAIN);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EspaceQuai quai = gare.getQuai();
		quai.entrerQuai(this);
		try {
			Thread.sleep(ARRET_TRAIN);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		quai.sortirQuai(this);	
	}
	
	public long getId() {
		return id;
	}
}
