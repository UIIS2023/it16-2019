package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

//apstraktna klasa ima za cilj da objedini neke zaj karakteristike nekih metoda

public abstract class Shape implements Moveable, Comparable, Serializable {
//Comparable ima metodu compare to u sebi koja obavezno vraca int
	private boolean selected;
	private Color color = Color.BLACK;

	public Shape() {

	}

	public Shape(Color color) {
		this.color = color;
	}

	public Shape(Color color, boolean selected) {
		this(color);
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	// razlikuje se od ostalih klasa po apstraktnim metodama
	// Obicna metoda ima svoju implementaciju(logiku), dok APSTRAKTNA metoda
	// poseduje samo definiciju, ne moze imati specifikaciju implementacije
	public abstract boolean contains(int x, int y);

	public abstract void draw(Graphics g);

}
