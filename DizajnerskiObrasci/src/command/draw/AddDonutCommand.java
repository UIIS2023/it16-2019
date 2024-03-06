package command.draw;

import command.Command;
import geometry.Donut;
import geometry.Point;
import mvc.Model;

public class AddDonutCommand implements Command {

	private Model model;
	private Donut d;
	
	public AddDonutCommand(Model model, Donut d) {
		this.model = model;
		this.d=d;
	}
	
	@Override
	public void execute() {
		model.addShape(d);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(d);
	}
	@Override
	public String toString() {
		return "Draw Donut >>>" + d.getCenter().getX() + "," + d.getCenter().getY() + "," + d.getRadius() + ","+ d.getInnerRadius()+ "," + d.getColor().getRGB() + "," + d.getInnerColor().getRGB();  
	}
}
