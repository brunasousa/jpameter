package jpaentity.constraints;

/**
 * 
 * @author Leonardo Oliveira Moreira
 *
 * Class used to represents a foreign key constraint
 */
public class ForeignKey extends Constraint {

	private String joinTable;
	private String joinColumn;
	
	public String getJoinTable() {
		return joinTable;
	}
	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}
	public String getJoinColumn() {
		return joinColumn;
	}
	public void setJoinColumn(String joinColumn) {
		this.joinColumn = joinColumn;
	}
}