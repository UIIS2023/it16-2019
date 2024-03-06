package command.delete;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import geometry.Shape;
import mvc.Model;

public class CmdDelete implements Command {

	List<ShapeIndex> list = new ArrayList<ShapeIndex>();
	List<Shape> shapesToDelete = new ArrayList<Shape>();
	private Model model;
	
	public CmdDelete(List<Shape> shapes, Model model) {
		shapesToDelete = shapes;
		this.model = model;
	}
	
	@Override
	public void execute() {
		for(Shape sh : shapesToDelete) {
			ShapeIndex si = new ShapeIndex();
			si.index = model.getAll().indexOf(sh);
			si.shape = sh;
			list.add(si);
		}
		for(Shape sh : shapesToDelete) {
			model.getAll().remove(sh);
		}
		
	}

	@Override
	public void unexecute() {
		for(ShapeIndex si:list) {
			model.getAll().add(si.index, si.shape);
		}
		
	}
	
	@Override
	public String toString() {
		String string = "Delete >>>>";
		for(ShapeIndex si:list) {
			string += si.index + ",";
		}
		return string;
	}

}
