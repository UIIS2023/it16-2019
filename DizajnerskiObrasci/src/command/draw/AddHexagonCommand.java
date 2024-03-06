package command.draw;

import command.Command;
import geometry.Donut;
import hexagon.HexagonAdapter;
import mvc.Model;

public class AddHexagonCommand implements Command {

	private Model model;
	private HexagonAdapter h;
	
	public AddHexagonCommand(Model model, HexagonAdapter h) {
		this.model = model;
		this.h=h;
	}
	
	@Override
	public void execute() {
		model.addShape(h);
	}

	@Override
	public void unexecute() {
		model.getAll().remove(h);
	}
	@Override
	public String toString() {
		return "Draw Hexagon >>>" + h.getX() + "," + h.getY() + "," + h.getRadius() + "," + h.getColor().getRGB() + "," + h.getInnerColor().getRGB();  
	}
}
