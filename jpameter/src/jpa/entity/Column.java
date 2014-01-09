package jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jpa.constraints.Constraint;
import jpa.dbmsdriver.TableColumnType;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 *         Class used to represents a column of a table
 */
public class Column {

	private int type;
	private String name;
	private List<Constraint> constraints;

	public Column() {
		constraints = new ArrayList<Constraint>();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}
	
	public boolean isPK(){
		for(Constraint c: this.getConstraints())
			if(c.getColumn().toLowerCase().equals("primary"))
				return true;
		
		return false;
	}

}