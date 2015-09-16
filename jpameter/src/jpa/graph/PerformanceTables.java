package jpa.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jpa.results.QueryFile;
import jpa.results.QueryType;

public class PerformanceTables{
	private HashMap<File, List<QueryFile>> results4File;

	public PerformanceTables(HashMap<File, List<QueryFile>> results4File) {
		this.setResults4File(results4File);
	}

	public HashMap<File, JScrollPane> getTables() {
		HashMap<File, JScrollPane> fileTable = new HashMap<>(); 
		
		for (Map.Entry<File, List<QueryFile>> map : getResults4File().entrySet()) {
			
			JScrollPane jcp = new JScrollPane();
			DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, new String[]{"Type Query","Total Time","Total Rows Afeccted"});
			JTable jt = new JTable();
			jt.setModel(dtm);
			
			for (QueryFile qf : map.getValue()) {
				dtm.addRow(new String[]{QueryType.getNameByType(qf.getQueryType()),String.valueOf(qf.getTotalTime()),String.valueOf(qf.getRowsAfeccted())});
			}
			
			configureTable(jcp, jt);
			fileTable.put(map.getKey(), jcp);
		}
		
		
		return fileTable;
	}

	private void configureTable(JScrollPane jcp, JTable jt) {
		jt.getColumnModel().getColumn(0).setPreferredWidth(200);
		jt.getColumnModel().getColumn(1).setPreferredWidth(300);
		jt.getColumnModel().getColumn(2).setPreferredWidth(300);
		//jt.setEnabled(false);
	
		jcp.setViewportView(jt);
		jcp.setPreferredSize(new Dimension(800, 100));
		jcp.setBackground(Color.WHITE);
		jcp.createHorizontalScrollBar();
	}

	public HashMap<File, List<QueryFile>> getResults4File() {
		return results4File;
	}

	public void setResults4File(HashMap<File, List<QueryFile>> results4File) {
		this.results4File = results4File;
	}
}
