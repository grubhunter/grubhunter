package edu.nyu.gh.api.model;

public class UpdateRatingsRequest {
	
	private String email;
	private int dishId;
	private int restaurantId;
	private String rating;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDishId() {
		return dishId;
	}
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurentId) {
		this.restaurantId = restaurentId;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

}