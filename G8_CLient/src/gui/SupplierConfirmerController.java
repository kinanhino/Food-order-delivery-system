package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Request;
import logic.User;

public class SupplierConfirmerController extends Application implements Initializable {
	static User user;

	public static String resturantName;

	public static int resturantID;

	@FXML
	private Button orderlistbutton;

	@FXML
	private Label labelhello;

	@FXML
	private Label labelsupplierfor;

	@FXML
	private Label managername;

	
	
	/**
	 * @author salmanamer input: ActionEvent instance functionality: it opens the orders list page  output: null
	 */
	@FXML
	void orderListbtn(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("view Ready orders for restaurant", resturantID));
		ConnectFormController.chat.accept(new Request("view working orders for restaurant", null));
		((Node) event.getSource()).getScene().getWindow().hide();
		ViewOrdersController vo = new ViewOrdersController();
		Stage primaryStage = new Stage();
		vo.start(primaryStage);
	}
	/**
	 * @author salmanamer input: ActionEvent instance functionality: it log out the user and returns to the log in page 
	 *  output: null
	 */
	@FXML
	void LogOut(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("Log out", user));
		((Node) event.getSource()).getScene().getWindow().hide();
		LoginScreenController login = new LoginScreenController();
		Stage primaryStage = new Stage();
		login.start(primaryStage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/Supplier-Confirmer.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("Supplier-Confirmer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares a welcome message for the Supplier
	 *  output: null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		labelhello.setText("Hello " + user.getFname() + " " + user.getLname());
		labelsupplierfor.setText("Order Confirmer of " + resturantName);
	}

}
