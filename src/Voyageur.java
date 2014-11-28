
public class Voyageur extends Thread {

	private Integer id;
	private boolean billet = false;
	private Gare gare;
	
	public Voyageur(Integer id, Gare gare){
		this.id = id;
		this.gare = gare;
	}
	
	public boolean isBillet() {
		return billet;
	}

	public void setBillet() {
		this.billet = true;
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
