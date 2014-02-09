package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jpa.graph.GenereteComplexityChart;
import jpa.graph.GenereteMeanTimeChart;

public class JPAMeterView extends JFrame{

	private JLabel jlOperacao;
	private JTabbedPane jtAbas;
	private JPanel jpComplexidade;
	private JPanel jpTempMedio;
	private JPanel jpDesempnho;
	private GenereteComplexityChart gc;
	private GenereteMeanTimeChart gm;

	public JPAMeterView(File[] files) {
		gc = new GenereteComplexityChart(files);
		gm = new GenereteMeanTimeChart(files);
		createComponents();
	}

	private void createComponents() {

		jlOperacao = new JLabel("<html><h3>Resultados: </h3></html>");
		jlOperacao.setBounds(5, 5, 300, 20);
				
		jpComplexidade = new JPanel();
		jpTempMedio = new JPanel();
		jpDesempnho = new JPanel();
		
		// Aba complexidade		
		jpComplexidade.setSize(1000, 700);
		jpComplexidade.add(gc.genereteChartComplexity()[0].getChart());
		jpComplexidade.add(gc.genereteChartComplexity()[0].getLegend());
		jpComplexidade.add(gc.genereteChartComplexity()[1].getChart());
		jpComplexidade.add(gc.genereteChartComplexity()[1].getLegend());
		jpComplexidade.add(gc.genereteChartComplexity()[2].getChart());
		jpComplexidade.add(gc.genereteChartComplexity()[2].getLegend());
		jpComplexidade.add(gc.genereteChartComplexity()[3].getChart());
		jpComplexidade.add(gc.genereteChartComplexity()[3].getLegend());
		
		//Aba tempo médio
		jpTempMedio.add(gm.genereteMeanTimeChart());
		
		jtAbas = new JTabbedPane();
		jtAbas.setBounds(0, 30, 1000, 850);
		jtAbas.add(jpComplexidade, "Complexidade");
		jtAbas.add(jpTempMedio, "Tempo Médio");
		jtAbas.add(jpDesempnho, "Desempenho");
		
		// Configuracoes do Jframe ======================================================
		this.setSize(1000, 850);
		this.add(jtAbas);
		this.add(jlOperacao);
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	private JScrollPane legendChart(int width, int height, List<String> rows){
		JScrollPane jcp = new JScrollPane();
		DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, new String[]{"Arquivo","Num","Registros","Consulta"});
		JTable jt = new JTable();
		jt.setModel(dtm);
		
		for(String s:rows){
			String[] vals = s.split("\\|");
			dtm.addRow(new String[]{vals[6],vals[7],vals[5],vals[2]});
		} 
		jt.getColumnModel().getColumn(0).setPreferredWidth(20);
		jt.getColumnModel().getColumn(1).setPreferredWidth(5);
		jt.getColumnModel().getColumn(2).setPreferredWidth(10);
		
	
		jcp.setViewportView(jt);
		jcp.setPreferredSize(new Dimension(width, height));
		jcp.createHorizontalScrollBar();
		return jcp;
	}

}
