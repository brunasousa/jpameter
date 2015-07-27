package jpa.results;

public class ProcessFileLine {
	
	/* Ordem das informações nas linhas do arquivo*/
	private static final int TIME = 2;
	private static final int QUERY = 4;
	private static final int ROWS = 5;

	public static void process(String line[], QueryFile qf){
		increaseRows(Long.parseLong(line[ROWS]),qf);
		increaseTotalTime(Long.parseLong(line[TIME]),qf);
		complexQueries(Long.parseLong(line[TIME]), line[QUERY],qf);
	}
	
	private static void increaseRows(long rows, QueryFile qf){
		qf.setRowsAfeccted(qf.getRowsAfeccted()+rows);		
	}
	
	private static void increaseTotalTime(long time, QueryFile qf){
		qf.setTotalTime(qf.getTotalTime()+time);
	}
	
	private static void complexQueries(long time, String query, QueryFile qf){
		qf.getComplexityQueries().put(time, query);
		
		if(qf.getComplexityQueries().size()>3)
			qf.getComplexityQueries().remove(qf.getComplexityQueries().firstKey());
	}
}
