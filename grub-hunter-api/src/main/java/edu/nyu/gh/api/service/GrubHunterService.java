package edu.nyu.gh.api.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.nyu.gh.api.dao.GrubHunterDAO;
import edu.nyu.gh.api.model.AuthRequest;
import edu.nyu.gh.api.model.GrubSimpleResponse;
import edu.nyu.gh.api.model.RegistrationRequest;

@Path("/api")
public class GrubHunterService {
	/**
	 * service - login service
	 * service - user registration service
	 * TODO user preference update service
	 * TODO fetch user recommendation service
	 * TODO user update review status
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

}
