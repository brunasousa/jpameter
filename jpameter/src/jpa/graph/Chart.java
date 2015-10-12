package jpa.graph;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartPanel;

@SuppressWarnings("serial")
public class Chart implements Cloneable, Serializable{
	
	private ChartPanel chart;
	private JScrollPane legend;
	
	public ChartPanel getChart() {
		return chart;
	}
	public void setChart(ChartPanel chart) {
		this.chart = chart;
	}
	public JScrollPane getLegend() {
		return legend;
	}
	public void setLegend(JScrollPane legend) {
		this.legend = legend;
	}
	
	public JTable getTable(){
		return (JTable)legend.getViewport().getComponent(0);
	}
	
	/*http://www.guj.com.br/java/250021-resolvido-clonar-objeto*/
	public Chart cloneSerializable() {  
		  ObjectOutputStream out = null;  
		  ObjectInputStream in = null;  
		  
		  try {  
		    ByteArrayOutputStream bout = new ByteArrayOutputStream();  
		    out = new ObjectOutputStream(bout);  
		              
		    out.writeObject(this);  
		    out.close();  
		              
		    ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());  
		    in = new ObjectInputStream(bin);              
		    Object copy = in.readObject();  
		  
		    in.close();  
		              
		    return (Chart)copy;  
		  } catch (Exception ex) {  
		    ex.printStackTrace();  
		  } finally {  
		    try {  
		      if(out != null) {  
		        out.close();  
		      }  
		                  
		      if(in != null) {  
		        in.close();  
		      }  
		    } catch (IOException ignore) {}  
		  }  
		          
		  return null;  
		} 

}
