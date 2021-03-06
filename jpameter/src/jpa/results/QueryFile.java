package jpa.results;

import java.util.TreeMap;

public class QueryFile {
	
	private TreeMap<Long, String> complexityQueries;
	private long rowsAfeccted;
	private long totalTime;
	private int queryType;
	private int numTotalQueries;
	private String fileName;
	
	public QueryFile(int queryType) {
		this.queryType = queryType;
		this.complexityQueries = new TreeMap<>();
	}
	
	public TreeMap<Long, String> getComplexityQueries() {
		return complexityQueries;
	}
	public void setComplexityQueries(TreeMap<Long, String> complexityQueries) {
		this.complexityQueries = complexityQueries;
	}
	public long getAverageTime() {
		if(getTotalTime() == 0 || getNumTotalQueries() == 0)
			return 0;
		
		return getTotalTime()/getNumTotalQueries();
	}
	public long getRowsAfeccted() {
		return rowsAfeccted;
	}
	public void setRowsAfeccted(long rowsAfeccted) {
		this.rowsAfeccted = rowsAfeccted;
	}
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getNumTotalQueries() {
		return numTotalQueries;
	}

	public void setNumTotalQueries(int numTotalQueries) {
		this.numTotalQueries = numTotalQueries;
	}
}
