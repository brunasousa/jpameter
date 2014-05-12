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
public class ExecutingExperiment extends JFrame implements Runnable{
	
	public JFrame getRef() {
		return this;
	}
	private ExecutingExperiment execExper;
	private JLabel jlQFiles;
	private JTextField jtfQFiles;
	private JFileChooser jfc;
	private JTextArea jtaConsole;
	private JScrollPane jcp;
	
	private JButton jbChooser;
	private JButton jbExecute;
	private JButton jbExit;
	
	private JPanel jpMain;
	private JPanel jpTitle;
	private JPanel jpControl;
	
	private JPanel jpForm;
	
	public void build() {
		execExper = this;
		
		//Instacia dos componentes de tela
		jpMain = new JPanel(new BorderLayout());

		jpTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		jpControl = new JPanel(new FlowLayout());

		JLabel jlTitle = new JLabel("Choose a jar file and execute the experiment:");

		jpTitle.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("database_gear.png"))));
		jpTitle.add(jlTitle);

		jpForm = new JPanel(null);
	

		jlQFiles = new JLabel("Queries File: ");

		jtfQFiles = new JTextField("");
		
		jbExecute = new JButton("Execute", new ImageIcon(
				IconUtils.class.getResource("database_connect.png")));
		jbExit = new JButton("Exit", new ImageIcon(
				IconUtils.class.getResource("door_in.png")));
		jbChooser = new JButton("File", new ImageIcon(
				IconUtils.class.getResource("magnifier.png")));
		
		jtaConsole = new JTextArea(1000, 700);
		jcp = new JScrollPane(jtaConsole);
		
		jfc = new JFileChooser();
		
		//Configuração dos componentes
		FileFilter ff = new FileNameExtensionFilter("Jar File", "jar");
		jfc.setFileFilter(ff);
		jfc.setAcceptAllFileFilterUsed(false);
		
		jlQFiles.setSize(110, 30);
		jtfQFiles.setSize(200, 30);
		jtfQFiles.setEditable(false);
		
		jlQFiles.setLocation(10, 10);
		jtfQFiles.setLocation(130, 10);
		
		jbChooser.setBounds(340, 10, 100, 30);
		
		jtaConsole.setBounds(0, 0, 520, 210);
		jcp.setBounds(10, 70, 530, 240);

		jbExecute.setSize(100, 30);
		jbExit.setSize(100, 30);
		
		//Adição dosframe componentes no painel de formulário
		jpForm.add(jlQFiles);
		jpForm.add(jtfQFiles);
		jpForm.add(jbChooser);

		//Adição dos componentes no painel de botões
		jpControl.add(jbExecute);
		jpControl.add(jbExit);

		//Adição dos componentes no painel do título
		jpMain.add(jpTitle, BorderLayout.NORTH);
		jpMain.add(jpForm, BorderLayout.CENTER);
		jpMain.add(jpControl, BorderLayout.SOUTH);

		add(jpMain);

		setTitle("JPAMeter - A tool for analyzing implementation of JPA");
		setSize(550, 200);
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
		
		jbChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = jfc.showOpenDialog(ExecutingExperiment.this);
				if(ret == JFileChooser.APPROVE_OPTION){
					File f = jfc.getSelectedFile();
					jtfQFiles.setText(f.getAbsolutePath());
				}
			}
		});
		
		jbExecute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(execExper);
	            thread.start();
			}
		});
		
		jbExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getRef().dispose();
				new HomeProjectView().execute();
			}
		});
	}

	public void execute() {
		build();
		events();
		setVisible(true);
	}
	
	public static void main(String args[]){
		new ExecutingExperiment().execute();
	}
	
    public void run() {
    	jbExecute.setEnabled(false);
    	jbExit.setEnabled(false);
    	jpForm.add(jcp);
    	setSize(550, 400);
    	this.validate();
    	
    	Compiler c = new Compiler();
		BufferedReader reader =  c.executeJar(jtfQFiles.getText());

		String line;
		try {
			line = reader.readLine();
			while(line != null){
				jtaConsole.append(line+"\n");
				jtaConsole.setCaretPosition(jtaConsole.getDocument().getLength()-1);
				System.out.println(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		c = null;
		jtaConsole.append("\n\n\n\n** Fim do Experimento **\n\n\n\n");
		jtaConsole.setCaretPosition(jtaConsole.getDocument().getLength()-1);
        validate();
        jbExit.setEnabled(true);
        jbExecute.setEnabled(true);
    }
}
