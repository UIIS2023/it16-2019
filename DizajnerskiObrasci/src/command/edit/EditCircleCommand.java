package command.edit;

import command.Command;
import geometry.Circle;

public class EditCircleCommand implements Command {


	private Circle oldCircle;
	private Circle newCircle;
	private Circle orig;
	
	public EditCircleCommand (Circle old, Circle newCircle) {
		this.oldCircle = old;
		this.newCircle=newCircle;
	}
	
	@Override
	public void execute() {
		this.orig=oldCircle.clone();
		newCircle.mapProps(oldCircle);
	}

	@Override
	public void unexecute() {
		orig.mapProps(oldCircle);
		
	}
	@Override
	public String toString() {
		return "Edit Circle >>>" + oldCircle.getCenter().getX() + "," + oldCircle.getCenter().getY() + "," + oldCircle.getRadius() + "," + oldCircle.getColor().getRGB() + "," + oldCircle.getInnerColor().getRGB() + ">>>" + newCircle.getCenter().getX() + "," + newCircle.getCenter().getY() + "," + newCircle.getRadius() + "," + newCircle.getColor().getRGB() + "," + newCircle.getInnerColor().getRGB();
	}

}
