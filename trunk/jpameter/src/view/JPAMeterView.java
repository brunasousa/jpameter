package view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

public class JPAMeterView {

	private JFrame windowConection;
	
	//Lado esquerdo
	private JPanel paneOptions;
	private JPanel jpbPersistence;
	private JLabel jlTecnlogia;
	private JLabel jlConexoes;
	private JLabel jlOperacoes;
	private JRadioButton jrbHibernate;
	private JRadioButton jrbOpenJPA;
	private JRadioButton jrbEclipseLink;
	private JCheckBox jcAbas;
	private ButtonGroup bGroup;
	private JComboBox cbBD;
	
	//Lado direito
	private JLabel jlOperacao;
	private JTabbedPane jtAbas;
	private JPanel jp250R;
	private JPanel jp1000R;
	private JPanel jpMedia;

	public JPAMeterView() {
		createComponents();
	}

	private void createComponents() {

		//Lado esquerdo ======================================================
		jlTecnlogia = new JLabel("Tecnologias de Persistência");
		jlTecnlogia.setBounds(10, 20, 300, 30);

		jrbHibernate = new JRadioButton("Hibernate");
		jrbHibernate.setBackground(Color.WHITE);
		jrbOpenJPA = new JRadioButton("Open JPA");
		jrbOpenJPA.setBackground(Color.WHITE);
		jrbEclipseLink = new JRadioButton("Eclipse Link");
		jrbEclipseLink.setBackground(Color.WHITE);
		
		bGroup = new ButtonGroup();
		bGroup.add(jrbEclipseLink);
		bGroup.add(jrbHibernate);
		bGroup.add(jrbOpenJPA);
		
		jpbPersistence = new JPanel();
		jpbPersistence.setLayout(new BoxLayout(jpbPersistence, BoxLayout.Y_AXIS));
		jpbPersistence.add(jrbEclipseLink);
		jpbPersistence.add(jrbHibernate);
		jpbPersistence.add(jrbOpenJPA);
		jpbPersistence.setBounds(10, 50, 300, 75);
		jpbPersistence.setBackground(Color.WHITE);
		
		jlConexoes = new JLabel("Conexões Simuladas");
		jlConexoes.setBounds(10, 150, 300, 10);
		
		jlOperacoes = new JLabel("Operações");
		jlOperacoes.setBounds(10, 220, 300, 10);

		String itens[] = { "Selecione a operação", "Op 1", "Op2 2" };
		cbBD = new JComboBox(itens);
		cbBD.setBounds(10, 245, 200, 20);
		
		jcAbas = new JCheckBox("Ver em abas");
		jcAbas.setBounds(10, 300, 200, 20);
		jcAbas.setBackground(Color.WHITE);

		paneOptions = new JPanel();
		paneOptions.setLayout(null);
		paneOptions.setSize(250, 360);
		paneOptions.setLocation(5, 5);
		paneOptions.setBackground(Color.WHITE);

		// AdiÃ§Ã£o dos componentes do Jpanel
		paneOptions.add(jlTecnlogia);
		paneOptions.add(cbBD);
		paneOptions.add(jpbPersistence);
		paneOptions.add(jlConexoes);
		paneOptions.add(jlOperacoes);
		paneOptions.add(jcAbas);
		
		
		//Lado direito ======================================================
		jlOperacao = new JLabel("<html><h3>Operaçõeo: </h3></html>");
		jlOperacao.setBounds(260, 5, 300, 20);
		
		jp250R = new JPanel();
		jp1000R = new JPanel();
		jpMedia = new JPanel();
		
		jtAbas = new JTabbedPane();
		jtAbas.setBounds(260, 30, 725, 330);
		jtAbas.add(jp250R, "50/250 registros");
		jtAbas.add(jp1000R, "500/1000 registros");
		jtAbas.add(jpMedia, "Media");
		
		// Configuracoes do Jframe ======================================================
		windowConection = new JFrame("JPA Meter");
		windowConection.setSize(1000, 400);
		windowConection.add(paneOptions);
		windowConection.add(jtAbas);
		windowConection.add(jlOperacao);
		windowConection.setLayout(null);
		windowConection.setVisible(true);
		windowConection.setResizable(false);
		windowConection.setBackground(Color.WHITE);
		windowConection.setLocationRelativeTo(null);
		windowConection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public static void main(String[] args) {
		new JPAMeterView();

	}

}
