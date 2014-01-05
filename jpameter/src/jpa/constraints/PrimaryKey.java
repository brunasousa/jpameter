package jpa.constraints;

/**
 * 
 * @author Leonardo Oliveira Moreira
 *
 * Class used to represents a primary key constraint
 */
public class PrimaryKey extends Constraint {
	
	public PrimaryKey() {
		// TODO Auto-generated constructor stub
	}

	public PrimaryKey(String name, String column){
		setColumn(column);
		setName(name);
	}

}