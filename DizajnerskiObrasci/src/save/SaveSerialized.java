package save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import geometry.Shape;
import mvc.Model;

public class SaveSerialized implements SaveStrategy {

	private Model model;
	
	@Override
	public void save() {
		JFileChooser jFileChooser = new JFileChooser(new File("D:\\"));
		jFileChooser.setDialogTitle("Save file");
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath();
			List<Shape> listOfShapes = new ArrayList<Shape>();
		
			for(Shape s : model.getAll()) {
				listOfShapes.add(s);
			}
			
			try {
				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
				os.writeObject(listOfShapes);
				os.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"Not found", "Error",JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Error", "Error",JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

	public void setModel(Model model) {
		this.model = model;
	}
}
