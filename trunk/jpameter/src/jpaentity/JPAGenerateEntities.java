package jpaentity;

import java.io.ObjectInputStream.GetField;
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
 *         Class used to generates a set of entities of a database schema
 */
public class JPAGenerateEntities {

	public static void main(String[] args) {
		try {
			DatabaseSystemDriver systemDriver = new DatabaseSystemDriverMySQLImpl(
					"192.168.0.110", 3306, "root", "ufc123");
			systemDriver.openConnection();
			List<Table> tables = systemDriver.getEntityTables("tpcc");
			for (Table t : tables) {
				System.out.println(t.toJavaClassString());
			}
			systemDriver.closeConnection();
		} catch (SQLException ex) {

		}
	}

}