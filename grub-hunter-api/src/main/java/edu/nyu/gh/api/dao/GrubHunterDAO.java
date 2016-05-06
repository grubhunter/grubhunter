package edu.nyu.gh.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrubHunterDAO {

	private Connection getRemoteConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbName = "grubhunterdb";
			String userName = "root";
			String password = "rootroot";
			String hostname = "grubhunterdb.crbi4rfww47t.us-west-2.rds.amazonaws.com";
			String port = "3306";
			String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password="
					+ password;
			System.out.println("Getting remote connection with connection string from environment variables.");
			Connection con = DriverManager.getConnection(jdbcUrl);
			System.out.println("Remote connection successful.");
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet fetchUser(String userId) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("SELECT * FROM user where email=?");
		pstmnt.setString(1, userId);
		return pstmnt.executeQuery();
	}

	public void registerUser(String email, String password, String fname, String lname, String phone, int[] dishPrefs) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("INSERT INTO user (email,password,first_name,last_name,phone) values (?,?,?,?,?)");
		pstmnt.setString(1, email);
		pstmnt.setString(2, password);
		pstmnt.setString(3, fname);
		pstmnt.setString(4, lname);
		pstmnt.setString(5, phone);
		pstmnt.executeUpdate();
		for(int pref:dishPrefs){
			pstmnt = con.prepareStatement("INSERT INTO dish_preferences (email,dish_id) values (?,?)");
			pstmnt.setString(1, email);
			pstmnt.setInt(2, pref);
			pstmnt.executeUpdate();
		}
	}

}
