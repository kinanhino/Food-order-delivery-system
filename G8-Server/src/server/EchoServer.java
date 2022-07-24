// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.net.UnknownHostException;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

import com.mysql.cj.MysqlConnection;

import MySQLconnection.SQLconnection;
import gui.ServerPortFrameController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.clients;
import logic.orderReport;
import logic.Business;
import logic.BusinessUser;
import logic.CeoReport;
import logic.Choice;
import logic.Delivery;
import logic.Item;
import logic.MyFile;
import logic.Order;
import logic.OrderForRestaurant;
import logic.OrderView;
import logic.Payment;
import logic.Request;
import logic.ResNameAndIncome;
import logic.ResNameAndOrderNum;
import logic.Restaurant;
import logic.Toppings;
import logic.User;
import logic.UserForImport;
import logic.UserForStatus;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */

public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	SQLconnection c = new SQLconnection();
	ArrayList<Item> items = new ArrayList<Item>();
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */

	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		String message;// the message contains the request from client
		String type1 = ((Request) msg).getRequest();
		String[] arr = type1.split("\t");
		message = arr[0];
		/**
		 * @author gethe
		 * @param String message
		 * @Functionality according to message in class Request the server Decides what
		 *                action to do
		 */
		switch (message) {
		case "connect":
			ServerController.ConnectClient(client);
			try {
				client.sendToClient(new Request("connected", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "disconnect":
			ServerController.DisonnectClient(client);
			try {
				System.out.print("after send to client");
				client.sendToClient(new Request("disconnected", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Login":
			try {
				String str = SQLconnection.GetUserWithPassword((User) ((Request) msg).getObj());
				client.sendToClient(new Request("Role	" + str, (User) ((Request) msg).getObj()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Log out":
			try {
				SQLconnection.GetLogOut((User) ((Request) msg).getObj());
				client.sendToClient(new Request("Logged out", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "get user":
			try {
				String str = SQLconnection.GetUser((User) ((Request) msg).getObj());
				client.sendToClient(new Request("email	" + str, (User) ((Request) msg).getObj()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "reset password":
			SQLconnection.ResetPassword((User) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("password saved", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "get resturant name":
			String[] NameAndID = SQLconnection.GetResturantNameForSupplierId((User) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("resturant name sent", NameAndID));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "edit item":
			int id = Integer.parseInt(arr[1]);
			String status = SQLconnection.UpdateItemInMenu((Item) ((Request) msg).getObj(), id);
			try {
				client.sendToClient(new Request("after update item	" + status, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "show items in restaurant":
			items = SQLconnection.GetItemsForRestaurantId((int) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("res menu sent", items));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "add item to menu":
			int id1 = Integer.parseInt(arr[1]);
			String status1 = SQLconnection.AddItemToMenu((Item) ((Request) msg).getObj(), id1);
			try {
				client.sendToClient(new Request("after add item	" + status1, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "remove item from menu":
			int id2 = Integer.parseInt(arr[1]);
			String status2 = SQLconnection.RemoveItemFromMenu((Item) ((Request) msg).getObj(), id2);

			try {
				client.sendToClient(new Request("after add item	" + status2, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "view users list":
			ArrayList<User> a = SQLconnection.GetUsersList();

			try {
				if (!a.equals(null)) {
					client.sendToClient(new Request("Users List", a));
					System.out.print("Users List");
				} else
					client.sendToClient(new Request("Error", null));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "Add user":
			int resid = Integer.parseInt(arr[1]);
			String str1 = SQLconnection.AddUser((User) ((Request) msg).getObj(), resid);
			try {
				client.sendToClient(new Request("User Added", str1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Add Restaurant":
			String str2 = SQLconnection.AddRes((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Restaurant Added", str2));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Check choices":
			boolean[] flags;// size-degree-toppings
			flags = SQLconnection.CheckChoices(Integer.parseInt(arr[1]), (int) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("check choices sent", flags));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "get choices":

			try {
				client.sendToClient(new Request("AdditionalChoices",
						SQLconnection.GetChoices(Integer.parseInt(arr[1]), (Integer) ((Request) msg).getObj())));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "Delete choice":
			String str3 = SQLconnection.DeleteChoice((Choice) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Choice Delete", str3));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Change choice price":
			String str4 = SQLconnection.ChangeChoicePrice(Double.parseDouble(arr[2]),
					(Choice) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Change Choice Price", str4));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "get BM location":
			String location = SQLconnection.getBMlocation((User) ((Request) msg).getObj());
			System.out.println(location);
			try {
				client.sendToClient(new Request("BMlocation" + "	" + location, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "get users status":
			ArrayList<UserForStatus> users = SQLconnection.getUsersInLocation((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("users status", users));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "set status":
			int userid = Integer.parseInt(arr[1]);
			SQLconnection.ChangeStatus(userid, (String) ((Request) msg).getObj());
			try {
				client.sendToClient("status changed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "view Ready orders for restaurant":
			ArrayList<OrderForRestaurant> orders = new ArrayList<OrderForRestaurant>();
			try {
				orders = SQLconnection.getReadyOrdersForRestaurant((int) ((Request) msg).getObj());
				client.sendToClient(new Request("ready orders sent", orders));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "view working orders for restaurant":
			try {
				ArrayList<OrderForRestaurant> rd = SQLconnection.getWorkingOrdersForRestaurant();
				System.out.println(rd);
				client.sendToClient(new Request("working orders sent", rd));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "show items in order":
			try {
				client.sendToClient(new Request("items for orders sent",
						SQLconnection.getItemsForOrder(Integer.parseInt(arr[1]), (int) ((Request) msg).getObj())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "delete order":
			String deleteorder = SQLconnection.deleteOrder((OrderForRestaurant) ((Request) msg).getObj());
			System.out.println(deleteorder);
			try {
				client.sendToClient(new Request("order delete status is", deleteorder));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "set order as working":
			String setorder = SQLconnection.setOrderAsWorking((OrderForRestaurant) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("order set as working status is" + "	" + setorder, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "GetHRBussiness":
			Business business = SQLconnection.GetBussiness((User) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Business", business));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "AskForBusAcc":
			SQLconnection.ChangeBusStatusToWaiting((Business) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Business Status Changed", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Waiting Business":
			ArrayList<Business> businesses = SQLconnection.GetWaitingBusiness((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Waiting Business", businesses));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Accept Business":
			SQLconnection.ChangeBusStatus((Business) ((Request) msg).getObj(), "Allowed");
			try {
				client.sendToClient(new Request("Business Accepted", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Reject Business":
			SQLconnection.ChangeBusStatus((Business) ((Request) msg).getObj(), "NotAllowed");
			try {
				client.sendToClient(new Request("Business Rejceted", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "Get Flags":
			try {
				client.sendToClient(new Request("Flags sent", SQLconnection.CheckChoices(Integer.parseInt(arr[1]),
						((Item) ((Request) msg).getObj()).getId())));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "Get Resturant List":
			try {
				client.sendToClient(new Request("res list sent", SQLconnection.GetRestaurantList()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Resturant List with location":
			try {
				client.sendToClient(new Request("res list sent",
						SQLconnection.GetRestaurantListWithLocation((String) ((Request) msg).getObj())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Check if Business":
			boolean s = SQLconnection.CheckifBus((User) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("after check bus", s));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Resturant Menu by Id": {
			items = SQLconnection.GetItemsForRestaurantId((int) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("res menu sent", items));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		case "check id for restaurant":
			try {
				client.sendToClient(new Request("check id for restaurant sent",
						SQLconnection.checkIdForItem(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "add toppings for item":
			try {
				client.sendToClient(new Request("add toppings for item sent",
						SQLconnection.AddToppingsForItem(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]),
								(ArrayList<Toppings>) ((Request) msg).getObj())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Sizes":
			try {
				client.sendToClient(new Request("Sizes sent",
						SQLconnection.getSizes(Integer.parseInt(arr[1]), (Item) ((Request) msg).getObj())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Waiting Approval List":
			ArrayList<UserForImport> array = SQLconnection.GetWaitingList((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Waiting Users List", array));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Allow User":
			String varString = SQLconnection.AllowUser((UserForImport) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("User Allowance", varString));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Waiting Business User":
			varString = SQLconnection.AddWaitingBusinessUser((UserForImport) ((Request) msg).getObj(),
					Integer.parseInt(arr[1]));
			try {
				client.sendToClient(new Request("User Allowance", varString));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Waiting HR Approval List":
			ArrayList<BusinessUser> arrayList = SQLconnection
					.GetWaitingBusinessUser((Business) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Business User List", arrayList));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Allow BusinessUser":
			varString = SQLconnection.AllowBusinessUser((BusinessUser) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("BusinessUser Allowance", varString));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Reject BusinessUser":
			varString = SQLconnection.RejectBusinessUser((BusinessUser) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("BusinessUser Recjection", varString));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Toppings":
			try {
				client.sendToClient(new Request("Toppings sent",
						SQLconnection.gettoppings(Integer.parseInt(arr[1]), (Item) ((Request) msg).getObj())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get User Status":
			try {
				client.sendToClient(new Request("User Status", SQLconnection.GetUser((User) ((Request) msg).getObj())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "set ready time for order":
			SQLconnection.setReadyTime(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]),
					(LocalDateTime) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("order ready time updated", null));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "Get Buisness Wallet":
			try {
				client.sendToClient(
						new Request("Business Wallet Sent", SQLconnection.GetBusinessWallet(Integer.parseInt(arr[1]))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Private Wallet":
			try {
				client.sendToClient(
						new Request("Private Wallet Sent", SQLconnection.GetPrivateWallet(Integer.parseInt(arr[1]))));
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Update Credit":
			try {
				client.sendToClient(new Request(
						"Update Credit Sent	"
								+ SQLconnection.UpdateCredit(Integer.parseInt(arr[1]), Double.parseDouble(arr[2])),
						null));
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "Check if its Commons Delivery":
			try {
				client.sendToClient(new Request("Commons Delivery Sent", SQLconnection.CommonsDeliveryCheck(
						Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), (String) ((Request) msg).getObj())));
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Meals Table":
			int restaurantid = Integer.parseInt(arr[1]);
			int orderid = Integer.parseInt(arr[2]);
			ArrayList<OrderView> cart = (ArrayList<OrderView>) ((Request) msg).getObj();
			try {
				SQLconnection.AddItemForOrder(restaurantid, orderid, cart);
				client.sendToClient(new Request("Meals Table Sent", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Delivery Details":
			int restaurantid1 = Integer.parseInt(arr[1]);
			int userid1 = Integer.parseInt(arr[2]);
			double price = Double.parseDouble(arr[3]);
			Delivery delivery = (Delivery) ((Request) msg).getObj();
			try {
				client.sendToClient(new Request("Delivery Details",
						SQLconnection.AddOrder(restaurantid1, userid1, price, delivery)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Payment Method":
			try {
				client.sendToClient(new Request("Payment Method Sent", SQLconnection.takePriceFromUser(
						Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), (Payment) ((Request) msg).getObj())));
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Check Qr":
			try {
				client.sendToClient(new Request("QR Checked",
						SQLconnection.CheckQR(Integer.parseInt(arr[1]), (String) ((Request) msg).getObj())));
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Order History":
			try {
				client.sendToClient(new Request("orders history sent",
						SQLconnection.getOrderHistory((int) ((Request) msg).getObj())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Order Received":
			SQLconnection.SetORderAsRE(Integer.parseInt(arr[1]));
			try {
				client.sendToClient(new Request("Order Received", null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Delete User":
			String str = SQLconnection.DeleteUser((int) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request(str, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Check W4C":
			Boolean flag = SQLconnection.CheckW4C((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("W4C Checked", flag));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "get user info":
			String sw = SQLconnection.GetUserinfo((int) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("user info sent\t" + sw, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Get Status For User":
			try {
				client.sendToClient(
						new Request("Status For User", SQLconnection.getuserstatus((User) ((Request) msg).getObj())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// get BM managers restaurants list
		case "get BMMRestaurants":

			ArrayList<Restaurant> restaurantsAndIDs = SQLconnection
					.getBMMRestaurantsIDs((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("BMM restaurants recieved", restaurantsAndIDs));
				// System.out.println(restaurants);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Get total incomeReport
		case "get incometotal":
			Object[] objs = (Object[]) ((Request) msg).getObj();
			int foodTotal = SQLconnection.getIncomeTotal1((int) objs[0], (int) objs[1]);
			try {
				client.sendToClient(new Request("Income total recieved", foodTotal));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Get ordersReport
		case "get ordersReport":
			ArrayList<orderReport> ordersR = new ArrayList<>();
			Object[] orderObjs = (Object[]) ((Request) msg).getObj();
			ordersR = SQLconnection.getOrdersReport((int) orderObjs[0], (int) orderObjs[1]);
			try {
				client.sendToClient(new Request("Order reports recieved", ordersR));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Get activityReport
		case "get activityReport":
			Object[] objs1 = (Object[]) ((Request) msg).getObj();
			Double delays = SQLconnection.getActivityReport((int) objs1[0], (int) objs1[1]);
			try {
				client.sendToClient(new Request("Activity reports recieved", delays));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Get all restaurants orders report for year quarter
		case "get quarter orders":
			ArrayList<ResNameAndOrderNum> qOrders = SQLconnection.getCEOOrdersReport((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Quarter orders report recieved", qOrders));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Get all restaurants orders report for year quarter
		case "get quarter income":
			ArrayList<ResNameAndIncome> qIncome = SQLconnection.getCEOIncomeReport((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Quarter income report recieved", qIncome));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Write CEO file to DB
		case "write ceoBLOB":
			Object[] objsBLB = (Object[]) ((Request) msg).getObj();
			SQLconnection.insertCEOReportBLOB((MyFile) objsBLB[0], (String) objsBLB[1]);
			try {
				client.sendToClient(new Request("ceo report written to DB", null));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Write file to DB
		case "write incomeBLOB":
			// ArrayList<ResNameAndIncome> qIncome =
			// SQLconnection.getCEOIncomeReport((String) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("Income report written to DB", null));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		// @Ibraheem & @Kinan
		// Get all ceo reports
		case "get all ceoReports":
			ArrayList<CeoReport> ceoRs = SQLconnection.getCEOReportBLOB();
			try {
				client.sendToClient(new Request("all ceo reports recieved", ceoRs));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "get CEOBlob":
			byte[] b = SQLconnection.getCEOBLOB((int) ((Request) msg).getObj());
			try {
				client.sendToClient(new Request("CEOO Blob", b));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Illegal Commaned: " + message);
			System.exit(0);
			break;
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}
}
//End of EchoServer class
