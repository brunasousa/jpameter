package jpa.util;

import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

public class ProgressBar{
	
	private ProgressMonitor progressMonitor;
	private JFrame windowBase;
	
	public ProgressBar(JFrame windowBase, String message) {
		progressMonitor = new ProgressMonitor(windowBase, message, "", 0, 100);
		progressMonitor.setProgress(0);
		progressMonitor.setMillisToPopup(0);
		progressMonitor.setMillisToDecideToPopup(0);
		this.windowBase = windowBase;
	}
	
	public void execute(SwingWorker<Void, Void> task){
		task.addPropertyChangeListener((PropertyChangeListener) windowBase);
		task.execute();
	}
	
	public void setMessage(String message){
		progressMonitor.setNote(message);
	}
	
	public void setProgress(int progress){
		if(progress > 0  && progress <=100)
			progressMonitor.setProgress(progress);
	}
	
	public boolean isCanceled(){
		return progressMonitor.isCanceled();
	}

	
}
