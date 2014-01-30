package jpa.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GenereteMeanTimeChart {

	private File[] files;

	public GenereteMeanTimeChart(File[] files){
		this.files = files;
	}
	
	public ChartPanel genereteMeanTimeChart(){
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(File f : files){
			if(f!=null){
				dataset.setValue(calTime("SELECT",f), "SELECT", f.getName());
				dataset.setValue(calTime("INSERT",f), "INSERT", f.getName());
				dataset.setValue(calTime("UPDATE",f), "UPDATE", f.getName());
			}
		}
		
		JFreeChart jfc = ChartFactory.createBarChart("Tempo Médio das Consultas",null, "Tempo(ms)", dataset, 
				  											PlotOrientation.VERTICAL, true,true, false);
		jfc.setBackgroundPaint(Color.WHITE);
		jfc.getTitle().setPaint(Color.BLACK); 
		CategoryPlot p = jfc.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		ChartPanel cp = new ChartPanel(jfc);
		cp.setPreferredSize(new Dimension(800,300));
		
		return cp;
	}
	
	private long calTime(String typeQuery, File f){
		BufferedReader br;
		long totalTime = 0;
		int lines = 0;
		if(f!=null){
			try {
				br = new BufferedReader(new FileReader(f));
				while(br.ready()){ 
					String linha = br.readLine();  
					System.out.println(linha);
					String[] vals = linha.split("\\|");
					if(vals[3].toUpperCase().equals(typeQuery.toUpperCase())){
						totalTime += Long.parseLong(vals[2]);
						lines++;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return totalTime/lines;
	} 
}
