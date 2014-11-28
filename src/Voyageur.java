
public class Voyageur extends Thread {

	private Integer id;
	private Billet billet;
	private Gare gare;
	
	public Voyageur(ThreadGroup group, Integer id, Gare gare){
		super(group, id.toString());
		this.id = id;
		this.gare = gare;
	}
	
	public long getId() {
		return id;
	}
	
	public Billet getBillet() {
		return billet;
	}

	public void setBillet(Billet billet) {
		this.billet = billet;
	}
	
	public void run() {
		EspaceVente vente = gare.getEspaceVente();
		try {
			vente.acheterBillet(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		EspaceQuai quai = gare.getQuai();
		try {
			quai.faireQueue(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
