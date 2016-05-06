package edu.nyu.gh.api.model;

public class UpdatePreferenceRequest {
	
	private String email;
	private int[] categories;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int[] getCategories() {
		return categories;
	}
	public void setCategories(int[] categories) {
		this.categories = categories;
	}
	
	

}
