package edu.nyu.gh.grubhunter_mob.model;

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
