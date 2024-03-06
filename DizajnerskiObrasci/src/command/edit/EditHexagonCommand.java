package command.edit;

import command.Command;
import geometry.Circle;
import hexagon.HexagonAdapter;

public class EditHexagonCommand  implements Command {


	private HexagonAdapter oldHexagon;
	private HexagonAdapter newHexagon;
	private HexagonAdapter orig;
	
	public EditHexagonCommand (HexagonAdapter old, HexagonAdapter newCircle) {
		this.oldHexagon = old;
		this.newHexagon=newCircle;
	}
	
	@Override
	public void execute() {
		this.orig=oldHexagon.clone();
		newHexagon.mapProps(oldHexagon);
	}

	@Override
	public void unexecute() {
		orig.mapProps(oldHexagon);
		
	}
	@Override
	public String toString() {
		return "Edit Hexagon >>>" + oldHexagon.getX() + "," + oldHexagon.getY() + "," + oldHexagon.getRadius() + "," + oldHexagon.getColor().getRGB() + "," + oldHexagon.getInnerColor().getRGB() + ">>>" + newHexagon.getX() + "," + newHexagon.getY() + "," + newHexagon.getRadius() + "," + newHexagon.getColor().getRGB() + "," + newHexagon.getInnerColor().getRGB();
	}

}
