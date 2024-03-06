package command.draw;

import command.Command;
import geometry.Circle;
import geometry.Point;
import mvc.Model;

public class AddCircleCOmmand implements Command {
	private Model model;
	private Circle c;
	
	public AddCircleCOmmand(Model model, Circle c) {
		this.model = model;
		this.c=c;
	}
	
	@Override
	public void execute() {
		model.addShape(c);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(c);
	}
	
	@Override
	public String toString() {
		return "Draw Circle >>>" + c.getCenter().getX() + "," + c.getCenter().getY() + "," + c.getRadius() + "," + c.getColor().getRGB() + "," + c.getInnerColor().getRGB();  
	}
}
