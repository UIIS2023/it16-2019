package command.vertical_moves;

import command.Command;
import geometry.Shape;
import mvc.Model;

public class VerticalDownEndCommand implements Command {
	
	private Model model;
	private Shape shape;
	private int currentIndex;

	public VerticalDownEndCommand(Model model, int index) {
		this.model = model;
		this.currentIndex = index;
	}

	@Override
	public void execute() {
		shape = model.getAll().get(currentIndex);
		model.getAll().remove(shape);
		model.getAll().add(0, shape);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(shape);
		model.getAll().add(currentIndex,shape);
	}

	@Override
	public String toString() {
		return "Vertical down end >>>>" + currentIndex;
	}

}
