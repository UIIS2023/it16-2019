package mvc;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import command.Command;
import command.delete.CmdDelete;
import command.draw.AddCircleCOmmand;
import command.draw.AddDonutCommand;
import command.draw.AddHexagonCommand;
import command.draw.AddLineCommand;
import command.draw.AddPointCommand;
import command.draw.AddRectangleCommand;
import command.edit.EditCircleCommand;
import command.edit.EditDonutCommand;
import command.edit.EditHexagonCommand;
import command.edit.EditLineCommand;
import command.edit.EditPointCommand;
import command.edit.EditRectangleCommand;
import command.select.SelectCommand;
import command.select.UnSelectCommand;
import command.vertical_moves.VerticalDownCommand;
import command.vertical_moves.VerticalDownEndCommand;
import command.vertical_moves.VerticalUpCommand;
import command.vertical_moves.VerticalUpEndCommand;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import hexagon.HexagonAdapter;
import observer.Observable;
import observer.Observer;
import save.SaveLog;
import save.SaveManager;
import save.SaveSerialized;

public class Controller implements Observable {

	private final Model model;
	private Frame frame;
	private final List<Observer> observers = new ArrayList<Observer>();
	
	private boolean lineWaitingForEndPoint = false;
	private Point startPoint = null;
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();
	private SaveManager saveManager = new SaveManager();
	private Queue<String> logsFromFile = new ArrayBlockingQueue<String>(20000);
	
	public Controller(Model model) {
		this.model=model;
	}
	
