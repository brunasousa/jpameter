package jpa.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import jpa.JPAConstants;

public class FilesApplication {
	
	public void genereteMain(String folder){
	}
	
	public void generatePersistenseFile(String folder,int jpaStrategy){
		String file = "<?xml version='1.0' encoding='UTF-8' ?>\n";
		file+= "<persistence xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'\n";
		file+= "  xsi:schemaLocation='http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd'\n";
		file+= "  version='2.0' xmlns='http://java.sun.com/xml/ns/persistence'>\n";
		file+= "<persistence-unit name='jpameter' transaction-type='RESOURCE_LOCAL'>\n";
		file+= persistenceClass();
		switch (jpaStrategy) {
			case JPAConstants.JPA_HIBERNATE:
				file+=propertiesHibernate();
				break;
				
			default:
				file+=propertiesDefault();
		}
		file+= "</persistence-unit>\n";
		file+= "</persistence>";
		
		File f = new File(folder+"persistence.xml");
		try {
			f.createNewFile();
			PrintWriter ps = new PrintWriter(f);
			BufferedWriter bw = new BufferedWriter(ps);
			bw.append(file);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String propertiesDefault(){
		String properties = "  <properties>\n";
		properties+= "    <property name='javax.persistence.jdbc.driver' value='com.mysql.jdbc.Driver' />\n";
		properties+= "    <property name='javax.persistence.jdbc.url'  value='jdbc:mysql://"+ConectionData.HOST+":"+ConectionData.PORT+"/"+ConectionData.SCHEMA+"' />\n";
		properties+= "    <property name='javax.persistence.jdbc.user' value='"+ConectionData.USER+"' />\n";
		properties+= "    <property name='javax.persistence.jdbc.password' value='"+ConectionData.PASS+"' />\n";
		properties+= "  </properties>\n";
		
		return properties;
	}
	
	public String propertiesHibernate(){
		String properties = "  <properties>\n";
		properties+= "    <property name='hibernate.connection.driver_class' value='com.mysql.jdbc.Driver' />\n";
		properties+= "    <property name='hibernate.connection.url'  value='jdbc:mysql://"+ConectionData.HOST+":"+ConectionData.PORT+"/"+ConectionData.SCHEMA+"' />\n";
		properties+= "    <property name='hibernate.connection.username' value='"+ConectionData.USER+"' />\n";
		properties+= "    <property name='hibernate.connection.password' value='"+ConectionData.PASS+"' />\n";
		properties+= "  </properties>\n";
		
		return properties;
	}
	
	public String persistenceClass(){
		String persistencesClass = "";
		String path = 	CompilerConstants.DEFAULT_FOLDER
						+CompilerConstants.FILES_JAR
						+System.getProperty("file.separator")
						+CompilerConstants.URL_ORIGIN_FILES;
		
		File f = new File(path);
		File[] listFiles = f.listFiles();
		for(File file : listFiles)
			if(!f.getName().equals("Main.java"))
				persistencesClass+="  <class>"+file.getName().substring(0,file.getName().lastIndexOf("."))+"</class>\n";
		
		return persistencesClass;
	}
}
