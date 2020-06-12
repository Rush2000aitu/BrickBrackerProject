package brickBracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	static Connection conn = null;
	private DBConnection (){
}
	public static Connection getDBConnection(){
        	try{
			if(conn == null){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","123456");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
}

	

