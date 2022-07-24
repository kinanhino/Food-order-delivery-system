package logic;

import java.io.Serializable;

public class Item implements Serializable{

	public Item(Integer id, String name, String type, Double price, boolean sizeflag, boolean degreeflag,
			boolean toppingsflag) {
		
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.sizeflag = sizeflag;
		this.degreeflag = degreeflag;
		this.toppingsflag = toppingsflag;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String type;
	private Double price;
	private boolean sizeflag;
	private boolean degreeflag;
	private boolean toppingsflag;
	public boolean isSizeflag() {
		return sizeflag;
	}
	public void setSizeflag(boolean sizeflag) {
		this.sizeflag = sizeflag;
	}
	public boolean isDegreeflag() {
		return degreeflag;
	}
	public void setDegreeflag(boolean degreeflag) {
		this.degreeflag = degreeflag;
	}
	public boolean isToppingsflag() {
		return toppingsflag;
	}
	public void setToppingsflag(boolean toppingsflag) {
		this.toppingsflag = toppingsflag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
	

