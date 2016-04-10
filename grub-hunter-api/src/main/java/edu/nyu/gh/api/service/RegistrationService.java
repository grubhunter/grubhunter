package edu.nyu.gh.api.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.nyu.gh.api.model.RegistrationRequest;

@Path("/registration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationService {
	
	@POST
	public String register(RegistrationRequest request){
		System.out.println("got request>>"+request.getUserName()+"<<>>"+request.getEmail());
		return "id:"+1;
	}

}
