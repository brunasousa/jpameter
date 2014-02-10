package view;

import icons.IconUtils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 * 
 * @author Dêmora Bruna
 * 
 *         Graphical interface that implements the form to connect to the DBMS
 */
public class ConfigurationExperimentGUI extends JFrame {

	public JFrame getRef() {
		return this;
	}

	private JLabel jlStrategy;

	private JLabel jlQFiles;
	private JLabel jlNClients;
	private JLabel jlTExperiment;
	private JLabel jlSazon;
	private JLabel jlMin;
	private JLabel jlMin2;

	private JTextField jtfStrategy;
	private JTextField jtfQFiles;
	private JTextField jtfNClients;
	private JTextField jtfTExperiment;
	private JTextField jtfSazon;

	private JButton jbChooser;
	private JButton jbExecute;
	private JButton jbExit;
	private JButton jbCalSteps;
	
	private JTextField[] fieldsSazon;
	private JScrollPane jSFieldsSazon;
	private JPanel internalScroll;
	public void build() {
		JPanel jpMain = new JPanel(new BorderLayout());

		JPanel jpTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel jlTitle = new JLabel("Connecting to the DBMS");

		jpTitle.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("database_gear.png"))));
		jpTitle.add(jlTitle);

		JPanel jpForm = new JPanel(null);

		jlStrategy = new JLabel("JPA Strategy");
		jlQFiles = new JLabel("Queries File: ");
		jlNClients = new JLabel("Nº Clients: ");
		jlTExperiment = new JLabel("Time Experimet: ");
		jlSazon = new JLabel("Sazonalidade: ");
		jlMin = new JLabel("min");
		jlMin2 = new JLabel("min");

		jtfQFiles = new JTextField("");
		jtfNClients = new JTextField("");
		jtfTExperiment = new JTextField("");
		jtfSazon = new JTextField("");
		jtfStrategy = new JTextField("");
		
		jbExecute = new JButton("Execute", new ImageIcon(
				IconUtils.class.getResource("database_connect.png")));
		jbExit = new JButton("Exit", new ImageIcon(
				IconUtils.class.getResource("door_in.png")));
		jbChooser = new JButton("File", new ImageIcon(
				IconUtils.class.getResource("magnifier.png")));
		jbCalSteps = new JButton("Calculate Steps");
		
		jSFieldsSazon = new JScrollPane();
		internalScroll = new JPanel();
		
		jlStrategy.setSize(110, 30);
		jtfStrategy.setSize(200, 30);

		jlStrategy.setLocation(10, 10);
		jtfStrategy.setLocation(130, 10);

		jlQFiles.setSize(110, 30);
		jtfQFiles.setSize(200, 30);
		
		jlQFiles.setLocation(10, 50);
		jtfQFiles.setLocation(130, 50);
		jbChooser.setBounds(340, 50, 100, 30);

		jlNClients.setSize(110, 30);
		jtfNClients.setSize(80, 30);

		jlNClients.setLocation(10, 130);
		jtfNClients.setLocation(130, 130);

		jlTExperiment.setSize(120, 30);
		jtfTExperiment.setSize(80, 30);

		jlTExperiment.setLocation(265, 130);
		jtfTExperiment.setLocation(390, 130);
		
		jlSazon.setSize(110, 30);
		jtfSazon.setSize(80, 30);

		jlSazon.setLocation(10, 170);
		jtfSazon.setLocation(130, 170);
		
		jlMin.setBounds(215, 170, 40, 30);
		jlMin2.setBounds(475, 130, 40, 30);
		
		jbCalSteps.setBounds(265, 170, 150, 30);
		
		jSFieldsSazon.setBounds(10, 210, 500, 200);
		jSFieldsSazon.setViewportView(internalScroll);
		jSFieldsSazon.createVerticalScrollBar();

		jpForm.add(jlStrategy);
		jpForm.add(jtfStrategy);
		jpForm.add(jlQFiles);
		jpForm.add(jtfQFiles);
		jpForm.add(jlNClients);
		jpForm.add(jtfNClients);
		jpForm.add(jlTExperiment);
		jpForm.add(jtfTExperiment);
		jpForm.add(jlSazon);
		jpForm.add(jtfSazon);
		jpForm.add(jlMin);
		jpForm.add(jlMin2);
		jpForm.add(jbChooser);
		jpForm.add(jbCalSteps);
		jpForm.add(jSFieldsSazon);

		JPanel jpControl = new JPanel(new FlowLayout());

		jbExecute.setSize(100, 30);
		jbExit.setSize(100, 30);

		jpControl.add(jbExecute);
		jpControl.add(jbExit);

		jpMain.add(jpTitle, BorderLayout.NORTH);
		jpMain.add(jpForm, BorderLayout.CENTER);
		jpMain.add(jpControl, BorderLayout.SOUTH);

		add(jpMain);

		setTitle("JPAMeter - A tool for analyzing implementation of JPA");
		setSize(550, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setMinimumSize(getSize());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public void events() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int reply = JOptionPane.showConfirmDialog(null,
	                    "Are you sure to exit the application?", "Quit", JOptionPane.YES_NO_OPTION);
	            if (reply == JOptionPane.YES_OPTION)
	                System.exit(0);
			}
		});
		
		jbCalSteps.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tE = Integer.parseInt(jtfTExperiment.getText());
				int sazon = Integer.parseInt(jtfSazon.getText());
				if(tE>0 && sazon>0){
					int nSteps = tE/sazon;
					fieldsSazon = new JTextField[nSteps*4];
					internalScroll.removeAll();
					internalScroll.setLayout(new GridLayout(nSteps,1,2,2));
					for(int i=0; i<nSteps;i++){
						JPanel jp = new JPanel();
						jp.setSize(500,60);
						jp.setLayout(new GridLayout(2,4,30,4));
						jp.setBorder(BorderFactory.createTitledBorder("Step "+(i+1)));
						
						JLabel jl1 = new JLabel("SELECT: ");
						jl1.setMinimumSize(new Dimension(60,30));
						JTextField jtf1 = new JTextField();
						jtf1.setMinimumSize(new Dimension(100,30));
						
						JLabel jl2 = new JLabel("INSERT: ");
						jl2.setMinimumSize(new Dimension(60,30));
						JTextField jtf2 = new JTextField();
						jtf2.setMinimumSize(new Dimension(100,30));
						
						JLabel jl3 = new JLabel("UPDATE: ");
						jl3.setMinimumSize(new Dimension(60,30));
						JTextField jtf3 = new JTextField();
						jtf3.setMinimumSize(new Dimension(100,30));
						
						JLabel jl4 = new JLabel("DELETE: ");
						jl4.setMinimumSize(new Dimension(60,30));
						JTextField jtf4 = new JTextField();
						jtf4.setMinimumSize(new Dimension(100,30));
						
						jp.add(jl1);
						jp.add(jtf1);
						jp.add(jl2);
						jp.add(jtf2);
						jp.add(jl3);
						jp.add(jtf3);
						jp.add(jl4);
						jp.add(jtf4);
						
						internalScroll.add(jp);
						fieldsSazon[i] = jtf1;
						fieldsSazon[i+1] = jtf2;
						fieldsSazon[i+2] = jtf3;
						fieldsSazon[i+3] = jtf4;
					}
					internalScroll.setSize(nSteps*30,500);
					internalScroll.validate();
					jSFieldsSazon.validate();
				}
				
			}
		});
		
	}

	public void enabledAllControls(boolean state) {
		jtfQFiles.setEnabled(state);
		jtfNClients.setEnabled(state);
		jtfTExperiment.setEnabled(state);
		jtfSazon.setEnabled(state);
		jbExecute.setEnabled(state);
	}

	public void unsetAllTextFields() {
		jtfQFiles.setText("");
		jtfNClients.setText("");
		jtfTExperiment.setText("");
		jtfSazon.setText("");
	}

	public void execute() {
		build();
		events();
		enabledAllControls(true);
		setVisible(true);
	}
	
	public static void main(String args[]){
		new ConfigurationExperimentGUI().execute();
	}

}