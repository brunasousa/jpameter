package jpa.experiment;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ManagerExperiment extends Thread implements Observer{
	private String url;
	private String db;
	private String user;
	private String password;
	private String queryFile;
	private String fileResult;
	private int nThreads;
	private int nRunningThread = 0;
	
	private List<Element> steps;

	public static void main(String args[]){
		ManagerExperiment me = new ManagerExperiment();
		me.start();
	}
	
	public ManagerExperiment(){
		super();
		getExperimentInformation();
	}

	public void getExperimentInformation(){
//		File experimentFile = new File(System.getProperty("user.home")+System.getProperty("file.separator")+"experiment.xml");
		File experimentFile = new File("experiment.xml");
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
		
		//Informacoes JPAStrategy
		Element jpaStrategy = jpameter.getChild("jpa-provider");
		
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
		
		fileResult = "JM_"+jpaStrategy.getValue().toUpperCase()+"_"+System.currentTimeMillis()+".txt";
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
				if(sazon > 0){
					long timeExe = TimeUnit.MINUTES.toMillis(time)/(100/sazon);
					System.out.println("Executando step de "+query.getName());
					System.out.println("timeExec: "+timeExe);
					System.out.println(query.getName());
				
					for(int j = 1; j<= nThreads; j++){
						System.out.println("Criando thread "+j+" de "+query.getName());
						QueryThread qt =  new QueryThread(queryFile, timeExe, query.getName(), fileResult);
						qt.setName("thread "+j+" de "+query.getName());
						qt.addObserver(this);
						Thread t = new Thread(qt);
						t.start();
					}
					System.out.println("Suspendendo");
					this.suspend();
				}
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		System.out.println("Terminando "+((QueryThread)o).getName());
		
		if(!((QueryThread)o).isExecuting())
			nRunningThread++;
		
		if(nRunningThread == nThreads){
			System.out.println("Vontando de onde parou");
			this.resume();
			nRunningThread = 0;
		}
	}

}
