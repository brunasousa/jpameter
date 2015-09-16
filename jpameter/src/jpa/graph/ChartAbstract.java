package jpa.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import jpa.results.QueryFile;

public abstract class ChartAbstract {

	private HashMap<File, List<QueryFile>> results4File;
	
	public ChartAbstract(HashMap<File, List<QueryFile>> results4File) {
		this.results4File = results4File;
	}
	
	public HashMap<File, List<QueryFile>> getResults4File(){
		return results4File;
	}
	
	public Chart generateDefaultBarChart(String title, String col1, String col2){
		
		JFreeChart jfc = ChartFactory.createBarChart(
		 title,
		 col1,
		 col2, 
		 getDataOfChat(), 
		 PlotOrientation.VERTICAL, false,true, true
		);
		
		if(title != null){
			jfc.setBackgroundPaint(Color.WHITE);
			jfc.getTitle().setPaint(Color.BLACK); 
			CategoryPlot p = jfc.getCategoryPlot(); 
			p.setRangeGridlinePaint(Color.red); 
		}

		ChartPanel cp = new ChartPanel(jfc);
		cp.setPreferredSize(new Dimension(850,200));
		
		Chart c = new Chart();
		c.setChart(cp);
		c.setLegend(getLegendOfChart());
		
		return c;
	}
	
	protected abstract DefaultCategoryDataset getDataOfChat();
	
	protected abstract JScrollPane getLegendOfChart();
	
	/*
	 * Colocar método para legendas aqui, mas antes ver uma forma melhor de ter os dados para
	 * as tebelas, com split é muita gambi
	 */
	
	public String getNumberWithColor(int number){
		switch (number) {
			case 1:
				return "<html><font color='red'>1</font></html>";
	
			case 2:
				return "<html><font color='blue'>2</font></html>";
			
			case 3:
				return "<html><font color='green'>3</font></html>";
				
			case 4:
				return "<html><font color='yellow'>4</font></html>";
			
			case 5:
				return "<html><font color='pink'>5</font></html>";
		}
		return null;
	}
	
	

}
