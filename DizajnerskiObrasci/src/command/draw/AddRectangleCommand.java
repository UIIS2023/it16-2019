package command.draw;

import command.Command;
import geometry.Point;
import geometry.Rectangle;
import mvc.Model;

public class AddRectangleCommand implements Command {
	private Model model;
	private Rectangle r;
	
	public AddRectangleCommand(Model model, Rectangle r) {
		this.model = model;
		this.r=r;
	}
	
	@Override
	public void execute() {
		model.addShape(r);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(r);
	}
	@Override
	public String toString() {
		return "Draw Rectangle >>>" + r.getUpperLeftPoint().getX() + "," + r.getUpperLeftPoint().getY() + "," + r.getWidth() + "," + r.getHeight()  + "," + r.getColor().getRGB() + "," + r.getInnerColor().getRGB();  
	}
}
