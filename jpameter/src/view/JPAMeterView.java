package view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import jpa.graph.GenereteComplexityChart;
import jpa.graph.GenereteMeanTimeChart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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
		jpComplexidade.add(gc.genereteChartComplexity()[0]);
		jpComplexidade.add(gc.genereteChartComplexity()[1]);
		jpComplexidade.add(gc.genereteChartComplexity()[2]);
		
		//Aba tempo médio
		jpTempMedio.add(gm.genereteMeanTimeChart());
		
		jtAbas = new JTabbedPane();
		jtAbas.setBounds(0, 30, 1000, 700);
		jtAbas.add(jpComplexidade, "Complexidade");
		jtAbas.add(jpTempMedio, "Tempo Médio");
		jtAbas.add(jpDesempnho, "Desempenho");
		
		// Configuracoes do Jframe ======================================================
		this.setSize(1000, 700);
		this.add(jtAbas);
		this.add(jlOperacao);
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
