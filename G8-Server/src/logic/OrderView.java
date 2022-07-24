package logic;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.control.Spinner;

public class OrderView implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ItemName;
	private Double Price;
	private int Qty;
	private String Size;
	private String LevelOfCoocking;
    private String topings;
	private String Description;
	
	private String Type;
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		this.Type = type;
	}
	

	public OrderView(String ItemName, Double Price, int Qty,String SizeString ,String LevelOfCoocking,String topings,String Description) {
		super();
		this.ItemName = ItemName;
		this.Price = Price;
		this.Qty = Qty;
		this.Size=SizeString;
		this.LevelOfCoocking=LevelOfCoocking;
		this.topings=topings;
		this.Description=Description;
	
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String ItemName) {
		this.ItemName = ItemName;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double Price) {
		this.Price = Price;
	}

	public int getQty() {
		return Qty;
	}

	public void setQty(int Qty) {
	this.Qty = Qty;
	}
	

	public String getSize() {
		return Size;
	}

	public void setSize(String Size) {
		this.Size = Size;
	}
	
	
	public String getLevelOfCoocking() {
		return LevelOfCoocking;
	}

	public void setLevelOfCoocking(String LevelOfCoocking) {
		this.LevelOfCoocking = LevelOfCoocking;
	}
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}
	
	public String getTopings() {
		return topings;
	}

	public void settopings(String topings) {
		this.topings = topings;
	}
	

}
