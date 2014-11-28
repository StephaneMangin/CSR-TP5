
public class Billet {

	private Train train;
	
	Billet(Train train) {
		this.setTrain(train);
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}
	
}
