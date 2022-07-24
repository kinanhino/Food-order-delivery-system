package MySQLconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import gui.ServerStartController;
import logic.User;
import logic.UserForImport;

public class ExternalDB {
	private static Connection con;
	public void connectToDB(String loc, String password, String user) throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			System.out.println("Driver definition failed");
			/* handle the error */}

		try {
			con = DriverManager.getConnection(loc, user, password);

			System.out.println("SQL connection succeed");

		} catch (SQLException ex) {
			ServerStartController.flag = false;
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

		}
	}
	
	
	/**
	 * @author salmanamer input: null
	 * functionality: it return all the users in the external data base whose CEO,BM manager
	 *         output:ArrayList<User>     
	 */
	public static ArrayList<User> Import() {
		ArrayList<User> arr = new ArrayList<>();
		Statement statement;
		String query = "SELECT * from user";
		ResultSet rs;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				if(rs.getString("Role").equals("CEO")||rs.getString("Role").equals("BM manager")){
				User u = new User(rs.getString("Username"), rs.getString("Password"));
				u.setEmail(rs.getString("Email"));
				u.setFname(rs.getString("FirstName"));
				u.setLname(rs.getString("LastName"));
				u.setPhone(rs.getString("PhoneNumber"));
				u.setId(Integer.parseInt(rs.getString("id")));
				u.setRole(rs.getString("Role"));
				u.setLocation(rs.getString("Location"));
				if(rs.getString("Role").equals("CEO"))u.setW4c("0");
				else if(rs.getString("Location").equals("North"))
				u.setW4c("1");
				else if(rs.getString("Location").equals("Center"))
					u.setW4c("2");
				else u.setW4c("3");
				arr.add(u);
				
				}
				
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * @author salmanamer input: null
	 * functionality: it return all the users in the external data base whose NOT CEO,BM manager
	 *         output:ArrayList<UserForImport>     
	 */
	public static ArrayList<UserForImport> ImportElse() {
		ArrayList<UserForImport> arr = new ArrayList<>();
		Statement statement;
		String query = "SELECT * from user";
		ResultSet rs;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				if(!rs.getString("Role").equals("CEO")&&!rs.getString("Role").equals("BM manager")){
				UserForImport u = new UserForImport(rs.getString("Username"), rs.getString("Password"));
				u.setEmail(rs.getString("Email"));
				u.setFname(rs.getString("FirstName"));
				u.setLname(rs.getString("LastName"));
				u.setPhone(rs.getString("PhoneNumber"));
				u.setId(Integer.parseInt(rs.getString("id")));
				u.setRole(rs.getString("Role"));
				u.setLocation(rs.getString("Location"));
				u.setBus(rs.getString("Business"));
				u.setRes(rs.getString("Restaurant"));
				u.setResid(rs.getInt("RestaurantID"));
				u.setBusid(rs.getInt("BusinessID"));
				u.setqRcode(rs.getString("QRCode"));
				arr.add(u);
				}
				
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
