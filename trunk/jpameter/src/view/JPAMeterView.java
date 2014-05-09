package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jpa.graph.Chart;
import jpa.graph.GenereteComplexityChart;
import jpa.graph.GenereteAverageTimeChart;
import jpa.graph.GeneretePerformance;

public class JPAMeterView extends JFrame implements ActionListener{

	private JLabel jlOperacao;
	private JTabbedPane jtAbas;
	private JPanel jpComplexidade;
	private JPanel jpTempMedio;
	private JPanel jpDesempnho;
	private GenereteComplexityChart gc;
	private GenereteAverageTimeChart gm;
	private GeneretePerformance gp;
	
	private JButton jbDetailsSelect;
	private JButton jbDetailsUpdate;
	private JButton jbDetailsInsert;
	private JButton jbDetailsDelete;
	
	private Chart[] charts;
	private Chart averageChart;
	private int sizeFrame;
	private File[] files;

	public JPAMeterView(File[] files) {
		gc = new GenereteComplexityChart(files);
		gm = new GenereteAverageTimeChart(files);
		gp = new GeneretePerformance();
		this.files = files;
	}
	private void build() {

		jlOperacao = new JLabel("<html><h3>Resultados: </h3></html>");
		jlOperacao.setBounds(5, 5, 300, 20);
				
		jpComplexidade = new JPanel();
		jpTempMedio = new JPanel();
		jpDesempnho = new JPanel();
		
		jbDetailsSelect = new JButton("Details");
		jbDetailsUpdate = new JButton("Details");
		jbDetailsInsert = new JButton("Details");
		jbDetailsDelete = new JButton("Details");
		
		jbDetailsDelete.setPreferredSize(new Dimension(100, 100));
		jbDetailsUpdate.setPreferredSize(new Dimension(100, 100));
		jbDetailsInsert.setPreferredSize(new Dimension(100, 100));
		jbDetailsSelect.setPreferredSize(new Dimension(100, 100));
		
		// Aba complexidade		
		charts = gc.genereteChartComplexity(); 
		jpComplexidade.setSize(1000, 700);
		
		if(charts[0].getTable().getRowCount() != 0){
			jpComplexidade.add(charts[0].getChart());
			jpComplexidade.add(jbDetailsSelect);
			sizeFrame++;
		}
		if(charts[1].getTable().getRowCount() != 0){
			jpComplexidade.add(charts[1].getChart());
			jpComplexidade.add(jbDetailsInsert);
			sizeFrame++;
		}
		if(charts[2].getTable().getRowCount() != 0){
			jpComplexidade.add(charts[2].getChart());
			jpComplexidade.add(jbDetailsUpdate);
			sizeFrame++;
		}
		if(charts[3].getTable().getRowCount() != 0){
			jpComplexidade.add(charts[3].getChart());
			jpComplexidade.add(jbDetailsDelete);
			sizeFrame++;
		}

		//configuracoes dos botoes
		jbDetailsDelete.addActionListener(this);
		jbDetailsUpdate.addActionListener(this);
		jbDetailsInsert.addActionListener(this);
		jbDetailsSelect.addActionListener(this);
		
		//Aba tempo médio
		averageChart = gm.genereteMeanTimeChart();
		jpTempMedio.add(averageChart.getChart());
		jpTempMedio.add(averageChart.getLegend());
		
		jtAbas = new JTabbedPane();
		jtAbas.setBounds(0, 30, 1000, 850);
		jtAbas.add(jpComplexidade, "Complexity");
		jtAbas.add(jpTempMedio, "Average Time");
		jtAbas.add(jpDesempnho, "Performance");
		
		//Aba tempo performance
		
		for (File file : files) {
			if(file != null){
				JLabel jl = new JLabel(file.getName());
				jl.setSize(100, 30);
				jpDesempnho.add(jl);
				jpDesempnho.add(gp.generetePerformanceTables(file));
			}
		}
		
		// Configuracoes do Jframe ======================================================
		
		if(sizeFrame>2)
			this.setSize(1000, 850);
		else
			this.setSize(1000, 600);
		
		this.add(jtAbas);
		this.add(jlOperacao);
		this.setLayout(null);
		this.setVisible(true);
		//this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void execute() {
		build();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton)e.getSource();
		
		if(jb == jbDetailsSelect)
			new ShowDetailsGUI(charts[0].cloneSerializable()).execute();
		
		if(jb == jbDetailsInsert)
			new ShowDetailsGUI(charts[1].cloneSerializable()).execute();
		
		if(jb == jbDetailsUpdate)
			new ShowDetailsGUI(charts[2].cloneSerializable()).execute();
		
		if(jb == jbDetailsDelete)
			new ShowDetailsGUI(charts[3].cloneSerializable()).execute();
		
	}

}
