package save;

public class SaveManager implements SaveStrategy {

	private SaveStrategy strategy;
	
	@Override
	public void save() {
		this.strategy.save();
	}
	
	public void setStrategy(SaveStrategy strategy) {
		this.strategy = strategy;
	}

}
