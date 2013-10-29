package jpa.compiler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class Compiler {
	String separator = System.getProperty("file.separator");

	public void generateJar() throws IOException{
		File temp  = new File(CompilerConstants.FOLDER_TEMP);
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")
													+separator+"src"
													+separator+"jpameter.jar");
		
		//Manifest Informations 
		StringBuffer sb = new StringBuffer();
		sb.append("Manifest-Version: 1.0\n");
		sb.append("Main-Class: "+CompilerConstants.MAIN_CLASS+"\n");
		sb.append("Java-Bean: True\n");
		
		InputStream is = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
		Manifest mf = new Manifest(is);

		JarOutputStream out = new JarOutputStream(fout, mf);
		addFilesToJar(out, temp);
		
		if(temp.exists())
			temp.delete();
		
		out.close();
		fout.close();
		System.out.println("jpameter.jar gerado em "+System.getProperty("user.dir")+separator+"src/");
	}
	
	public boolean addFilesToJar(JarOutputStream out, File archive) throws IOException{	
		byte b[] = new byte[1024];
		if(archive.isDirectory()){
			for(File f: archive.listFiles()){
				addFilesToJar(out, f);
			}
		}else{
			JarEntry addFiles = new JarEntry(archive.getAbsolutePath().replace(System.getProperty("user.dir")
											+separator
											+CompilerConstants.FOLDER_TEMP
											+separator, ""));
			
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
			return true;
		}
		return false;
	}
	
	public void compileClasses() throws IOException, URISyntaxException{
		File temp  = new File(CompilerConstants.FOLDER_TEMP);
		
		if(!temp.exists())
			temp.mkdir();
		
		File dir = new File(System.getProperty("user.dir")
							+separator
							+"src"
							+separator
							+CompilerConstants.URL_ORIGIN_FILES);
		
	     File[] javaFiles = dir.listFiles(
	              new FilenameFilter() {
	                  public boolean accept(File file, String name) {
	                      return name.endsWith(".java");
	                  }
	              });
	     
	     //Add dependency --- dont work
	     List<String> options = new ArrayList<String>();
	     options.add("-cp");
	     options.add(System.getProperty("user.dir")+"/src/libs/mysql-connector-java-5.1.26-bin.jar;");
	     
	     JavaCompiler javaCompiler =  ToolProvider.getSystemJavaCompiler();
	     DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	     StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.getDefault(), Charset.forName("UTF-8"));
	     fileManager.setLocation(StandardLocation.CLASS_OUTPUT,Arrays.asList(new File(CompilerConstants.FOLDER_TEMP)));
	     Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFiles));
	     javaCompiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();
	        
	     for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
	         System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toString());
	     }        
	 
	    fileManager.close();
	}
	
	public void addDependencies(){
		
	}
	
	public static void main(String args[]){
		try {
			Compiler c = new Compiler();
			c.compileClasses();
			c.generateJar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
