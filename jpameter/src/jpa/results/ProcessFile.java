package jpa.results;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessFile {

	private static List<QueryFile> queryFiles;
	private static final int TYPE = 3;

	private static void initQueryFiles() {
		queryFiles = new ArrayList<QueryFile>();
		queryFiles.add(new QueryFile(QueryType.SELECT.getType()));
		queryFiles.add(new QueryFile(QueryType.INSERT.getType()));
		queryFiles.add(new QueryFile(QueryType.DELETE.getType()));
		queryFiles.add(new QueryFile(QueryType.UPDATE.getType()));
	}
	
	public static List <QueryFile> process(File f){
		initQueryFiles();
		
		if(f == null)
			return null;
		
		try {
			FileInputStream fIU = new FileInputStream(f);
			Scanner sc = new Scanner(fIU);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				System.out.println(line);
				String[] info = line.split("\\|");	
				QueryFile qf = getQueryFile(QueryType.getTypeByName((info[TYPE])));
				ProcessFileLine.process(info, qf);
				qf.setFileName(f.getName());
			}	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return queryFiles;
	}
	
	private static QueryFile getQueryFile(int type){
		for (QueryFile queryFile : queryFiles)
			if(queryFile.getQueryType() == type)
				return queryFile;
		
		return null;
	}
	
}
