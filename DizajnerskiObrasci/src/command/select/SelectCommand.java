package command.select;

import command.Command;
import geometry.Shape;
import mvc.Model;

public class SelectCommand implements Command {

	Shape shape;
	Model model;
	int index;

	public SelectCommand(Shape shape, Model model) {
		super();
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		shape.setSelected(true);
		index = model.getAll().indexOf(shape);
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
	}
	
	@Override
	public String toString() {
		return "Select >>>>" + index;
	}

}
