package jpa.experiment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jfree.chart.plot.ThermometerPlot;

public class ManagerExperiment extends Thread implements Observer{
	private String url;
	private String db;
	private String user;
	private String password;
	private String queryFile;
	private int nThreads;

	public static void main(String args[]){
		ManagerExperiment me = new ManagerExperiment();
		me.start();
	}
	private List<QueryThread> queryTs = new ArrayList<QueryThread>();
	
	private List steps;
	
	public ManagerExperiment(){
		super();
		getExperimentInformation();
	}

	public void getExperimentInformation(){
		File experimentFile = new File("/home/chico/experiment.xml");
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
		url = dbConfig.getChild("url").getValue();
		db = dbConfig.getChild("database").getValue();
		user = dbConfig.getChild("user").getValue();
		password = dbConfig.getChild("password").getValue();
		queryFile = dbConfig.getChild("query_file").getValue();
		
		//Numero de threads
		Element threads = jpameter.getChild("threads");
		nThreads = Integer.parseInt(threads.getValue());
		
		//Passos do experimento
		Element experiment = jpameter.getChild("experiment");
		steps = experiment.getChildren();
	}

	@Override
	public void run() {
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
				System.out.println("Executando step de "+query.getName());
				System.out.println("sazon: "+sazon);
				System.out.println("timeExec: "+timeExe);
				System.out.println(query.getName());
				
				for(int j = 1; j<= nThreads; j++){
					System.out.println("Criando thread "+j+" de "+query.getName());
					QueryThread qt =  new QueryThread(queryFile, timeExe, query.getName());
					qt.setName("thread "+j+" de "+query.getName());
					queryTs.add(qt);
					qt.addObserver(this);
					Thread t = new Thread(qt);
					t.start();
				}
				System.out.println("Suspendendo");
				this.suspend();
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		boolean allFinished = true;
		System.out.println("Terminando "+((QueryThread)o).getName());
		for(QueryThread qt:queryTs){
			if(qt.isExecuting()){
				allFinished = false;
				break;
			}
		}
		if(allFinished){
			this.resume();
			System.out.println("Vontando de onde parou");
			queryTs.removeAll(queryTs);
		}
	}

}
