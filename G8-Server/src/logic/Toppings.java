package logic;

import java.io.Serializable;

public class Toppings implements Serializable {
/**
	 * 
	 */
	@Override
	public String toString() {
		return "Toppings [topping=" + topping + ", price=" + price + "]";
	}
	private static final long serialVersionUID = 1L;
private String topping;
private double price;
public String getTopping() {
	return topping;
}
public void setTopping(String topping) {
	this.topping = topping;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public Toppings(String topping, double price) {
	
	this.topping = topping;
	this.price = price;
}

}
