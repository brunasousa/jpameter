package jpa.strategy;

import jpa.dbmsdriver.TableColumnType;
import jpa.entity.Column;
import jpa.entity.Table;

public class JPAStrategyHibernate implements JPAStrategy {

	@Override
	public String getEntityJavaClass(Table table) {
		String string = "";
		string += "@Entity\n";
		string += "@Table(name=\"" + table.getName() + "\")\n";
		string += "public class " + table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1).toLowerCase() + " {\n";
		for (Column column : table.getColumns()) {
			switch (column.getType()) {
			case TableColumnType.TYPE_TIMESTAMP : {
				string += "    @Temporal(TemporalType.TIMESTAMP)\n";
				break;
			}
		}
		string += "    @Column(name=\"" + column.getName() + "\")\n";
		string += "    private ";
		switch (column.getType()) {
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
		string += column.getName().toLowerCase() + ";\n";
		}
		for (Column column : table.getColumns()) {
			// create getter
			string += "    public ";
			switch (column.getType()) {
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
			string += "get" + column.getName().substring(0, 1).toUpperCase() + column.getName().substring(1) + "() {\n";
			string += "        return " + column.getName() + ";\n";
			string += "    }\n";
			// create setter
			string += "    public void set" + column.getName().substring(0, 1).toUpperCase() + column.getName().substring(1) + "(";
			switch (column.getType()) {
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
			string += column.getName();
			string += ") {\n";
			string += "        this." + column.getName() + " = " + column.getName() + ";\n";
			string += "    }\n";
		}
		string += "}";
		return string;
	}

}
