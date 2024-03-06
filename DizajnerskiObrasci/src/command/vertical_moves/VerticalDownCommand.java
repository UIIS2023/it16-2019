package command.vertical_moves;

import command.Command;
import mvc.Model;

public class VerticalDownCommand implements Command {
	
	private Model model;
	private int index;
	
	
	public VerticalDownCommand(Model model, int index) {
		super();
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		model.swapShapes(index, index - 1);	
	}

	@Override
	public void unexecute() {
		model.swapShapes(index, index - 1);
	}
	
	@Override
	public String toString() {
		return "Vertical down >>>>" + index;
	}

}
