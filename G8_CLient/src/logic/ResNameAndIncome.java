package logic;

import java.io.Serializable;

/**
 * 
 * @author Ibraheem
 * This class holds information about restaurant and its income
 * @param restaurantName
 * @param income
 */
public class ResNameAndIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	private String restaurantName;
	private int income;

	public ResNameAndIncome(String restaurantName, int income) {
		super();
		this.restaurantName = restaurantName;
		this.income = income;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}
}
