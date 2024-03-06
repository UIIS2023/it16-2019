package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Drawing");
		frame.setSize(800, 600);
		Drawing drawing = new Drawing(); // kreiramo instancu ove klase zato sto na taj frame treba da dodamo drawing
		frame.getContentPane().add(drawing);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // komanda za gasenje prozora, zatvaranje, ubijanje frejma
																// -> x

	}

//obavezna da bi se oblici mogli iscrtavati, preklopljena metoda paint u okviru koje mi moramo da prosledimo pozive draw metoda za svaki pojedinacni oblik
	public void paint(Graphics g) {
		// da ih ne bi sve crtao i popunjavao crnom, u svakoj klasi napravimo dodatne
		// konstruktore koje ce omoguciti inicijalizaciju i boje

		Point p = new Point(50, 50, true, Color.BLUE); // objekat kreiran, ali nije nacrtan
		// crtamo pozivom DRAW metode nad objektom p
		p.draw(g);

		Line l1 = new Line(new Point(70, 70), new Point(70, 200), true, Color.RED);
		l1.draw(g);

		Circle c = new Circle(new Point(200, 120), 40, true, Color.RED, Color.GREEN);
		c.draw(g);

		Rectangle r = new Rectangle(new Point(300, 60), 70, 120, true, Color.BLUE, Color.MAGENTA);
		r.draw(g);

		Donut d = new Donut(new Point(400, 300), 80, 50, true, Color.BLACK, Color.YELLOW);
		d.draw(g);
		
		//ArrayList-> jos jedan tip kolekcije
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(p);
		shapes.add(l1);
		shapes.add(c);
		shapes.add(r);
		shapes.add(d);
		//koncept ITERATOR(objekat nad kojim mozemo pozivati metode i na osnovu tih metoda mozemo videti da li postoje neki el u listi)
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) {
			System.out.println("Selected:" + it.next().isSelected());
		}
		//iscrtati treci element iz liste shapes
		shapes.get(2).draw(g);
		
		//iscrtati poslednji element iz liste 
		shapes.get(shapes.size()-1).draw(g);
		
		//iscrtati element sa indeksom 3
		shapes.get(3).draw(g);
		
		//kreirati i dodati u listu, a potom i iscrtati novu liniju l2 tako da ona bude 4. el u listi
		Line l2 = new Line(new Point(450,200), new Point(550,200));
		shapes.add(3, l2);
		shapes.get(3).draw(g);//roze je jer je poslednji poziv draw metode iscrtavao roze boju pa je ostala upamcena
		
		/*ukloniti drugi el liste
		shapes.remove(1);
		*/
		
		//iscrtati svaki oblik iz liste shapes pomocu FOR EACH petlje
		for (Shape s : shapes) {
			s.draw(g);
		}
		
		//iscrtati samo povrsinske oblike
		for (Shape s : shapes) {
			if(s instanceof SurfaceShape) {
				s.draw(g);
			}
		}
		
	}

}
