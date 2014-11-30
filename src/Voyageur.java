
public class Voyageur extends Thread {

	private Integer id;
	private Billet billet;
	private Gare gare;
	
	public Voyageur(Integer id, Gare gare){
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
		if (this.billet == null) {
			this.billet = billet;
		}
	}
	
	public void run() {
		
		gare.getEspaceVente().faireQueue(this);
		gare.getQuai().faireQueue(this);
	}

	public String toString() {
		return "Voyageur(" + id + ")"; 
	}
}
