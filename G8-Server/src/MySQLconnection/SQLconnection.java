package MySQLconnection;

import java.io.File;
import java.io.FileInputStream;
//im salman
//Also Ibraheem +ghaith
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

import gui.ServerStartController;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import logic.Business;
import logic.BusinessUser;
import logic.CeoReport;
import logic.Choice;
import logic.Delivery;
import logic.Item;
import logic.ItemForOrder;
import logic.MyFile;
import logic.Order;
import logic.OrderForRestaurant;
import logic.OrderView;
import logic.OrdersHistory;
import logic.Payment;
import logic.ResNameAndIncome;
import logic.ResNameAndOrderNum;
import logic.Restaurant;
import logic.Toppings;
import logic.User;
import logic.UserForImport;
import logic.UserForStatus;
import logic.orderReport;
import server.EchoServer;

public class SQLconnection {
	private static Connection con;
	static ArrayList<Integer> ordersid = new ArrayList<>();
	static ArrayList<Integer> itemsinorder = new ArrayList<>();

	/**
	 * @author gethe functionality : this method connect server to database set
	 *         driver and connection input : String loc -location of the database in
	 *         MySQL ,String password-MySQL password ,String user=MySQL username
	 */
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
	 * @author gethe functionality : this method returns the role for a given user
	 *         +saving user data in fields , if user not found or a wrong password
	 *         is given it returns a string according to the case input : user u
	 *         which contains username and password from login screen
	 */
	public static String GetUserWithPassword(User u) {
		String query = "SELECT * FROM user WHERE Username=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, u.getUsername());
			rs = ps.executeQuery();
			if (rs.next()) {
				String Pass = rs.getString("Password");
				boolean loged = rs.getBoolean("IsLoggedIn");
				if (u.getPassword().equals(Pass)) {
					if (!loged) {
						String role = rs.getString("Role");
						u.setRole(role);
						u.setEmail(rs.getString("Email"));
						u.setFname(rs.getString("FirstName"));
						u.setLname(rs.getString("LastName"));
						u.setPhone(rs.getString("PhoneNumber"));
						u.setW4c(rs.getString("W4_Code"));
						u.setLocation(rs.getString("Location"));
						u.setId(rs.getInt("id"));
						ps = con.prepareStatement("update user set IsLoggedIn = ? where Username = ?");
						ps.setBoolean(1, true);
						ps.setString(2, u.getUsername());
						ps.executeUpdate();
						return role;
					} else {
						return "You're Already logged in";
					}
				} else {
					return "Wrong Password";

				}
			} else {
				query = "SELECT * FROM UsersWaitingForApproval WHERE Username=?";
				ps = con.prepareStatement(query);
				ps.setString(1, u.getUsername());
				rs = ps.executeQuery();
				if (rs.next())
					return "Wait for BM approval";
				else
					return "Username Not Found";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "Username Not Found";
		}

	}

