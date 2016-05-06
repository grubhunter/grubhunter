package edu.nyu.gh.api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AuthResponse")
public class GrubSimpleResponse {
	
	public GrubSimpleResponse(String status) {
		this.status = status;
	}

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
