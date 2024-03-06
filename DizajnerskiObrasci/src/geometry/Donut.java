package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius); 
		this.innerRadius = innerRadius;
	}
	 
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        java.awt.Shape outer = new Ellipse2D.Double(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), 2*getRadius(),2*getRadius());
        java.awt.Shape inner = new Ellipse2D.Double(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, 2*getInnerRadius(),2*getInnerRadius());
        Area donut = new Area( outer );
        donut.subtract( new Area(inner) );
        g2d.setColor(getInnerColor());
        g2d.fill(donut);
        g2d.setColor(getColor());
        g2d.draw(donut);
        g2d.dispose();
        
        if(isSelected()) {
        	g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() -2, getCenter().getY()-2, 4, 4);
			g.drawRect(getCenter().getX() -2, getCenter().getY()-getRadius()-2, 4, 4);
			g.drawRect(getCenter().getX() -2, getCenter().getY()+getRadius()-2, 4, 4);
			g.drawRect(getCenter().getX()+getRadius() -2, getCenter().getY()-2, 4, 4);
			g.drawRect(getCenter().getX()-getRadius() -2, getCenter().getY()-2, 4, 4);
        	
        	g.setColor(Color.BLUE);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() + getInnerRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - getInnerRadius() - 3, getCenter().getY() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() + getInnerRadius() - 3, 6, 6);
    		g.drawRect(getCenter().getX() - 3, getCenter().getY() - getInnerRadius() - 3, 6, 6);
    		g.setColor(Color.BLACK);
        }
	}

	public void fill(Graphics g) {
		
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.getCenter().equals(pomocni.getCenter()) &&
					this.getRadius() == pomocni.getRadius() &&
					this.innerRadius == pomocni.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public String toString() {
		return super.toString() + ", inner radius=" + this.innerRadius;
	}
	
	public Donut clone() {
		Donut newDonut = new Donut();
		mapProps(newDonut);
		return newDonut;
	}

	public void mapProps(Donut destination) {
		destination.setCenter(this.getCenter().clone());
		try {
			destination.setRadius(this.getRadius());
			destination.setInnerRadius(this.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		destination.setColor(this.getColor());
		destination.setInnerColor(this.getInnerColor());
	}

	
}