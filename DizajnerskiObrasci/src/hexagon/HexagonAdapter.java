package hexagon;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Circle;
import geometry.Point;
import geometry.SurfaceShape;

public class HexagonAdapter extends SurfaceShape {

private Hexagon hexagon;
	
	public HexagonAdapter(Point center, int radius) {
		hexagon = new Hexagon(center.getX(), center.getY(), radius);
	}
	
	public HexagonAdapter(Point center, int radius, Color color, Color inner) {
		hexagon = new Hexagon(center.getX(), center.getY(), radius);
		hexagon.setBorderColor(color);
		hexagon.setAreaColor(inner);
	}
	
	
	@Override
	public void fill(Graphics g) {
	}
	
	
	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter hexagonAdapter = (HexagonAdapter)obj;
			return getX() == hexagonAdapter.getX()
					&& getY() == hexagonAdapter.getY()
					&& getRadius() == hexagonAdapter.getRadius()
					&& getColor().equals(hexagonAdapter.getColor())
					&& getInnerColor().equals(hexagonAdapter.getInnerColor());
		}
		
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		
	}
	
	public int getX() {
		return hexagon.getX();
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public void setY(int y) {
		hexagon.setY(y);
	}
	
	public void setRadius(int radius) {
		hexagon.setR(radius);
	}
	
	public int getRadius() {
		return hexagon.getR();
	}
	
	public void setInnerColor(Color color) {
		hexagon.setAreaColor(color);
	}
	
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	
	public void setOutlineColor(Color color) {
		hexagon.setBorderColor(color);
	}
	
	public Color getOutlineColor() {
		return hexagon.getBorderColor();
	}
	
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
	}
	
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	@Override
	public HexagonAdapter clone() {
		HexagonAdapter hexagonAdapter = new HexagonAdapter(new Point(hexagon.getX(),hexagon.getY()), hexagon.getR());
		hexagonAdapter.setOutlineColor(getOutlineColor());
		hexagonAdapter.setInnerColor(getInnerColor());
		hexagonAdapter.setSelected(isSelected());
		return hexagonAdapter;
	}
	
	
	@Override
	public String toString() {
		return "Hexagon:x=" + hexagon.getX() + 
				",y=" + hexagon.getY() +
				",radius=" + hexagon.getR() +
				",outline color=" + hexagon.getBorderColor().getRGB() +
				",inner color=" + hexagon.getAreaColor().getRGB() +
				",selected=" + isSelected();
	}


	@Override
	public void moveBy(int byX, int byY) {
		hexagon.setX(hexagon.getX() + byX);
		hexagon.setY(hexagon.getY() + byY);
	}


	@Override
	public int compareTo(Object o) {
		HexagonAdapter comparer = (HexagonAdapter) o;
		return hexagon.getR() - comparer.getRadius();
	}

	public void mapProps(HexagonAdapter destination) {
		destination.setX(this.getX());
		destination.setY(this.getY());
		
			destination.setRadius(this.getRadius());
		
		
		destination.setColor(this.getColor());
		destination.setInnerColor(this.getInnerColor());
	}
	
}
