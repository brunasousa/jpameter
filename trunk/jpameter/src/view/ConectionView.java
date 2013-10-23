package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.jdesktop.xswingx.PromptSupport;
import org.jdesktop.xswingx.PromptSupport.FocusBehavior;

import com.towel.swing.img.JImagePanel;

import controller.ActionsController;
import controller.ConectionController;

public class ConectionView {
	private JFrame windowConection;
	private JImagePanel paneConection;
	private JTextField jtextUrl;
	private JTextField jtextUser;
	private JPasswordField jpassSenha;
	private JButton jbConfirmar;
	private JComboBox cbBD;
	private JButton jbAvancar;
	
	public ConectionView(){
		createComponents();
		setListener();
	}
	
	private void createComponents(){
		
		jtextUrl = new JTextField(200);
		jtextUrl.setBounds(40, 20, 300, 30);
		
		jtextUser = new JTextField(40);
		jtextUser.setBounds(40, 70, 300, 30);
		
		jpassSenha = new JPasswordField(40);
		jpassSenha.setBounds(40, 120, 300, 30);
		
		jbConfirmar = new JButton("Confimar");
		jbConfirmar.setBounds(240, 160, 100, 30);
		
		String itens[] = {"Selecione o banco...", "Banco 1", "Banco 2"};
		cbBD = new JComboBox(itens);
		cbBD.setBounds(40, 240, 300, 30);
		cbBD.setEnabled(false);
		
		jbAvancar = new JButton("Avançar");
		jbAvancar.setBounds(285, 320, 100, 30);
		
		PromptSupport.setPrompt("URL do Banco de Dados", jtextUrl);
		PromptSupport.setPrompt("Usuário", jtextUser);
		PromptSupport.setPrompt("Senha", jpassSenha);
		
		PromptSupport.setForeground(Color.BLACK, jtextUrl);
		PromptSupport.setForeground(Color.BLACK, jtextUser);
		PromptSupport.setForeground(Color.BLACK, jpassSenha);
		
		PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, jtextUrl);
		PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, jtextUser);
		PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, jpassSenha);
		
		try {
			//paneConection = new JImagePanel("/home/chico/workspace/jpameter/src/res/tweed.png");
			paneConection = new JImagePanel(getClass().getResource("../res/tweed.png").getPath().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paneConection.setFillType(JImagePanel.FillType.CENTER);
		paneConection.setLayout(null);
		paneConection.setSize(400, 400);
		paneConection.setLocation(0, 0);
		paneConection.setBackground(new Color(222, 222, 222, 0));
		
		//AdiÃ§Ã£o dos componentes do Jpanel
		paneConection.add(jtextUrl);
		paneConection.add(jtextUser);
		paneConection.add(jpassSenha);
		paneConection.add(jbConfirmar);
		paneConection.add(cbBD);
		paneConection.add(jbAvancar);
		
		//Configuracoes do Jframe
		windowConection = new JFrame("JPA Meter - Configurações Iniciais");
		windowConection.setSize(400, 400);
		windowConection.add(paneConection);
		windowConection.setLayout(null);
		windowConection.setVisible(true);
		windowConection.setResizable(false);
		windowConection.setLocationRelativeTo(null);
		windowConection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void setListener(){
		jbConfirmar.addActionListener(new ActionsController(this));
	}
	
	public String getUser(){
		return jtextUser.getText();
	}
	
	public String getPass(){
		return String.valueOf(jpassSenha.getPassword());
	} 
	
	public String getUrl(){
		return jtextUrl.getText();
	}
	
	public JComboBox getcbBD(){
		return cbBD;
	}

	public JFrame getJFrame(){
		return windowConection;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ConectionView();
	}

}
