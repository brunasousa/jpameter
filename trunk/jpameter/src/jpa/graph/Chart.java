package jpa.graph;

import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;

public class Chart {
	
	private ChartPanel chart;
	private JScrollPane legend;
	
	public ChartPanel getChart() {
		return chart;
	}
	public void setChart(ChartPanel chart) {
		this.chart = chart;
	}
	public JScrollPane getLegend() {
		return legend;
	}
	public void setLegend(JScrollPane legend) {
		this.legend = legend;
	}

}