	public void pnlDrawingClickHandler(int x, int y, int option) {
		Point mouseClick = new Point(x,y);
		
		if (option == 1) {
			boolean shpeFound=false;
			Point p=new Point(x,y);
			for (int i = model.getAll().size() - 1; i >= 0; i--) {
				Shape shape = model.getAll().get(i);
				if(shape.contains(p.getX(), p.getY()))
				{
					shpeFound=true;
					
					if(shape.isSelected()) {
						List<Shape> placeholder = new ArrayList<Shape>();
						placeholder.add(shape);
						Command cmdUnselect = new UnSelectCommand(placeholder, model);
						cmdUnselect.execute();
						undoStack.push(cmdUnselect);
						redoStack.clear();
						frame.log(cmdUnselect.toString());
					} else {
						Command cmdSelection = new SelectCommand(shape, model);
						cmdSelection.execute();
						undoStack.push(cmdSelection);
						redoStack.clear();
						frame.log(cmdSelection.toString());
					}
					
					break;
				}			
			}
			
			if (shpeFound == false) {
				if (model.getMultipleSelected().size() != 0) {
					Command cmdUnselect = new UnSelectCommand(model.getMultipleSelected(), model);
					cmdUnselect.execute();
					undoStack.push(cmdUnselect);
					redoStack.clear();
					frame.log(cmdUnselect.toString());
				}
			}
		}
		
		if (option == 2) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint(mouseClick);
			dlgPoint.setColors(frame.getBorderColor());
			dlgPoint.setVisible(true);
			if(dlgPoint.getPoint() != null) {
				AddPointCommand command = new AddPointCommand(model, dlgPoint.getPoint());
				command.execute();
				undoStack.push(command);
				redoStack.clear();
				frame.log(command.toString());
			};
			
		} else if (option == 3) {
			if(lineWaitingForEndPoint) {
				DlgLine dlgLine = new DlgLine();
				Line line = new Line(startPoint,mouseClick);
				dlgLine.setLine(line);
				dlgLine.setColors(frame.getBorderColor());
				dlgLine.setVisible(true);
				if(dlgLine.getLine()!= null) {
					AddLineCommand command = new AddLineCommand(model, dlgLine.getLine());
					command.execute();
					undoStack.push(command);
					redoStack.clear();
					frame.log(command.toString());
				};
			} else {
			startPoint = mouseClick;
			lineWaitingForEndPoint=true;
			return;
			}
		} else if (option == 4) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setPoint(mouseClick);
			dlgRectangle.setColors(frame.getBorderColor(), frame.getInsideColor());
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				AddRectangleCommand command = new AddRectangleCommand(model, dlgRectangle.getRectangle());
				command.execute();
				undoStack.push(command);
				redoStack.clear();
				frame.log(command.toString());
			};
		} else if (option == 5) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setPoint(mouseClick);
			dlgCircle.setColors(frame.getBorderColor(), frame.getInsideColor());
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				AddCircleCOmmand command = new AddCircleCOmmand(model, dlgCircle.getCircle());
				command.execute();
				undoStack.push(command);
				redoStack.clear();
				frame.log(command.toString());
			};
		} else if (option == 6) {
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.setPoint(mouseClick);
			dlgDonut.setColors(frame.getBorderColor(), frame.getInsideColor());
			dlgDonut.setVisible(true);
			
			if(dlgDonut.getDonut() != null) {
				AddDonutCommand command = new AddDonutCommand(model, dlgDonut.getDonut());
				command.execute();
				undoStack.push(command);
				redoStack.clear();
				frame.log(command.toString());
			};
		
		} else if (option == 7) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setPoint(mouseClick);
			dlgHexagon.setColors(frame.getBorderColor(), frame.getInsideColor());
			dlgHexagon.setVisible(true);
			if(dlgHexagon.getHexagon() != null) {
				AddHexagonCommand command = new AddHexagonCommand(model, dlgHexagon.getHexagon());
				command.execute();
				undoStack.push(command);
				redoStack.clear();
				frame.log(command.toString());
			};
		}
		
		lineWaitingForEndPoint=false;
		notifyObservers();
	}

	public void editClickHandler() {
		Shape shape = model.getSelected();
		
		if (shape instanceof Point) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint((Point)shape);
			dlgPoint.setVisible(true);
			
			if(dlgPoint.getPoint() != null) {
				EditPointCommand cmd = new EditPointCommand((Point)shape, dlgPoint.getPoint());
				cmd.execute();
				undoStack.push(cmd);
				redoStack.clear();
				frame.log(cmd.toString());
			}
		} else if (shape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			dlgLine.setLine((Line)shape);
			dlgLine.setVisible(true);
			
			if(dlgLine.getLine() != null) {
			EditLineCommand cmd = new EditLineCommand((Line)shape, dlgLine.getLine());
			cmd.execute();
			undoStack.push(cmd);
			redoStack.clear();
			frame.log(cmd.toString());
			}
		} else if (shape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setRectangle((Rectangle)shape);
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				EditRectangleCommand cmd = new EditRectangleCommand((Rectangle)shape, dlgRectangle.getRectangle());
				cmd.execute();
				undoStack.push(cmd);
				redoStack.clear();
				frame.log(cmd.toString());
			}
		
		}else if (shape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setDonut((Donut)shape);
				dlgDonut.setVisible(true);
				
				if(dlgDonut.getDonut() != null) {
					EditDonutCommand cmd = new EditDonutCommand((Donut)shape, dlgDonut.getDonut());
					cmd.execute();
					undoStack.push(cmd);
					redoStack.clear();
					frame.log(cmd.toString());
				}
		} else if (shape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setCircle((Circle)shape);
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				EditCircleCommand cmd = new EditCircleCommand((Circle)shape, dlgCircle.getCircle());
				cmd.execute();
				undoStack.push(cmd);
				redoStack.clear();
				frame.log(cmd.toString());
			}
		}  else if (shape instanceof HexagonAdapter) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setHexagon((HexagonAdapter)shape);
			dlgHexagon.setVisible(true);
			
			if(dlgHexagon.getHexagon() != null) {
				EditHexagonCommand cmd = new EditHexagonCommand((HexagonAdapter)shape, dlgHexagon.getHexagon());
				cmd.execute();
				undoStack.push(cmd);
				redoStack.clear();
				frame.log(cmd.toString());
			}
		} 
		
		notifyObservers();
	}
	
	public void deleteClickHandler() {
		if (JOptionPane.showConfirmDialog(null, "Do you really want to delete selected shape?", "Yes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			CmdDelete cmd = new CmdDelete(model.getMultipleSelected(), model);
			cmd.execute();
			undoStack.push(cmd);
			redoStack.clear();
			frame.log(cmd.toString());
		}
		
		notifyObservers();
	}
	
	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(undoStack.size(), redoStack.size(), logsFromFile.size());
		}
	}

	public void undoHandler() {
		Command lastCmd = undoStack.pop();
		lastCmd.unexecute();
		redoStack.push(lastCmd);
		frame.log("UNDO");
		notifyObservers();
	}

	public void redoHandler() {
		Command lastCmd = redoStack.pop();
		lastCmd.execute();
		undoStack.push(lastCmd);
		frame.log("REDO");
		notifyObservers();	
	}

	public void up() {
		Command up = new VerticalUpCommand(model, model.getAll().indexOf(model.getSelected()));
		up.execute();
		undoStack.push(up);
		redoStack.clear();
		frame.log(up.toString());
		
		notifyObservers();
	}

	public void down() {
		
		Command down = new VerticalDownCommand(model, model.getAll().indexOf(model.getSelected()));
		down.execute();
		undoStack.push(down);
		redoStack.clear();
		frame.log(down.toString());
		notifyObservers();
	}

	public void upEnd() {

		Command upEnd = new VerticalUpEndCommand(model, model.getAll().indexOf(model.getSelected()));
		upEnd.execute();
		undoStack.push(upEnd);
		redoStack.clear();
		frame.log(upEnd.toString());
		notifyObservers();
	}

	public void downEnd() {
		Command down = new VerticalDownEndCommand(model, model.getAll().indexOf(model.getSelected()));
		down.execute();
		undoStack.push(down);
		redoStack.clear();
		frame.log(down.toString());
		notifyObservers();
		
	}
	
	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public void saveSerialized() {
		SaveSerialized saveSerialized = new SaveSerialized();
		saveSerialized.setModel(model);
		saveManager.setStrategy(saveSerialized);
		saveManager.save();
		notifyObservers();
	}

	public void saveLog() {
		SaveLog saveLog = new SaveLog();
		saveLog.setLogList(frame.getLogs());
		saveManager.setStrategy(saveLog);
		saveManager.save();
		notifyObservers();
	}

	public void readSerialized() {
		JFileChooser jFileChooser = new JFileChooser(new File("C:\\"));
		jFileChooser.setDialogTitle("Open file");
		
		int result = jFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath();
			
			try {
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
				List<Shape> list = (List<Shape>)is.readObject();
				is.close();
				model.replaceList(list);
				undoStack.clear();
				redoStack.clear();
				frame.clearLogs();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"File not loaded.","Error!",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		notifyObservers();
		
	}

	public void readLog() {
		JFileChooser jFileChooser = new JFileChooser(new File("C:\\"));
		jFileChooser.setDialogTitle("Open file");
		jFileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));
		
		List<String> logs = new ArrayList<String>();
		int result = jFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			logsFromFile.clear();
			String filePath = jFileChooser.getSelectedFile().getAbsolutePath();
			try {
				BufferedReader buffer = new BufferedReader(new FileReader(filePath));
				
				String line;
				while((line = buffer.readLine()) != null) {
					logsFromFile.add(line);
					model.replaceList(new ArrayList<Shape>());
					undoStack.clear();
					redoStack.clear();
					frame.clearLogs();
				}
				
				buffer.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"File not found","Error",JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Format problem","Error",JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"File not loaded.","Error",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		notifyObservers();
		
	}
	
	public void executeNextStep() {
		System.out.println(undoStack.size());
		String nextLine = logsFromFile.remove();
		if (nextLine.contains("Draw Point")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			Point point = new Point(Integer.parseInt(data[0]),
					Integer.parseInt(data[1]),
					false,
					new Color(Integer.parseInt(data[2])));
			
			AddPointCommand cmd = new AddPointCommand(model, point);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Draw Line")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			Line Line = new Line(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					new Point(Integer.parseInt(data[2]),Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[2])));
			
			AddLineCommand cmd = new AddLineCommand(model, Line);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Draw Circle")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			Circle circle = new Circle(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					false,
					new Color(Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[4]))
					);
			
			AddCircleCOmmand cmd = new AddCircleCOmmand(model, circle);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Draw Circle")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			Circle circle = new Circle(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					false,
					new Color(Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[4]))
					);
			
			AddCircleCOmmand cmd = new AddCircleCOmmand(model, circle);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Draw Donut")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			Donut d = new Donut(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					Integer.parseInt(data[3]),
					false,
					new Color(Integer.parseInt(data[4])),
					new Color(Integer.parseInt(data[5]))
					);
			
			AddDonutCommand cmd = new AddDonutCommand(model, d);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Draw Rectangle")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			Rectangle r = new Rectangle(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					Integer.parseInt(data[3]),
					false,
					new Color(Integer.parseInt(data[4])),
					new Color(Integer.parseInt(data[5]))
					);
			
			AddRectangleCommand cmd = new AddRectangleCommand(model, r);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Draw Hexagon")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			HexagonAdapter h = new HexagonAdapter(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					new Color(Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[4]))
					);
			
			AddHexagonCommand cmd = new AddHexagonCommand(model, h);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Select")) {
			String data = nextLine.split(">>>>")[1];
			int index=Integer.parseInt(data);
			SelectCommand sel=new SelectCommand(model.getAll().get(index), model);
			sel.execute();
			frame.log(nextLine);
			undoStack.add(sel);
			redoStack.clear();
		} else if (nextLine.contains("Unselect")) {
			String[] data = nextLine.split(">>>>")[1].split(",");
			ArrayList<Shape> shapes = new ArrayList<Shape>();
			for (int i = 0;i<data.length; i++) {
				int index = Integer.parseInt(data[i]);
				shapes.add(model.getAll().get(index));
			}
			
			UnSelectCommand sel=new UnSelectCommand(shapes, model);
			sel.execute();
			frame.log(nextLine);
			undoStack.add(sel);
			redoStack.clear();
		} else if (nextLine.contains("Delete")) {
			String[] data = nextLine.split(">>>>")[1].split(",");
			ArrayList<Shape> shapes = new ArrayList<Shape>();
			for (int i = 0;i<data.length; i++) {
				int index = Integer.parseInt(data[i]);
				shapes.add(model.getAll().get(index));
			}
			
			CmdDelete sel=new CmdDelete(shapes, model);
			sel.execute();
			frame.log(nextLine);
			undoStack.add(sel);
			redoStack.clear();
		} else if (nextLine.contains("Vertical down end")) {
			String data = nextLine.split(">>>>")[1];
			int index=Integer.parseInt(data);
			VerticalDownEndCommand cmd = new VerticalDownEndCommand(model, index);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Vertical down")) {
			String data = nextLine.split(">>>>")[1];
			int index=Integer.parseInt(data);
			VerticalDownCommand cmd = new VerticalDownCommand(model, index);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Vertical up end")) {
			String data = nextLine.split(">>>>")[1];
			int index=Integer.parseInt(data);
			VerticalUpEndCommand cmd = new VerticalUpEndCommand(model, index);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Vertical up")) {
			String data = nextLine.split(">>>>")[1];
			int index=Integer.parseInt(data);
			VerticalUpCommand cmd = new VerticalUpCommand(model, index);
			cmd.execute();
			
			undoStack.add(cmd);
			redoStack.clear(); 
		} else if (nextLine.toLowerCase().contains("undo")) {
			undoHandler();
		} else if (nextLine.toLowerCase().contains("redo")) {
			redoHandler();
		} else if (nextLine.contains("Edit Point")) {
			String[] data = nextLine.split(">>>")[2].split(",");
			Point point = new Point(Integer.parseInt(data[0]),
					Integer.parseInt(data[1]),
					false,
					new Color(Integer.parseInt(data[2])));
			
			EditPointCommand cmd = new EditPointCommand((Point)model.getSelected(), point);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Edit Line")) {
			String[] data = nextLine.split(">>>")[2].split(",");
			Line Line = new Line(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					new Point(Integer.parseInt(data[2]),Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[2])));
			
			EditLineCommand cmd = new EditLineCommand((Line)model.getSelected(), Line);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Edit Circle")) {
			String[] data = nextLine.split(">>>")[2].split(",");
			Circle circle = new Circle(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					false,
					new Color(Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[4]))
					);
			
			EditCircleCommand cmd = new EditCircleCommand((Circle)model.getSelected(), circle);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Edit Donut")) {
			String[] data = nextLine.split(">>>")[2].split(",");
			Donut d = new Donut(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					Integer.parseInt(data[3]),
					false,
					new Color(Integer.parseInt(data[4])),
					new Color(Integer.parseInt(data[5]))
					);
			
			EditDonutCommand cmd = new EditDonutCommand((Donut)model.getSelected(), d);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Edit Rectangle")) {
			String[] data = nextLine.split(">>>")[2].split(",");
			Rectangle r = new Rectangle(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					Integer.parseInt(data[3]),
					false,
					new Color(Integer.parseInt(data[4])),
					new Color(Integer.parseInt(data[5]))
					);
			
			EditRectangleCommand cmd = new EditRectangleCommand((Rectangle)model.getSelected(), r);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		} else if (nextLine.contains("Edit Hexagon")) {
			String[] data = nextLine.split(">>>")[1].split(",");
			HexagonAdapter h = new HexagonAdapter(new Point (Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					Integer.parseInt(data[2]),
					new Color(Integer.parseInt(data[3])),
					new Color(Integer.parseInt(data[4]))
					);
			
			EditHexagonCommand cmd = new EditHexagonCommand((HexagonAdapter)model.getSelected(), h);
			cmd.execute();
			frame.log(nextLine);
			undoStack.add(cmd);
			redoStack.clear();
		}
		
		frame.repaint();
		notifyObservers();
	}
}
