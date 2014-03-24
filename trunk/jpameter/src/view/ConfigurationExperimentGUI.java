package view;

import icons.IconUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jpa.JPAConstants;
import jpa.experiment.ExperimentFile;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * 
 * @author Dêmora Bruna
 * 
 *         Graphical interface that implements the form to connect to the DBMS
 */
public class ConfigurationExperimentGUI extends JFrame implements PropertyChangeListener{

	public ConfigurationExperimentGUI(int jpaStrategy){
		
	}
	
	public JFrame getRef() {
		return this;
	}

	private int valStrategy;
	private JLabel jlStrategy;

	private JLabel jlQFiles;
	private JLabel jlNClients;
	private JLabel jlTExperiment;
	private JLabel jlSazon;
	private JLabel jlMin;
	private JLabel jlMin2;
	private JLabel jlQuanQueries;

	private JTextField jtfStrategy;
	private JTextField jtfQFiles;
	private JFormattedTextField jtfNClients;
	private JFormattedTextField jtfTExperiment;
	private JFormattedTextField jtfSazon;

	private JButton jbChooser;
	private JButton jbExecute;
	private JButton jbExit;
	private JButton jbCalSteps;
	
	private JTextField[] fieldsSazon;
	private JScrollPane jSFieldsSazon;
	private JPanel internalScroll;
	
	private JFileChooser jfc;
	private int valTExperiment  = 10;
	private int valNClients = 1;
	private int valSazon = 10;
	
