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

import javax.swing.JScrollPane;
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
	
	public Chart[] genereteChartComplexity(){
		Chart[] charts = new Chart[4];
		
		charts[0] = generateChartPanelComplexity("SELECT");
		charts[1] = generateChartPanelComplexity("INSERT");
		charts[2] = generateChartPanelComplexity("UPDATE");
		charts[3] = generateChartPanelComplexity("DELETE");
		return charts;
	}
	
	private Chart generateChartPanelComplexity(String typeQuery){
		List<String> linesCharts = new ArrayList<String>();
		JTable jt = new JTable(new DefaultTableModel(new Object[][]{},new String[]{"Arquivo, Numero, Query, Registros Afetados"}));
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int numberFile = 1;
		for(File f : files){
			if(f!=null){
				String lines[] = searchRowsFile(f, typeQuery);
				if(lines.length>0)
					for(int i=1;i<=bar4File;i++){
						if(lines[i-1]!=null){
							String vals[] = lines[i-1].split("\\|");
							linesCharts.add(lines[i-1]+"|"+f.getName()+"|"+String.valueOf(i));
							dataset.setValue(Long.parseLong(vals[2]), String.valueOf(i), f.getName());
						}
					}
			}
			numberFile++;
		}
		
		JFreeChart jfc = ChartFactory.createBarChart("Complexidade "+typeQuery,"Arquivos", "Tempo(ms)", dataset, 
				  											PlotOrientation.VERTICAL, false,true, true);
		jfc.setBackgroundPaint(Color.WHITE);
		jfc.getTitle().setPaint(Color.BLACK); 
		CategoryPlot p = jfc.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		ChartPanel cp = new ChartPanel(jfc);
		cp.setPreferredSize(new Dimension(550,190));
		
		Chart c = new Chart();
		c.setChart(cp);
		c.setLegend(legendChart(400, 150, linesCharts));
		return c;
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
	
	private JScrollPane legendChart(int width, int height, List<String> rows){
		JScrollPane jcp = new JScrollPane();
		DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, new String[]{"Arquivo","Num","Registros","Consulta"});
		JTable jt = new JTable();
		jt.setModel(dtm);
		
		for(String s:rows){
			String[] vals = s.split("\\|");
			dtm.addRow(new String[]{vals[6],vals[7],vals[5],vals[4]});
		} 
		jt.getColumnModel().getColumn(0).setPreferredWidth(20);
		jt.getColumnModel().getColumn(1).setPreferredWidth(5);
		jt.getColumnModel().getColumn(2).setPreferredWidth(10);
		//jt.setEnabled(false);
	
		jcp.setViewportView(jt);
		jcp.setPreferredSize(new Dimension(width, height));
		jcp.setBackground(Color.WHITE);
		jcp.createHorizontalScrollBar();
		return jcp;
	}
}
