package jpa;

/**
 * 
 * @author Leonardo Oliveira Moreira
 * 
 */
public final class JPAConstants {

	public static final String[] JPA_STRATEGIES = new String[] { "Hibernate", "EclipseLink", "OpenJPA" };
	
	public static final int JPA_HIBERNATE = 0;
	public static final int JPA_ECLIPSELINK = 1;
	public static final int JPA_OPENJPA = 2;
	
	private JPAConstants() {
		
	}
	
}