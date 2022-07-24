package logic;

import java.io.Serializable;

public class Delivery implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String RestarauntName;
	private String OrderType;
	private String DeliverWay;
	private String DeliveryType;
	private String ExecutedTime;
    private String ExpectedArrivalTime;
    
    
    
    
    
	public Delivery(String restarauntName, String orderType, String deliverWay, String deliveryType,
			String executedTime, String expectedArrivalTime) {
		super();
		this.RestarauntName = restarauntName;
	    this.OrderType = orderType;
		this.DeliverWay = deliverWay;
		this.DeliveryType = deliveryType;
		this.ExecutedTime = executedTime;
		this.ExpectedArrivalTime = expectedArrivalTime;
	}
	
	public String getRestarauntName() {
		return RestarauntName;
	}
	public void setRestarauntName(String restarauntName) {
		this.RestarauntName = restarauntName;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		this.OrderType = orderType;
	}
	public String getDeliverWay() {
		return DeliverWay;
	}
	public void setDeliverWay(String deliverWay) {
		this.DeliverWay = deliverWay;
	}
	public String getDeliveryType() {
		return DeliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.DeliveryType = deliveryType;
	}
	public String getExecutedTime() {
		return ExecutedTime;
	}
	public void setExecutedTime(String executedTime) {
		this.ExecutedTime = executedTime;
	}
	public String getExpectedArrivalTime() {
		return ExpectedArrivalTime;
	}
	public void setExpectedArrivalTime(String expectedArrivalTime) {
		this.ExpectedArrivalTime = expectedArrivalTime;
	}

    
    

}
