package logic;

import java.io.Serializable;

public class OrdersHistory implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Date;
	private String Restaurant;
    private String Transactiontype;
	private String Price;
	private int orderid;
	
	
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public OrdersHistory(String date, String restaurant, String transactiontype, String price,int orderid) {
		super();
		this.Date = date;
		this.Restaurant = restaurant;
		this.Transactiontype = transactiontype;
		this.Price = price;
		this.orderid=orderid;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		this.Date = date;
	}
	public String getRestaurant() {
		return Restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.Restaurant = restaurant;
	}
	public String getTransactiontype() {
		return Transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.Transactiontype = transactiontype;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		this.Price = price;
	}

	
	
	
	
}
