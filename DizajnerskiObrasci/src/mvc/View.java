package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class View extends JPanel {

	private final Model model;
	
	public View(Model model) {
		this.model = model;
		setBackground(Color.WHITE);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext())
			it.next().draw(g);
	}
	
	public Model getModel() {
		return this.model;
	}

	
}
