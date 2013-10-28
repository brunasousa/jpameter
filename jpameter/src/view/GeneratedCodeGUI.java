package view;

import icons.IconUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import jpa.JPAConstants;
import jpa.dbmsdriver.DatabaseSystemDriver;
import jpa.entity.Table;
import jpa.strategy.JPAStrategy;
import jpa.strategy.JPAStrategyHibernate;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 *         Graphical interface that implements the form to generate JPA
 *         application
 */
public class GeneratedCodeGUI extends JFrame {

	public JFrame getRef() {
		return this;
	}

	private JTabbedPane jtpEntities;
	private List<Table> entityList;
	private List<JTextPane> entityContent;

	private JButton jbCreate;
	private JButton jbExit;

	private DatabaseSystemDriver databaseSystemDriver = null;
	private int jpaStrategy = -1;
	private String database;

	public GeneratedCodeGUI(DatabaseSystemDriver databaseSystemDriver,
			int jpaStrategy, String database) throws SQLException {
		this.databaseSystemDriver = databaseSystemDriver;
		this.jpaStrategy = jpaStrategy;
		this.database = database;
		entityList = databaseSystemDriver.getEntityTables(database);
		if (entityList.size() == 0) {
			throw new SQLException("No table found");
		}
	}

	public void build() {
		JPanel jpMain = new JPanel(new BorderLayout());

		JPanel jpTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel jlTitle = new JLabel("Generate JPA application");

		jpTitle.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("page_white_cup.png"))));
		jpTitle.add(jlTitle);

		JPanel jpForm = new JPanel(new BorderLayout());

		jtpEntities = new JTabbedPane();
		entityContent = new ArrayList<JTextPane>();
		
		JPAStrategy jpaCodeStrategy = null;
		
		for (Table table : entityList) {
			switch (jpaStrategy) {
				case JPAConstants.JPA_HIBERNATE: {
					jpaCodeStrategy = new JPAStrategyHibernate();
					break;
				}
				case JPAConstants.JPA_ECLIPSELINK: {
					jpaCodeStrategy = new JPAStrategyHibernate();
					break;
				}
				case JPAConstants.JPA_OPENJPA: {
					jpaCodeStrategy = new JPAStrategyHibernate();
					break;
				}
			}
			JPanel panel = new JPanel(new BorderLayout());
			JTextPane textPane = new JTextPane();
			textPane.setText(jpaCodeStrategy.getEntityJavaClass(table));
			textPane.setEditable(false);
			panel.add(new JScrollPane(textPane));
			jtpEntities.addTab(table.getName().substring(0, 1).toUpperCase()
					+ table.getName().substring(1).toLowerCase() + ".java",
					panel);
			entityContent.add(textPane);
		}

		jpForm.add(jtpEntities, BorderLayout.CENTER);

		jbCreate = new JButton("Create Application", new ImageIcon(
				IconUtils.class.getResource("package.png")));
		jbExit = new JButton("Exit", new ImageIcon(
				IconUtils.class.getResource("door_in.png")));

		JPanel jpControl = new JPanel(new FlowLayout());

		jbCreate.setSize(100, 30);
		jbExit.setSize(100, 30);

		jpControl.add(jbCreate);
		jpControl.add(jbExit);

		jpMain.add(jpTitle, BorderLayout.NORTH);
		jpMain.add(jpForm, BorderLayout.CENTER);
		jpMain.add(jpControl, BorderLayout.SOUTH);

		add(jpMain);

		setTitle("JPAMeter - A tool for analyzing implementation of JPA");
		setSize(640, 480);
		setLocationRelativeTo(null);
		setResizable(true);
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
		jbCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
