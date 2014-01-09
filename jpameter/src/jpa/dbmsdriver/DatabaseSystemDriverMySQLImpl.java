package jpa.dbmsdriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jpa.constraints.Constraint;
import jpa.constraints.PrimaryKey;
import jpa.entity.Column;
import jpa.entity.Table;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 *         Class that implements a driver for MySQL Database System
 */
public class DatabaseSystemDriverMySQLImpl implements DatabaseSystemDriver {

	private Connection connection;
	private String host;
	private int port;
	private String user;
	private String password;
	private boolean connected = false;

	public DatabaseSystemDriverMySQLImpl(String host, int port, String user,
			String password) throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	@Override
	public void openConnection() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + host + ":"
				+ port + "/information_schema", user, password);
		connected = true;
	}

	@Override
	public void closeConnection() throws SQLException {
		if (!connected) {
			throw new SQLException("Not connected to the DBMS");
		}
		connection.close();
	}

	@Override
	public List<String> getDatabases() throws SQLException {
		if (!connected) {
			throw new SQLException("Not connected to the DBMS");
		}
		List<String> result = new ArrayList<String>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("show databases");
		while (resultSet != null && resultSet.next()) {
			String database = resultSet.getString(1);
			result.add(database);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public List<String> getTables(String database) throws SQLException {
		if (!connected) {
			throw new SQLException("Not connected to the DBMS");
		}
		String sql = "select table_name from information_schema.tables where table_schema = '"
				+ database + "'";
		List<String> result = new ArrayList<String>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet != null && resultSet.next()) {
			String table = resultSet.getString(1);
			result.add(table);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public List<String> getColumns(String database, String table)
			throws SQLException {
		if (!connected) {
			throw new SQLException("Not connected to the DBMS");
		}
		String sql = "select column_name from information_schema.columns where table_schema = '"
				+ database + "' and table_name = '" + table + "'";
		List<String> result = new ArrayList<String>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet != null && resultSet.next()) {
			String column = resultSet.getString(1);
			result.add(column);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	@Override
	public int getColumnDataType(String database, String table, String column)
			throws SQLException {
		if (!connected) {
			throw new SQLException("Not connected to the DBMS");
		}
		String sql = "select data_type from information_schema.columns where table_schema = '"
				+ database
				+ "' and table_name = '"
				+ table
				+ "' and column_name = '" + column + "'";
		int result = -1;
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet != null && resultSet.next()) {
			String type = resultSet.getString(1);
			if (type.equalsIgnoreCase("int")) {
				result = TableColumnType.TYPE_INT;
				break;
			}
			if (type.equalsIgnoreCase("varchar")) {
				result = TableColumnType.TYPE_VARCHAR;
				break;
			}
			if (type.equalsIgnoreCase("char")) {
				result = TableColumnType.TYPE_CHAR;
				break;
			}
			if (type.equalsIgnoreCase("decimal")) {
				result = TableColumnType.TYPE_DECIMAL;
				break;
			}
			if (type.equalsIgnoreCase("float")) {
				result = TableColumnType.TYPE_FLOAT;
				break;
			}
			if (type.equalsIgnoreCase("timestamp")) {
				result = TableColumnType.TYPE_TIMESTAMP;
				break;
			}
			throw new SQLException();
		}
		resultSet.close();
		statement.close();
		return result;
	}
	
	@Override
	public List<Table> getEntityTables(String database) throws SQLException {
		List<Table> result = new ArrayList<Table>();	
		List<String> tableList = getTables(database);
		for (String t : tableList) {
			Table table = new Table();
			table.setName(t);
			List<String> columnList = getColumns(database, table.getName());
			List<Constraint> constraintsList = getPrimaryConstraintsTable(database, table.getName()); 
			if(constraintsList.size()>1) //Verifica se a tabela contem mais de uma chave primaria
				table.setCompositePK(true);
			
			for (String c : columnList) {
				int columnType = getColumnDataType(database, t, c);
				Column column = new Column();
				column.setName(c);
				column.setType(columnType);
				
				for(Constraint cnst: constraintsList)//Adição das constraints nas colunas --Dêmora Bruna
					if(column.getName().equals(cnst.getName()))
						column.addConstraint(cnst);
				
				table.addColumn(column);
			}
			result.add(table);
		}
		return result;
	}
	
	@Override
	public List<Constraint> getPrimaryConstraintsTable(String database, String table) throws SQLException{
		List<Constraint> constraints = new ArrayList<Constraint>();
		
		String sql = "select COLUMN_NAME, CONSTRAINT_NAME from KEY_COLUMN_USAGE where TABLE_SCHEMA = '"
				+ database + "' and table_name = '" + table + "'";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet != null && resultSet.next()) {
			String name = resultSet.getString(1);
			String column = resultSet.getString(2);

			if(column.toLowerCase().equals("primary")){
				constraints.add(new PrimaryKey(name, column));
			}
		}
		
		return constraints;
	}

	public static void main(String[] args) {
		try {
			DatabaseSystemDriver systemDriver = new DatabaseSystemDriverMySQLImpl(
					"192.168.0.100", 3306, "root", "ufc123");
			systemDriver.openConnection();
			List<String> databases = systemDriver.getDatabases();
			for (String db : databases) {
				if (db.equalsIgnoreCase("tpcc")) {
					System.out.println(db);
					List<String> tables = systemDriver.getTables(db);
					for (String tb : tables) {
						System.out.println("\t" + tb);
						List<String> columns = systemDriver.getColumns(db, tb);
						for (String co : columns) {
							System.out.println("\t\t"
									+ systemDriver
											.getColumnDataType(db, tb, co)
									+ "\t" + co);
						}
					}
				}
			}

			systemDriver.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}