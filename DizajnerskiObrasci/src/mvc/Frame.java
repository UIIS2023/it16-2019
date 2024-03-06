package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import drawing.PnlDrawing;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.Observer;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Frame extends JFrame implements Observer {

	private final int OPERATION_DRAWING = 1;
	private final int OPERATION_EDIT_DELETE = 0;
	
	private int activeOperation = OPERATION_DRAWING;
	
	private final JPanel pnlDrawing;
	private final Controller controller;
	private ButtonGroup btnsOperation = new ButtonGroup();
	private ButtonGroup btnsShapes = new ButtonGroup();
	private JToggleButton btnOperationDrawing = new JToggleButton("Drawing");
	private JToggleButton btnOperationEditOrDelete = new JToggleButton("Select");
	private JButton btnActionEdit = new JButton("Modify");
	private JButton btnActionDelete = new JButton("Delete");
	private JToggleButton btnShapePoint = new JToggleButton("Point");
	private JToggleButton btnShapeLine = new JToggleButton("Line");
	private JToggleButton btnShapeRectangle = new JToggleButton("Rectangle");
	private JToggleButton btnShapeCircle = new JToggleButton("Circle");
	private JToggleButton btnShapeDonut = new JToggleButton("Donut");
	private JButton btnColorEdge = new JButton("Edge color");
	private JButton btnColorInner = new JButton("Inner color");
	
	private Color edgeColor = Color.BLACK, innerColor = Color.WHITE;
	boolean lineWaitingForEndPoint = false;
	private Point startPoint;
	
	
	private JPanel contentPane;
	private final JMenuBar menuBar;
	private final JMenu mnUndo = new JMenu("Undo/Redo");
	private final JMenuItem mntmUndo = new JMenuItem("Undo");
	private final JMenuItem mntmRedo = new JMenuItem("Redo");
	private final JToggleButton tglbtnHex = new JToggleButton("Hexagon");
	private final JMenu mnVerticalMenu = new JMenu("Move vertical");
	private final JMenuItem mntmUp = new JMenuItem("Up");
	private final JMenuItem mntmDown = new JMenuItem("Down");
	private final JMenuItem mntmDownEnd = new JMenuItem("Down end");
	private final JMenuItem mntmTopEnd = new JMenuItem("Top end");
	private final JPanel southPanel = new JPanel();
	private final JTree logTree = new JTree();
	private final List<String> logList = new ArrayList<String>();
	private DefaultTreeModel treeModel;
	private final JMenu mnSave = new JMenu("Save");
	private final JMenuItem mntmSerialized = new JMenuItem("Serialized");
	private final JMenuItem mntmLog = new JMenuItem("Log");
	private final JPanel panel = new JPanel();
	private final JPanel pnlBorderColor = new JPanel();
	private final JPanel pnlInsideColor = new JPanel();
	private final JMenu mnRead = new JMenu("Read");
	private final JMenuItem mntmReadSerialized = new JMenuItem("Serialized");
	private final JMenuItem mntmReadLog = new JMenuItem("Log");
	private final JButton btnNext = new JButton("Next");
	
	public Frame(View view, Controller c) {
		setTitle("Luka Bozanic,IT16/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
		
		pnlDrawing=view;
		controller=c;
		
		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 0, 70, 0));
		
		menuBar.add(mnUndo);
		setJMenuBar(menuBar);
		mnUndo.setHorizontalAlignment(SwingConstants.LEFT);
		
		mnUndo.add(mntmUndo);
		
		mnUndo.add(mntmRedo);
		
		menuBar.add(mnVerticalMenu);
		
		mnVerticalMenu.add(mntmUp);
		
		mnVerticalMenu.add(mntmDown);
		
		mnVerticalMenu.add(mntmDownEnd);
		
		mnVerticalMenu.add(mntmTopEnd);
		
		menuBar.add(mnSave);
		
		mnSave.add(mntmSerialized);
		
		mnSave.add(mntmLog);
		
		menuBar.add(mnRead);
		
		mnRead.add(mntmReadSerialized);
		
		mnRead.add(mntmReadLog);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 128, 128));
		contentPane.add(panel_1, BorderLayout.WEST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{128, 0};
		gbl_panel_1.rowHeights = new int[]{134, 134, 134, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 128, 128));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel_1.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		btnOperationDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationDrawing();
			}
		});
		btnOperationDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationDrawing);
		panel_2.add(btnOperationDrawing);
		
		btnOperationEditOrDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationEditDelete();
			}
		});
		btnOperationEditOrDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationEditOrDelete);
		panel_2.add(btnOperationEditOrDelete);
		
		btnOperationDrawing.setSelected(true);
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 128, 128));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel_1.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{115, 0};
		gbl_panel_4.rowHeights = new int[]{23, 23, 23, 23, 23, 23, 23, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		btnShapePoint.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapePoint);
		GridBagConstraints gbc_btnShapePoint = new GridBagConstraints();
		gbc_btnShapePoint.anchor = GridBagConstraints.WEST;
		gbc_btnShapePoint.insets = new Insets(15, 0, 5, 0);
		gbc_btnShapePoint.gridx = 0;
		gbc_btnShapePoint.gridy = 0;
		panel_4.add(btnShapePoint, gbc_btnShapePoint);
		
		btnShapeLine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeLine);
		GridBagConstraints gbc_btnShapeLine = new GridBagConstraints();
		gbc_btnShapeLine.anchor = GridBagConstraints.WEST;
		gbc_btnShapeLine.insets = new Insets(0, 0, 5, 0);
		gbc_btnShapeLine.gridx = 0;
		gbc_btnShapeLine.gridy = 1;
		panel_4.add(btnShapeLine, gbc_btnShapeLine);
		
		btnShapeRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeRectangle);
		GridBagConstraints gbc_btnShapeRectangle = new GridBagConstraints();
		gbc_btnShapeRectangle.anchor = GridBagConstraints.WEST;
		gbc_btnShapeRectangle.insets = new Insets(0, 0, 5, 0);
		gbc_btnShapeRectangle.gridx = 0;
		gbc_btnShapeRectangle.gridy = 2;
		panel_4.add(btnShapeRectangle, gbc_btnShapeRectangle);
		
		btnShapeCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeCircle);
		GridBagConstraints gbc_btnShapeCircle = new GridBagConstraints();
		gbc_btnShapeCircle.anchor = GridBagConstraints.WEST;
		gbc_btnShapeCircle.insets = new Insets(0, 0, 5, 0);
		gbc_btnShapeCircle.gridx = 0;
		gbc_btnShapeCircle.gridy = 3;
		panel_4.add(btnShapeCircle, gbc_btnShapeCircle);
		
		btnShapeDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeDonut);
		GridBagConstraints gbc_btnShapeDonut = new GridBagConstraints();
		gbc_btnShapeDonut.anchor = GridBagConstraints.WEST;
		gbc_btnShapeDonut.insets = new Insets(0, 0, 5, 0);
		gbc_btnShapeDonut.gridx = 0;
		gbc_btnShapeDonut.gridy = 4;
		panel_4.add(btnShapeDonut, gbc_btnShapeDonut);
		btnsShapes.add(tglbtnHex);
		
		GridBagConstraints gbc_tglbtnHex = new GridBagConstraints();
		gbc_tglbtnHex.anchor = GridBagConstraints.WEST;
		gbc_tglbtnHex.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnHex.gridx = 0;
		gbc_tglbtnHex.gridy = 5;
		panel_4.add(tglbtnHex, gbc_tglbtnHex);
		
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane logScrollPane = new JScrollPane();
		logScrollPane.setViewportView(logTree);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Logs");
		treeModel = new DefaultTreeModel(root);
		logTree.setModel(treeModel);
		logTree.setVisibleRowCount(5);
		southPanel.add(logScrollPane);
		panel.setBackground(new Color(255, 128, 128));
		
		contentPane.add(panel, BorderLayout.NORTH);
		pnlBorderColor.setBackground(new Color(64, 0, 0));
		
		panel.add(pnlBorderColor);
		pnlInsideColor.setBackground(new Color(255, 255, 255));
		
		panel.add(pnlInsideColor);
		panel.add(btnActionEdit);
		btnActionEdit.setBackground(new Color(255, 255, 0));
		

		btnActionEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnActionDelete);
		btnActionDelete.setBackground(new Color(255, 0, 0));
		
		btnActionDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runNext();
			}
		});
		btnActionDelete.addActionListener(btnActionDeleteClickListener());
		btnActionEdit.addActionListener(btnActionEditClickListener());
		setOperationDrawing();
		
		// Events
		pnlDrawing.addMouseListener(pnlDrawingClickListener());
		mntmUndo.addActionListener(btnUndiClickListener());
		mntmRedo.addActionListener(btnRedoClickListener());
		mntmUp.addActionListener(btnUpClickListener());
		mntmDown.addActionListener(btnDownClickListener());
		mntmTopEnd.addActionListener(btnUpEndClickListener());
		mntmDownEnd.addActionListener(btnDownENdClickListener());
		mntmSerialized.addActionListener(btnSaveserializedClickListener());
		mntmLog.addActionListener(btnSavelogClickListener());
		mntmReadSerialized.addActionListener(btnReadserializedClickListener());
		mntmReadLog.addActionListener(btnReadlogClickListener());
		pnlInsideColor.addMouseListener(pnlInsideColorClickListener());
		pnlBorderColor.addMouseListener(pnlBorderColorClickListener());
	}
	
	// Handlers
	
	private MouseAdapter pnlDrawingClickListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int option = 0;
				if (btnOperationEditOrDelete.isSelected()) {
					option = 1;
				} else if (btnShapePoint.isSelected()) {
					option = 2;
				} else if (btnShapeLine.isSelected()) {
					option = 3;
				} else if (btnShapeRectangle.isSelected()) {
					option = 4;
				} else if (btnShapeCircle.isSelected()) {
					option = 5;
				} else if (btnShapeDonut.isSelected()) {
					option = 6;
				} else if (tglbtnHex.isSelected()) {
					option = 7;
				}
				
				controller.pnlDrawingClickHandler(e.getX(), e.getY(), option);
			}
		};
	}
	
	private ActionListener btnActionEditClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editClickHandler();
			}
		};
	}
	
	private ActionListener btnActionDeleteClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deleteClickHandler();
			}
		};
	}
	
	private ActionListener btnUndiClickListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.undoHandler();
				
			}
		};
	}
	
	private ActionListener btnRedoClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.redoHandler();
			}
		};
	}
	
	private ActionListener btnUpClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.up();
			}
		};
	}
	
	private ActionListener btnDownClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.down();
			}
		};
	}
	private ActionListener btnUpEndClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.upEnd();
			}
		};
	}
	
	private ActionListener btnDownENdClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.downEnd();
			}
		};
	}
	
	private ActionListener btnSaveserializedClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveSerialized();
			}
		};
	}
	
	private ActionListener btnSavelogClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		};
	}
	
	private ActionListener btnReadserializedClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.readSerialized();
			}
		};
	}
	
	private ActionListener btnReadlogClickListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.readLog();
			}
		};
	}

	private MouseAdapter pnlBorderColorClickListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(null, "Choose a color", pnlBorderColor.getBackground());
				if (color != null) {
					pnlBorderColor.setBackground(color);
				}
			}
		};
	}
	
	private MouseAdapter pnlInsideColorClickListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(null, "Choose a color", pnlInsideColor.getBackground());
				if (color != null) {
					pnlInsideColor.setBackground(color);
				}
			}
		};
	}
	
	public void runNext() {
		controller.executeNextStep();
	}

	private void setOperationDrawing() {
		activeOperation = OPERATION_DRAWING;
		
		btnShapePoint.setEnabled(true);
		btnShapeLine.setEnabled(true);
		btnShapeRectangle.setEnabled(true);
		btnShapeCircle.setEnabled(true);
		btnShapeDonut.setEnabled(true);
		tglbtnHex.setEnabled(true);
		
		btnColorEdge.setEnabled(true);
		btnColorInner.setEnabled(true);
	}
	
	private void setOperationEditDelete() {
		activeOperation = OPERATION_EDIT_DELETE;
		
		btnShapePoint.setEnabled(false);
		btnShapeLine.setEnabled(false);
		btnShapeRectangle.setEnabled(false);
		btnShapeCircle.setEnabled(false);
		btnShapeDonut.setEnabled(false);
		tglbtnHex.setEnabled(false);
		
		btnColorEdge.setEnabled(false);
		btnColorInner.setEnabled(false);
	}

	@Override
	public void update(int undo, int redo, int logsFromFile) {
		Model model = ((View)this.pnlDrawing).getModel();
		btnOperationEditOrDelete.setEnabled(model.getAll().size() > 0);
		btnActionEdit.setEnabled(model.getMultipleSelected().size() == 1);
		btnActionDelete.setEnabled(model.getMultipleSelected().size() >= 1);
		if (model.getMultipleSelected().size() == 1) {
			int selectedShapePOsition = model.getAll().indexOf(model.getSelected());
			if (selectedShapePOsition == 0) {
				mntmUp.setEnabled(true);
				mntmTopEnd.setEnabled(true);
				mntmDown.setEnabled(false);
				mntmDownEnd.setEnabled(false);
			} else if (selectedShapePOsition == model.getAll().size() - 1) {
				mntmDown.setEnabled(true);
				mntmDownEnd.setEnabled(true);
				mntmUp.setEnabled(false);
				mntmTopEnd.setEnabled(false);
			} else {
				mntmUp.setEnabled(true);
				mntmDown.setEnabled(true);
				mntmTopEnd.setEnabled(true);
				mntmDownEnd.setEnabled(true);
			}
		} else {
			mntmUp.setEnabled(false);
			mntmDown.setEnabled(false);
			mntmTopEnd.setEnabled(false);
			mntmDownEnd.setEnabled(false);
		}
		mntmUndo.setEnabled(undo > 0);
		mntmRedo.setEnabled(redo > 0);
		btnNext.setVisible(logsFromFile > 0);
		repaint();
	}
	
	public void log(String text) {
		treeModel.insertNodeInto(new DefaultMutableTreeNode(text), (DefaultMutableTreeNode)treeModel.getRoot(), ((DefaultMutableTreeNode)treeModel.getRoot()).getChildCount());
		logList.add(text);
	}
	
	public List<String> getLogs() {
		return logList;
	}

	public void clearLogs() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Logs");
		treeModel = new DefaultTreeModel(root);
		logTree.setModel(treeModel);
		logTree.setVisibleRowCount(5);
		logList.clear();
	}
	
	public Color getBorderColor() {
		return pnlBorderColor.getBackground();
	}
	public Color getInsideColor() {
		return pnlInsideColor.getBackground();
	}
}
