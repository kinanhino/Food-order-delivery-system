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

public class BMScreenController extends Application implements Initializable {
	public static String loc;
	static User user;
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private Button logout;

	@FXML
	private Button EditMenu;

	@FXML
	private Button sendreport;

	@FXML
	private Button ViewReports;

	@FXML
	private Label labelhello;

	@FXML
	private Button RegAcc;

	@FXML
	private Button EditUser;

	@FXML
	private Button neworder;

	@FXML
	private Button ConfirmEmp;

	
	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares and opens the Edit User page
	 *  output: null
	 */
	@FXML
	void EditUser(ActionEvent event) throws Exception {
		
		ConnectFormController.chat.accept(new Request("get users status", loc));
		((Node) event.getSource()).getScene().getWindow().hide();
		EditUserController eu = new EditUserController();
		Stage primaryStage = new Stage();
		eu.start(primaryStage);
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares and opens the Business Confirmation page
	 *  output: null
	 */
	@FXML
	void EmpConfirmation(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		BusinessConfirmationController b = new BusinessConfirmationController();
		b.start(primaryStage);
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

	@FXML
	void OpenMenu(ActionEvent event) {

	}

	@FXML
	void ReportList(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/PickReport.fxml"), resources);
			Stage stage = new Stage();
			stage.setTitle("View Reports");
			stage.setScene(new Scene(root, 750, 500));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void SendReport(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/ReadyReports.fxml"), resources);
			Stage stage = new Stage();
			stage.setTitle("Ready Reports");
			stage.setScene(new Scene(root, 750, 550));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares and opens the User Registration page
	 *  output: null
	 */
	@FXML
	void UserReg(ActionEvent event) throws Exception {
		BMUserRegController screen = new BMUserRegController();
		Stage primaryStage = new Stage();
		screen.start(primaryStage);
	}

//	@FXML
//	void RegSupplier(ActionEvent event) throws Exception {
//		RegisterSupplierController reg = new RegisterSupplierController();
//		Stage primaryStage = new Stage();
//		reg.start(primaryStage);
//	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/BMScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("BM");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @author salmanamer input: URL location, ResourceBundle resources
	 *  functionality: it prepares  the welcome message and saves the location in loc variable
	 *  output: null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelhello.setText("Hello " + user.getFname() + " " + user.getLname() +" "+user.getLocation());
		loc=user.getLocation();

	}
}
