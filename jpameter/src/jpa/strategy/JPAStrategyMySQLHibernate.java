package jpa.strategy;

import jpa.dbmsdriver.TableColumnType;
import jpa.entity.Column;
import jpa.entity.Table;

/**
 * 
 * @author Leonardo Olivira Moreira
 * 
 */
public class JPAStrategyMySQLHibernate implements JPAStrategy {

	@Override
	public String getEntityJavaClass(Table table, String database) {
		String string = "";
		// add package
		string += "package entities;\n";
		string += "\n";
		string += "import java.io.Serializable;\n";
		string += "import java.math.BigDecimal;\n";
		string += "import java.util.Date;\n";
		string += "import javax.persistence.Basic;\n";
		string += "import javax.persistence.Column;\n";
		string += "import javax.persistence.EmbeddedId;\n";
		string += "import javax.persistence.Entity;\n";
		string += "import javax.persistence.NamedQueries;\n";
		string += "import javax.persistence.NamedQuery;\n";
		string += "import javax.persistence.Table;\n";
		string += "import javax.persistence.Temporal;\n";
		string += "import javax.persistence.TemporalType;\n";
		string += "import javax.xml.bind.annotation.XmlRootElement;\n";
		string += "\n";
		// add class definition
		string += "@Entity\n";
		string += "@Table(name=\"" + table.getName() + "\", catalog = \""
				+ database + "\", schema = \"\")\n";
		string += "public class " + formatWithJavaClass(table.getName())
				+ " implements Serializable {\n";
		// add attributes
		string += "    private static final long serialVersionUID = 1L;\n";
		string += "\n";
		for (Column column : table.getColumns()) {
			switch (column.getType()) {
			case TableColumnType.TYPE_TIMESTAMP: {
				string += "    @Temporal(TemporalType.TIMESTAMP)\n";
				break;
			}
			}
			string += "    @Column(name=\"" + column.getName() + "\")\n";
			string += "    private ";
			switch (column.getType()) {
			case TableColumnType.TYPE_CHAR: {
				string += "char ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "float ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "int ";
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
			string += formatWithAttributeJavaClass(column.getName()) + ";\n";
		}
		string += "\n";
		// add default constructor
		string += "    public " + formatWithJavaClass(table.getName())
				+ " () {\n";
		string += "    \n";
		string += "    }\n";
		string += "\n";
		// add full constructor
		string += "    public " + formatWithJavaClass(table.getName()) + " (";
		for (Column column : table.getColumns()) {
			switch (column.getType()) {
			case TableColumnType.TYPE_CHAR: {
				string += "char ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "float ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "int ";
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
			string += formatWithAttributeJavaClass(column.getName()) + ", ";
		}
		if (string.endsWith(", ")) {
			string = string.substring(0, string.length() - ", ".length());
		}
		string += ") {\n";
		for (Column column : table.getColumns()) {
			string += "        this."
					+ formatWithAttributeJavaClass(column.getName()) + " = "
					+ formatWithAttributeJavaClass(column.getName()) + ";\n";
		}
		string += "    }\n";
		string += "\n";
		// add getter and setter methods
		for (Column column : table.getColumns()) {
			// create getter
			string += "    public ";
			switch (column.getType()) {
			case TableColumnType.TYPE_CHAR: {
				string += "char ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "float ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "int ";
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
			string += "get" + formatWithJavaClass(column.getName()) + "() {\n";
			string += "        return "
					+ formatWithAttributeJavaClass(column.getName()) + ";\n";
			string += "    }\n";
			string += "\n";
			// create setter
			string += "    public void set"
					+ formatWithJavaClass(column.getName()) + "(";
			switch (column.getType()) {
			case TableColumnType.TYPE_CHAR: {
				string += "char ";
				break;
			}
			case TableColumnType.TYPE_DECIMAL: {
				string += "double ";
				break;
			}
			case TableColumnType.TYPE_FLOAT: {
				string += "float ";
				break;
			}
			case TableColumnType.TYPE_INT: {
				string += "int ";
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
			string += formatWithAttributeJavaClass(column.getName());
			string += ") {\n";
			string += "        this."
					+ formatWithAttributeJavaClass(column.getName()) + " = "
					+ formatWithAttributeJavaClass(column.getName()) + ";\n";
			string += "    }\n";
		}
		string += "}";
		return string;
	}

	private static String formatWithJavaClass(String string) {
		if (string == null || string.length() == 0) {
			return "";
		}
		char[] s = string.toCharArray();
		string = "";
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '_' && i < s.length - 1) {
				string += Character.toUpperCase(s[i + 1]);
				i++;
				continue;
			}
			if (i == 0) {
				string += Character.toUpperCase(s[i]);
				continue;
			}
			string += s[i];
		}
		return string;
	}

	private static String formatWithAttributeJavaClass(String string) {
		if (string == null || string.length() == 0) {
			return "";
		}
		char[] s = string.toCharArray();
		string = "";
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '_' && i < s.length - 1) {
				string += Character.toUpperCase(s[i + 1]);
				i++;
				continue;
			}
			if (i == 0) {
				string += Character.toLowerCase(s[i]);
				continue;
			}
			string += s[i];
		}
		return string;
	}

}
