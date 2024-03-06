package command.draw;

import command.Command;
import geometry.Line;
import geometry.Point;
import mvc.Model;

public class AddLineCommand implements Command {
	
	private Model model;
	private Line line;
	
	public AddLineCommand(Model model, Line line) {
		this.model = model;
		this.line=line;
	}
	
	@Override
	public void execute() {
		model.addShape(line);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(line);
	}
	@Override
	public String toString() {
		return "Draw Line >>>" + line.getStartPoint().getX() + "," + line.getStartPoint().getY() + "," + line.getEndPoint().getX() + "," + line.getEndPoint().getY() + "," + line.getColor().getRGB();  
	}
}
