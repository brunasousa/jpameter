package jpaentity.entity;

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
	
}