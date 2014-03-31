package jpa.experiment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
	private static final String PERSISTENCE_UNIT_NAME = "jpameter";
	private final String fileResult = System.getProperty("user.home")+System.getProperty("file.separator")+"Result_JPAMeter.txt";
	
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
		
		while(fullTime < timeExec){
			//Ler query do arquivo xml e seleciona uma consulta sql
			String sql = chooserQuery();
			//Marcar inicio de uma nova consulta
			long timeInit = System.currentTimeMillis();
			
			//Executar consulta
			int rows = processSQL(sql);
		
			//Finalizar marcacao de tempo
			long timeF = System.currentTimeMillis() - timeInit;
			fullTime += timeF;
			
			String result = timeInit+"|"+this.getName()+"|"+timeF+"|"+sql+"|"+rows;
			
			//Gravar dados de execucao em arquivo
			writeInFile(result);
		}
		
		setExecuting(false);
	}
	
	private int processSQL(String sql){
		Query q = null;
		int rows;
		List pojoList = null;
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		if(typeQuery+1 == 1){
			q = em.createNativeQuery(sql);
			pojoList = q.getResultList();
			rows = pojoList.size();
		}else{
			rows = q.executeUpdate();
		}

		em.getTransaction().commit();
		em.close();
		
		return rows;
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
	
	private void writeInFile(String line){
		File f = new File(fileResult);
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.println(line);
		pw.close();
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
