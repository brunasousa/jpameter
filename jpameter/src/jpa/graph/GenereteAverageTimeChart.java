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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GenereteAverageTimeChart {

	private File[] files;

	public GenereteAverageTimeChart(File[] files){
		this.files = files;
	}
	
	public Chart genereteMeanTimeChart(){
		List<String> linesCharts = new ArrayList<String>();
		Long time = (long) 0;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(File f : files){
			if(f!=null){
				
				time = calTime("SELECT",f);
				dataset.setValue(time, "SELECT", f.getName());
				linesCharts.add(genereteLineToLegend(time, "SELECT", f.getName(), 1));
				
				time = calTime("INSERT",f);
				dataset.setValue(time, "INSERT", f.getName());
				linesCharts.add(genereteLineToLegend(time, "INSERT", f.getName(), 2));
				
				time = calTime("UPDATE",f);
				dataset.setValue(time, "UPDATE", f.getName());
				linesCharts.add(genereteLineToLegend(time, "UPDATE", f.getName(), 3));
				
				time = calTime("DELETE",f);
				dataset.setValue(time, "DELETE", f.getName());
				linesCharts.add(genereteLineToLegend(time, "DELETE", f.getName(), 4));
			}
		}
		
		JFreeChart jfc = ChartFactory.createBarChart("Averege Time Queries",null, "Time(ms)", dataset, 
				  											PlotOrientation.VERTICAL, true,true, false);
		jfc.setBackgroundPaint(Color.WHITE);
		jfc.getTitle().setPaint(Color.BLACK); 
		CategoryPlot p = jfc.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		ChartPanel cp = new ChartPanel(jfc);
		cp.setPreferredSize(new Dimension(800,300));
		
		Chart c = new Chart();
		c.setChart(cp);
		c.setLegend(legendChart(800, 160, linesCharts));
		
		return c;
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
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(totalTime>0)
			return totalTime/lines;
		
		return 0;
	} 
	
	private String genereteLineToLegend(Long time, String query, String nameFile, int bar){
		String line;
		if(time > 0){
			line  = nameFile+"|"+query+"|"+time.toString()+"|"+getNumberWithColor(bar);
			time = (long)0;
			return line;
		}
		
		return null;
	}
	
	private JScrollPane legendChart(int width, int height, List<String> rows){
		JScrollPane jcp = new JScrollPane();
		DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, new String[]{"File","Bar","Type Query","Time(ms)"});
		JTable jt = new JTable();
		jt.setModel(dtm);
		
		for(String s:rows){
			if(s!=null){
				String[] vals = s.split("\\|");
				dtm.addRow(new String[]{vals[0],vals[3],vals[1],vals[2]});
			}
		} 
		jt.getColumnModel().getColumn(0).setPreferredWidth(200);
		jt.getColumnModel().getColumn(1).setPreferredWidth(50);
		jt.getColumnModel().getColumn(2).setPreferredWidth(50);
		jt.getColumnModel().getColumn(3).setPreferredWidth(50);
		//jt.setEnabled(false);
	
		jcp.setViewportView(jt);
		jcp.setPreferredSize(new Dimension(width, height));
		jcp.setBackground(Color.WHITE);
		jcp.createHorizontalScrollBar();
		return jcp;
	}
	
	private String getNumberWithColor(int number){
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
