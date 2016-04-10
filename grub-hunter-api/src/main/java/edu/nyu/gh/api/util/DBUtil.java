package edu.nyu.gh.api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	private static Connection getRemoteConnection() {
	    //if (System.getProperty("RDS_HOSTNAME") != null) {
	      try {
	      Class.forName("org.postgresql.Driver");
	      String dbName = "grub_hunter_db";//System.getProperty("RDS_DB_NAME");
	      String userName = "root";//System.getProperty("RDS_USERNAME");
	      String password = "rootpass";//System.getProperty("RDS_PASSWORD");
	      String hostname = "grubhunterdb.crbi4rfww47t.us-west-2.rds.amazonaws.com";//System.getProperty("RDS_HOSTNAME");
	      String port = "3306";//System.getProperty("RDS_PORT");
	      String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
	      System.out.println("Getting remote connection with connection string from environment variables.");
	      Connection con = DriverManager.getConnection(jdbcUrl);
	      System.out.println("Remote connection successful.");
	      return con;
	    }
	    catch (ClassNotFoundException e) { e.printStackTrace();}
	    catch (SQLException e) { e.printStackTrace();}
	   // }
	    return null;
	  }
	
	public ResultSet fetchUser(String userId) throws SQLException{
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("SELECT * FROM user where user_id=?");
		pstmnt.setString(1, userId);
		return pstmnt.executeQuery();
	}

}
