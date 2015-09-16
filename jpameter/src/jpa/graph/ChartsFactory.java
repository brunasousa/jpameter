package jpa.graph;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;

import jpa.results.QueryFile;
import jpa.results.QueryType;

public class ChartsFactory {

	public static Chart generateAverageTimeCharts(HashMap<File, List<QueryFile>> results4File){
		return new AverageTimeChart(results4File).generateDefaultBarChart("Averege Time Queries",null, "Time(ms)");
	}
	
	public static Chart[] generateComplexityCharts(HashMap<File, List<QueryFile>> results4File) {
		Chart[] charts = new Chart[4];
				
		charts[0] = (new ComplexityChart(results4File,QueryType.SELECT.getType())).generateDefaultBarChart("Complexity "+QueryType.SELECT.getName(),"Files", "Time(ms)"); 
		charts[1] = (new ComplexityChart(results4File,QueryType.DELETE.getType())).generateDefaultBarChart("Complexity "+QueryType.DELETE.getName(),"Files", "Time(ms)");
		charts[2] = (new ComplexityChart(results4File,QueryType.UPDATE.getType())).generateDefaultBarChart("Complexity "+QueryType.UPDATE.getName(),"Files", "Time(ms)");
		charts[3] = (new ComplexityChart(results4File,QueryType.INSERT.getType())).generateDefaultBarChart("Complexity "+QueryType.INSERT.getName(),"Files", "Time(ms)");
				
		return charts;
	}
	
	public static HashMap<File, JScrollPane> generatePerformanceTables(HashMap<File, List<QueryFile>> results4File){
		return new PerformanceTables(results4File).getTables();
	}
	
}
