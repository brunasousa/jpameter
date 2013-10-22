package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConectionController {
	public Connection con = null;
	
	public Connection getConnection(String url, String user, String pwd){
        if(con==null){
            try{
            	Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://"+url,user,pwd);
            }catch (Exception e) {
               System.out.println("Erro na conexão." + e);
           }
        }
        return con;
    }
	
	public ArrayList<String> buscarDataBases(){
		ArrayList<String> dbs = new ArrayList<String>();
		int indice = 0;
		String sql = "SHOW DATABASES";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){
				dbs.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dbs;
	}
}
