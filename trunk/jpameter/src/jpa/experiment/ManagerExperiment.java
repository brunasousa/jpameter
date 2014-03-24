package jpa.experiment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ManagerExperiment {
	
	public static void main(String[] args) {
		
		File experimentFile = new File(args[1]);
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
		
		//Informacoes do banco de dados 
		Element dbConfig = jpameter.getChild("db-config");
		String url = dbConfig.getChild("url").getValue();
		String db = dbConfig.getChild("database").getValue();
		String user = dbConfig.getChild("user").getValue();
		String password = dbConfig.getChild("password").getValue();
		String queryFile = dbConfig.getChild("query_file").getValue();
		
		Element experiment = jpameter.getChild("experiment");
		List steps = experiment.getChildren();
		Iterator i = steps.iterator(); 
		
		while(i.hasNext()){
			Element step = (Element) i.next();
			int time = Integer.parseInt(step.getAttributeValue("time"));
			Iterator q = step.getChildren().iterator();
			while(q.hasNext()){
				Element query = (Element)q.next();
				int sazon = Integer.parseInt(query.getValue());
				double timeExe = time/(sazon/100);
				QueryThread qt =  new QueryThread(url, db, password, user, queryFile, (int)timeExe*60, query.getName());	
				qt.start();
			}
			
		}
	}

}
