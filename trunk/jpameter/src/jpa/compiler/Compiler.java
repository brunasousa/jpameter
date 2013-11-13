package jpa.compiler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.crypto.spec.OAEPParameterSpec;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class Compiler {
	public Compiler(){
		createFolder(CompilerConstants.DEFAULT_FOLDER);
	}
	
	String separator = System.getProperty("file.separator");
	
	public void generateJar() throws IOException{
		File temp  = new File(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR);
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.home")
													 +separator
													 +"jpameter.jar");
		
		File oldManifest = new File(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR+separator+"META-INF"+separator+"MANIFEST.MF");
		if(oldManifest.exists())
			oldManifest.delete();
		
		//Manifest Informations 
		StringBuffer sb = new StringBuffer();
		sb.append("Manifest-Version: 1.0\n");
		sb.append("Main-Class: "+CompilerConstants.MAIN_CLASS+"\n");
		sb.append("Java-Bean: True\n");
		
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
			JarEntry addFiles = new JarEntry(archive.getAbsolutePath().replace(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR, ""));
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
						+CompilerConstants.FILES_JAR
						+separator
						+CompilerConstants.URL_ORIGIN_FILES; 
		
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
	
	public void compileClasses() throws IOException, InterruptedException{
		File folder = new File(	CompilerConstants.DEFAULT_FOLDER
								+CompilerConstants.FILES_JAR
								+separator
								+CompilerConstants.URL_ORIGIN_FILES);
		
		String pathPersistence =System.getProperty("user.dir")
								+separator
								+"src"
								+separator
								+"libs"+separator+"eclipselink"+separator+"javax.persistence_2.1.0.v201304241213.jar";
		
		File[] files = folder.listFiles();
		for(File f: files){
			Process p = Runtime.getRuntime().exec("javac -cp "+pathPersistence+" "+f.getAbsolutePath(),
					null,
					new File(CompilerConstants.DEFAULT_FOLDER+CompilerConstants.FILES_JAR));
			p.waitFor();
		}
		
	}
	public void createFolder(String folder){
		File f = new File(folder);
		if(!f.exists())
			f.mkdir();
	}
	
	public static void main(String args[]){
		try {
			Compiler c = new Compiler();
			c.addDependencies(CompilerConstants.ECLIPSELINK);
			c.addDependencies(CompilerConstants.COMMONS);
			c.compileClasses();
			c.generateJar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
