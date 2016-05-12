package edu.nyu.gh.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.gh.api.model.Dish;
import edu.nyu.gh.api.model.GrubSimpleResponse;
import edu.nyu.gh.api.model.Recommendation;

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

	public GrubSimpleResponse fetchUser(String userId, String password) {
		Connection con = null;
		try {
			con = getRemoteConnection();
			PreparedStatement pstmnt = con.prepareStatement("SELECT * FROM user where email=?");
			pstmnt.setString(1, userId);
			ResultSet rs = pstmnt.executeQuery();

			if (rs.next()) {
				String pass = rs.getString(2);
				if (pass.equals(password)) {
					return new GrubSimpleResponse("success");
				} else {
					return new GrubSimpleResponse("credential error");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new GrubSimpleResponse("credential error");
	}

	public List<Dish> fetchDishes() {
		Connection con = null;
		List<Dish> dishes = new ArrayList<>();
		try {
			con = getRemoteConnection();
			PreparedStatement pstmnt = con.prepareStatement("SELECT dish_id,dish_name FROM dishes_available");
			ResultSet rs = pstmnt.executeQuery();
			while (rs.next()) {
				Dish dish = new Dish();
				dish.setDishId(rs.getInt(1));
				dish.setDishName(rs.getString(2));
				dish.setRating("");
				dishes.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dishes;
	}

	public void registerUser(String email, String password, String fname, String lname, String phone, int[] dishPrefs)
			throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con
				.prepareStatement("INSERT INTO user (email,password,first_name,last_name,phone) values (?,?,?,?,?)");
		pstmnt.setString(1, email);
		pstmnt.setString(2, password);
		pstmnt.setString(3, fname);
		pstmnt.setString(4, lname);
		pstmnt.setString(5, phone);
		pstmnt.executeUpdate();
		for (int pref : dishPrefs) {
			pstmnt = con.prepareStatement("INSERT INTO dish_preferences (email,dish_id) values (?,?)");
			pstmnt.setString(1, email);
			pstmnt.setInt(2, pref);
			pstmnt.executeUpdate();
		}
		pstmnt = con.prepareStatement("INSERT INTO user_recommendations (email,restaurant_id,dish_id) "
				+ "SELECT ?,restaurant_id,dish_id from restaurant_dishes "
				+ "where dish_id in (SELECT dish_id from dish_preferences " + "where email=?)");
		pstmnt.setString(1, email);
		pstmnt.setString(2, email);
		pstmnt.executeUpdate();
		con.close();
	}

	public void updatePreferences(String email, int[] dishPrefs) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con.prepareStatement("DELETE FROM dish_preferences WHERE email=?");
		pstmnt.setString(1, email);
		pstmnt.executeUpdate();
		for (int pref : dishPrefs) {
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
				+ "where dish_id in (SELECT dish_id from dish_preferences " + "where email=?)");
		pstmnt.setString(1, email);
		pstmnt.setString(2, email);
		pstmnt.executeUpdate();
		con.close();
	}

	public void insertRatings(String email, int restaurantId, int dishId, String rating) throws SQLException {
		Connection con = getRemoteConnection();
		PreparedStatement pstmnt = con
				.prepareStatement("DELETE FROM user_rating where email=? AND restaurant_id=? AND dish_id=?");
		pstmnt.setString(1, email);
		pstmnt.setInt(2, restaurantId);
		pstmnt.setInt(3, dishId);
		pstmnt.executeUpdate();
		pstmnt = con.prepareStatement(
				"INSERT INTO user_rating (email,restaurant_id, dish_id, dish_rating) values (?,?,?,?)");
		pstmnt.setString(1, email);
		pstmnt.setInt(2, restaurantId);
		pstmnt.setInt(3, dishId);
		pstmnt.setString(4, rating);
		pstmnt.executeUpdate();
		con.close();
	}

	public List<Recommendation> fetchUserRecommendations(String userId) {
		Connection con = getRemoteConnection();
		List<Recommendation> recommendations = new ArrayList<>();
		try {
			PreparedStatement pstmnt = con
					.prepareStatement("SELECT email, user_recommendations.restaurant_id,name,near,address, "
							+ "price, reviews, rating, user_recommendations.dish_id, dish_name, dish_rating "
							+ "from user_recommendations join restaurant on(id=restaurant_id) "
							+ "join dishes_available on(dishes_available.dish_id=user_recommendations.dish_id) "
							+ "join restaurant_dishes on(restaurant_dishes.dish_id=user_recommendations.dish_id "
							+ "and restaurant_dishes.restaurant_id=user_recommendations.restaurant_id) "
							+ "where email=? order by user_recommendations.restaurant_id asc LIMIT 50");
			pstmnt.setString(1, userId);
			ResultSet rs = pstmnt.executeQuery();
			int prev = 0;

			Recommendation rec = null;
			List<Dish> dishes = null;
			while (rs.next()) {
				int id = rs.getInt(2);
				if (prev != id) {
					if (rec != null) {
						recommendations.add(rec);
					}
					rec = new Recommendation();
					rec.setRestaurantId(id);
					rec.setRestaurantName(rs.getString(3));
					rec.setRestaurantNear(rs.getString(4));
					rec.setAddress(rs.getString(5));
					rec.setPrice(rs.getString(6));
					rec.setReviews(rs.getString(7));
					rec.setRating(rs.getString(8));
					dishes = new ArrayList<>();
					prev = id;
				}
				Dish dish = new Dish();
				dish.setDishId(rs.getInt(9));
				dish.setDishName(rs.getString(10));
				dish.setRating(rs.getString(11));
				dishes.add(dish);
				rec.setDishes(dishes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return recommendations;
	}

}
