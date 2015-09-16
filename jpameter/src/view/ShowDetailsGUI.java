package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jpa.graph.Chart;

public class ShowDetailsGUI extends JFrame{

	private JLabel jlOperacao;
	private JPanel jpComplexidade;
	private JPanel jpLegend;
	private Chart chart;

	public ShowDetailsGUI(Chart c) {
		this.chart = c;
	}

	private void build() {

		jlOperacao = new JLabel("<html><h3>Details: </h3></html>");
		jlOperacao.setSize(20, 20);
				
		jpComplexidade = new JPanel(new BorderLayout(10,10));
		jpLegend = new JPanel();
		
		// Panel complexidade		
		jpComplexidade.setSize(880, 350);
		jpComplexidade.setLocation(10, 0);
		chart.getChart().setMaximumSize(new Dimension(700, 300));
		chart.getLegend().setMaximumSize(new Dimension(700, 200));
	
		jpComplexidade.add(jlOperacao, BorderLayout.PAGE_START);
		jpComplexidade.add(chart.getChart(), BorderLayout.CENTER);
		
		jpLegend.setLocation(10, 352);
		jpLegend.setSize(880, 170);
		jpLegend.add(chart.getLegend());
		
		
		// Configuracoes do Jframe ======================================================
		this.setSize(900, 550);
		this.add(jpComplexidade);
		this.add(jpLegend);
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		this.setTitle("Details Chart");

	}
	
	
	public void execute() {
		build();
		setVisible(true);
	}

}
