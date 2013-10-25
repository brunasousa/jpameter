package jpaentity.entity;

import java.util.ArrayList;
import java.util.List;

import jpaentity.constraints.Constraint;
import jpaentity.dbmsdriver.TableColumnType;

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

	public String toJavaAttributeColumnString() {
		String string = "";
		switch (type) {
			case TableColumnType.TYPE_TIMESTAMP : {
				string += "    @Temporal(TemporalType.TIMESTAMP)\n";
				break;
			}
		}
		string += "    @Column(name=\"" + name + "\")\n";
		string += "    private ";
		switch (type) {
			case TableColumnType.TYPE_CHAR: {
				string += "Character ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "Double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "Double ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "Integer ";
				break;
			}
			case TableColumnType.TYPE_TIMESTAMP: {
				string += "java.util.Date ";
				break;
			}
			case TableColumnType.TYPE_VARCHAR: {
				string += "String ";
				break;
			}
		}
		string += name.toLowerCase() + ";\n";
		return string;
	}
	
	public String toJavaGetterAndSetterAttributeColumnString() {
		String string = "";
		// create getter
		string += "    public ";
		switch (type) {
			case TableColumnType.TYPE_CHAR: {
				string += "Character ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "Double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "Double ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "Integer ";
				break;
			}
			case TableColumnType.TYPE_TIMESTAMP: {
				string += "java.util.Date ";
				break;
			}
			case TableColumnType.TYPE_VARCHAR: {
				string += "String ";
				break;
			}
		}
		string += "get" + name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\n";
		string += "        return " + name + ";\n";
		string += "    }\n";
		// create setter
		string += "    public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(";
		switch (type) {
			case TableColumnType.TYPE_CHAR: {
				string += "Character ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "Double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "Double ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "Integer ";
				break;
			}
			case TableColumnType.TYPE_TIMESTAMP: {
				string += "java.util.Date ";
				break;
			}
			case TableColumnType.TYPE_VARCHAR: {
				string += "String ";
				break;
			}
		}
		string += name;
		string += ") {\n";
		string += "        this." + name + " = " + name + ";\n";
		string += "    }\n";		
		return string;
	}

}