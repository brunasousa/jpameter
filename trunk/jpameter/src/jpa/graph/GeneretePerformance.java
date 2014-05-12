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

public class GeneretePerformance {

	private File file;

	public JScrollPane generetePerformanceTables(File file){
		this.file = file;
		
		JScrollPane jcp = new JScrollPane();
		DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, new String[]{"Type Query","Total Time","Total Rows Afeccted"});
		JTable jt = new JTable();
		jt.setModel(dtm);
		
		
		List<String> linesCharts = new ArrayList<String>();
		linesCharts.add(searchRowsFile(file, "SELECT"));
		linesCharts.add(searchRowsFile(file, "INSERT"));
		linesCharts.add(searchRowsFile(file, "UPDATE"));
		linesCharts.add(searchRowsFile(file, "DELETE"));
		
		
		for (String line : linesCharts) {
			if(line!=null){
				String[] vals = line.split("\\|");
				dtm.addRow(new String[]{vals[0],vals[1],vals[2]});
			}
		}
		
		jt.getColumnModel().getColumn(0).setPreferredWidth(200);
		jt.getColumnModel().getColumn(1).setPreferredWidth(300);
		jt.getColumnModel().getColumn(2).setPreferredWidth(300);
		//jt.setEnabled(false);
	
		jcp.setViewportView(jt);
		jcp.setPreferredSize(new Dimension(800, 100));
		jcp.setBackground(Color.WHITE);
		jcp.createHorizontalScrollBar();
		
		
		return jcp;
	}
	
	private String searchRowsFile(File f, String typeQuery){
        BufferedReader br;
        long time = 0;
        long rowsAfeccted = 0;
		try {
				br = new BufferedReader(new FileReader(f));
				while(br.ready()){  
					String linha = br.readLine();  
					System.out.println(linha);
					String[] vals = linha.split("\\|");
					if(vals[3].toUpperCase().equals(typeQuery.toUpperCase())){
						time += Long.parseLong(vals[2]);
						rowsAfeccted += Long.parseLong(vals[5]);
					}
				}
				br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		if(time>0){
			return typeQuery+"|"+String.valueOf(time)+"|"+String.valueOf(rowsAfeccted);
		}
        return null;
	}
	
	
}
