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

public class SupplierEditorController extends Application implements Initializable {
	static User user;
	public static String resturantName;
	public static int resturantID;

	@FXML
	private Button btnlogout;

	@FXML
	private Button editmenubutton;

	@FXML
	private Label labelhello;

	@FXML
	private Label labelsupplierfor;

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it opens the
	 *         edit menu page to edit the menu output: null
	 */
	@FXML
	void EditMenuBtn(ActionEvent event) throws Exception {
		RestaurantMenuController.ResName = resturantName;
		ConnectFormController.chat.accept(new Request("show items in restaurant", resturantID));
		((Node) event.getSource()).getScene().getWindow().hide();
		RestaurantMenuController rm = new RestaurantMenuController();
		Stage primaryStage = new Stage();
		rm.start(primaryStage);
	}

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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/Supplier-Editor.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e -> e.consume());
		primaryStage.setTitle("Supplier-Editor");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares a
	 *         welcome message for the Supplier output: null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		labelhello.setText("Hello " + user.getFname() + " " + user.getLname());
		labelsupplierfor.setText("Menu Editor of " + resturantName);
	}

}
