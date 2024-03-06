package stack;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import geometry.Rectangle;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmStack extends JFrame {
	
	JList lstStack = new JList();
	private JPanel contentPane;
	private DefaultListModel<Rectangle> dlm= new DefaultListModel<Rectangle>();


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmStack frame = new FrmStack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmStack() {
		setTitle("Luka Bozanic,IT16/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		
		JScrollPane scrlPnStack = new JScrollPane();
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addGap(46)
					.addComponent(scrlPnStack, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlCenter.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrlPnStack, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		lstStack.setModel(dlm);
		scrlPnStack.setViewportView(lstStack);
		pnlCenter.setLayout(gl_pnlCenter);
		
		JPanel pnlBottom = new JPanel();
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=0;
				DlgStack dlgStack = new DlgStack();
				dlgStack.setVisible(true);
				
					if (dlgStack.getRectangle()!=null)
					{
						dlm.add(i, dlgStack.getRectangle());
						i++;
					}
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dlm.isEmpty()==false)
				{
				DlgStack dlgStack = new DlgStack();
				dlgStack.setRectangle(dlm.getElementAt(0));
				dlgStack.setVisible(true);
				dlm.removeElementAt(0);
				}
				else {
				JOptionPane.showMessageDialog(null,"Stack is empty!","ERROR", JOptionPane.ERROR_MESSAGE);
				return;
				}	
			}
		});
		GroupLayout gl_pnlBottom = new GroupLayout(pnlBottom);
		gl_pnlBottom.setHorizontalGroup(
			gl_pnlBottom.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBottom.createSequentialGroup()
					.addGap(44)
					.addComponent(btnAdd)
					.addPreferredGap(ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
					.addComponent(btnDelete)
					.addGap(39))
		);
		gl_pnlBottom.setVerticalGroup(
			gl_pnlBottom.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlBottom.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_pnlBottom.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd)
						.addComponent(btnDelete)))
		);
		pnlBottom.setLayout(gl_pnlBottom);
	}
}
