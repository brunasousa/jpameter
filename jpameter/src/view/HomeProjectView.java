package view;

import icons.IconUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jpa.compiler.Compiler;

/**
 *  * @author Dêmora Bruna
 *  * Graphical interface that implements the form to connect to the DBMS
 */
public class HomeProjectView extends JFrame {
	
	public JFrame getRef() {
		return this;
	}
	private HomeProjectView execExper;
	
	private JButton jbBuild;
	private JButton jbExecute;
	private JButton jbGraph;
	
	private JPanel jpMain;
	
	private JPanel jpForm;
	
	public void build() {
		execExper = this;
		
		//Instacia dos componentes de tela
		jpMain = new JPanel(new BorderLayout());
		
		jbExecute = new JButton("Run a Experiment", new ImageIcon(
				IconUtils.class.getResource("run.png")));
		jbBuild = new JButton("Build a Experiment", new ImageIcon(
				IconUtils.class.getResource("build.png")));
		jbGraph = new JButton("Graph", new ImageIcon(
				IconUtils.class.getResource("graph.png")));
		
		
		//Configuração dos componentes
		jbBuild.setSize(100, 100);
		jbExecute.setSize(100, 100);
		jbGraph.setSize(100, 100);
	

		//Adição dos componentes no painel do título
		jpMain.add(jbBuild, BorderLayout.NORTH);
		jpMain.add(jbExecute, BorderLayout.CENTER);
		jpMain.add(jbGraph, BorderLayout.SOUTH);
		jpMain.setSize(400,300);
		jpMain.setLocation(40, 20);
		add(jpMain);
		
		setLayout(null);
		setTitle("JPAMeter - A tool for analyzing implementation of JPA");
		setSize(500, 400);
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
		
		jbBuild.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getRef().dispose();
				DBMSConnectionGUI gui = new DBMSConnectionGUI();
				gui.execute();
				
			}
		});
		
		jbExecute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getRef().dispose();
				new ExecutingExperiment().execute();
			}
		});
		
		jbGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}

	public void execute() {
		build();
		events();
		setVisible(true);
	}
	
	public static void main(String args[]){
		new HomeProjectView().execute();
	}
	
}
