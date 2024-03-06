package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {

	private Point startPoint;
	private Point endPoint;

	public Line() {

	}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		setColor(Color.BLACK);
	}
	
	public Line(Point startPoint, Point endPoint, Color color) {
		this(startPoint, endPoint);
		this.setColor(color);
	}

	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		this.setSelected(selected);
	}

	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		this.setColor(color);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		return 0;
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(startPoint.getX()-3, startPoint.getY()-3, 6, 6);
			g.drawRect(endPoint.getX()-3, endPoint.getY()-3, 6, 6);
			/*g.drawRect((startPoint.getX()+endPoint.getX())/2-3,(startPoint.getY()+endPoint.getY())/2-3,6,6);
			MOZE I OVAKO BEZ PRAVLJENJA METODE
			*/
			g.drawRect(this.middleOfLine().getX()-3, this.middleOfLine().getY()-3, 6, 6);
		}

	}
	//metoda koja vraca sredinu linije -> ISPIT
	public Point middleOfLine() {
		int middleByX = (startPoint.getX()+endPoint.getX())/2;
		int middleByY = (startPoint.getY()+endPoint.getY())/2;
		Point p = new Point(middleByX,middleByY);
		return p;
	}

	public boolean contains(int x, int y) {
		if (startPoint.distance(x, y) + endPoint.distance(x, y) - length() <= 0.05) {
			return true;
		} else {
			return false;
		}
	}

	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}

	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line pomocna = (Line) obj;
			if (this.startPoint.equals(pomocna.getStartPoint()) && this.getEndPoint().equals(pomocna.endPoint)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public String toString() {
		// (x1,x2)-->(y1,y2)
		// return "(" + startPoint.getX() + "," + startPoint.getY() + ")" + "-->" + "("
		// + endPoint.getX() + "," + endPoint.getY() + ")";
		return startPoint + "-->" + endPoint;
	}
	
	public Line clone() {
		Line newLine = new Line();
		mapProps(newLine);
		return newLine;
	}
	
	public void mapProps(Line destination) {
		destination.setStartPoint(this.getStartPoint().clone());
		destination.setEndPoint(this.getEndPoint().clone());
		destination.setColor(this.getColor());
	}

}
