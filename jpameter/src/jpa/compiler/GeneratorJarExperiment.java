package jpa.compiler;

import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import jpa.JPAConstants;
import jpa.util.ProgressBar;
import view.HomeProjectView;

public class GeneratorJarExperiment extends SwingWorker<Void, Void>{
	
	private JFrame windowBase;
	private ProgressBar progressBar;
	private Compiler c;
	private int strategy;
	
	public GeneratorJarExperiment(JFrame windowBase, int strategy) {
		this.windowBase = windowBase;
		this.progressBar = new ProgressBar(windowBase, "");
		this.strategy = strategy;
		progressBar.execute(this);
		c = new Compiler();
	}

    @Override
    public Void doInBackground() {
        progressBar.setMessage("Adding dependencies...");
		try {
			String libsJPA = CompilerConstants.HIBERNATE;
			
			switch (strategy) {
			case JPAConstants.JPA_ECLIPSELINK:
				libsJPA = CompilerConstants.ECLIPSELINK;
				break;
			case JPAConstants.JPA_OPENJPA:
				libsJPA = CompilerConstants.OPENJPA;
				break;
			}
			
			progressBar.setProgress(10);
			c.addDependencies(libsJPA);
			c.addDependencies(CompilerConstants.COMMONS);
			progressBar.setProgress(20);
			c.addMainClasses();
			progressBar.setProgress(30);

			FilesApplication fa = new FilesApplication();
			
			fa.generatePersistenseFile(
					CompilerConstants.DEFAULT_FOLDER
							+ CompilerConstants.FILES_JAR
							+ System.getProperty("file.separator")
							+ "META-INF"
							+ System.getProperty("file.separator"),
					strategy);
		
			progressBar.setProgress(40);
			progressBar.setMessage("Compiling classes...");
			
			c.compileClasses();
			
			
			progressBar.setProgress(60);
			progressBar.setMessage("Generating jar...");
			
			c.generateJar(JPAConstants.JPA_STRATEGIES[strategy]);
			
			progressBar.setProgress(90);
			progressBar.setMessage("Removing source files...");
			
			c.removeFiles(CompilerConstants.DEFAULT_FOLDER);
			
			progressBar.setProgress(100);

			JOptionPane.showMessageDialog(null,
			"Jar do experimento gerado em "
					+ CompilerConstants.DEFAULT_FOLDER);
			
			windowBase.dispose();
			new HomeProjectView().execute();
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        return null;
    }

    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        windowBase.setEnabled(true);
    }
	

}
