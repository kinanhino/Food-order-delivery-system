// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import ocsf.server.ConnectionToClient;

import client.*;
import common.ChatIF;
import gui.ActivityReportController;
import gui.AddItemController;
import gui.AdditionalChoicesController;
import gui.BMScreenController;
import gui.BMUserRegController;
import gui.BusinessConfirmationController;
import gui.CEOProducedReportsController;
import gui.CEOReportController;
import gui.ConnectFormController;
import gui.DeliveryDetailsController;
import gui.EditItemController;
import gui.EditUserController;
import gui.EmailVerificationController;
import gui.HRMScreenController;
import gui.HRUserApprovalController;
import gui.IncomeReportController;
import gui.LoginScreenController;
import gui.NewOrderController;
import gui.OrderPreviewController;
import gui.OrdersListController;
import gui.OrdersReportController;
import gui.PaymentDetailsController;
import gui.PickReportController;
import gui.RegisterSupplierController;
import gui.ResMenuForCustomersController;
import gui.RestaurantMenuController;
import gui.SupplierConfirmerController;
import gui.SupplierEditorController;
import gui.USERHSController;
import gui.ViewOrdersController;
import gui.ViewUsersController;
import javafx.collections.ObservableList;
import logic.Business;
import logic.BusinessUser;
import logic.CeoReport;
import logic.Choice;
import logic.Item;
import logic.ItemForOrder;
import logic.Order;
import logic.OrderForRestaurant;
import logic.OrdersHistory;
import logic.Request;
import logic.ResNameAndIncome;
import logic.ResNameAndOrderNum;
import logic.Toppings;
import logic.User;
import logic.UserForImport;
import logic.UserForStatus;
import logic.orderReport;
import logic.Restaurant;

