package logic;

import java.io.Serializable;

/**
 * 
 * @author Ibraheem
 * This class holds information about restaurant and the number of orders it has
 * @param restaurantName
 * @param ordersNum
 */
public class ResNameAndOrderNum implements Serializable {

	private static final long serialVersionUID = 1L;
	private String restaurantName;
	private int ordersNum;

	public ResNameAndOrderNum(String restaurantName, int ordersNum) {
		super();
		this.restaurantName = restaurantName;
		this.ordersNum = ordersNum;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public int getOrdersNum() {
		return ordersNum;
	}

	public void setOrdersNum(int ordersNum) {
		this.ordersNum = ordersNum;
	}

}
