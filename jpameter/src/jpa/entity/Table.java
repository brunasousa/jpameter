package jpa.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Leonardo Oliveira Moreira
 *
 * Class used to represents a table
 */
public class Table {
	
	private String name;
	private List<Column> columns;
	private boolean compositePK;
	public Table() {
		columns = new ArrayList<Column>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public void addColumn(Column column) {
		columns.add(column);
	}
	public boolean isCompositePK() {
		return compositePK;
	}
	public void setCompositePK(boolean compositePK) {
		this.compositePK = compositePK;
	}
	
}