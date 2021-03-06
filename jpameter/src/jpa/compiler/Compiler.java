package jpa.compiler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import jpa.JPAConstants;

public class Compiler {

	public Compiler(){
		createFolder(CompilerConstants.DEFAULT_FOLDER);
		createFolder(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR);
	}
	
	String separator = System.getProperty("file.separator");
	
	public void generateJar(String strategy) throws IOException{
		removeSignedFiles();
		File temp  = new File(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR);
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.home")
													 +separator
													 +"jpameter_"+strategy.toLowerCase()+"_"+System.currentTimeMillis()+".jar");
		
		File oldManifest = new File(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR+separator+"META-INF"+separator+"MANIFEST.MF");
		if(oldManifest.exists())
			oldManifest.delete();
		
		//Manifest Informations 
		StringBuffer sb = new StringBuffer();
		sb.append("Manifest-Version: 1.0\n");
		sb.append("Class-Path: .\n");
		sb.append("Main-Class: "+CompilerConstants.MAIN_CLASS+"\n");
		
		InputStream is = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
		Manifest mf = new Manifest(is);

		JarOutputStream out = new JarOutputStream(fout, mf);
		addFilesToJar(out, temp);
		
		out.close();
		fout.close();
		System.out.println("jpameter.jar gerado em "+System.getProperty("user.home"));
	}
	
	public boolean addFilesToJar(JarOutputStream out, File archive) throws IOException{	
		if(archive.isDirectory()){
			File[] files = archive.listFiles();
			for(File f: files){
				addFilesToJar(out, f);
			}
		}else{
			byte b[] = new byte[1024];
			JarEntry addFiles = new JarEntry(archive.getAbsolutePath().replace(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR+separator, ""));
			addFiles.setTime(archive.lastModified());
			out.putNextEntry(addFiles);
			BufferedInputStream bi = new BufferedInputStream(new FileInputStream(archive));
			while (true) {
				int len = bi.read(b, 0, b.length);
				if (len <= 0)
					break;
				out.write(b, 0, len);
			}
			bi.close();
		}
		return true;
	}
	
	public boolean saveClass(String contentClass){
		int inicioClass = contentClass.indexOf("class")+6;
		String nameFile = contentClass.substring(inicioClass,contentClass.indexOf(" ", inicioClass))+".java";
		
		String path = 	CompilerConstants.DEFAULT_FOLDER
						+CompilerConstants.FILES_JAR;
		createFolder(path);
		
		path+= separator+CompilerConstants.URL_ORIGIN_FILES;
		createFolder(path);
		
		File f = new File(path+separator+nameFile);
		PrintStream ps = null;
		
		try {
			f.createNewFile();
			ps = new PrintStream(f);
			ps.print(contentClass);
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public boolean addDependencies(String strategyFolder) throws IOException, InterruptedException{
		File folder = new File(System.getProperty("user.dir")
								+separator
								+"src"
								+separator
								+strategyFolder);
		
		if(!folder.exists()&&!folder.isDirectory())
			return false;
		
		String[] jars = folder.list();
		if(jars.length==0)
			return false;
		
		createFolder(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR); 
		
		for(String jar: jars){
			if(jar.endsWith(".jar")){
				System.out.println("\n======================================");
				System.out.println("Jar: "+jar);
				System.out.println("Comand: \n");
				System.out.println("jar -xf "
											+System.getProperty("user.dir")
											+separator
											+"src"
											+separator
											+strategyFolder+separator+jar);
				
				Process p = Runtime.getRuntime().exec("jar -xf "
											+System.getProperty("user.dir")
											+separator
											+"src"
											+separator
											+strategyFolder+separator+jar,
											null,
											new File(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR));
				p.waitFor();
			}
		}
		
		return true;
	}
	
	public BufferedReader executeJar(String pathJar){
		System.out.println("Començando a executar!============================");
		File f = new File(pathJar);
		String line = "";
		BufferedReader reader = null;
		ProcessBuilder pb  = new ProcessBuilder("java", "-jar", f.getName());
		pb.directory(new File(f.getParent()));
		pb.redirectErrorStream(true);
		try {
			Process p = pb.start();
			InputStream stdout = p.getInputStream ();
			reader = new BufferedReader (new InputStreamReader(stdout));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return reader;
	}
	
	public boolean copyExperimentFileToFilesJar(String originPath, int strategy){
		String finalPath = CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR+separator+"experiment.xml";
		return copyFileTo(originPath, finalPath) && modifyStrategyOfExperimetFile(strategy, finalPath);
	}
	
	public boolean modifyStrategyOfExperimetFile(int strategy, String pathFile){
		File experimentFile = new File(pathFile);
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
		Element jpaStrategy = jpameter.getChild("jpa-provider");
		jpaStrategy.setText(JPAConstants.JPA_STRATEGIES[strategy]);
		experimentFile.delete();
		
		Document d2 = new Document(jpameter.detach());
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat());
		try {
			xmlOut.output(d2, new FileWriter(pathFile));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public boolean copyFileTo(String originPath, String finalPath){
		
		File oFile  = new File(originPath);
		File fFile  = new File(finalPath);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream(oFile);
			fos = new FileOutputStream(fFile);
			
			FileChannel fcIn = fis.getChannel();
			FileChannel fcOut = fos.getChannel();
			
			fcOut.transferFrom(fcIn, 0, fcIn.size());
			
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao selecionar aquivo de origem.");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Erro ao copiar arquivos.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void addMainClasses(){ //Não deve ficar assim, precisar ser arrumado
		
		File f1 = new File(System.getProperty("user.dir")+separator
						+"src"+separator
						+"jpa"+separator
						+"experiment"+separator+"ManagerExperiment.java");
		
		File f2 = new File(System.getProperty("user.dir")+separator
				+"src"+separator
				+"jpa"+separator
				+"experiment"+separator+"QueryThread.java");
		
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(f1));
			String contentFile1="";
			for(int i=0;br1.ready();i++){
				if(i>0)
					contentFile1+=br1.readLine()+"\n";
				else
					br1.readLine();
			}
			
			BufferedReader br2 = new BufferedReader(new FileReader(f2));
			String contentFile2="";
			for(int i=0;br2.ready();i++){
				if(i>0)
					contentFile2+=br2.readLine()+"\n";
				else
					br2.readLine();
			}
			
			saveClass(contentFile1);
			saveClass(contentFile2);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void compileClasses() throws IOException, InterruptedException{
		
		String sourceFiles = CompilerConstants.DEFAULT_FOLDER
							 +CompilerConstants.FILES_JAR
							 +separator
							 +CompilerConstants.URL_ORIGIN_FILES;
		
		File folder = new File(sourceFiles);
		
		String pathDependencies =System.getProperty("user.dir")
								+separator
								+"src"
								+separator
								+"libs"+separator+"eclipselink"+separator+"javax.persistence_2.1.0.v201304241213.jar";
		
		pathDependencies+= System.getProperty("path.separator")
						+System.getProperty("user.dir")
						+separator
						+"src"
						+separator
						+"libs"+separator+"commons"+separator+"jdom-2.0.2.jar";
		
		String pathFinal = 	CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR;
		
		String filesToCompiler = "";
		
		File[] files = folder.listFiles();
		for(File f: files){
			filesToCompiler+= f.getName()+" ";
		}
		
		System.out.println("javac -d "+pathFinal+" -cp "+sourceFiles+System.getProperty("path.separator")+pathDependencies+" "+filesToCompiler);
		Process p = Runtime.getRuntime().exec("javac -d "+pathFinal+" -cp "+sourceFiles+System.getProperty("path.separator")+pathDependencies+" "+filesToCompiler,
				null,
				new File(sourceFiles));
		p.waitFor();
		
	}
	public void createFolder(String folder){
		File f = new File(folder);
		if(!f.exists())
			f.mkdir();
	}
	
	private void removeSignedFiles(){
		String path = CompilerConstants.DEFAULT_FOLDER
					  +CompilerConstants.FILES_JAR
					  +separator
					  +"META-INF"
					  +separator;
		File f = new File(path);
		if(f.exists()){
			File[] files = f.listFiles();
			for(File file: files){
				if(!file.isDirectory()){
					String name = file.getName().toUpperCase();
					if(name.lastIndexOf(".") != -1){
						String ext = name.substring(name.lastIndexOf("."), name.length());
						if(ext.equals(".SF")||ext.equals(".DSA")||ext.equals(".RSA")||ext.equals(".EC"))
							file.delete();
					}
				}
			}
		}
	}
	
	public void removeFiles(String path){
		File f = new File(path);
		if(f.isDirectory()){
			File files [] = f.listFiles();
			for(File file: files){
				if(file.isDirectory())
					removeFiles(file.getAbsolutePath());
				else
					file.delete();
			}
		}
		f.delete();
	}
}
