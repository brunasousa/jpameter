package view;

import icons.IconUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import jpa.JPAConstants;
import jpa.compiler.ConectionData;
import jpa.dbmsdriver.DatabaseSystemDriver;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 *         Graphical interface that implements the choice of database and JPA
 *         strategy
 */
public class SelectDBAndJPAGUI extends JFrame {

	public JFrame getRef() {
		return this;
	}

	private JLabel jlDB;
	private JComboBox jcbDB;

	private JLabel jlJPA;
	private JComboBox jcbJPA;

	private JButton jbGenerate;
	private JButton jbExit;

	private DatabaseSystemDriver databaseSystemDriver = null;

	public SelectDBAndJPAGUI(DatabaseSystemDriver databaseSystemDriver) {
		this.databaseSystemDriver = databaseSystemDriver;
	}

	public void build() {
		JPanel jpMain = new JPanel(new BorderLayout());

		JPanel jpTitleDB = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel jlTitleDB = new JLabel("Select the database");

		jpTitleDB.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("database.png"))));
		jpTitleDB.add(jlTitleDB);

		JPanel jpFormDB = new JPanel(null);

		jlDB = new JLabel("Database: ");
		String[] databaseArray = new String[] {};
		try {
			List<String> databaseList = databaseSystemDriver.getDatabases();
			if (databaseList == null || databaseList.size() == 0) {
				JOptionPane.showMessageDialog(getRef(), "No database found",
						"Error Message", JOptionPane.ERROR_MESSAGE);
				databaseSystemDriver.closeConnection();
				System.exit(0);
			}
			databaseArray = new String[databaseList.size()];
			for (int i = 0; i < databaseArray.length; i++) {
				databaseArray[i] = databaseList.get(i);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(getRef(), e1.getMessage(),
					"Error Message", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		jcbDB = new JComboBox(databaseArray);

		jbGenerate = new JButton("Generate Code", new ImageIcon(
				IconUtils.class.getResource("page_white_cup.png")));
		jbExit = new JButton("Exit", new ImageIcon(
				IconUtils.class.getResource("door_in.png")));

		jlDB.setSize(100, 30);
		jcbDB.setSize(240, 30);

		jlDB.setLocation(10, 10);
		jcbDB.setLocation(100, 10);

		jpFormDB.add(jlDB);
		jpFormDB.add(jcbDB);

		JPanel jpTitleJPA = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel jlTitleJPA = new JLabel("Select the JPA strategy");

		jpTitleJPA.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("database_link.png"))));
		jpTitleJPA.add(jlTitleJPA);

		JPanel jpFormJPA = new JPanel(null);

		jlJPA = new JLabel("JPA strategy: ");
		jcbJPA = new JComboBox(JPAConstants.JPA_STRATEGIES);

		jbGenerate = new JButton("Generate Code", new ImageIcon(
				IconUtils.class.getResource("page_white_cup.png")));
		jbExit = new JButton("Exit", new ImageIcon(
				IconUtils.class.getResource("door_in.png")));

		jlJPA.setSize(100, 30);
		jcbJPA.setSize(240, 30);

		jlJPA.setLocation(10, 10);
		jcbJPA.setLocation(100, 10);

		jpFormJPA.add(jlJPA);
		jpFormJPA.add(jcbJPA);

		JPanel jpControl = new JPanel(new FlowLayout());

		jbGenerate.setSize(100, 30);
		jbExit.setSize(100, 30);

		jpControl.add(jbGenerate);
		jpControl.add(jbExit);

		JPanel jpAllForms = new JPanel(new GridLayout(2, 1));

		JPanel jpDB = new JPanel(new BorderLayout());

		jpDB.add(jpTitleDB, BorderLayout.NORTH);
		jpDB.add(jpFormDB, BorderLayout.CENTER);

		JPanel jpJPA = new JPanel(new BorderLayout());

		jpJPA.add(jpTitleJPA, BorderLayout.NORTH);
		jpJPA.add(jpFormJPA, BorderLayout.CENTER);

		jpAllForms.add(jpDB);
		jpAllForms.add(jpJPA);

		jpMain.add(jpAllForms, BorderLayout.CENTER);
		jpMain.add(jpControl, BorderLayout.SOUTH);

		add(jpMain);

		setTitle("JPAMeter - A tool for analyzing implementation of JPA");
		setSize(480, 210);
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
						"Are you sure to exit the application?", "Quit",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					try {
						databaseSystemDriver.closeConnection();
					} catch (SQLException e1) {
					}
					System.exit(0);
				}
			}
		});
		jbGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getRef().dispose();
				try {
					GeneratedCodeGUI generatedCodeGUI = new GeneratedCodeGUI(
							databaseSystemDriver, jcbJPA.getSelectedIndex(),
							jcbDB.getSelectedItem().toString());
					ConectionData.SCHEMA = jcbDB.getSelectedItem().toString();
					generatedCodeGUI.execute();
				} catch (SQLException ex) {
					try {
						JOptionPane.showMessageDialog(getRef(),
								ex.getMessage(), "Error Message",
								JOptionPane.ERROR_MESSAGE);
						databaseSystemDriver.closeConnection();
						System.exit(0);
					} catch (SQLException e1) {
					}
				}
			}
		});
		jbExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null,
						"Are you sure to exit the application?", "Quit",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					try {
						databaseSystemDriver.closeConnection();
					} catch (SQLException e1) {
					}
					System.exit(0);
				}
			}
		});
	}

	public void execute() {
		build();
		events();
		setVisible(true);
	}

}