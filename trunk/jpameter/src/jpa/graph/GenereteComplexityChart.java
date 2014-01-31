package jpa.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import jpa.compiler.CompilerConstants;

public class GenereteComplexityChart {
	private File[] files;
	private List<String> linesCharts;
	private int nFiles = 0;
	private int bar4File = 5;
	private int regA = 0 ;//Maior número de registros afetados
	
	public GenereteComplexityChart(File[] files){
		this.files = files;
		for(File f : files){
			if(f!=null)
				nFiles++;
		}
		if(nFiles>1)
			bar4File = 3;	
	}
	
	public ChartPanel[] genereteChartComplexity(){
		ChartPanel[] jfc = new ChartPanel[3];
		
		jfc[0] = generateChartPanelComplexity("SELECT");
		jfc[1] = generateChartPanelComplexity("INSERT");
		jfc[2] = generateChartPanelComplexity("UPDATE");
		return jfc;
	}
	
	private ChartPanel generateChartPanelComplexity(String typeQuery){
		linesCharts = new ArrayList<String>();
		JTable jt = new JTable(new DefaultTableModel(new Object[][]{},new String[]{"Arquivo, Numero, Query, Registros Afetados"}));
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int numberFile = 1;
		for(File f : files){
			if(f!=null){
				String lines[] = searchRowsFile(f, typeQuery);
				for(int i=1;i<=bar4File;i++){
					if(lines[i-1]!=null){
						String vals[] = lines[i-1].split("\\|");
						linesCharts.add(lines[i-1]+"|"+f.getName());
						if(nFiles>1)
							dataset.setValue(Long.parseLong(vals[2]), String.valueOf(i), f.getName());
						else
							dataset.setValue(Long.parseLong(vals[2]), String.valueOf(i), vals[5]);
					}
				}
			}
			numberFile++;
		}
		
		String titleH = "Arquivos";
		if(nFiles==1)
			titleH = "Registros afetados";
		
		JFreeChart jfc = ChartFactory.createBarChart("Complexidade "+typeQuery,titleH, "Tempo(ms)", dataset, 
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

	public List getLinesLastChart(){
		return linesCharts;
	}
}
