package jpa.dbmsdriver;

import java.sql.SQLException;
import java.util.List;

import jpa.constraints.Constraint;
import jpa.entity.Table;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 *         Interface used to represents a database system connection
 */
public interface DatabaseSystemDriver {

	/**
	 * Open connection with database system
	 * 
	 * @throws SQLException
	 */
	public void openConnection() throws SQLException;

	/**
	 * Close connection with database system
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException;

	/**
	 * Retrieve a list of databases
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<String> getDatabases() throws SQLException;

	/**
	 * Retrieve a tables' list of database
	 * 
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	public List<String> getTables(String database) throws SQLException;

	/**
	 * Retrieve a columns' list of database and table
	 * 
	 * @param database
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public List<String> getColumns(String database, String table)
			throws SQLException;

	/**
	 * Get the type of a tables' column
	 * 
	 * @param database
	 * @param table
	 * @param column
	 * @return
	 */
	public int getColumnDataType(String database, String table, String column)
			throws SQLException;

	/**
	 * Retrieve a tables' list of database
	 * 
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	public List<Table> getEntityTables(String database) throws SQLException;
	
	/**
	 * Retrieve a table's list of constraints
	 * 
	 * @param database
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public List<Constraint> getPrimaryConstraintsTable(String database, String table) throws SQLException;
}