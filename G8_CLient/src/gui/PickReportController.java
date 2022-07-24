package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Request;
import logic.Restaurant;
import logic.User;

/**
 * @author Ibraheem This class controls "Pick Report screen" Supplier pick
 *         report screen controller Redirects to 3 possible type of reports
 * @param loc           : The branch that the manager is responsible for
 * @param reslist       : list of restaurants the manager is responsible for
 * @param resNameIDlist : list of restaurant names & IDs the manager is
 *                      responsible for
 * @param chosenRes     : the name of the restaurant chosen from the list
 * @param chosenResID   :the id of restaurant chosen from the list
 * @param currMonth     : selected month index
 * @param months        : array of months
 */
public class PickReportController extends Application implements Initializable {
	static User user;
	public static String loc;
	public static ArrayList<String> reslist = new ArrayList<>();
	public static ArrayList<Restaurant> resNameIDlist = new ArrayList<>();
	public static String chosenRes;
	public static int chosenResID;
	public static int currMonth = 0;
	public static String[] months = { "Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov",
			"Dec" };

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label branchLBL;

	@FXML
	private Button incomeReportBTN;

	@FXML
	private Button orderReportBTN;

	@FXML
	private Button activityReportBTN;

	@FXML
	private Button returnBTN;

	@FXML
	private ComboBox<String> restaurantCMBX;

	/**
	 * Get the chosenResID every time a retaurant is chosen
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void restaurantChosen(ActionEvent event) {
		chosenRes = (String) restaurantCMBX.getSelectionModel().getSelectedItem();
		for (Restaurant rest : resNameIDlist)
			if (rest.getRestaurantName() == chosenRes)
				chosenResID = rest.getRestaurantID();
		restaurantCMBX.setStyle(
				"-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, lightgreen;");
	}

	/**
	 * Return to "BM screen" screen
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void reurnBTN(ActionEvent event) {
		chosenRes = null;
		currMonth = 0;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/BMScreen.fxml"), resources);
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 1200, 700));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show Income Report screen for the chosen restaurant
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void showIncomeReport(ActionEvent event) {
		// Validate that a restaurant is selected
		if (chosenRes != null) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/IncomeReport.fxml"), resources);
				Stage stage = new Stage();
				stage.setTitle("Income Report");
				stage.setScene(new Scene(root, 780, 630));
				stage.show();
				// Hide this current window (if this is what you want)
				((Node) (event.getSource())).getScene().getWindow().hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			restaurantCMBX.setStyle(
					"-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, red;");
		}
	}

	/**
	 * Show Orders Report screen for the chosen restaurant
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void showOrdersReport(ActionEvent event) {
		// Validate that a restaurant is selected
		if (chosenRes != null) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/OrdersReport.fxml"), resources);
				Stage stage = new Stage();
				stage.setTitle("Orders Report");
				stage.setScene(new Scene(root, 770, 550));
				stage.show();
				// Hide this current window (if this is what you want)
				((Node) (event.getSource())).getScene().getWindow().hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			restaurantCMBX.setStyle(
					"-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, red;");
		}
	}

	/**
	 * Show Activity Report screen for the chosen restaurant
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void showActivityReport(ActionEvent event) {
		// Validate that a restaurant is selected
		if (chosenRes != null) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/ActivityReport.fxml"), resources);
				Stage stage = new Stage();
				stage.setTitle("Activity Report");
				stage.setScene(new Scene(root, 700, 600));
				stage.show();
				((Node) (event.getSource())).getScene().getWindow().hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			restaurantCMBX.setStyle(
					"-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, red;");
		}
	}

	/**
	 * @author Ibraheem Get the branch location that the manager is responsible for
	 * @param loc : The branch that the manager is responsible for
	 */
	protected void setBranch() {
		switch (loc) {
		case "North":
			branchLBL.setText("for Northern branch");
			break;
		case "South":
			branchLBL.setText("for Southern branch");
			break;
		case "Center":
			branchLBL.setText("for Center branch");
			break;
		default:
			branchLBL.setText("Unknown branch");
			break;
		}
	}

	/**
	 * @author Ibraheem Get the list of restaurants the manager is responsible for
	 */
	protected void getRestaurants() {
		if (reslist != null)
			reslist.clear();

		ConnectFormController.chat.accept(new Request("get BMMRestaurants", loc));
		for (Restaurant rest : resNameIDlist)
			reslist.add(rest.getRestaurantName());

		restaurantCMBX.setItems(FXCollections.observableArrayList(reslist));
	}

	@FXML
	void initialize() {

		// Set branchLBL
		setBranch();

		// Get restaurants
		getRestaurants();

		if (chosenRes != null)
			restaurantCMBX.getSelectionModel().select(chosenRes);

		assert incomeReportBTN != null
				: "fx:id=\"incomeReportBTN\" was not injected: check your FXML file 'PickReport.fxml'.";
		assert orderReportBTN != null
				: "fx:id=\"orderReportBTN\" was not injected: check your FXML file 'PickReport.fxml'.";
		assert activityReportBTN != null
				: "fx:id=\"activityReportBTN\" was not injected: check your FXML file 'PickReport.fxml'.";
		assert returnBTN != null : "fx:id=\"returnBTN\" was not injected: check your FXML file 'PickReport.fxml'.";
		assert restaurantCMBX != null
				: "fx:id=\"restaurantCMBX\" was not injected: check your FXML file 'PickReport.fxml'.";

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loc=user.getLocation();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
