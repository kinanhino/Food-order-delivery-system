package logic;

import java.io.Serializable;

/**
 * 
 * @author Ibraheem
 * This class holds data for a specific item type and its quantity
 * @param itemType: Enum ('Main Meal', 'First Meal', 'Salad', 'Drinks', 'Dessert')
 * @param quantity: The amount of this item
 */
public class orderReport implements Serializable{

	private static final long serialVersionUID = 1L;
	private String itemType;
	private int quantity;

	public orderReport(String itemType, int quantity) {
		this.itemType = itemType;
		this.quantity = quantity;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
