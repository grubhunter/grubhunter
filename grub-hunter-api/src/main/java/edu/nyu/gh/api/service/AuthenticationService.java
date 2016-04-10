package edu.nyu.gh.api.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.nyu.gh.api.model.AuthRequest;
import edu.nyu.gh.api.util.DBUtil;

@Path("/auth/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationService {

	@POST
	@Path("/login")
	public String login(AuthRequest request){
		System.out.println("Got login>>"+request.getUserId()+"<>"+request.getPassword());
		
		try {
			ResultSet rs = new DBUtil().fetchUser(request.getUserId());
			while(rs.next()){
				String pass = rs.getString(2);
				if (pass.equals(request.getPassword())){
					return "success";
				}
			}
			return "failure";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";
	}
}
