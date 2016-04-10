package edu.nyu.gh.api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AuthRequest")
public class AuthRequest {

	private String userId;
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
