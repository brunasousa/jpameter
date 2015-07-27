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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import jpa.results.QueryFile;
import jpa.results.QueryType;

public class GenereteComplexityChart2 extends GenerateChartAbstract{
	
	public GenereteComplexityChart2(HashMap<File, List<QueryFile>> results4File){
		super(results4File);
	}
	
	@Override
	public Chart[] generate() {
		Chart[] charts = new Chart[4];
				
		charts[0] = generateChartPanelComplexity(QueryType.SELECT.getType());
		charts[1] = generateChartPanelComplexity(QueryType.DELETE.getType());
		charts[2] = generateChartPanelComplexity(QueryType.UPDATE.getType());
		charts[3] = generateChartPanelComplexity(QueryType.INSERT.getType());
				
		return charts;
	}
	
	private Chart generateChartPanelComplexity(int typeQuery){
		List<String> linesCharts = new ArrayList<String>();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Map.Entry<File, List<QueryFile>> map : getResults4File().entrySet()) {
			for (QueryFile qf : map.getValue()) {
				if(qf.getQueryType() == typeQuery){
					int ordem = 1;
					for (Map.Entry<Long, String> query : qf.getComplexityQueries().entrySet()) {
						linesCharts.add(qf.getFileName()+"|"+getNumberWithColor(ordem)+"|"+query.getKey()+"|"+"0"+"|"+query.getValue());
						dataset.setValue(query.getKey(), String.valueOf(ordem), qf.getFileName());
						ordem++;
					}
				}
			}
		}
		
		JFreeChart jfc = ChartFactory.createBarChart("Complexity "+typeQuery,"Files", "Time(ms)", dataset, 
				  											PlotOrientation.VERTICAL, false,true, true);
		jfc.setBackgroundPaint(Color.WHITE);
		jfc.getTitle().setPaint(Color.BLACK); 
		CategoryPlot p = jfc.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		ChartPanel cp = new ChartPanel(jfc);
		cp.setPreferredSize(new Dimension(850,200));
		
		Chart c = new Chart();
		c.setChart(cp);
		c.setLegend(legendChart(875, 165, linesCharts));
		return c;
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
