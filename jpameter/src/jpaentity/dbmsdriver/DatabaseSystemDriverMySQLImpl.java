package jpaentity.dbmsdriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		}
		resultSet.close();
		statement.close();
		return result;
	}

	public static void main(String[] args) {
		try {
			DatabaseSystemDriver systemDriver = new DatabaseSystemDriverMySQLImpl(
					"192.168.1.100", 3306, "root", "ufc123");
			systemDriver.openConnection();
			List<String> databases = systemDriver.getDatabases();
			for (String db : databases) {
				System.out.println(db);
				List<String> tables = systemDriver.getTables(db);
				for (String tb : tables) {
					System.out.println("\t" + tb);
					List<String> columns = systemDriver.getColumns(db, tb);
					for (String co : columns) {
						System.out.println("\t\t" + systemDriver.getColumnDataType(db, tb, co) + "\t"  + co);
					}
				}
			}

			systemDriver.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}