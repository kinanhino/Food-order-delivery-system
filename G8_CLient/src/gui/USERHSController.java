package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Request;
import logic.User;

public class USERHSController extends Application implements Initializable {
	static User user;
	public static boolean status;

	@FXML
	private Button NewOrder;

	@FXML
	private Label labelhello;

	@FXML
	private Button Orders;

	@FXML
	private Button Logout;

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it log out the
	 *         user and returns to the log in page output: null
	 */
	@FXML
	void LogOut(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("Log out", user));
		((Node) event.getSource()).getScene().getWindow().hide();
		LoginScreenController login = new LoginScreenController();
		Stage primaryStage = new Stage();
		login.start(primaryStage);

	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it starts the
	 *         process of new order by opening new order page output: null
	 */
	@FXML
	void StartOrder(ActionEvent event) throws Exception {
		System.out.println("1");
		System.out.println(user.getW4c());
		((Node) event.getSource()).getScene().getWindow().hide();
		W4CController WC = new W4CController();
		Stage primaryStage = new Stage();
		WC.start(primaryStage);

	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it opens the
	 *         page of the history of orders for the user output: null
	 */
	@FXML
	void ViewOrdersList(ActionEvent event) throws Exception {

		ConnectFormController.chat.accept(new Request("Get Order History", user.getId()));
		((Node) event.getSource()).getScene().getWindow().hide();
		OrdersListController OHS = new OrdersListController();
		Stage primaryStage = new Stage();
		OHS.start(primaryStage);

	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/USERHS.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e -> e.consume());
		primaryStage.setTitle("User");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares a
	 *         welcome message for the Costumer and sets the new order button
	 *         disable for frozen users output: null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		labelhello.setText("Hello " + user.getFname() + " " + user.getLname());
		ConnectFormController.chat.accept(new Request("Get Status For User", user));
		NewOrder.setDisable(!status);

	}
}
