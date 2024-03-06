package command.edit;

import command.Command;
import geometry.Donut;

public class EditDonutCommand implements Command {

	private Donut oldDonut;
	private Donut newDonut;
	private Donut orig;
	
	public EditDonutCommand (Donut old, Donut newDonut) {
		this.oldDonut = old;
		this.newDonut=newDonut;
	}
	
	@Override
	public void execute() {
		this.orig=oldDonut.clone();
		newDonut.mapProps(oldDonut);
	}

	@Override
	public void unexecute() {
		orig.mapProps(oldDonut);
		
	}
	@Override
	public String toString() {
		return "Edit Donut >>>" + oldDonut.getCenter().getX() + "," + oldDonut.getCenter().getY() + "," + oldDonut.getRadius() + ","+ oldDonut.getInnerRadius()+ "," + oldDonut.getColor().getRGB() + "," + oldDonut.getInnerColor().getRGB() + ">>>" + newDonut.getCenter().getX() + "," + newDonut.getCenter().getY() + "," + newDonut.getRadius() + ","+ newDonut.getInnerRadius()+ "," + newDonut.getColor().getRGB() + "," + newDonut.getInnerColor().getRGB();  
	}


}
