package jpa.results;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Results {
	
	private File[] files;
	
	public Results(File[] files) {
		this.files = files;
	}
	
	public HashMap<File, List<QueryFile>> getResults(){
		HashMap<File, List<QueryFile>> results = new HashMap<>();
		
		for (File file : files) 
			results.put(file, ProcessFile.process(file));
		
		return results;
	}

}
