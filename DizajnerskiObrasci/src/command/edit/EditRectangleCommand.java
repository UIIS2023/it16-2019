package command.edit;

import command.Command;
import geometry.Circle;
import geometry.Rectangle;

public class EditRectangleCommand implements Command {


	private Rectangle oldRectangle;
	private Rectangle newRectangle;
	private Rectangle orig;
	
	public EditRectangleCommand (Rectangle old, Rectangle newRectangle) {
		this.oldRectangle = old;
		this.newRectangle=newRectangle;
	}
	
	@Override
	public void execute() {
		this.orig=oldRectangle.clone();
		newRectangle.mapProps(oldRectangle);
	}

	@Override
	public void unexecute() {
		orig.mapProps(oldRectangle);
		
	}
	@Override
	public String toString() {
		return "Edit Rectangle >>>" + oldRectangle.getUpperLeftPoint().getX() + "," + oldRectangle.getUpperLeftPoint().getY() + "," + oldRectangle.getWidth() + "," + oldRectangle.getHeight()  + "," + oldRectangle.getColor().getRGB() + "," + oldRectangle.getInnerColor().getRGB() + ">>>" + + newRectangle.getUpperLeftPoint().getX() + "," + newRectangle.getUpperLeftPoint().getY() + "," + newRectangle.getWidth() + "," + newRectangle.getHeight()  + "," + newRectangle.getColor().getRGB() + "," + newRectangle.getInnerColor().getRGB();  
	}
}
