package command.draw;

import command.Command;
import geometry.Point;
import geometry.Shape;
import mvc.Model;

public class AddPointCommand implements Command {

	private Model model;
	private Point point;
	
	public AddPointCommand(Model model, Point point) {
		this.model = model;
		this.point=point;
	}
	
	@Override
	public void execute() {
		model.addShape(point);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(point);
	}
	@Override
	public String toString() {
		return "Draw Point >>>" + point.getX() + "," + point.getY() + "," + point.getColor().getRGB();  
	}
}
