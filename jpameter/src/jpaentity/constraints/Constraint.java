package jpaentity.constraints;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 * Class used to represents a generic constraint
 */
public class Constraint {
	
	private String name;
	private String column;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
}