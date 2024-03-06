package command.edit;

import command.Command;
import geometry.Point;

public class EditPointCommand implements Command {

	private Point oldPoint;
	private Point newPoint;
	private Point orig;
	
	public EditPointCommand (Point old, Point newPoint) {
		this.oldPoint = old;
		this.newPoint=newPoint;
	}
	
	@Override
	public void execute() {
		this.orig=oldPoint.clone();
		newPoint.mapProps(oldPoint);
	}

	@Override
	public void unexecute() {
		orig.mapProps(oldPoint);
		
	}
	@Override
	public String toString() {
		return "Edit Point >>>" + oldPoint.getX() + "," + oldPoint.getY() + "," + oldPoint.getColor().getRGB() + ">>>" + newPoint.getX() + "," + newPoint.getY() + "," + newPoint.getColor().getRGB();  
	}

}
