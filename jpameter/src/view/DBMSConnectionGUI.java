package view;

import icons.IconUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import jpa.compiler.ConectionData;
import jpa.dbmsdriver.DatabaseSystemDriver;
import jpa.dbmsdriver.DatabaseSystemDriverMySQLImpl;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 *         Graphical interface that implements the form to connect to the DBMS
 */
public class DBMSConnectionGUI extends JFrame {

	public JFrame getRef() {
		return this;
	}

	private JLabel jlDBMS;
	private JComboBox jcbDBMS;

	private JLabel jlHost;
	private JLabel jlPort;
	private JLabel jlUser;
	private JLabel jlPassword;

	private JTextField jtfHost;
	private JTextField jtfPort;
	private JTextField jtfUser;
	private JPasswordField jtfPassword;

	private JButton jbConnect;
	private JButton jbExit;

	public void build() {
		JPanel jpMain = new JPanel(new BorderLayout());

		JPanel jpTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel jlTitle = new JLabel("Connecting to the DBMS");

		jpTitle.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("database_gear.png"))));
		jpTitle.add(jlTitle);

		JPanel jpForm = new JPanel(null);

		jlDBMS = new JLabel("DBMS: ");
		jcbDBMS = new JComboBox(new String[] { "Select a DBMS", "MySQL" });

		jlHost = new JLabel("Host: ");
		jlPort = new JLabel("Port: ");
		jlUser = new JLabel("User: ");
		jlPassword = new JLabel("Password: ");

		jtfHost = new JTextField("");
		jtfPort = new JTextField("");
		jtfUser = new JTextField("");
		jtfPassword = new JPasswordField("");

		jbConnect = new JButton("Connect", new ImageIcon(
				IconUtils.class.getResource("database_connect.png")));
		jbExit = new JButton("Exit", new ImageIcon(
				IconUtils.class.getResource("door_in.png")));

		jlDBMS.setSize(50, 30);
		jcbDBMS.setSize(200, 30);

		jlDBMS.setLocation(10, 10);
		jcbDBMS.setLocation(100, 10);

		jlHost.setSize(50, 30);
		jtfHost.setSize(435, 30);

		jlHost.setLocation(10, 50);
		jtfHost.setLocation(100, 50);

		jlPort.setSize(50, 30);
		jtfPort.setSize(65, 30);

		jlPort.setLocation(10, 90);
		jtfPort.setLocation(100, 90);

		jlUser.setSize(50, 30);
		jtfUser.setSize(130, 30);

		jlUser.setLocation(10, 130);
		jtfUser.setLocation(100, 130);

		jlPassword.setSize(80, 30);
		jtfPassword.setSize(130, 30);

		jlPassword.setLocation(10, 170);
		jtfPassword.setLocation(100, 170);

		jpForm.add(jlDBMS);
		jpForm.add(jcbDBMS);
		jpForm.add(jlHost);
		jpForm.add(jtfHost);
		jpForm.add(jlPort);
		jpForm.add(jtfPort);
		jpForm.add(jlUser);
		jpForm.add(jtfUser);
		jpForm.add(jlPassword);
		jpForm.add(jtfPassword);

		JPanel jpControl = new JPanel(new FlowLayout());

		jbConnect.setSize(100, 30);
		jbExit.setSize(100, 30);

		jpControl.add(jbConnect);
		jpControl.add(jbExit);

		jpMain.add(jpTitle, BorderLayout.NORTH);
		jpMain.add(jpForm, BorderLayout.CENTER);
		jpMain.add(jpControl, BorderLayout.SOUTH);

		add(jpMain);

		setTitle("JPAMeter - A tool for analyzing implementation of JPA");
		setSize(550, 300);
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
		jcbDBMS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jcbDBMS.getSelectedItem().equals("MySQL")) {
					jtfHost.setEnabled(true);
					jtfPort.setEnabled(true);
					jtfUser.setEnabled(true);
					jtfPassword.setEnabled(true);
					jbConnect.setEnabled(true);
					jtfHost.setText("localhost");
					jtfPort.setText("3306");
					jtfUser.setText("root");
					jtfPassword.setText("");
					jlHost.transferFocus();
					return;
				}
				jtfHost.setText("");
				jtfPort.setText("");
				jtfUser.setText("");
				jtfPassword.setText("");
				jtfHost.setEnabled(false);
				jtfPort.setEnabled(false);
				jtfUser.setEnabled(false);
				jtfPassword.setEnabled(false);
				jbConnect.setEnabled(false);
			}
		});
		jbConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (jtfHost.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(getRef(),
								"The \"host\" is required",
								"Information Message",
								JOptionPane.INFORMATION_MESSAGE);
						jlHost.transferFocus();
						return;

					}
					if (jtfPort.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(getRef(),
								"The \"port\" is required",
								"Information Message",
								JOptionPane.INFORMATION_MESSAGE);
						jlPort.transferFocus();
						return;
					}
					if (jtfUser.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(getRef(),
								"The \"user\" is required",
								"Information Message",
								JOptionPane.INFORMATION_MESSAGE);
						jlUser.transferFocus();
						return;
					}
					if (jtfPassword.getPassword().length == 0) {
						JOptionPane.showMessageDialog(getRef(),
								"The \"password\" is required",
								"Information Message",
								JOptionPane.INFORMATION_MESSAGE);
						jlPassword.transferFocus();
						return;
					}
					DatabaseSystemDriver databaseSystemDriver = null;
					if (jcbDBMS.getSelectedItem().equals("MySQL")) {
						databaseSystemDriver = new DatabaseSystemDriverMySQLImpl(
								jtfHost.getText(), Integer.parseInt(jtfPort
										.getText()), jtfUser.getText(),
								new String(jtfPassword.getPassword()));
						databaseSystemDriver.openConnection();
						getRef().dispose();
						SelectDBAndJPAGUI selectDBAndJPAGUI = new SelectDBAndJPAGUI(databaseSystemDriver);
						selectDBAndJPAGUI.execute();
						ConectionData.HOST = jtfHost.getText();
						ConectionData.PASS = new String(jtfPassword.getPassword());
						ConectionData.PORT = jtfPort.getText();
						ConectionData.USER = jtfUser.getText();
						ConectionData.DRIVER = jcbDBMS.getSelectedItem().toString();
						return;
					}
					JOptionPane.showMessageDialog(getRef(),
							"Unable to connect to the DBMS",
							"Information Message",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(getRef(), ex.getMessage(),
							"Information Message",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});
		jbExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null,
	                    "Are you sure to exit the application?", "Quit", JOptionPane.YES_NO_OPTION);
	            if (reply == JOptionPane.YES_OPTION)
	                System.exit(0);
			}
		});
	}

	public void enabledAllControls(boolean state) {
		jtfHost.setEnabled(state);
		jtfPort.setEnabled(state);
		jtfUser.setEnabled(state);
		jtfPassword.setEnabled(state);
		jbConnect.setEnabled(state);
	}

	public void unsetAllTextFields() {
		jtfHost.setText("");
		jtfPort.setText("");
		jtfUser.setText("");
		jtfPassword.setText("");
	}

	public void execute() {
		build();
		events();
		enabledAllControls(false);
		setVisible(true);
	}

}