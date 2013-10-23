package jpaentity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jpaentity.dbmsdriver.DatabaseSystemDriver;
import jpaentity.dbmsdriver.DatabaseSystemDriverMySQLImpl;
import jpaentity.entity.Table;

/**
 * 
 * @author Leonardo Oliveira Moreira
 *
 * Class used to generates a set of entities of a database schema
 */
public class JPAGenerateEntities {

	public static List<Table> generateTables(String database, String host, int port, String user, String password) throws SQLException {
		List<Table> result = new ArrayList<Table>();	
		DatabaseSystemDriver systemDriver = new DatabaseSystemDriverMySQLImpl(host, port, user, password);
		systemDriver.openConnection();
		List<String> tableList = systemDriver.getTables(database);
		for (String s : tableList) {
			Table table = new Table();
			table.setName(s);
			
		}
		systemDriver.closeConnection();
		return result;
	}
	
	public static void main(String[] args) {
		
	}
	
}