	public void build() {
		
		//Instacia dos componentes de tela
		JPanel jpMain = new JPanel(new BorderLayout());

		JPanel jpTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JPanel jpControl = new JPanel(new FlowLayout());

		JLabel jlTitle = new JLabel("Configurations to the experiment with database");

		jpTitle.add(new JLabel(new ImageIcon(IconUtils.class
				.getResource("database_gear.png"))));
		jpTitle.add(jlTitle);

		JPanel jpForm = new JPanel(null);

		jlStrategy = new JLabel("JPA Strategy");
		jlQFiles = new JLabel("Queries File: ");
		jlNClients = new JLabel("Client's number: ");
		jlTExperiment = new JLabel("Experiment time: ");
		jlSazon = new JLabel("Sazonality: ");
		jlMin = new JLabel("min");
		jlMin2 = new JLabel("min");
		jlQuanQueries = new JLabel();

		jtfQFiles = new JTextField("");
		jtfNClients = new JFormattedTextField();
		jtfTExperiment = new JFormattedTextField();
		jtfSazon = new JFormattedTextField();
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
		
		jfc = new JFileChooser();
		
		//Configuração dos componentes
		FileFilter ff = new FileNameExtensionFilter("Extensible Markup Language", "xml");
		jfc.setFileFilter(ff);
		jfc.setAcceptAllFileFilterUsed(false);
		
		jlStrategy.setSize(110, 30);
		jtfStrategy.setSize(200, 30);

		jlStrategy.setLocation(10, 10);
		jtfStrategy.setLocation(130, 10);
		jtfStrategy.setText(JPAConstants.JPA_STRATEGIES[valStrategy]);
		jtfStrategy.setEditable(false);

		jlQFiles.setSize(110, 30);
		jtfQFiles.setSize(200, 30);
		jtfQFiles.setEditable(false);
		
		jlQFiles.setLocation(10, 50);
		jtfQFiles.setLocation(130, 50);
		jbChooser.setBounds(340, 50, 100, 30);

		jlNClients.setSize(110, 30);
		jtfNClients.setSize(80, 30);
		
		jlQuanQueries.setBounds(10, 90, 400, 30);

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
		jSFieldsSazon.setVisible(false);
		
		jbExecute.setSize(100, 30);
		jbExit.setSize(100, 30);
		
		//Validacao dos campos de texto
		jtfTExperiment.setValue(new Integer(valTExperiment));
		jtfTExperiment.setColumns(5);
		jtfTExperiment.addPropertyChangeListener("value", this);
		
		jtfNClients.setValue(new Integer(valNClients));
		jtfNClients.setColumns(2);
		jtfNClients.addPropertyChangeListener("value", this);
		
		jtfSazon.setValue(new Integer(valSazon));
		jtfSazon.setColumns(5);
		jtfSazon.addPropertyChangeListener("value", this);
		
		//Adição dosframe componentes no painel de formulário
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
		jpForm.add(jlQuanQueries);
		jpForm.add(jbChooser);
		jpForm.add(jbCalSteps);
		jpForm.add(jSFieldsSazon);

		//Adição dos componentes no painel de botões
		jpControl.add(jbExecute);
		jpControl.add(jbExit);

		//Adição dos componentes no painel do título
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
		
		jbChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = jfc.showOpenDialog(ConfigurationExperimentGUI.this);
				if(ret == JFileChooser.APPROVE_OPTION){
					File f = jfc.getSelectedFile();
					jtfQFiles.setText(f.getAbsolutePath());
					calculateNumberQueriesFile(f);
				}
			}
		});
		
		jbCalSteps.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tE = valTExperiment;
				int sazon = valSazon;
				if(tE>0 && sazon>0){ //Veririfa se o tempo de experimento e a sazonalidade foram preenchidos
					int nSteps = tE/sazon; //Calcula em quantas etapas serão realizadas o experimento
					if(nSteps>10){ 
						nSteps = 10;
						JOptionPane.showMessageDialog(null, "Podem ser gerados no máximo 10 steps.");
					}
					int stepsBorder = 1;//será utilizado na borda
					fieldsSazon = new JTextField[nSteps*4];
					internalScroll.removeAll();
					internalScroll.setLayout(new GridLayout(nSteps,1,2,2));
					for(int i=0; i<nSteps*4;i+=4){
						JPanel jp = new JPanel();
						jp.setSize(500,60);
						jp.setLayout(new GridLayout(2,4,30,4));
						jp.setBorder(BorderFactory.createTitledBorder("Step "+stepsBorder)); //Adciona a borda para o painel da estava
						//Adiciona os labels e campos de texto para cada tipo de query
						JLabel jl1 = new JLabel("SELECT(%): ");
						jl1.setMinimumSize(new Dimension(60,30));
						JTextField jtf1 = new JTextField();
						jtf1.setMinimumSize(new Dimension(100,30));
						jtf1.setMaximumSize(new Dimension(100,30));
						jtf1.setText("25");
						
						JLabel jl2 = new JLabel("INSERT(%): ");
						jl2.setMinimumSize(new Dimension(60,30));
						JTextField jtf2 = new JTextField();
						jtf2.setMinimumSize(new Dimension(100,30));
						jtf2.setMaximumSize(new Dimension(100,30));
						jtf2.setText("25");
						
						JLabel jl3 = new JLabel("UPDATE(%): ");
						jl3.setMinimumSize(new Dimension(60,30));
						JTextField jtf3 = new JTextField();
						jtf3.setMinimumSize(new Dimension(100,30));
						jtf3.setMaximumSize(new Dimension(100,30));
						jtf3.setText("25");
						
						JLabel jl4 = new JLabel("DELETE(%): ");
						jl4.setMinimumSize(new Dimension(60,30));
						JTextField jtf4 = new JTextField();
						jtf4.setMinimumSize(new Dimension(100,30));
						jtf4.setMaximumSize(new Dimension(100,30));
						jtf4.setText("25");
						
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
						stepsBorder++;
					}
					internalScroll.setMaximumSize(new Dimension(nSteps*30*2,500));//Calcula o tamanho do painel que contem todos os paineis de etapa
					internalScroll.validate();
					jSFieldsSazon.setVisible(true);
					jSFieldsSazon.validate();
				}
				
			}
		});
		
		jbExecute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean cont = true;
				
				if(!checkQueriesSazonFields()){
					JOptionPane.showMessageDialog(null, "A soma interna de todos os campos de cada step, deve ser igual a 100%.");
					cont=false;
				}
				if(!checkMandatoryFields()){
					JOptionPane.showMessageDialog(null, "Os campos 'Time Experiment' e 'Nº Clients' devem ser maiores que zero.");
					cont=false;
				}
				
				if(jtfQFiles.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Escolha um arquivo de consultas para prosseguir.");
					cont=false;
				}
				
				if(cont){
					int steps[] = null;
					if(fieldsSazon!=null){
						steps = new int[fieldsSazon.length];
					
						for(int i=0; i<fieldsSazon.length;i++){
							steps[i] = Integer.parseInt(fieldsSazon[i].getText());
						}
					}
					ExperimentFile ef = new ExperimentFile();
					if(ef.create(valStrategy, valNClients, steps, jtfQFiles.getText(), valTExperiment, valSazon))
						JOptionPane.showMessageDialog(null, "Roteiro do experimento gerado em "+System.getProperty("user.home"));
				}
			}
			
		});
	}

	private boolean checkMandatoryFields(){
		if(valNClients<=0 || valTExperiment<=0)
			return false;
			
		return true;
	}
	
	private boolean checkQueriesSazonFields(){
		int total = 0;
		if(fieldsSazon !=null)
			if(fieldsSazon.length>0){
				for(int i=0;i<fieldsSazon.length;i++){
					try{
						total+= Integer.parseInt(fieldsSazon[i].getText());
					}catch(Exception e){
						fieldsSazon[i].setText("0");
						this.validate();
					}
					if((i+1)!=1 && (i+1)%4==0){
						if(total!=100)
							return false;
						total=0;
					}
				}
			}
				
		return true;
	}
	private void calculateNumberQueriesFile(File f){
		SAXBuilder sb = new SAXBuilder();  
		Document d = null;
		int select = 0, update = 0, insert = 0, delete=0;
		try {
			d = sb.build(f);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		Element element = d.getRootElement();  
		element = element.getChild("queries");
		List elements = element.getChildren();  
		Iterator i = elements.iterator();  
		while (i.hasNext()) { 
			Element e = (Element)i.next();
		    switch (e.getChildText("type").charAt(0)) {
			    case '1':
			    	select++;
			    	break;
			    case '2':
			    	insert++;
			    	break;
			    case '3':
			    	update++;
			    	break;
			    case '4':
			    	delete++;
			    	break;
		    }
		} 
		jlQuanQueries.setText("<html><font color=gray size=-1>SELECT: <b>"+select+"</b> | INSERT: <b>"+insert+"</b> | UPDATE: <b>"+update+"</b> | DELETE: <b>"+delete+"</b></font></html>");    
		this.validate();    
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
		setVisible(true);
	}
	
	public static void main(String args[]){
//		new ConfigurationExperimentGUI().execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object e = evt.getSource();
		if(e==jtfTExperiment){
			valTExperiment = ((Integer)jtfTExperiment.getValue()).intValue();
		}
		if(e==jtfNClients){
			valNClients = ((Integer)jtfNClients.getValue()).intValue();
		}
		if(e==jtfSazon){
			valSazon = ((Integer)jtfSazon.getValue()).intValue();
		}
	}
}