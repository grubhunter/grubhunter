package edu.nyu.gh.api.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.nyu.gh.api.dao.GrubHunterDAO;
import edu.nyu.gh.api.model.AuthRequest;
import edu.nyu.gh.api.model.Dish;
import edu.nyu.gh.api.model.GrubSimpleRequest;
import edu.nyu.gh.api.model.GrubSimpleResponse;
import edu.nyu.gh.api.model.Recommendation;
import edu.nyu.gh.api.model.RegistrationRequest;
import edu.nyu.gh.api.model.UpdatePreferenceRequest;
import edu.nyu.gh.api.model.UpdateRatingsRequest;

@Path("/api")
public class GrubHunterService {
	/**
	 * service - login service
	 * service - user registration service
	 * service - user preference update service
	 * service - fetch user recommendation service
	 * service - user update review status
	 * service - get available dishes
	 * */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public GrubSimpleResponse login(AuthRequest request){
		System.out.println("Got login>>"+request.getUserId()+"<>"+request.getPassword());
		try {
			ResultSet rs = new GrubHunterDAO().fetchUser(request.getUserId());
			if(rs.next()){
				String pass = rs.getString(2);
				if (pass.equals(request.getPassword())){
					return new GrubSimpleResponse("success");
				}else{
					return new GrubSimpleResponse("credential error");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new GrubSimpleResponse("failure");
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/available-dishes")
	public List<Dish> fetchDishes(AuthRequest request){
		System.out.println("Got fetch dishes>>");
		List<Dish> dishes = new ArrayList<>();
		try {
			ResultSet rs = new GrubHunterDAO().fetchDishes();
			while(rs.next()){
				Dish dish = new Dish();
				dish.setDishId(rs.getInt(1));
				dish.setDishName(rs.getString(2));
				dishes.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dishes;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	public GrubSimpleResponse register(RegistrationRequest request){
		System.out.println("Got registration>>"+request.getEmail());
		try {
			new GrubHunterDAO().registerUser(request.getEmail(), request.getPassword(), 
					request.getFirstName(), request.getLastName(), request.getPhone(), request.getCategories());
			return new GrubSimpleResponse("success");
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GrubSimpleResponse("already registered");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new GrubSimpleResponse("failure");
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update-preference")
	public GrubSimpleResponse updatePreferences(UpdatePreferenceRequest request){
		System.out.println("Got update prefs>>"+request.getEmail());
		try {
			new GrubHunterDAO().updatePreferences(request.getEmail(), request.getCategories());
			return new GrubSimpleResponse("success");
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GrubSimpleResponse("duplicate dish added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new GrubSimpleResponse("failure");
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/recommendations")
	public List<Recommendation> fetchRecommendations(GrubSimpleRequest request){
		System.out.println("Got recommendation request>>"+request.getUserId());
		List<Recommendation> recommendations = new ArrayList<>();
		try {
			ResultSet rs = new GrubHunterDAO().fetchUserRecommendations(request.getUserId());
			int prev = 0;
			
			Recommendation rec = null;
			List<Dish> dishes = null;
			while(rs.next()){
				int id = rs.getInt(2);
				if(prev != id){
					if(rec != null){
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
		}
		return recommendations;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rate-dish")
	public GrubSimpleResponse rateDish(UpdateRatingsRequest request){
		System.out.println("Got update ratings>>"+request.getEmail());
		try {
			new GrubHunterDAO().insertRatings(request.getEmail(), request.getRestaurantId(),
					request.getDishId(), request.getRating());
			return new GrubSimpleResponse("success");
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GrubSimpleResponse("duplicate rating for dish");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new GrubSimpleResponse("failure");
	}

}
