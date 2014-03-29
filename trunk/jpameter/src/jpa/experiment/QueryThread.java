package jpa.experiment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class QueryThread extends Observable implements Runnable{

	private String queryFile;
	private long timeExec;
	private boolean executing = true;
	private String name;
	private int typeQuery = 0;
	private final String TYPE_QUERY[] = new String []{"select", "insert", "update", "delete"};	
	
	public QueryThread(String queryFile, long timeExec, String type){
		this.queryFile = queryFile;
		this.timeExec = timeExec;
		
		if(type.toLowerCase().equals(TYPE_QUERY[1]))
			typeQuery =  1;
		
		if(type.toLowerCase().equals(TYPE_QUERY[2]))
			typeQuery =  2;
			
		if(type.toLowerCase().equals(TYPE_QUERY[3]))
			typeQuery =  3;
	}

	@Override
	public void run() {
		
		long fullTime = 0;
		long time = 0;
		
		while(fullTime < timeExec){
			time = System.currentTimeMillis();
//			System.out.println("Query escolhida: "+chooserQuery());
		//Ler query do arquivo xml
		//Marcar inicio de uma nova consulta
		//Executar consulta
		//Estruturar dados
		//Finalizar marcacao de tempo
		//Gravar tempo de execucao em arquivo
			time = System.currentTimeMillis() - time;
			fullTime += time;
		}
		
		setExecuting(false);
	}
	
	private String chooserQuery(){
		Random r = new Random();
		File experimentFile = new File(queryFile);
		SAXBuilder sb = new SAXBuilder();  
		Document d = null;
		try {
			d = sb.build(experimentFile);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		List<String> queries = new ArrayList<String>();

		Element jpameterTag = d.getRootElement();  
		Element queriesTag = jpameterTag.getChild("queries");
		
		List query = queriesTag.getChildren();
		Iterator i = query.iterator();
		
		while(i.hasNext()){
//			System.out.println(((Element)i.next()).getChild("type").getValue());
//			System.out.println(((Element)i.next()).getChild("sql").getValue());
			Element type = ((Element)i.next()).getChild("type");
			if(Integer.parseInt(type.getValue())==typeQuery+1){
				Element sql = ((Element)i.next()).getChild("sql");
				queries.add(sql.getValue());
			}
		}
		if(queries.size()>0)
			return queries.get(r.nextInt(queries.size()));
		else
			return null;
	}
	
	public boolean isExecuting() {
		return executing;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
		setChanged();
		notifyObservers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
