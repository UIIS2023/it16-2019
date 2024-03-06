package command.select;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import geometry.Shape;
import mvc.Model;

public class UnSelectCommand implements Command {

	List<Shape> shapes;
	Model model;
	List<Integer> indexList = new ArrayList<Integer>();
	
	public UnSelectCommand(List<Shape> shapes, Model model) {
		super();
		this.shapes = shapes;
		this.model = model;
	}

	@Override
	public void execute() {
		for(Shape s: shapes) {
			s.setSelected(false);
			indexList.add(model.getAll().indexOf(s));
		}
	}

	@Override
	public void unexecute() {
		for(Shape s: shapes) {
			s.setSelected(true);
		}
	}
	
	@Override
	public String toString() {
		String string = "Unselect >>>>";
		for(int index:indexList) {
			string += index + ",";
		}
		return string;
	}

}
