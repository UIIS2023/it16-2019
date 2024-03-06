package geometry;

import java.awt.Color;
import java.awt.Graphics;

//mora da implementira sve nasledjene apstraktne metode iz klase Shape, jer je nasledjuje
public class Point extends Shape {

	private int x;
	private int y;

	// overload
	public Point() {

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		setColor(Color.BLACK);
	}

	public Point(int x, int y, boolean selected) {
		this(x, y); // kada pozivamo konstruktor iz iste ili nadredjene klase to mora da bude prva
					// liniija koda
		this.setSelected(selected);
		// this.setSelected(selected);
	}
	
	public Point(int x, int y, Color color) {
		this(x,y);
		this.setColor(color);
	}

	public Point(int x, int y, boolean selected, Color color) {
		this(x, y, selected);
		this.setColor(color);
	}

	/*
	 * PITANJE NA ISPITU->moguce je da se dve ili vise metoda(konstruktora) zovu
	 * isto -> "overload koncept" USLOVI overloada: 1.metode se razlikuju pre svega
	 * po broju parametara ILI 2.ako prihvataju isti broj parametara onda bar jedan
	 * od tih parametara mora imati drugaciji tip
	 */

	@Override
	public int compareTo(Object o) {
		if (o instanceof Point) {
			Point pocetak = new Point(0, 0);
			return (int) (this.distance(pocetak.getX(), pocetak.getY())
					- ((Point) o).distance(pocetak.getX(), pocetak.getY()));
			// ako dobijemo negativan rezultat znaci da je parametarska tacka "veca" od nase
			// prosledjene tacke
		}
		return 0;
	}

	@Override
	public void moveBy(int byX, int byY) {

		this.x = this.x + byX;
		this.y = this.y + byY;
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(getColor()); // vraca crnu po difoltu
		g.drawLine(this.x - 2, this.y, this.x + 2, this.y); // y se ne menja po horizontali, samo x se menja
		g.drawLine(this.x, this.y - 2, this.x, this.y + 2);
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3, 6, 6);
		}

	}

	// definicija je u apstraktnoj klasi, a implementacija je ovde
	public boolean contains(int x, int y) {
		// return this.distance(x,y)<=3;
		if (this.distance(x, y) <= 3) {
			return true;
		} else {
			return false;
		}
	}

	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;

	}

	public boolean equals(Object obj) {
		// prosledjujemo object zato sto je object nadklasa svih klasa a mi ne mozemo
		// uvek znati sta je prosledjeno metodi equals
		if (obj instanceof Point) {
			Point pomocna = (Point) obj; // downcast-> nesto sto je nadtipa kastujemo u nesto sto je nizeg tipa;
											// suprotno-> upcast
			if (this.x == pomocna.getX() && this.y == pomocna.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		// (x,y)
		return "(" + x + "," + y + ")";
	}
	// override(preklopili, redefinisali)-> iskoristiti metodu iz neke druge klase

	public Point clone() {
		Point newPoint = new Point();
		mapProps(newPoint);
		return newPoint;
	}
	
	public void mapProps(Point destination) {
		destination.setX(this.getX());
		destination.setY(this.getY());
		destination.setColor(this.getColor());
	}
	
}
