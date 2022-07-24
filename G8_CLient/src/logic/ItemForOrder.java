package logic;

import java.io.Serializable;

public class ItemForOrder implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String name;
private String size;
private String degree;//degree of doneness
private String other;
private int quantity;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
public String getDegree() {
	return degree;
}
public void setDegree(String degree) {
	this.degree = degree;
}
public String getOther() {
	return other;
}
public void setOther(String other) {
	this.other = other;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public ItemForOrder(String name, String size, String degree, String other, int quantity) {
	
	this.name = name;
	this.size = size;
	this.degree = degree;
	this.other = other;
	this.quantity = quantity;
}

}
