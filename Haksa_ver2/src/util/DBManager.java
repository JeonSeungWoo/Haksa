package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    public Connection getConnection() {
    	Connection conn = null;
    	  try {
    			Class.forName("oracle.jdbc.driver.OracleDriver");
    			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:myoracle", "ora_user", "woo");
    			System.out.println("¿¬°á");
		} catch (Exception e) {
		}
    	 
    	  
    	  
    	  return conn;
    	 
    	  
    	  
    }
    
  
}

