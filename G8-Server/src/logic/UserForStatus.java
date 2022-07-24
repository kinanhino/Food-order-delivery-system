package logic;

import java.io.Serializable;

public class UserForStatus implements Serializable {
@Override
	public String toString() {
		return "UserForStatus [id=" + id + ", first=" + first + ", last=" + last + ", status=" + status + "]";
	}
private static final long serialVersionUID = 1L;
private int id;
private String first;
private String last;
private String status;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirst() {
	return first;
}
public void setFirst(String first) {
	this.first = first;
}
public String getLast() {
	return last;
}
public void setLast(String last) {
	this.last = last;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public UserForStatus(int id, String first, String last, String status) {
	this.id = id;
	this.first = first;
	this.last = last;
	this.status = status;
}



}
