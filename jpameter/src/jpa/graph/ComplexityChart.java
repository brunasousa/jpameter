package jpa.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.category.DefaultCategoryDataset;

import jpa.results.QueryFile;

public class ComplexityChart extends ChartAbstract{
	
	private int typeQuery;
	
	public ComplexityChart(HashMap<File, List<QueryFile>> results4File,int typeQuery){
		super(results4File);
		this.typeQuery = typeQuery;
	}
	

	@Override
	protected DefaultCategoryDataset getDataOfChat() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Map.Entry<File, List<QueryFile>> map : getResults4File().entrySet()) {
			for (QueryFile qf : map.getValue()) {
				if(qf.getQueryType() == typeQuery){
					int ordem = 1;
					for (Map.Entry<Long, String> query : qf.getComplexityQueries().entrySet()) {
						dataset.setValue(query.getKey(), String.valueOf(ordem), qf.getFileName());
						ordem++;
					}
				}
			}
		}
		
		return dataset;
	}

	@Override
	protected JScrollPane getLegendOfChart() {
		List<String> linesCharts = new ArrayList<String>();
		
		for (Map.Entry<File, List<QueryFile>> map : getResults4File().entrySet()) {
			for (QueryFile qf : map.getValue()) {
				if(qf.getQueryType() == typeQuery){
					int ordem = 1;
					for (Map.Entry<Long, String> query : qf.getComplexityQueries().entrySet()) {
						linesCharts.add(qf.getFileName()+"|"+getNumberWithColor(ordem)+"|"+query.getKey()+"|"+"0"+"|"+query.getValue());
						ordem++;
					}
				}
			}
		}
		
		return legendChart(875, 165, linesCharts);
	}
	
	
	private JScrollPane legendChart(int width, int height, List<String> rows){
		JScrollPane jcp = new JScrollPane();
		DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, new String[]{"File","Bar","Time(ms)","Rows","Query"});
		JTable jt = new JTable();
		jt.setModel(dtm);
		
		for(String s:rows){
			String[] vals = s.split("\\|");
			dtm.addRow(new String[]{vals[0],vals[1],vals[2],vals[3],vals[4]});
		} 
		
		jt.getColumnModel().getColumn(0).setPreferredWidth(100);
		jt.getColumnModel().getColumn(1).setPreferredWidth(20);
		jt.getColumnModel().getColumn(2).setPreferredWidth(50);
		jt.getColumnModel().getColumn(3).setPreferredWidth(50);
		jt.getColumnModel().getColumn(4).setPreferredWidth(500);
		//jt.setEnabled(false);
	
		jcp.setViewportView(jt);
		jcp.setPreferredSize(new Dimension(width, height));
		jcp.setBackground(Color.WHITE);
		jcp.createHorizontalScrollBar();
		return jcp;
	}


}
