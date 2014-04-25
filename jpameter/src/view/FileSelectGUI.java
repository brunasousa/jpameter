package view;

import icons.IconUtils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelectGUI extends JFrame{

	private JPanel panelSelectFile;
	private static JFileChooser fc;
	private JLabel jlDescribe;
	private JButton btChooser1;
	private static JTextField jtfFile1;
	private JButton btChooser2;
	private static JTextField jtfFile2;
	private JButton btChooser3;
	private static JTextField jtfFile3;
	private JButton btChart;
	private static File[] files = new File[3];

	public FileSelectGUI() {
		build();
	}
	
	public JFrame getRef() {
		return this;
	}

	private void build() {
		jlDescribe = new JLabel("Choose JPAMeter's Files:");
		jlDescribe.setBounds(25, 2, 200, 20);
		FileFilter ff = new FileNameExtensionFilter("Arquivos de Texto", "txt");
		
		fc = new JFileChooser();
		fc.setFileFilter(ff);
		fc.setAcceptAllFileFilterUsed(false);
		btChooser1 = new JButton("File 1...");
		btChooser1.setBounds(285, 40, 100, 35);
		
		jtfFile1 = new JTextField();
		jtfFile1.setBounds(25, 40, 240, 35);
		jtfFile1.setEditable(false);
		
		btChooser2 = new JButton("File 2...");
		btChooser2.setBounds(285, 90, 100, 35);
		
		jtfFile2 = new JTextField();
		jtfFile2.setBounds(25, 90, 240, 35);
		jtfFile2.setEditable(false);
		
		btChooser3 = new JButton("File 3...");
		btChooser3.setBounds(285, 140, 100, 35);
		
		jtfFile3 = new JTextField();
		jtfFile3.setBounds(25, 140, 240, 35);
		jtfFile3.setEditable(false);
		
		btChart = new JButton("Show Charts", new ImageIcon(
				IconUtils.class.getResource("chart_pie.png")));
		btChart.setBounds(250, 210, 150, 35);
		
		panelSelectFile = new JPanel();
		panelSelectFile.setLayout(null);
		panelSelectFile.setSize(430, 310);
		panelSelectFile.add(btChooser1);
		panelSelectFile.add(jtfFile1);
		panelSelectFile.add(btChooser2);
		panelSelectFile.add(jtfFile2);
		panelSelectFile.add(btChooser3);
		panelSelectFile.add(jtfFile3);
		panelSelectFile.add(btChart);
		panelSelectFile.add(jlDescribe);
		
		this.add(panelSelectFile);
		this.setSize(430, 310);
		this.setLayout(null);
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		events();
	}
	
	public void events(){
		btChooser1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectFile(e.getSource());
			}
		});
		btChooser2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectFile(e.getSource());
			}
		});
		btChooser3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectFile(e.getSource());
			}
		});
		
		btChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(files[0] == null && files[1]==null && files[2]==null){
					JOptionPane.showMessageDialog(null, "Please, choose one file to continue.");
				}else{
					getRef().dispose();
					JPAMeterView jmv = new JPAMeterView(files);
					jmv.execute();
				}
			}
		});
	}
	public void selectFile(Object o){
		int ret = fc.showOpenDialog(FileSelectGUI.this);
		JButton bt  = (JButton)o;
		if(ret == JFileChooser.APPROVE_OPTION){
			File f = fc.getSelectedFile();
			if(!verifyFile(f)){
				if(bt.getText() == btChooser1.getText()){
					jtfFile1.setText(f.getAbsolutePath());
					files[0] = f; 
				}
				if(bt.getText() == btChooser2.getText()){
					jtfFile2.setText(f.getAbsolutePath());
					files[1] = f; 
				}
				if(bt.getText() == btChooser3.getText()){
					jtfFile3.setText(f.getAbsolutePath());
					files[2] = f; 
				}
			}
		}
	}
	public boolean verifyFile(File f){
		for(File file : files)
			if(file!=null)
				if(f.getName().equals(file.getName())){
					JOptionPane.showMessageDialog(this, "O arquivo "+f.getName()+" já foi escolhido \ne não pode ser selecionado novamente.");
					return true;
				}
		return false;
	}
	
	public void execute() {
		build();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new FileSelectGUI();

	}

}
