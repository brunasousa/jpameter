package jpa.experiment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class QueryThread extends Thread{

	private String queryFile;
	private long timeExec;
	
	private final String TYPE_QUERY[] = new String []{"select", "insert", "update", "delete"};	
	private final int SELECT = 0;
	private final int INSERT = 1;
	private final int UPDATE = 2;
	private final int DELETE = 3;
	
	public QueryThread(String queryFile, long timeExec, String type){
		this.queryFile = queryFile;
		this.timeExec = timeExec;
	}
	@Override
	public void run() {
		
		long fullTime = 0;
		long time = 0;
		
		while(fullTime < timeExec){
			time = System.currentTimeMillis();
		
		//Ler query do arquivo xml
		//Marcar inicio de uma nova consulta
		//Executar consulta
		//Estruturar dados
		//Finalizar marcacao de tempo
		//Gravar tempo de execucao em arquivo
			time = System.currentTimeMillis() - time;
			fullTime += time;
		}
	}
	
	private String chooserQuery(){
		
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
		String[] queries;

		Element jpameterTag = d.getRootElement();  
		Element queriesTag = jpameterTag.getChild("queries");
		
		List query = queriesTag.getChildren();
		Iterator i = query.iterator();
		
		while(i.hasNext()){
			Element type = ((Element)i.next()).getChild("type");
	
		}
		
		
		return "";
	}
	
	
}
