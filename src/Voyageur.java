
public class Voyageur extends Thread {

	private Integer id;
	private Billet billet;
	private Gare gare;
	
	public Voyageur(Integer id, Gare gare){
		this.id = id;
		this.gare = gare;
	}
	
	public Billet getBillet() {
		return billet;
	}

	public void setBillet(Billet billet) {
		this.billet = billet;
	}
	
	public void run() {
		EspaceVente vente = gare.getEspaceVente();
		vente.acheterBillet(this);
		EspaceQuai quai = gare.getQuai();
		quai.faireQueue(this);
	}
	public long getId() {
		return id;
	}

}
