package command.edit;

import command.Command;
import geometry.Line;

public class EditLineCommand implements Command {

	private Line oldLine;
	private Line newLine;
	private Line orig;
	
	public EditLineCommand (Line old, Line newLine) {
		this.oldLine = old;
		this.newLine=newLine;
	}
	
	@Override
	public void execute() {
		this.orig=oldLine.clone();
		newLine.mapProps(oldLine);
	}

	@Override
	public void unexecute() {
		orig.mapProps(oldLine);
		
	}
	
	@Override
	public String toString() {
		return "Edit Line >>>" + oldLine.getStartPoint().getX() + "," + oldLine.getStartPoint().getY() + oldLine.getEndPoint().getX() + "," + oldLine.getEndPoint().getY() + "," + oldLine.getColor().getRGB() + ">>>" + + newLine.getStartPoint().getX() + "," + newLine.getStartPoint().getY() + newLine.getEndPoint().getX() + "," + newLine.getEndPoint().getY() + "," + newLine.getColor().getRGB();  
	}

}
