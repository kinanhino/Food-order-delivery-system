package logic;

import java.io.Serializable;

public class UserForImport implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String fname;
	private String lname;
	private String Username;
	private String Password;
	private String email;
	private String phone;
	private String role;
	private String w4c;
	private String location;
    private String res;
    private int resid;
    private String bus;
    private int busid;

	

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		this.resid = resid;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public int getBusid() {
		return busid;
	}

	public void setBusid(int busid) {
		this.busid = busid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserForImport(String username, String password) {
		id = 0;
		fname = "";
		lname = "";
		Username = username;
		Password = password;
		email = "";
		phone = "";
		role = "";
		w4c = "";
		location="";
		bus=null;
		res=null;

	}

	public String getW4c() {
		return w4c;
	}

	public void setW4c(String w4c) {
		this.w4c = w4c;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
