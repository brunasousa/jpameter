package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jpa.graph.Chart;
import jpa.graph.GenereteComplexityChart;
import jpa.graph.GenereteAverageTimeChart;

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
		jpComplexidade.setSize(900, 350);
		jpComplexidade.setLocation(0, 0);
		chart.getChart().setMaximumSize(new Dimension(700, 300));
		chart.getLegend().setMaximumSize(new Dimension(700, 200));
	
		jpComplexidade.add(jlOperacao, BorderLayout.PAGE_START);
		jpComplexidade.add(chart.getChart(), BorderLayout.CENTER);
		
		jpLegend.setLocation(0, 352);
		jpLegend.setSize(900, 150);
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
