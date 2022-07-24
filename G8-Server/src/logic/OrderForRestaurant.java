package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

public class OrderForRestaurant implements Serializable {
	@Override
	public String toString() {
		return "OrderForRestaurant [id=" + id + ", ordertime=" + ordertime + ", price=" + price + ", deliveryType="
				+ deliveryType + ", timestr=" + timestr + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Timestamp ordertime;
	private double price;
	private String deliveryType;
	private String timestr;// time as string
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getTimestr() {
		return timestr;
	}
	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}
	public OrderForRestaurant(int id, Timestamp ordertime, double price, String deliveryType) {
		super();
		this.id = id;
		this.ordertime = ordertime;
		this.price = price;
		this.deliveryType = deliveryType;
		this.timestr = ordertime.toString();
	}

	
}
