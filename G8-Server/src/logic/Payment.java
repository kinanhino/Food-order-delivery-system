package logic;

import java.io.Serializable;

public class Payment implements  Serializable{
	@Override
	public String toString() {
		return "Payment [AccountType=" + AccountType + ", DeliveryCost=" + DeliveryCost + ", OrderPrice=" + OrderPrice
				+ ", Discounts=" + Discounts + ", FinalPrice=" + FinalPrice + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String AccountType;
	private String DeliveryCost;
	private String OrderPrice;
	private String Discounts;
	private String FinalPrice;
	
	
	
	
	public Payment(String accountType, String deliveryCost, String orderPrice, String discounts, String finalPrice) {
		super();
		this.AccountType = accountType;
		this.DeliveryCost = deliveryCost;
	    this.OrderPrice = orderPrice;
		this.Discounts = discounts;
		this.FinalPrice = finalPrice;
	}
	public String getAccountType() {
		return AccountType;
	}
	public void setAccountType(String accountType) {
		this.AccountType = accountType;
	}
	public String getDeliveryCost() {
		return DeliveryCost;
	}
	public void setDeliveryCost(String deliveryCost) {
		this.DeliveryCost = deliveryCost;
	}
	public String getOrderPrice() {
		return OrderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.OrderPrice = orderPrice;
	}
	public String getDiscounts() {
		return Discounts;
	}
	public void setDiscounts(String discounts) {
		this.Discounts = discounts;
	}
	public String getFinalPrice() {
		return FinalPrice;
	}
	public void setFinalPrice(String finalPrice) {
		this.FinalPrice = finalPrice;
	}
  
    
    
    
     

}
