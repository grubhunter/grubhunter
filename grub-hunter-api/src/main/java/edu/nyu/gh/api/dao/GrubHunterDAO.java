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
	
	public ResultSet fetchDishes() throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("SELECT dish_id,dish_name FROM dishes_available");
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
		pstmnt = con.prepareStatement("INSERT INTO user_recommendations (email,restaurant_id,dish_id) "
				+ "SELECT ?,restaurant_id,dish_id from restaurant_dishes "
				+ "where dish_id in (SELECT dish_id from dish_preferences "
				+ "where email=?)");
		pstmnt.setString(1, email);
		pstmnt.setString(2, email);
		pstmnt.executeUpdate();
	}
	
	public void updatePreferences(String email,int[] dishPrefs) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("DELETE FROM dish_preferences WHERE email=?");
		pstmnt.setString(1, email);
		pstmnt.executeUpdate();
		for(int pref:dishPrefs){
			pstmnt = con.prepareStatement("INSERT INTO dish_preferences (email,dish_id) values (?,?)");
			pstmnt.setString(1, email);
			pstmnt.setInt(2, pref);
			pstmnt.executeUpdate();
		}
		pstmnt = con.prepareStatement("DELETE FROM user_recommendations WHERE email=?");
		pstmnt.setString(1, email);
		pstmnt.executeUpdate();
		pstmnt = con.prepareStatement("INSERT INTO user_recommendations (email,restaurant_id,dish_id) "
				+ "SELECT ?,restaurant_id,dish_id from restaurant_dishes "
				+ "where dish_id in (SELECT dish_id from dish_preferences "
				+ "where email=?)");
		pstmnt.setString(1, email);
		pstmnt.setString(2, email);
		pstmnt.executeUpdate();
	}
	
	public void insertRatings(String email,int restaurantId, int dishId, String rating) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("DELETE FROM user_rating where email=? AND restaurant_id=? AND dish_id=?");
		pstmnt.setString(1, email);
		pstmnt.setInt(2, restaurantId);
		pstmnt.setInt(3, dishId);
		pstmnt.executeUpdate();
		pstmnt = con.prepareStatement("INSERT INTO user_rating (email,restaurant_id, dish_id, dish_rating) values (?,?,?,?)");
		pstmnt.setString(1, email);
		pstmnt.setInt(2, restaurantId);
		pstmnt.setInt(3, dishId);
		pstmnt.setString(4, rating);
		pstmnt.executeUpdate();
	}
	
	public ResultSet fetchUserRecommendations(String userId) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("SELECT email, user_recommendations.restaurant_id,name,near,address, "
				+ "price, reviews, rating, user_recommendations.dish_id, dish_name, dish_rating "
				+ "from user_recommendations join restaurant on(id=restaurant_id) "
				+ "join dishes_available on(dishes_available.dish_id=user_recommendations.dish_id) "
				+ "join restaurant_dishes on(restaurant_dishes.dish_id=user_recommendations.dish_id "
				+ "and restaurant_dishes.restaurant_id=user_recommendations.restaurant_id) "
				+ "where email=? order by user_recommendations.restaurant_id asc LIMIT 50");
		pstmnt.setString(1, userId);
		return pstmnt.executeQuery();
	}

}
