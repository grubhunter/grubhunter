package edu.nyu.gh.api.service;

import java.sql.SQLException;
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
	 * service - login service service - user registration service service -
	 * user preference update service service - fetch user recommendation
	 * service service - user update review status service - get available
	 * dishes
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public GrubSimpleResponse login(AuthRequest request) {
		System.out.println("Got login>>" + request.getUserId() + "<>" + request.getPassword());
		return new GrubHunterDAO().fetchUser(request.getUserId(), request.getPassword());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/available-dishes")
	public List<Dish> fetchDishes(AuthRequest request) {
		System.out.println("Got fetch dishes>>");
		return new GrubHunterDAO().fetchDishes();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	public GrubSimpleResponse register(RegistrationRequest request) {
		System.out.println("Got registration>>" + request.getEmail());
		try {
			new GrubHunterDAO().registerUser(request.getEmail(), request.getPassword(), request.getFirstName(),
					request.getLastName(), request.getPhone(), request.getCategories());
			return new GrubSimpleResponse("success");
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return new GrubSimpleResponse("already registered");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new GrubSimpleResponse("failure");
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update-preference")
	public GrubSimpleResponse updatePreferences(UpdatePreferenceRequest request) {
		System.out.println("Got update prefs>>" + request.getEmail());
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
	public List<Recommendation> fetchRecommendations(GrubSimpleRequest request) {
		System.out.println("Got recommendation request>>" + request.getUserId());
		return new GrubHunterDAO().fetchUserRecommendations(request.getUserId());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rate-dish")
	public GrubSimpleResponse rateDish(UpdateRatingsRequest request) {
		System.out.println("Got update ratings>>" + request.getEmail());
		try {
			new GrubHunterDAO().insertRatings(request.getEmail(), request.getRestaurantId(), request.getDishId(),
					request.getRating());
			new UserRatingNotifierService().notifyUserRating(request.getEmail());
			return new GrubSimpleResponse("success");
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return new GrubSimpleResponse("duplicate rating for dish");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new GrubSimpleResponse("failure");
	}

}
