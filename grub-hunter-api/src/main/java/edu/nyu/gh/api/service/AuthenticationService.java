package edu.nyu.gh.api.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.nyu.gh.api.model.AuthRequest;
import edu.nyu.gh.api.model.AuthResponse;
import edu.nyu.gh.api.util.DBUtil;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationService {

	@POST
	@Path("/login")
	public AuthResponse login(AuthRequest request){
		System.out.println("Got login>>"+request.getUserId()+"<>"+request.getPassword());
		
		try {
			ResultSet rs = new DBUtil().fetchUser(request.getUserId());
			if(rs.next()){
				String pass = rs.getString(2);
				if (pass.equals(request.getPassword())){
					return new AuthResponse("success");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AuthResponse("failure");
	}
}
