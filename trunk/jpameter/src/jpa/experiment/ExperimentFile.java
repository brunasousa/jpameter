package jpa.experiment;

import java.io.FileWriter;
import java.io.IOException;

import jpa.JPAConstants;
import jpa.compiler.ConectionData;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ExperimentFile {

	public boolean create(int jpaProvider, int nTreads, int[] steps, String queryFile, int timeExp, int sazon){
		Element jpameter = new Element("jpameter");
		Element provider = new Element("jpa-provider");
		Element dbConfig = new Element("db-config");
		Element experiment = new Element("experiment");
		
		dbConfig.addContent(new Element("url").setText(ConectionData.HOST+":"+ConectionData.PORT));
		dbConfig.addContent(new Element("user").setText(ConectionData.USER));
		dbConfig.addContent(new Element("password").setText(ConectionData.PASS));
		dbConfig.addContent(new Element("database").setText(ConectionData.SCHEMA));
		provider.setText(JPAConstants.JPA_STRATEGIES[jpaProvider]);
		
		if(steps!=null)
			for(int i=0;i<steps.length;i+=4){
				Element s = new Element("step");
				s.setAttribute(new Attribute("time", String.valueOf(sazon)));
				s.addContent(new Element("select").setText(String.valueOf(steps[i])));
				s.addContent(new Element("insert").setText(String.valueOf(steps[i+1])));
				s.addContent(new Element("update").setText(String.valueOf(steps[i+2])));
				s.addContent(new Element("delete").setText(String.valueOf(steps[i+3])));
				experiment.addContent(s);
			}
		jpameter.addContent(provider);
		jpameter.addContent(dbConfig);
		jpameter.addContent(new Element("threads").setText(String.valueOf(nTreads)));
		jpameter.addContent(experiment);
		
		Document d = new Document(jpameter);
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat());
		try {
			xmlOut.output(d, new FileWriter(System.getProperty("user.home")+System.getProperty("file.separator")+"experiment.xml"));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
