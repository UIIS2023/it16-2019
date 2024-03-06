package mvc;

import java.util.ArrayList;
import java.util.List;

import geometry.Shape;

public class Model {

	private List<Shape> allShapes=new ArrayList<Shape>();
	
	public List<Shape> getAll(){
		return allShapes;
	}
	public Shape getSelected() {
		for (Shape s : allShapes ) {
			if (s.isSelected()) {
				return s;
			}
		}
		
		return null;
	}
	public List<Shape> getMultipleSelected() {
		
		ArrayList<Shape> multi = new ArrayList<Shape>();
		for (Shape s : allShapes ) {
			if (s.isSelected()) {
				multi.add(s);
			}
		}
		
		return multi;
	}
	
	public void addShape(Shape shape) {
		this.allShapes.add(shape);
	}
	
	public void swapShapes(int firstIndex, int secondIndex) {
		Shape helper = allShapes.get(firstIndex);
		allShapes.set(firstIndex, allShapes.get(secondIndex));
		allShapes.set(secondIndex ,helper);
	}
	
	public void replaceList(List<Shape> newList) {
		allShapes = newList;
	}
}
