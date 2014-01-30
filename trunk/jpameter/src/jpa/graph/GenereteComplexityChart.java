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

import jpa.compiler.CompilerConstants;

public class GenereteComplexityChart {
	private File[] files;
	private int nFiles = 0;
	private int bar4File = 5;
	
	public GenereteComplexityChart(File[] files){
		this.files = files;
		for(File f : files){
			if(f!=null)
				nFiles++;
		}
	}
	
	public ChartPanel[] genereteChartComplexity(){
		ChartPanel[] jfc = new ChartPanel[3];
		if(nFiles>1)
			bar4File = 3;
		
		jfc[0] = generateChartPanelComplexity("SELECT");
		jfc[1] = generateChartPanelComplexity("INSERT");
		jfc[2] = generateChartPanelComplexity("UPDATE");
		return jfc;
	}
	
	private ChartPanel generateChartPanelComplexity(String typeQuery){
		String serie1 = "1 a 100 registros afetados";
		String serie2 = "101 a 500 registros afetados";
		String serie3 = "501 a 1000 registros afetados";
		String serie4 = "acima de 1000 registros afetados";
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(File f : files){
			if(f!=null){
				String lines[] = searchRowsFile(f, typeQuery);
				for(int i=1;i<=bar4File;i++){
					if(lines[i-1]!=null){
						String vals[] = lines[i-1].split("\\|");
						int registros = Integer.parseInt(vals[5]);
						String serie = null;
						
						if(registros>1 && registros<=100)
							serie = serie1;
						if(registros>101 && registros<=500)
							serie = serie2;
						if(registros>501 && registros<=1000)
							serie = serie3;
						if(registros>1000)
							serie = serie4;
						
						dataset.setValue(Long.parseLong(vals[2]), String.valueOf(i), f.getName());
					}
				}
			}
		}
		
		JFreeChart jfc = ChartFactory.createBarChart("Complexidade "+typeQuery,"Queries", "Tempo(ms)", dataset, 
				  											PlotOrientation.VERTICAL, false,true, true);
		jfc.setBackgroundPaint(Color.WHITE);
		jfc.getTitle().setPaint(Color.BLACK); 
		CategoryPlot p = jfc.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		ChartPanel cp = new ChartPanel(jfc);
		cp.setPreferredSize(new Dimension(800,200));
		
		return cp;
	}
	
	private String[] searchRowsFile(File f, String typeQuery){
		String[] rows = new String[bar4File];
        BufferedReader br;
        long val1 = 0;
        long val2 = 0;
        int readLines = 0;
		try {
			for(int i=1;i<=bar4File;i++){
				br = new BufferedReader(new FileReader(f));
				while(br.ready()){  
					String linha = br.readLine();  
					System.out.println(linha);
					String[] vals = linha.split("\\|");
					if(vals[3].toUpperCase().equals(typeQuery.toUpperCase())){
						long time = Long.parseLong(vals[2]);
						if(val2==0 && time>val1){
								rows[i-1]=linha;
								val1=time; 
						}else{
							if(val2>time && time>val1){
								rows[i-1]=linha;
								val1=time; 
							}
						}
					}
				}	
				if(val1!=0){
					val2 = val1;
					val1 = 0;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return rows;
	}

	
}
