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
import jpa.results.QueryType;

public class AverageTimeChart extends ChartAbstract{

	public AverageTimeChart(HashMap<File, List<QueryFile>> results4File) {
		super(results4File);
	}
	
	@Override
	protected DefaultCategoryDataset getDataOfChat() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Map.Entry<File, List<QueryFile>> map : getResults4File().entrySet()) {
			for (QueryFile qf : map.getValue()) {
					if(qf.getAverageTime()!=0)
						dataset.setValue(Long.valueOf(qf.getAverageTime()), QueryType.getNameByType(qf.getQueryType()), qf.getFileName());
			}
		}
		
		return dataset;
	}

	@Override
	protected JScrollPane getLegendOfChart() {
		List<String> linesCharts = new ArrayList<String>();
		
		for (Map.Entry<File, List<QueryFile>> map : getResults4File().entrySet()) {
			for (QueryFile qf : map.getValue()) {
				linesCharts.add(genereteLineToLegend(qf.getAverageTime(), QueryType.getNameByType(qf.getQueryType()), qf.getFileName(), qf.getQueryType()));
			}
		}
		return legendChart(800, 160, linesCharts);
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

}