	/**
	 * @author gethe functionality : this method sets the logged-in field as 0 in
	 *         database for a given user input: User u
	 */
	public static void GetLogOut(User u) {
		String query = "update user set IsLoggedIn = ? where Username = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setBoolean(1, false);
			ps.setString(2, u.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author gethe functionality: this method gets data for a user using his
	 *         username as primary key - for reseting password input user u -
	 *         contains just username
	 */
	public static String GetUser(User u) {
		String query = "SELECT * FROM user WHERE Username=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, u.getUsername());
			rs = ps.executeQuery();
			if (rs.next()) {
				u.setRole(rs.getString("Role"));
				u.setEmail(rs.getString("Email"));
				u.setFname(rs.getString("FirstName"));
				u.setLname(rs.getString("LastName"));
				u.setPhone(rs.getString("PhoneNumber"));
				u.setId(rs.getInt("id"));
				return u.getEmail();
			}
			return "error happens";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Username Not Found";
		}
	}

	/**
	 * @author gethe functionality:this method resets user password in database
	 *         input: user u -contains username and new password
	 **/
	public static void ResetPassword(User u) {
		String query = "update user set Password = ? where Username = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, u.getPassword());
			ps.setString(2, u.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Functionality : this method return restaurant id and name for a given
	 * supplier input : user u - to get his id
	 */
	public static String[] GetResturantNameForSupplierId(User u) {
		String name = "";
		int resturantid = 0;
		/**
		 * first query to get restaurant id from supplier_for_resturant table based on
		 * supplier id
		 */
		String query1 = "SELECT * FROM supplier_for_resturant WHERE Supplierid=?";
		System.out.println(u.getUsername() + " and his id is " + u.getId());
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query1);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
			if (rs.next())
				resturantid = rs.getInt("Restaurantid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[] arr = { "supplier id not found in 1st table", "" };
			return arr;
		}
		/**
		 * second query to get restaurant name from restaurants table based on
		 * restaurant id
		 */
		String query2 = "SELECT * FROM restaurants WHERE restaurantid=?";
		PreparedStatement ps1;
		try {
			ps1 = con.prepareStatement(query2);
			ps1.setInt(1, resturantid);
			rs = ps1.executeQuery();
			if (rs.next()) {
				name = rs.getString("restaurantname");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String[] arr = { "supplier id not found in 2nd table", "" };
			return arr;
		}
		String[] arr = { name, Integer.toString(resturantid) };
		return arr;
	}

	/**
	 * this method reurnd array of items for a given restaurant to get the items of
	 * the menu
	 */
	public static ArrayList<Item> GetItemsForRestaurantId(int id) {
		ArrayList<Item> Items = new ArrayList<Item>();

		String query = "SELECT * FROM resturant_have_items WHERE resturantid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Items.add(new Item(rs.getInt("itemid"), rs.getString("itemName"), rs.getString("itemType"),
						rs.getDouble("itemPrice"), rs.getBoolean("haveSize"), rs.getBoolean("haveDegree"),
						rs.getBoolean("iihaadditionalch")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return Items;
	}

	/**
	 * this method updates item in menu for restaurant input : item to get his id
	 * and updated fields , restaurantid to get the restaurant that the item is in
	 * his menu
	 */
	public static String UpdateItemInMenu(Item item, int restaurantid) {
		String query = "update resturant_have_items set itemName = ? ,itemType=?,itemPrice=? where itemid = ? AND resturantid=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, item.getName());
			ps.setString(2, item.getType());
			ps.setDouble(3, item.getPrice());
			ps.setInt(4, item.getId());
			ps.setInt(5, restaurantid);
			ps.executeUpdate();
			return "item updated";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "connection to database failed";
		}

	}

	/**
	 * this method adds new item to restaurant's menu input: item to add ,id - the
	 * restaurant id
	 */
	public static String AddItemToMenu(Item item, int id) {
		//
		String querySearch = "select * from resturant_have_items where resturantid=? AND itemid = ?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, id);
			ps.setInt(2, item.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {
			if (rs.next())
				if (!rs.getString("itemName").equals(null))
					return "item id already excists choose different id";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}
		String query = "insert into resturant_have_items(resturantid,itemid,itemName,itemType,itemPrice,haveSize,haveDegree,iihaadditionalch) values(?,?,?,?,?,?,?,?)";

		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, item.getId());
			ps.setString(3, item.getName());
			ps.setString(4, item.getType());
			ps.setDouble(5, item.getPrice());
			ps.setBoolean(6, item.isSizeflag());
			ps.setBoolean(7, item.isDegreeflag());
			ps.setBoolean(8, item.isToppingsflag());
			ps.executeUpdate();
			return "item added";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "connection to database while inserting failed";
		}
	}

	/**
	 * this method removes item from restaurant's menu input: item to remove and
	 * restaurant id
	 */
	public static String RemoveItemFromMenu(Item item, int id) {
		String query = "delete from resturant_have_items where resturantid=? AND itemid = ? ";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, item.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "connection to database failed";
		}
		query = "delete from item_have_additional_choices where resid=? AND Itemid = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, item.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "connection to database failed";
		}
		return "item deleted";
	}

	public static ArrayList<User> GetUsersList() {
		ArrayList<User> arr = new ArrayList<>();
		String query = "SELECT * from user";
		Statement statement;
		ResultSet rs;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				User u = new User(rs.getString("Username"), rs.getString("Password"));
				u.setEmail(rs.getString("Email"));
				u.setFname(rs.getString("FirstName"));
				u.setLname(rs.getString("LastName"));
				u.setPhone(rs.getString("PhoneNumber"));
				u.setId(Integer.parseInt(rs.getString("id")));
				u.setRole(rs.getString("Role"));
				u.setW4c(rs.getString("W4_Code"));

				arr.add(u);
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method deletes a user from the system DB , according to his id the
	 * functions search for tabels user are involved in and deletes him from every
	 * table input: int user id returns: String to know functionality status
	 */
	public static String DeleteUser(int id) {
		ResultSet rs;
		String searchQuery = "select * from user_bussiness_account where employeeid=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(searchQuery);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				String query = "delete from user_bussiness_account where employeeid=? ";

				ps = con.prepareStatement(query);
				ps.setInt(1, id);
				ps.executeUpdate();

			}
			String query = "delete from user where id=? ";

			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();

			query = "delete from usersinfo where userid=? ";

			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			return "user deleted";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "connection to database failed";
		}
	}

	public static String AddUser(User u, int resid) {

		String querySearch = "select * from user where id=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {
			if (rs.next())
				if (!rs.getString("id").equals(null))
					return "User is already exist";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}
		querySearch = "select * from restaurants where restaurantid=?";

		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, resid);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {
			if (rs.next())
				if (!rs.getString("restaurantid").equals(null))
					return "Restaurant is already exist Choose another ID";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}

		String query = "insert into user (id,FirstName,LastName,Username,Password,Email,PhoneNumber,W4_Code,Role,IsLoggedIn,activestatus) values(?,?,?,?,?,?,?,?,?,?,?);";

		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getFname());
			ps.setString(3, u.getLname());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getEmail());
			ps.setString(7, u.getPhone());
			ps.setString(8, u.getW4c());
			ps.setString(9, u.getRole());
			ps.setBoolean(10, false);
			ps.executeUpdate();
			return "User Added";

		} catch (SQLException e) {
			e.printStackTrace();
			return "connection to database failed user";
		}
	}

	public static String AddRes(String str) {
		String[] arr = str.split("	");
		PreparedStatement ps;
		String query = "insert restaurants (restaurantid,restaurantname) values(?,?);";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(arr[1]));
			ps.setString(2, arr[2]);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "connection to database failed";
		}

		query = "insert into supplier_for_resturant (Supplierid,resturantid) values(?,?);";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(arr[0]));
			ps.setInt(2, Integer.parseInt(arr[1]));
			ps.executeUpdate();
			return "Restaurant Added";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "connection to database failed";
		}
	}

	/**
	 * this method returns all the additional choices for an item given input:
	 * restaurant id and item id returns : arraylist of the addititional choices for
	 * the given item
	 */
	public static ArrayList<Choice> GetChoices(int resid, int itemid) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Choice> arr = new ArrayList<Choice>();
		try {

			String query = "select * from Item_have_additional_choices where itemid=? and resid=? and toppingstype='Size'";
			ps = con.prepareStatement(query);
			ps.setInt(1, itemid);
			ps.setInt(2, resid);
			rs = ps.executeQuery();
			while (rs.next()) {

				arr.add(new Choice(rs.getInt("toppingsid"), rs.getString("Size"), rs.getDouble("Price")));
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		try {

			String query = "select * from Item_have_additional_choices where itemid=? and resid=? and toppingstype='CookingLevel'";
			ps = con.prepareStatement(query);
			ps.setInt(1, itemid);
			ps.setInt(2, resid);
			rs = ps.executeQuery();
			while (rs.next()) {

				arr.add(new Choice(rs.getInt("toppingsid"), rs.getString("CookingLevel"), rs.getDouble("Price")));
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		try {

			String query = "select * from Item_have_additional_choices where itemid=? and resid=? and toppingstype='Toppings'";
			ps = con.prepareStatement(query);
			ps.setInt(1, itemid);
			ps.setInt(2, resid);
			rs = ps.executeQuery();
			while (rs.next()) {

				arr.add(new Choice(rs.getInt("toppingsid"), rs.getString("Toppings"), rs.getDouble("Price")));
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		return arr;
	}

	/**
	 * @author gethe
	 * @functionality: this method delete a toping for a
	 */
	public static String DeleteChoice(Choice c) {
		String querySearch = "delete from Item_have_additional_choices where toppingsid=? ";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(querySearch);

			ps.setInt(1, c.getId());
			ps.executeUpdate();
			return "Choice Deleted";
		} catch (SQLException e) {
			e.printStackTrace();
			return "connection to database while searching failed";
		}

	}

	public static String ChangeChoicePrice(double price, Choice c) {
		String query = "update item_have_additional_choices set Price = ? where toppingsid=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setDouble(1, price);
			ps.setInt(2, c.getId());
			ps.executeUpdate();
			return "Price saved";
		} catch (SQLException e) {
			e.printStackTrace();
			return "connection to database while searching failed";
		}

	}

	/**
	 * @author gethe this method returns the location of a given branch manager
	 */
	public static String getBMlocation(User u) {
		String location = "";
		String query = "select * from user where id=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				location = rs.getString("Location");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return location;
	}

	/**
	 * this method returns all the users is a given location{north,center,south}
	 * input : String location
	 */
	public static ArrayList<UserForStatus> getUsersInLocation(String location) {
		ArrayList<UserForStatus> arr = new ArrayList<UserForStatus>();
		ArrayList<Integer> usersid = new ArrayList<Integer>();
		ArrayList<String> usersstatus = new ArrayList<String>();
		ArrayList<String> usersfname = new ArrayList<String>();
		ArrayList<String> userslname = new ArrayList<String>();
		String query = "select * from user where Location=?";// query to return users status in the given
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, location);
			rs = ps.executeQuery();
			while (rs.next()) {
				usersid.add(rs.getInt("id"));
				usersfname.add(rs.getString("FirstName"));
				userslname.add(rs.getString("LastName"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		for (int i = 0; i < usersid.size(); i++) {
			String query1 = "select * from usersinfo where userid=?";// query to return the data of the users returned
																		// in first
			// query
			try {
				ps = con.prepareStatement(query1);
				ps.setInt(1, usersid.get(i));
				rs = ps.executeQuery();
				while (rs.next()) {
					arr.add(new UserForStatus(usersid.get(i), usersfname.get(i), userslname.get(i),
							rs.getString("status")));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}
		}

		return arr;
	}

	/**
	 * @author:gethe
	 * @functionality: this method changes the status of a user (FROEZN/ACTIVE )
	 *                 input: user id / status to change to
	 */
	public static void ChangeStatus(int id, String status) {
		String query = "update usersinfo set status = ? where userid = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(2, id);
			ps.setString(1, status);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author:gethe
	 * @functionality: this method returns the ready orders for a given restaurant
	 *                 input: restaurant id
	 */
	public static ArrayList<OrderForRestaurant> getReadyOrdersForRestaurant(int id) {
		ArrayList<OrderForRestaurant> arr = new ArrayList<>();
		ordersid.clear();
		Statement statement;
		ResultSet rs;
		String query = "select * from restauranthaveorder where restaurantid=" + id;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				ordersid.add(rs.getInt(1));
			}
			for (int i = 0; i < ordersid.size(); i++) {
				String Query = "select * from restauranthaveorder where Ready='0' and orderid=" + ordersid.get(i);
				statement = con.createStatement();
				rs = statement.executeQuery(Query);
				while (rs.next()) {
					arr.add(new OrderForRestaurant(rs.getInt("orderid"), rs.getTimestamp("OrderPlacedTime"),
							rs.getDouble("Price"), rs.getString("Deliveryconfirm")));
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
	 * @author:gethe
	 * @functionality: this method returns the working-on orders for a given
	 *                 restaurant input: restaurant id
	 */
	public static ArrayList<OrderForRestaurant> getWorkingOrdersForRestaurant() {
		ArrayList<OrderForRestaurant> arr = new ArrayList<>();
		Statement statement;
		ResultSet rs;
		for (int i = 0; i < ordersid.size(); i++) {
			String Query = "select * from restauranthaveorder where Ready='1' and orderid=" + ordersid.get(i);
			try {
				statement = con.createStatement();
				rs = statement.executeQuery(Query);
				while (rs.next()) {
					arr.add(new OrderForRestaurant(rs.getInt("orderid"), rs.getTimestamp("OrderPlacedTime"),
							rs.getDouble("Price"), rs.getString("Deliveryconfirm")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return arr;
	}

	/**
	 * @author:gethe
	 * @functionality: this method returns the items in order input: restaurant id
	 *                 /order id
	 */
	public static ArrayList<ItemForOrder> getItemsForOrder(int resid, int orderid) {
		ArrayList<ItemForOrder> items = new ArrayList<>();
		Statement statement;
		ResultSet rs;
		String query = "select * from orderhaveitems where orderid=" + orderid;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				String name = getItemName(resid, rs.getInt("itemid"));
				String degree = rs.getString("degreeofdoneness");
				String size = rs.getString("sizeofitem");
				String other = rs.getString("otherdescreption");
				int num = rs.getInt("quantity");
				items.add(new ItemForOrder(name, size, degree, other, num));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
			items.add(new ItemForOrder("name", "size", "degree", "other", 11));
			return items;
		}
	}

	/**
	 * @author:gethe
	 * @functionality: this method returns the item name input: restaurant id,item
	 *                 id
	 */
	public static String getItemName(int resid, int itemid) {
		Statement statement1;
		ResultSet rs1;
		String s = "a";
		try {
			statement1 = con.createStatement();
			rs1 = statement1
					.executeQuery("select resturant_have_items.itemName from resturant_have_items where resturantid="
							+ resid + " and itemid=" + itemid);
			while (rs1.next()) {
				s = rs1.getString("itemname");
			}
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @author:gethe
	 * @functionality: this method updates the order and set it as finished input:
	 *                 order
	 */
	public static String deleteOrder(OrderForRestaurant order) {
		String query = "update restauranthaveorder set Ready = ? where orderid = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(2, order.getId());
			ps.setString(1, "2");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "set as done failed";

		}
		return "set as done Success";
//		if (!order.getDeliveryType().equals("Take-Away")) {
//			String query1 = "delete from order_have_delivery where orderid= ? ";
//			PreparedStatement ps1;
//			try {
//				ps1 = con.prepareStatement(query1);
//				ps1.setInt(1, order.getId());
//				ps1.executeUpdate();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				return "failed to delete from order have delivery";
//			}
//		}
//		String query2 = "delete from orderhaveitems where orderid= ? ";
//		PreparedStatement ps2;
//		try {
//			ps2 = con.prepareStatement(query2);
//			ps2.setInt(1, order.getId());
//			ps2.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return "failed to delete from orderhaveitems";
//		}
//		String query3 = "delete from restauranthaveorder where orderid= ? ";
//		PreparedStatement ps3;
//		try {
//			ps3 = con.prepareStatement(query3);
//			ps3.setInt(1, order.getId());
//			ps3.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return "failed to delete from restauranthaveorder";
//		}
////		String query = "delete from orderinformation where IdOrder= ? ";
////		PreparedStatement ps;
////		try {
////			ps = con.prepareStatement(query);
////			ps.setInt(1, order.getId());
////			ps.executeUpdate();
////		} catch (SQLException e) {
////			e.printStackTrace();
////			return "failed to delete from orders_infrormation";
////		}
//		return "order was Succefully deleted";
	}

	/**
	 * @author:gethe
	 * @functionality: this method updates the order and set it as working-on input:
	 *                 order
	 */
	public static String setOrderAsWorking(OrderForRestaurant order) {
		String query = "update restauranthaveorder set Ready = ? where orderid = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(2, order.getId());
			ps.setString(1, "1");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "set as working failed";

		}
		return "set as working Success";
	}

	/**
	 * @author:gethe
	 * @functionality: this method finds the business for a given user input: user
	 *                 return: return the business for user
	 */
	public static Business GetBussiness(User user) {
		int Businessid = -1;
		String query = "select * from HRForBusiness where HRid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, user.getId());
			rs = ps.executeQuery();
			if (rs.next())
				Businessid = rs.getInt("BusinessID");

			query = "select * from Business where BusinessID=?";
			ps = con.prepareStatement(query);
			ps.setInt(1, Businessid);
			rs = ps.executeQuery();
			if (rs.next()) {
				Business bus = new Business(Businessid, rs.getString("Name"), rs.getString("Allowed"));

				return bus;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * @author:gethe
	 * @functionality: this method updates the business and set it as waiting input:
	 *                 business
	 */
	public static void ChangeBusStatusToWaiting(Business business) {

		PreparedStatement ps;
		try {

			String query = "update Business set Allowed = ? where BusinessID = ?";
			ps = con.prepareStatement(query);
			ps.setInt(2, business.getId());
			ps.setString(1, "Waiting");
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author salmanamer input: String location functionality: it returns the all
	 *         the business that want permission in location
	 *         output:ArrayList<Business>
	 */
	public static ArrayList<Business> GetWaitingBusiness(String loc) {
		ArrayList<Business> arrayList = new ArrayList<>();
		ArrayList<Integer> hrs = new ArrayList<>();
		ArrayList<Integer> busids = new ArrayList<>();
		PreparedStatement ps;
		ResultSet rs;
		try {

			String query = "select * from user where Role = 'HR' AND Location=?";
			ps = con.prepareStatement(query);
			ps.setString(1, loc);
			rs = ps.executeQuery();
			while (rs.next()) {
				hrs.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < hrs.size(); i++) {
				String query = "select * from hrforbusiness where HRid = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, hrs.get(i));
				rs = ps.executeQuery();
				if (rs.next())
					busids.add(rs.getInt("BusinessID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < busids.size(); i++) {
				String query = "select * from Business where Allowed = 'Waiting' AND BusinessID=?";
				ps = con.prepareStatement(query);
				ps.setInt(1, busids.get(i));
				rs = ps.executeQuery();
				if (rs.next())
					arrayList.add(new Business(rs.getInt("BusinessID"), rs.getString("Name"), "Waiting"));
			}
			return arrayList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @author:gethe
	 * @functionality: this method updates the business status field and set it
	 *                 according to the string in input input: business / String
	 *                 status
	 */
	public static void ChangeBusStatus(Business business, String str) {

		PreparedStatement ps;
		try {

			String query = "update Business set Allowed = ? where BusinessID = ?";
			ps = con.prepareStatement(query);
			ps.setInt(2, business.getId());
			ps.setString(1, str);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author:gethe
	 * @functionality: this method returns the sizes for an item input: item returns
	 *                 : array list for sizes to the given item
	 */
	public static ArrayList<String> getSize(Item item) {
		Statement statement;
		ResultSet rs;
		ArrayList<String> arr = new ArrayList<String>();
		ArrayList<Integer> arrid = new ArrayList<Integer>();
		String query = "select * from item_have_additional_choices where Itemid=" + Integer.toString(item.getId());
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				arrid.add(rs.getInt("ChoiceId"));
			}

			for (int i = 0; i < arrid.size(); i++) {
				String query1 = "select * from additionalchoices where ChoiseID=" + arrid.get(i);
				statement = con.createStatement();
				rs = statement.executeQuery(query1);
				while (rs.next()) {
					if (!rs.getString("Size").equals(null)) {
						arr.add(rs.getString("Size"));
					}
				}
			}
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author:gethe
	 * @functionality: this method returns the toppings for an item input: item
	 *                 returns : array list for toppings to the given item
	 */
	public static ArrayList<String> getToppings(Item item) {
		Statement statement;
		ResultSet rs;
		ArrayList<String> arr = new ArrayList<String>();
		ArrayList<Integer> arrid = new ArrayList<Integer>();
		String query = "select * from item_have_additional_choices where Itemid=" + Integer.toString(item.getId());
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				arrid.add(rs.getInt("ChoiceId"));
			}

			for (int i = 0; i < arrid.size(); i++) {
				String query1 = "select * from additionalchoices where ChoiseID=" + arrid.get(i);
				statement = con.createStatement();
				rs = statement.executeQuery(query1);
				while (rs.next()) {
					if (!rs.getString("Toppings").equals(null)) {
						arr.add(rs.getString("Toppings"));
					}
				}
			}
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method returns all the restaurant list input : nothing returns : array
	 * list of restaurants
	 */

	public static ArrayList<Restaurant> GetRestaurantList() {
		ArrayList<Restaurant> Restaurantlist = new ArrayList<>();
		String query = "SELECT * FROM restaurants ";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {

				Restaurantlist.add(new Restaurant(rs.getInt("restaurantid"), rs.getString("restaurantname")));
			}
			return Restaurantlist;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;

		}
	}

	/**
	 * @author gethe this method returns the restaurant list in certain location
	 *         input : location returns : array list of restaurants
	 */
	public static ArrayList<Restaurant> GetRestaurantListWithLocation(String location) {
		ArrayList<Restaurant> Restaurantlist = new ArrayList<>();
		String query = "SELECT * FROM restaurants where restaurantregion='" + location + "'";
		Statement ps;
		ResultSet rs;
		try {
			ps = con.createStatement();
			rs = ps.executeQuery(query);
			while (rs.next()) {

				Restaurantlist.add(new Restaurant(rs.getInt("restaurantid"), rs.getString("restaurantname")));
			}
			return Restaurantlist;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;

		}
	}

	/**
	 * @author gethe this method check if a given id is already exists in data base
	 *         input: restaurant id - item id returns : true if there are no id and
	 *         false else
	 */
	public static boolean checkIdForItem(int resid, int itemid) {

		Statement statement;
		ResultSet rs;
		ArrayList<Integer> arr = new ArrayList<>();
		String query = "select * from resturant_have_items where resturantid=" + resid;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				arr.add(rs.getInt("itemid"));
			}
			return !arr.contains(itemid);
		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}
	}

	/**
	 * @author gethe this method adds topping for an item according to his id and
	 *         restaurant id input : item id / restaurant id/ and arraylist of
	 *         toppings (contains topings and its price) returns : string of status
	 */
	public static String AddToppingsForItem(int itemid, int resid, ArrayList<Toppings> toppings) {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String query = "insert into item_have_additional_choices (CookingLevel,Size,Toppings,Price,Itemid,resid,toppingstype) values (?,?,?,?,?,?,?) ";
		try {
			ps = con.prepareStatement(query);

			for (int i = 0; i < toppings.size(); i++) {
				if (toppings.get(i).getTopping().equals("S") || toppings.get(i).getTopping().equals("M")
						|| toppings.get(i).getTopping().equals("L") || toppings.get(i).getTopping().equals("XL")) {
					ps.setString(1, null);
					ps.setString(2, toppings.get(i).getTopping());
					ps.setString(3, null);
					ps.setDouble(4, toppings.get(i).getPrice());
					ps.setInt(5, itemid);
					ps.setInt(6, resid);
					ps.setString(7, "Size");

				} else if (toppings.get(i).getTopping().equals("Rare") || toppings.get(i).getTopping().equals("Meduim")
						|| toppings.get(i).getTopping().equals("Well Done")) {
					ps.setString(2, null);
					ps.setString(1, toppings.get(i).getTopping());
					ps.setString(3, null);
					ps.setDouble(4, toppings.get(i).getPrice());
					ps.setInt(5, itemid);
					ps.setInt(6, resid);
					ps.setString(7, "CookingLevel");
				} else {
					ps.setString(2, null);
					ps.setString(3, toppings.get(i).getTopping());
					ps.setString(1, null);
					ps.setDouble(4, toppings.get(i).getPrice());
					ps.setInt(5, itemid);
					ps.setInt(6, resid);
					ps.setString(7, "Toppings");
				}
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed to insert to additionalchoices";
		}

		return "add success ";
	}

	/**
	 * @author gethe this method returns booleana array of flags for size,
	 *         cookinglvl, toppings for a given item input : restaurant id / item id
	 *         returns : boolean array of size 3 contains 3flags for : size,
	 *         cookinglvl, toppings
	 */
	public static boolean[] CheckChoices(int resid, int itemid) {
		// TODO Auto-generated method stub
		boolean[] flags = new boolean[3];
		String querySearch = "select * from resturant_have_items where itemid=? and resturantid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, itemid);
			ps.setInt(2, resid);
			rs = ps.executeQuery();
			if (rs.next()) {
				flags[0] = rs.getBoolean("haveSize");
				flags[1] = rs.getBoolean("haveDegree");
				flags[2] = rs.getBoolean("iihaadditionalch");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED");
		}
		return flags;
	}

	/**
	 * @author gethe this method returns arraylist of sizes for a given item input :
	 *         restaurant id / item id returns : arraylist of sizes
	 */
	public static ArrayList<Toppings> getSizes(int id, Item item) {
		ArrayList<Toppings> arr = new ArrayList<Toppings>();
		PreparedStatement ps;
		ResultSet rs;
		try {

			String query = "select * from Item_have_additional_choices where itemid=? and resid=? and toppingstype='Size'";
			ps = con.prepareStatement(query);
			ps.setInt(1, item.getId());
			ps.setInt(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				arr.add(new Toppings(rs.getString("Size"), rs.getDouble("Price")));
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return arr;
	}

	/**
	 * @author gethe this method returns arraylist of toppings for a given item
	 *         input : restaurant id / item id returns : arraylist of toppings
	 */
	public static ArrayList<Toppings> gettoppings(int id, Item item) {
		ArrayList<Toppings> arr = new ArrayList<Toppings>();
		PreparedStatement ps;
		ResultSet rs;
		try {

			String query = "select * from Item_have_additional_choices where itemid=? and resid=? and toppingstype='Toppings'";
			ps = con.prepareStatement(query);
			ps.setInt(1, item.getId());
			ps.setInt(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				arr.add(new Toppings(rs.getString("Toppings"), rs.getDouble("Price")));
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return arr;
	}

	/**
	 * @author gethe this method returns user status (Frozen/Active) input : user
	 *         returns : (Frozen/Active)
	 */
	public static String getUserStatus(User user) {
		PreparedStatement ps;
		ResultSet rs;
		try {
			String query = "select * from usersinfo where userid=?";
			ps = con.prepareStatement(query);
			ps.setInt(1, user.getId());
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getString("status");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed to get user status";
		}
		return null;
	}

	/**
	 * @author salmanamer input: User instance functionality: inserts a user to the
	 *         user table output:"User Added"
	 */
	public static String InsertUser(User u) {

		String querySearch = "select * from user where id=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {
			if (rs.next())
				if (!rs.getString("id").equals(null))
					return "User is already exist";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}

		String query = "insert into user (id,FirstName,LastName,Username,Password,Email,PhoneNumber,W4_Code,Role,IsLoggedIn,Location) values(?,?,?,?,?,?,?,?,?,?,?);";

		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getFname());
			ps.setString(3, u.getLname());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getEmail());
			ps.setString(7, u.getPhone());
			ps.setString(8, u.getW4c());
			ps.setString(9, u.getRole());
			ps.setBoolean(10, false);
			ps.setString(11, u.getLocation());
//			System.out.print(u.getLocation() + "\n");
			ps.executeUpdate();
			return "User Added";

		} catch (SQLException e) {
			e.printStackTrace();
			return "connection to database failed user";
		}
	}

	/**
	 * @author salmanamer input: UserForImport instance functionality: inserts a
	 *         user to the UsersWaitingForApproval table output:"User Added"
	 */

	public static String InsertElseUser(UserForImport u) {

		String querySearch = "select * from user where id=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {
			if (rs.next())
				if (!rs.getString("id").equals(null))
					return "User is already exist";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}

		querySearch = "select * from UsersWaitingForApproval where id=?";

		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {
			if (rs.next())
				if (!rs.getString("id").equals(null))
					return "User is already exist";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}

		String query = "insert into UsersWaitingForApproval (id,FirstName,LastName,Username,Password,Email,PhoneNumber,Role,Location,RestaurantID,Restaurant,BusinessID,Business,QRCode) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getFname());
			ps.setString(3, u.getLname());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getEmail());
			ps.setString(7, u.getPhone());
			ps.setString(8, u.getRole());
			ps.setString(9, u.getLocation());
			ps.setInt(10, u.getResid());
			ps.setString(11, u.getRes());
			ps.setInt(12, u.getBusid());
			ps.setString(13, u.getBus());
			ps.setString(14, u.getqRcode());
			ps.executeUpdate();

			return "User Added";

		} catch (SQLException e) {
			e.printStackTrace();
			return "connection to database failed user";
		}
	}

	/**
	 * @author salmanamer input: String location functionality: it return all the
	 *         users to who are waiting for Approval output:ArrayList<UserForImport>
	 */
	public static ArrayList<UserForImport> GetWaitingList(String loc) {
		ArrayList<UserForImport> arr = new ArrayList<>();
		String query = "SELECT * from UsersWaitingForApproval where Location= ?";
		ResultSet rs;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, loc);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserForImport u = new UserForImport(rs.getString("Username"), rs.getString("Password"));
				u.setEmail(rs.getString("Email"));
				u.setFname(rs.getString("FirstName"));
				u.setLname(rs.getString("LastName"));
				u.setPhone(rs.getString("PhoneNumber"));
				u.setId(Integer.parseInt(rs.getString("id")));
				u.setRole(rs.getString("Role"));
				u.setBus(rs.getString("Business"));
				u.setBusid(rs.getInt("BusinessID"));
				u.setResid(rs.getInt("RestaurantID"));
				u.setRes(rs.getString("Restaurant"));
				u.setqRcode(rs.getString("QRCode"));
				u.setLocation(loc);

				arr.add(u);
			}
			return arr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author salmanamer input: UserForImport functionality:this function allows
	 *         new user according to : if the user already exists it reject him if
	 *         the user is HR and the business does not exist it adds him and his
	 *         business(if not it adds him connect him with the Business) if the
	 *         user is Supplier(Editor or Confirmer) and his restaurant does not
	 *         exist it add him and his resatrant (if not it adds him connect him
	 *         with the Restaurant) output:"User Added"
	 */
	public static String AllowUser(UserForImport u) {
		ResultSet rs1;
		String querySearch = "select * from user where id=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {

			if (rs.next())
				return "User is already exist";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}
		String role = u.getRole();

		if (role.equals("HR")) {
			Boolean busflag = false;

			querySearch = "select * from hrforbusiness where BusinessID=?";
			try {

				ps = con.prepareStatement(querySearch);
				ps.setInt(1, u.getBusid());
				rs = ps.executeQuery();
				System.out.print("hi");

			} catch (SQLException e) {
				e.printStackTrace();
				return "connection to database while searching failed";
			}
			try {
				if (rs.next())
					if (!((Integer) rs.getInt("HRid")).equals(null))
						return "HR for this Business is already exist";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block

			}

			querySearch = "select * from Business where BusinessID=?";
			try {
				ps = con.prepareStatement(querySearch);
				ps.setInt(1, u.getBusid());
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				return "connection to database while searching failed";
			}
			try {
				if (rs.next())
					if (!((Integer) rs.getInt("BusinessID")).equals(null))
						busflag = true;
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (!busflag) {
				String insertquery = "insert into Business (BusinessID,Name,Allowed,QRCode) values (?,?,?,?)";
				System.out.print(u.getqRcode());
				try {
					ps = con.prepareStatement(insertquery);
					ps.setInt(1, u.getBusid());
					ps.setString(2, u.getBus());
					ps.setString(3, "NotAllowed");
					ps.setString(4, u.getqRcode());

					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					return "connection to database failed user";
				}
			}
			String insertquery = "insert into hrforbusiness (HRid,BusinessID) values (?,?)";
			try {
				ps = con.prepareStatement(insertquery);
				ps.setInt(1, u.getId());
				ps.setInt(2, u.getBusid());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return "connection to database failed user";
			}

		} else if (role.startsWith("Supplier"))

		{
			boolean resflag = false;
			querySearch = "select * from restaurants where Restaurantid=?";
			try {
				ps = con.prepareStatement(querySearch);
				ps.setInt(1, u.getResid());
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				return "connection to database while searching failed";
			}
			try {

				if (rs.next()) {
					resflag = true;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block

			}
			if (!resflag) {
				String insertquery = "insert into restaurants (restaurantid,restaurantname,restaurantregion) values (?,?,?)";

				try {
					ps = con.prepareStatement(insertquery);
					ps.setInt(1, u.getResid());
					ps.setString(2, u.getRes());
					ps.setString(3, u.getLocation());
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					return "connection to database failed user";
				}

			}

			querySearch = "select * from supplier_for_resturant where Restaurantid=?";
			try {
				ps = con.prepareStatement(querySearch);
				ps.setInt(1, u.getResid());
				rs = ps.executeQuery();

				if (rs.next()) {
					querySearch = "select * from user where id=?";
					ps = con.prepareStatement(querySearch);
					ps.setInt(1, rs.getInt("Supplierid"));
					rs1 = ps.executeQuery();
					if (rs1.next()) {
						if (rs1.getString("Role").equals(role))
							return "a " + role + " for this restaurant is already exist";
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return "connection to database while searching failed";
			}

			String insertquery = "insert into supplier_for_resturant (Supplierid,restaurantid) values (?,?)";

			try {
				ps = con.prepareStatement(insertquery);
				ps.setInt(1, u.getId());
				ps.setInt(2, u.getResid());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return "connection to database failed user";
			}

		}
		String query = "insert into user (id,FirstName,LastName,Username,Password,Email,PhoneNumber,W4_Code,Role,IsLoggedIn,Location) values(?,?,?,?,?,?,?,?,?,?,?);";

		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getFname());
			ps.setString(3, u.getLname());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getEmail());
			ps.setString(7, u.getPhone());
			ps.setString(8, u.getW4c());
			ps.setString(9, u.getRole());
			ps.setBoolean(10, false);
			ps.setString(11, u.getLocation());
			// System.out.print(u.getLocation()+"\n");
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return "connection to database failed user";
		}

		query = "insert into usersinfo (userid,homeBranch,status) values(?,?,?);";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getLocation());
			ps.setString(3, "Active");
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		String deletequery = "delete from UsersWaitingForApproval where id=?";

		try {
			ps = con.prepareStatement(deletequery);
			ps.setInt(1, u.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "connection to database failed";
		}
		return "User Added";

	}

	/**
	 * @author salmanamer input: UserForImport and int(paymentlimit) functionality:
	 *         it inserts the user to UserWaitingForHR if his business is allowed
	 *         output:"User have to wait for HR of his company to accept"
	 */
	public static String AddWaitingBusinessUser(UserForImport u, int pl) {
		String searchQuery = "select * from Business where BusinessID=?";
		boolean busflag = false;
		ResultSet rs;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(searchQuery);
			ps.setInt(1, u.getBusid());
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("Allowed").equals("Allowed"))
					busflag = true;
			}

			if (busflag) {
				String query = "insert into UserWaitingForHR values (?,?,?,?,?,?,?,?,?,?,?) ";
				ps = con.prepareStatement(query);
				ps.setInt(1, u.getId());
				ps.setInt(2, u.getBusid());
				ps.setString(3, u.getFname());
				ps.setString(4, u.getLname());
				ps.setString(5, u.getUsername());
				ps.setString(6, u.getPassword());
				ps.setString(7, u.getEmail());
				ps.setString(8, u.getPhone());
				ps.setInt(9, pl);
				ps.setString(10, u.getLocation());
				ps.setString(11, u.getW4c());
				ps.executeUpdate();

				String deletequery = "delete from UsersWaitingForApproval where id=?";

				ps = con.prepareStatement(deletequery);
				ps.setInt(1, u.getId());
				ps.executeUpdate();

				return "User have to wait for HR of his company to accept";

			} else {
				return "The Business is not Allowed";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "connection to database failed";
		}

	}

	/**
	 * @author salmanamer input: Business instance functionality: it returns all the
	 *         Business users in UserWaitingForHR table output:
	 *         ArrayList<BusinessUser>
	 */
	public static ArrayList<BusinessUser> GetWaitingBusinessUser(Business b) {
		ArrayList<BusinessUser> arr = new ArrayList<BusinessUser>();
		String query = "select * from UserWaitingForHR where BusinessID=?";
		ResultSet rs;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, b.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				BusinessUser u = new BusinessUser(rs.getString("Username"), rs.getString("Password"));
				u.setEmail(rs.getString("Email"));
				u.setBusid(rs.getInt("BusinessID"));
				u.setFname(rs.getString("FirstName"));
				u.setLname(rs.getString("LastName"));
				u.setPhone(rs.getString("PhoneNumber"));
				u.setId(Integer.parseInt(rs.getString("id")));
				u.setPaymentlimit(rs.getInt("PaymentLimit"));
				u.setW4c(rs.getString("W4C_Code"));
				u.setLocation(rs.getString("Location"));

				arr.add(u);
			}
			return arr;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @author salmanamer input: BusinessUser instance functionality: it checks if
	 *         the user is already exists in user table ,if not, the function will
	 *         insert it into user, user_bussiness_account, usersinfo tables and
	 *         deletes it from UserWaitingForHR output: User Added
	 */
	public static String AllowBusinessUser(BusinessUser u) {

		String querySearch = "select * from user where id=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return "connection to database while searching failed";
		}
		try {

			if (rs.next())
				return "User is already exist";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block

		}

		String query = "insert into user (id,FirstName,LastName,Username,Password,Email,PhoneNumber,W4_Code,Role,IsLoggedIn,Location) values(?,?,?,?,?,?,?,?,?,?,?);";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getFname());
			ps.setString(3, u.getLname());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getEmail());
			ps.setString(7, u.getPhone());
			ps.setString(8, u.getW4c());
			ps.setString(9, "Costumer");
			ps.setBoolean(10, false);
			ps.setString(11, u.getLocation());
//			System.out.print(u.getLocation() + "\n");
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		query = "insert into user_bussiness_account (employeeid,BusinessID,PaymentLimit) values(?,?,?);";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setInt(2, u.getBusid());
			ps.setInt(3, u.getPaymentlimit());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		query = "insert into usersinfo (userid,status,credit) values(?,?,?);";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, "Active");
			ps.setInt(3, 0);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		String deletequery = "delete from UserWaitingForHR where id=?";

		try {
			ps = con.prepareStatement(deletequery);
			ps.setInt(1, u.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "connection to database failed";
		}
		return "User Added";

	}

	/**
	 * @author salmanamer input: BusinessUser instance functionality: it deletes the
	 *         user from UserWaitingForHR table without inserting it to user table
	 *         as a rejection output: User Rejected
	 */
	public static String RejectBusinessUser(BusinessUser u) {
		String deletequery = "delete from UserWaitingForHR where id=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(deletequery);
			ps.setInt(1, u.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return "connection to database failed";
		}
		return "User Rejected";

	}

	/**
	 * @author gethe this method set ready time for an order for a given time input
	 *         : restaurant id , order id , localdatetime return :nothing
	 */
	public static void setReadyTime(int resid, int orderid, LocalDateTime time) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"update restauranthaveorder set OrderReadyTime = ? where restaurantid = ? and orderid=?");
			ps.setTimestamp(1, Timestamp.valueOf(time));
			ps.setInt(2, resid);
			ps.setInt(3, orderid);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author gethe this method gets the business wallet for a business account
	 *         user input : user id return :yes + his business wallet if he is a
	 *         business account and no else
	 */
	public static String GetBusinessWallet(int id) {
		String querySearch = "select * from user_bussiness_account where employeeid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return "Yes" + "," + rs.getDouble("PaymentLimit");
			return "No";
		} catch (SQLException e) {
			return "FAILED";
		}

	}

	/**
	 * @author gethe this method returns the credit a user have from refunds (if he
	 *         have ) input : user id return :the credit in double
	 */
	public static double GetPrivateWallet(int id) {
		String querySearch = "select * from usersinfo where userid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getDouble("credit");
			else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * @author gethe this method takes from user credit input :user id , double
	 *         credit return :nothing
	 */
	public static String UpdateCredit(int userid, double credit) {
		String querySearch = "select * from usersinfo where userid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(querySearch);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				double creditDB = rs.getDouble("credit");
				querySearch = "update usersinfo set credit=? where userid=?";
				ps = con.prepareStatement(querySearch);
				ps.setInt(2, userid);
				ps.setDouble(1, creditDB);
				ps.executeUpdate();
				return "update done successfully";
			}
		} catch (SQLException e) {
			return "FAILED";
		}
		return "failed";
	}

	/**
	 * @author gethe this method checks if there are a common delivery in the
	 *         database input : res id , user id and date as string return : 1 if he
	 *         is the first user in the common delivery , 2 if second 3 if he is
	 *         third or more
	 */
	public static int CommonsDeliveryCheck(int resid, int userid, String dateAsString) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int business = 0, count = 1;
		String getbusiness = "select * from user_bussiness_account where employeeid=" + userid;
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			rs = st.executeQuery(getbusiness);
			while (rs.next()) {
				business = rs.getInt("BusinessID");
			}
			System.out.println(business);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 4;
		}

		String strring0 = dateAsString + " 00:00:00";
		String strring23 = dateAsString + " 23:59:59";
		String query = "select * from restauranthaveorder where restaurantid=? and Deliveryconfirm='Common' and OrderPlacedTime BETWEEN CAST('"
				+ strring0 + "' AS datetime ) AND CAST('" + strring23 + "' AS datetime)";
		System.out.println(query);
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, resid);
			rs = ps.executeQuery();
			while (rs.next()) {
				arr.add(rs.getInt("userid"));
			}
			System.out.println("the array of users id is ");

			for (int i = 0; i < arr.size(); i++) {
				System.out.println(arr.get(i));
				String getbusiness1 = "select * from user_bussiness_account where employeeid=" + arr.get(i);
				System.out.println(getbusiness1);
				st = con.createStatement();
				rs = st.executeQuery(getbusiness1);
				while (rs.next()) {
					System.out.println(
							"the new user id is " + arr.get(i) + "and his business id is " + rs.getInt("BusinessID"));
					if (business == rs.getInt("BusinessID") && arr.get(i) != userid)
						count++;
				}
			}
			if (count > 3)
				return 3;
			else
				return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @author gethe this method add new order items to data base input : restaurant
	 *         id -order id - arraylist of items return :nothing
	 */
	public static void AddItemForOrder(int restaurantid, int orderid, ArrayList<OrderView> cart) {
		PreparedStatement ps;
		ResultSet rs;
		int id = 0;
		for (int i = 0; i < cart.size(); i++) {
			try {
				String getitemidquery = "select * from resturant_have_items where resturantid=? and itemName=?";
				ps = con.prepareStatement(getitemidquery);
				ps.setInt(1, restaurantid);
				ps.setString(2, cart.get(i).getItemName());
				rs = ps.executeQuery();
				while (rs.next())
					id = rs.getInt("itemid");
				String query = "insert into orderhaveitems (orderid,itemid,sizeofitem,degreeofdoneness,quantity,otherdescreption,ItemType) values(?,?,?,?,?,?,?)";

				ps = con.prepareStatement(query);
				ps.setInt(1, orderid);
				ps.setInt(2, id);
				ps.setString(3, cart.get(i).getSize());
				ps.setString(4, cart.get(i).getLevelOfCoocking());
				ps.setInt(5, cart.get(i).getQty());
				ps.setString(6, cart.get(i).getTopings() + " " + cart.get(i).getDescription());
				ps.setString(7, cart.get(i).getType());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author gethe this method add new order to data base give it auto increment
	 *         id and return the id to the client input : restaurant id , user id ,
	 *         order price , Delivery return :order's auto generated id
	 */
	public static int AddOrder(int res, int user, double price, Delivery delivery) {
		PreparedStatement ps;
		ResultSet rs;
		String deliverytype;
		if (delivery.getDeliverWay().equals("TakeAway"))
			deliverytype = "Take-Away";
		else
			deliverytype = delivery.getDeliveryType();
		System.out.println(delivery.getExecutedTime());
		System.out.println(delivery.getExpectedArrivalTime());
		String query = "insert into restauranthaveorder (restaurantid,userid,OrderPlacedTime,Deliveryconfirm,Ready,Price,OrderType) values(?,?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, res);
			ps.setInt(2, user);
			ps.setTimestamp(3, Timestamp.valueOf(delivery.getExecutedTime()));
			ps.setString(4, deliverytype);
			ps.setString(5, "0");
			ps.setDouble(6, price);
			ps.setString(7, delivery.getOrderType());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return -1;

	}

	/**
	 * @author gethe this method takes price from user credit or payment limit
	 *         according to user type (business/private) input : userid , order id ,
	 *         payment return : status string
	 */
	public static String takePriceFromUser(int userid, int orderid, Payment payment) {
		PreparedStatement ps;
		double left = -1;
		ResultSet rs;
		System.out.println(payment);

		if (payment.getAccountType().equals("Business Account")) {
			try {
				String query1 = "select * from user_bussiness_account where employeeid=? ";
				ps = con.prepareStatement(query1);
				ps.setInt(1, userid);
				rs = ps.executeQuery();
				while (rs.next()) {
					left = rs.getDouble("PaymentLimit") - Double.parseDouble(payment.getFinalPrice());
					if (left < 0)
						left = 0;
				}
				if (left == -1)
					throw new SQLException();
				String query = "update user_bussiness_account set PaymentLimit = ? where employeeid=?";
				ps = con.prepareStatement(query);
				ps.setInt(2, userid);
				ps.setDouble(1, left);
				ps.executeUpdate();
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "FAILED";
			}
			return "SUCCESS";
		} else {
			try {
				String query1 = "select * from usersinfo where userid=? ";
				ps = con.prepareStatement(query1);
				ps.setInt(1, userid);
				rs = ps.executeQuery();
				while (rs.next()) {
					left = rs.getDouble("credit") - Double.parseDouble(payment.getFinalPrice());
					if (left < 0)
						left = 0;
				}
				if (left == -1)
					throw new SQLException();
				String query = "update usersinfo set credit=? where userid=?";
				ps = con.prepareStatement(query);
				ps.setInt(2, userid);
				ps.setDouble(1, left);
				ps.executeUpdate();
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "FAILED";
			}

			String queryupdate = "Update restauranthaveorder set transactiontype=? where orderid =? ";
			try {
				ps = con.prepareStatement(queryupdate);
				ps.setInt(2, orderid);
				ps.setString(1, payment.getAccountType());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "SUCCESS";
		}
	}

	/**
	 * @author gethe this method checks the QR code the business account wrote and
	 *         checks if it similar to the one in database input : user id , string
	 *         str which contains the QR code return :boolean true or false
	 */
	public static boolean CheckQR(int userid, String str) {
		String[] arr = str.split(",");
		String qr = arr[0];
		String name = arr[1];
		PreparedStatement ps;
		ResultSet rs;
		int businessid = 0;
		try {
			String query1 = "select * from user_bussiness_account where employeeid=? ";
			ps = con.prepareStatement(query1);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				businessid = rs.getInt("BusinessID");
			}
			if (businessid != 0) {
				query1 = "select * from business where BusinessID=? ";
				ps = con.prepareStatement(query1);
				ps.setInt(1, businessid);
				rs = ps.executeQuery();
				if (rs.next()) {
					if (rs.getString("QRCode").equals(qr) && rs.getString("Name").equals(name))
						return true;

				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author gethe this method returns all the orders done by a given user input :
	 *         user id return :array list of orders
	 */
	public static ArrayList<OrdersHistory> getOrderHistory(int userid) {
		ArrayList<OrdersHistory> arr = new ArrayList<OrdersHistory>();
		String query = "select * from restauranthaveorder where userid=?";
		PreparedStatement ps;
		ResultSet rs;
		String resname = null;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("restaurantid");
				String query1 = "select * from restaurants where restaurantid=?";
				PreparedStatement ps1;
				ResultSet rs1;
				ps1 = con.prepareStatement(query1);
				ps1.setInt(1, id);
				rs1 = ps1.executeQuery();
				if (rs1.next())
					resname = rs1.getString("restaurantname");
				arr.add(new OrdersHistory(rs.getTimestamp("OrderPlacedTime").toString(), resname,
						rs.getString("transactiontype"), Double.toString(rs.getDouble("Price")), rs.getInt("orderid")));
			}
			return arr;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void SetORderAsRE(int orderid) {
		String query = "update restauranthaveorder set Ready = ? where orderid = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(2, orderid);
			ps.setString(1, "3");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * @author gethe this method checks the w4c card the bm manager gives a new user
	 *         if it is already exists input : user id , string str which contains
	 *         the w4c code return :boolean true or false
	 */
	public static boolean CheckW4C(String w4c) {
		String queryString = "select * from user where W4_Code=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(queryString);
			ps.setString(1, w4c);
			rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * @author gethe this method returns email and phone for a given user (to send
	 *         info to him ) input : user id return :string status
	 */
	public static String GetUserinfo(int id) {
		String queryString = "select * from restauranthaveorder where orderid=?";
		PreparedStatement ps;
		String str = "";
		int userid = 0;
		ResultSet rs;
		try {
			ps = con.prepareStatement(queryString);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				userid = rs.getInt("userid");
			}
			if (userid != 0) {
				queryString = "select * from user where id=?";
				ps = con.prepareStatement(queryString);
				ps.setInt(1, userid);
				rs = ps.executeQuery();
				if (rs.next()) {
					str += rs.getString("Email");
					str += "\t";
					str += rs.getString("PhoneNumber");
				}
				return str;
			} else
				return "Wrong id";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "Wrong id";

		}
	}

	/**
	 * @author gethe
	 * 
	 *    this method checks the user if he is business user or not input :
	 *         user u return : true if he is business or false else
	 */
	public static Boolean CheckifBus(User u) {
		String queryString = "select * from user_bussiness_account where employeeid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(queryString);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;

		}
	}

	public static boolean getuserstatus(User u) {
		String queryString = "select * from usersinfo  where userid=?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(queryString);
			ps.setInt(1, u.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("status").equals("Active"))
					return true;
			}
			return false;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;

		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param region
	 * @return Get BM manager restaurants according to region
	 */
	public static ArrayList<Restaurant> getBMMRestaurantsIDs(String region) {
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		ResultSet rs;
		PreparedStatement ps;
		try {
			String query = "select restaurantid, restaurantname from restaurants where restaurantregion=?;";
			ps = con.prepareStatement(query);
			ps.setString(1, region);
			rs = ps.executeQuery();
			while (rs.next())
				restaurants.add(new Restaurant(rs.getInt(1), rs.getString(2)));
			return restaurants;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param restaurantID
	 * @param month
	 * @return Get Income Report
	 */
	public static int getIncomeTotal1(int restaurantID, int month) {
		PreparedStatement ps;
		ResultSet rs;
		try {
			String query = "select sum(Price) as 'Price' from restauranthaveorder where restaurantid = ? and month(OrderReadyTime) = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, restaurantID);
			ps.setInt(2, month);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			else
				return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 
	 * @author Ibraheem & Kinan
	 * @param restaurantID
	 * @param month
	 * @return Get orders report
	 */
	public static ArrayList<orderReport> getOrdersReport(int restaurantID, int month) {

		PreparedStatement ps;
		ResultSet rs;
		ArrayList<orderReport> OReports = new ArrayList<>();
		try {
			String query = "SELECT itemtype, sum(quantity)" + " FROM orderhaveitems" + " INNER JOIN restauranthaveorder"
					+ " ON orderhaveitems.orderid = restauranthaveorder.orderid"
					+ " where restaurantid = ? and month(OrderReadyTime) = ?" + " group by itemtype;";
			ps = con.prepareStatement(query);
			ps.setInt(1, restaurantID);
			ps.setInt(2, month);
			rs = ps.executeQuery();
			while (rs.next())
				OReports.add(new orderReport(rs.getString(1), rs.getInt(2)));
			return OReports;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param restaurantID
	 * @param month
	 * @return Get Activity Report Get avg Delay and OnTime percentages
	 */
	public static double getActivityReport(int restaurantID, int month) {
		double d;
		ResultSet rs;
		PreparedStatement ps;
		try {
			String query = "select avg("
					+ " (select count(*) from restauranthaveorder where ordertype = 'Pre-Order' and restaurantid = ? and month(OrderReadyTime) = ?"
					+ " and TIMESTAMPDIFF(MINUTE, OrderPlacedTime, OrderReadyTime) > 20)"
					+ "  + (select count(*) from restauranthaveorder where ordertype = 'Regular'and restaurantid = ? and month(OrderReadyTime) = ?"
					+ " and TIMESTAMPDIFF(HOUR, OrderPlacedTime, OrderReadyTime) > 1)) /"
					+ " (select count(*) from restauranthaveorder where restaurantid = ? and month(OrderReadyTime) = ?) as 'Delay avg'"
					+ " from restauranthaveorder;";
			ps = con.prepareStatement(query);
			ps.setInt(1, restaurantID);
			ps.setInt(2, month);
			ps.setInt(3, restaurantID);
			ps.setInt(4, month);
			ps.setInt(5, restaurantID);
			ps.setInt(6, month);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getDouble(1);
			else
				return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param yearQuarter
	 * @return Get all restaurants orders
	 */
	public static ArrayList<ResNameAndOrderNum> getCEOOrdersReport(String yearQuarter) {
		ArrayList<ResNameAndOrderNum> qOrders = new ArrayList<>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			String query = "SELECT restaurantname, count(orderid)" + " FROM restauranthaveorder"
					+ " INNER JOIN restaurants" + " ON restauranthaveorder.restaurantid = restaurants.restaurantid"
					+ " where month(OrderReadyTime) > ? and month(OrderReadyTime) < ?"
					+ " group by restauranthaveorder.restaurantid;";
			ps = con.prepareStatement(query);
			switch (yearQuarter) {
			case "1st quarter":
				ps.setInt(1, 0);
				ps.setInt(2, 4);
				break;
			case "2nd quarter":
				ps.setInt(1, 3);
				ps.setInt(2, 7);
				break;
			case "3rd quarter":
				ps.setInt(1, 6);
				ps.setInt(2, 10);
				break;
			case "4th quarter":
				ps.setInt(1, 9);
				ps.setInt(2, 13);
				break;
			default:
				break;
			}
			rs = ps.executeQuery();
			while (rs.next())
				qOrders.add(new ResNameAndOrderNum(rs.getString(1), rs.getInt(2)));
			return qOrders;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param yearQuarter
	 * @return Get all restaurants income
	 */
	public static ArrayList<ResNameAndIncome> getCEOIncomeReport(String yearQuarter) {
		ArrayList<ResNameAndIncome> qIncome = new ArrayList<>();
		PreparedStatement ps;
		ResultSet rs;
		try {
			String query = "SELECT restaurantname, sum(Price)" + " FROM restauranthaveorder" + " INNER JOIN restaurants"
					+ " ON restauranthaveorder.restaurantid = restaurants.restaurantid"
					+ " where month(OrderReadyTime) > ? and month(OrderReadyTime) < ?"
					+ " group by restauranthaveorder.restaurantid;";
			ps = con.prepareStatement(query);
			switch (yearQuarter) {
			case "1st quarter":
				ps.setInt(1, 0);
				ps.setInt(2, 4);
				break;
			case "2nd quarter":
				ps.setInt(1, 3);
				ps.setInt(2, 7);
				break;
			case "3rd quarter":
				ps.setInt(1, 6);
				ps.setInt(2, 10);
				break;
			case "4th quarter":
				ps.setInt(1, 9);
				ps.setInt(2, 13);
				break;
			default:
				break;
			}
			rs = ps.executeQuery();
			while (rs.next())
				qIncome.add(new ResNameAndIncome(rs.getString(1), rs.getInt(2)));
			return qIncome;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param mf
	 * @param bmName Get Income Report
	 */
	public static void insertCEOReportBLOB(MyFile mf, String bmName) {
		System.out.println("Babyyyy" + mf.getFileName() + " - " + bmName);
		PreparedStatement ps;
		try {
			String query = "insert into ceoreports (reportblob, sentdate, bmName) values (?, CURRENT_TIMESTAMP, ?);";
			ps = con.prepareStatement(query);
			ps.setBytes(1, mf.getMybytearray());
			ps.setString(2, bmName);
			ps.executeUpdate();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @return Get all CEO Reports
	 */
	public static ArrayList<CeoReport> getCEOReportBLOB() {
		ArrayList<CeoReport> arr = new ArrayList<CeoReport>();
		Statement ps;
		try {
			String query = "select reportid, bmName, sentdate from ceoreports";
			ps = con.createStatement();
			ResultSet rs11 = ps.executeQuery(query);
			while (rs11.next())
				arr.add(new CeoReport(rs11.getInt(1), rs11.getString(2), rs11.getTimestamp(3)));
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Ibraheem & Kinan
	 * @param id
	 * @return Get a specific blob
	 */
	public static byte[] getCEOBLOB(int id) {
		Statement ps;
		try {
			String query = "select reportblob from ceoreports";
			ps = con.createStatement();
			ResultSet rs11 = ps.executeQuery(query);
			if (rs11.next())
				return rs11.getBytes("reportblob");
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
