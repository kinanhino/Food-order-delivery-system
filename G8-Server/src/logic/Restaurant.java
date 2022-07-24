package logic;

import java.io.Serializable;

public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;
	private String RestaurantName;
	private int RestaurantID;
	
	
	public Restaurant(int restaurantID,String restaurantName)
	{
		super();
		this.RestaurantName=restaurantName;
		this.RestaurantID=restaurantID;
	}
	
	public String getRestaurantName() {
		return RestaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		RestaurantName = restaurantName;
	}
	public int getRestaurantID() {
		return RestaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		RestaurantID = restaurantID;
	}
	

}