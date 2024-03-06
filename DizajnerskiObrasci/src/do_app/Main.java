package do_app;

import mvc.Controller;
import mvc.Frame;
import mvc.Model;
import mvc.View;

public class Main {

	public static void main(String[] args) {
		
		Model model=new Model();
		View view=new View(model);
		Controller controller=new Controller(model);
		Frame frame=new Frame(view, controller);
		
		controller.setFrame(frame);
		controller.addObserver(frame);

		frame.setVisible(true);
		controller.notifyObservers();
	}

}
