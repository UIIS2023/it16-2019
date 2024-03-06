package command.vertical_moves;

import command.Command;
import geometry.Shape;
import mvc.Model;

public class VerticalUpEndCommand implements Command {
	
	private Model model;
	private Shape shape;
	private int currentIndex;
	
	
	public VerticalUpEndCommand(Model model, int currentIndex) {
		this.model = model;
		this.currentIndex = currentIndex;
	}

	@Override
	public void execute() {
		this.shape = model.getAll().get(currentIndex);
		model.getAll().remove(shape);
		model.getAll().add(shape);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(shape);
		model.getAll().add(currentIndex, shape);
		
	}
	
	@Override
	public String toString() {
		return "Vertical up end >>>>" + currentIndex;
	}

}
