package jpa.results;

public enum QueryType {
	
	SELECT(1,"SELECT"), INSERT(2,"INSERT"), UPDATE(3,"UPDATE"), DELETE(4,"DELETE");
	
	private QueryType(int type, String name) {
		this.type = type;
		this.name = name;
	}
	int type;
	String name;
	
	public int getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public static int getTypeByName(String name){
		if(name.toUpperCase().equals(QueryType.SELECT.getName()))
			return QueryType.SELECT.getType();
		
		if(name.toUpperCase().equals(QueryType.INSERT.getName()))
			return QueryType.INSERT.getType();
		
		if(name.toUpperCase().equals(QueryType.UPDATE.getName()))
			return QueryType.UPDATE.getType();
		
		if(name.toUpperCase().equals(QueryType.DELETE.getName()))
			return QueryType.DELETE.getType();
		
		return 0;
	}
	
	public static String getNameByType(int type){
		if(type == QueryType.SELECT.getType())
			return QueryType.SELECT.getName();
		
		if(type == QueryType.INSERT.getType())
			return QueryType.INSERT.getName();
		
		if(type == QueryType.DELETE.getType())
			return QueryType.DELETE.getName();
		
		if(type == QueryType.UPDATE.getType())
			return QueryType.UPDATE.getName();
			
		return null;
	}
}
