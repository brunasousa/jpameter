package jpa.results;

import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import jpa.util.ProgressBar;
import view.JPAMeterView;

public class Results extends SwingWorker<Void, Void>{
	
	private ProgressBar progressBar;
	private File[] files;
	private HashMap<File, List<QueryFile>> results;
	private JFrame windowBase;
	private int numFiles;
	
	public Results(JFrame windowBase, File[] files) {
		this.progressBar = new ProgressBar(windowBase, "");
		progressBar.execute(this);
		this.files = files;
		this.windowBase = windowBase;
		this.results = new HashMap<>();
		setQuantFiles();
	}
	
	public HashMap<File, List<QueryFile>> getResults(){
		return results;
	}

	@Override
	protected Void doInBackground() throws Exception {
		progressBar.setMessage("Processing files...");
		progressBar.setProgress(1);
		
		for (int i = 0; i < files.length; i++) {
			progressBar.setMessage("Processing file "+files[i].getName());
			results.put(files[i], ProcessFile.process(files[i]));
			progressBar.setProgress((100/numFiles)*(i+1));
		}
		return null;
	}
	
	private void setQuantFiles(){
		for (File file : files) {
			if(file!=null)
				numFiles++;
		}
	}
	
	@Override
	public void done() {
	   Toolkit.getDefaultToolkit().beep();
	   windowBase.setEnabled(true);
	   windowBase.dispose();
	   new JPAMeterView(results).execute();
	}

}
