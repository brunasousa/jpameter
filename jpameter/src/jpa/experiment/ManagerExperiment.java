package jpa.experiment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ManagerExperiment {
	
	public static void main(String[] args) {
		
		File experimentFile = new File("/home/bruna/experiment.xml");
		SAXBuilder sb = new SAXBuilder();  
		Document d = null;
		try {
			d = sb.build(experimentFile);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		Element jpameter = d.getRootElement();  
		
		//Informacoes do banco de dados (OBS: esses dados são configurados automaticamente no persistense.xml quando o experimento é compilado juntamente com as bibliotecas)
		Element dbConfig = jpameter.getChild("db-config");
		String url = dbConfig.getChild("url").getValue();
		String db = dbConfig.getChild("database").getValue();
		String user = dbConfig.getChild("user").getValue();
		String password = dbConfig.getChild("password").getValue();
		String queryFile = dbConfig.getChild("query_file").getValue();
		
		//Numero de threads
		Element threads = jpameter.getChild("threads");
		int nThreads = Integer.parseInt(threads.getValue());
		
		//Passos do experimento
		Element experiment = jpameter.getChild("experiment");
		List steps = experiment.getChildren();
		Iterator i = steps.iterator(); 
		
		while(i.hasNext()){
			Element step = (Element) i.next();
			int time = Integer.parseInt(step.getAttributeValue("time"));
			Iterator q = step.getChildren().iterator();
			System.out.println("step");
			System.out.println(time);
			while(q.hasNext()){
				Element query = (Element)q.next();
				int sazon = Integer.parseInt(query.getValue());
				long timeExe = TimeUnit.MINUTES.toMillis(time)/(100/sazon);
				System.out.println("sazon: "+sazon);
				System.out.println("timeExec: "+timeExe);
				System.out.println(query.getName());

				QueryThread qt =  new QueryThread(queryFile, timeExe, query.getName());	
				qt.start();
			}
			
		}
	}

}
