package jpa.graph;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;

import jpa.results.QueryFile;

public abstract class GenerateChartAbstract {

	private HashMap<File, List<QueryFile>> results4File;
	
	public GenerateChartAbstract(HashMap<File, List<QueryFile>> results4File) {
		this.results4File = results4File;
	}
	
	public abstract Chart[] generate();
	
	public HashMap<File, List<QueryFile>> getResults4File(){
		return results4File;
	}

}
