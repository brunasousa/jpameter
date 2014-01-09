package jpa.strategy;

import jpa.entity.Table;

public interface JPAStrategy {

	public String getEntityJavaClass(Table table, String database);
	public String getEntityPKClass(Table table);
}