import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	// public static Student s1 = new Student(null,null,null,new
	// Faculty(null,null));
	public static boolean awaitResponse = false;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {

		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		// openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		String type1 = ((Request) msg).getRequest();
		String[] arr = type1.split("\t");
		String type = arr[0];
		switch (type) {
		case "Role":
			LoginScreenController.role = arr[1];
			LoginScreenController.user1 = (User) ((Request) msg).getObj();
			break;
		case "Logged out": {
			System.out.print("Logged out");
		}
			break;
		case "connected": {
			System.out.print(type);
		}
			break;
		case "disconnected": {
			System.out.print(type);
		}
			break;
		case "email":
			EmailVerificationController.email = arr[1];
			break;
		case "password saved":
			System.out.print(type);
			break;
		case "resturant name sent":
			String[] arr1 = (String[]) ((Request) msg).getObj();
			SupplierEditorController.resturantName = arr1[0];
			SupplierEditorController.resturantID = Integer.parseInt(arr1[1]);
			SupplierConfirmerController.resturantName = arr1[0];
			SupplierConfirmerController.resturantID = Integer.parseInt(arr1[1]);
			break;
		case "res menu sent":
			RestaurantMenuController.items = (ArrayList<Item>) (((Request) msg).getObj());
			ResMenuForCustomersController.items = (ArrayList<Item>) (((Request) msg).getObj());
			break;
		case "after update item":
			EditItemController.status = arr[1];
			break;
		case "after add item":
			AddItemController.status = arr[1];
			break;
		case "res list sent":
			NewOrderController.restaurants = (ArrayList<Restaurant>) ((Request) msg).getObj();
			break;
		// case "Item added to cart":
		case "Flags sent":
			boolean[] flagsArr = (boolean[]) ((Request) msg).getObj();
			ResMenuForCustomersController.SizeFlag = flagsArr[0];
			ResMenuForCustomersController.CoockingFlag = flagsArr[1];
			ResMenuForCustomersController.TopingsFlag = flagsArr[2];

		case "Toppings sent":
			AdditionalChoicesController.Thetopings = (ArrayList<Toppings>) ((Request) msg).getObj();
			break;
		case"after check bus":
			DeliveryDetailsController.Busflag = (boolean) ((Request) msg).getObj();
			break;
		case "Sizes sent":
			AdditionalChoicesController.TheSize = (ArrayList<Toppings>) ((Request) msg).getObj();
			ResMenuForCustomersController.TheSize = (ArrayList<Toppings>) ((Request) msg).getObj();
			break;

		case "last price sent":
			AdditionalChoicesController.topingsprice = (Double) ((Request) msg).getObj();

			break;
		case "Users List":
			ViewUsersController.users = (ArrayList<User>) ((Request) msg).getObj();
			break;
		case"test":
			break;
		case "user deleted":
			break;
		case "User Added":
			RegisterSupplierController.lbl = (String) ((Request) msg).getObj();
			break;
		case "Restaurant Added":
			RegisterSupplierController.lbl = (String) ((Request) msg).getObj();
			break;
		case "AdditionalChoices":
			EditItemController.Choicesarr = (ArrayList<Choice>) ((Request) msg).getObj();

			break;
		case "Choice Delete":
			EditItemController.status = (String) ((Request) msg).getObj();
			break;
		case "Change Choice Price":
			EditItemController.status = (String) ((Request) msg).getObj();
			break;

		case "BMlocation":
			BMScreenController.loc = arr[1];
			break;
		case "users status":
			EditUserController.arr = (ArrayList<UserForStatus>) ((Request) msg).getObj();
			break;
		case "status changed":
			System.out.println("done");
			break;
		case "ready orders sent":
			ViewOrdersController.ready = (ArrayList<OrderForRestaurant>) ((Request) msg).getObj();
			break;
		case "working orders sent":
			ViewOrdersController.working = (ArrayList<OrderForRestaurant>) ((Request) msg).getObj();
			break;
		case "items for orders sent":

			ViewOrdersController.items = (ArrayList<ItemForOrder>) ((Request) msg).getObj();
			break;
		case "order set as working status is":
			ViewOrdersController.status = arr[1];
			break;
		case "order delete status is":
			ViewOrdersController.status = arr[1];
			break;
		case "Business":
			HRMScreenController.business = (Business) ((Request) msg).getObj();
			break;
		case "Business Status Changed":
			System.out.print("Business Status Changed");
			break;
		case "Waiting Business":
			BusinessConfirmationController.businesses = (ArrayList<Business>) ((Request) msg).getObj();
			break;
		case "Business Accepted":
			break;
		case "Business Rejceted":
			break;
		case "check id for restaurant sent":
			AddItemController.idflag = (boolean) ((Request) msg).getObj();
			break;
		case "add toppings for item sent":
			AddItemController.toppingsstatus = (String) ((Request) msg).getObj();
			break;
		case "check choices sent":
			RestaurantMenuController.flags = (boolean[]) ((Request) msg).getObj();
			break;
		case "User Allowance":
			BMUserRegController.lblString = (String) ((Request) msg).getObj();
			break;
		case "Business User List":
			HRUserApprovalController.users = (ArrayList<BusinessUser>) ((Request) msg).getObj();
			break;
		case "Waiting Users List":
			BMUserRegController.users = (ArrayList<UserForImport>) ((Request) msg).getObj();
			break;
		case "BusinessUser Allowance":
			HRUserApprovalController.lblString = (String) ((Request) msg).getObj();
			break;
		case "BusinessUser Recjection":
			HRUserApprovalController.lblString = (String) ((Request) msg).getObj();
			break;
		case"order ready time updated":
			break;
		case "Meals Table Sent":
			break;
			
		case "Delivery Details":
			OrderPreviewController.orderid=(int)((Request) msg).getObj();
			break;
			
		case "Payment Method Sent":
			break;
		case"Status For User":
			USERHSController.status = (boolean) ((Request) msg).getObj();
			break;
	
			
		case "Private Wallet Sent":
			PaymentDetailsController.PrivateWallet=(Double)((Request) msg).getObj();
			break;
			
		case "Business Wallet Sent":
			
			PaymentDetailsController.BuisnessWalletString=(String)((Request) msg).getObj();
			break;
			
		case "Commons Delivery Sent":
		
			DeliveryDetailsController.CommonNumber=(int)((Request) msg).getObj();
			break;
			
		case "QR Checked":
			PaymentDetailsController.qrcodeflag=(boolean)((Request) msg).getObj();
			break;
			
		case "orders history sent":
			OrdersListController.orderhis=(ArrayList<OrdersHistory>)((Request) msg).getObj();
			
			break;
		case"user info sent":
			if(!arr[1].equals("Wrong id"))
			{
			ViewOrdersController.email=arr[1];
			ViewOrdersController.phone=arr[2];
			}
			else 
			{
				ViewOrdersController.email="@";
				ViewOrdersController.phone="1";
			}
			break;
		case"W4C Checked":
			BMUserRegController.w4cflag = (boolean) ((Request) msg).getObj();
			break;
		case"Order Received":
			OrdersListController.status="order recieved updated";
			break;
		case "BMM restaurants recieved":
			PickReportController.resNameIDlist = (ArrayList<Restaurant>) ((Request) msg).getObj();
			break;
		case "Income total recieved":
			IncomeReportController.total = (int) ((Request) msg).getObj();
			break;
		case "Order reports recieved":
			OrdersReportController.orderArr = (ArrayList<orderReport>) ((Request) msg).getObj();
			break;
		case "Activity reports recieved":
			ActivityReportController.DelayP = (double) ((Request) msg).getObj();
			break;
		case "Quarter orders report recieved":
			CEOReportController.Ordersdata = (ArrayList<ResNameAndOrderNum>) ((Request) msg).getObj();
			break;
		case "Quarter income report recieved":
			CEOReportController.incomedata = (ArrayList<ResNameAndIncome>) ((Request) msg).getObj();
			break;
		case "ceo report written to DB":
			System.out.println("\nCEO report written to DB successfully\n");
			break;
		case "all ceo reports recieved":
			CEOProducedReportsController.reportsArr = (ArrayList<CeoReport>) ((Request) msg).getObj();
			break;
		case "CEOO Blob":
			CEOProducedReportsController.blob = (byte[]) ((Request) msg).getObj();
			break;
		default:
			System.out.println("Illegal Command " + type);
			System.exit(0);
			break;
		}

		// System.out.println(((ArrayList<Order>)msg).get(0).toString());// to check if
		// list arrived from sever

		// clientUI.display(msg);
	}
	// }

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object message) {
		try {

			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);

			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
					System.out.print("stuck");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	public void clientConnected(ConnectionToClient client) throws Exception {
		System.out.println(">Client Connected");
		try {
			String str = client.getInetAddress().getLocalHost().toString() + "//"
					+ client.getInetAddress().getLocalHost().getHostName().toString() + "//Connected";
			sendToServer(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clientDisConnected(ConnectionToClient client) throws Exception {
		System.out.println(">Client DisConnected");
		try {
			String str = client.getInetAddress().getLocalHost().toString() + "//"
					+ client.getInetAddress().getLocalHost().getHostName().toString() + "//DisConnected";
			sendToServer(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
