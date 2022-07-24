package logic;

import java.io.Serializable;
import java.sql.Timestamp;

public class CeoReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bmName;
	private Timestamp date;
	private int id;

	public CeoReport(int id, String bmName, Timestamp date) {
		super();
		this.bmName = bmName;
		this.date = date;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBmName() {
		return bmName;
	}

	public void setBmName(String bmName) {
		this.bmName = bmName;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}
