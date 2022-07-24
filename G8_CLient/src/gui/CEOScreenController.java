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

public class CEOScreenController extends Application implements Initializable {
	static User user;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Button ViewReport;

	@FXML
	private Button ViewPR;

	@FXML
	private Label labelhello;

	@FXML
	private Button AskForReports;

	@FXML
	private Button RegEmployer;

	@FXML
	private Button RegisterSupplier;

	@FXML
	private Button ViewUsers;

	@FXML
	private Button ApproveAcc;

	@FXML
	private Button ViewMenu;

	@FXML
	void OpenAccountsList(ActionEvent event) {

	}

	@FXML
	void OpenPR(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/CEOProducedReports.fxml"), resources);
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 800, 550));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void OpenRegisterEPage(ActionEvent event) {

	}

	@FXML
	void OpenRegisterSPage(ActionEvent event) {

	}

	@FXML
	void ReportPage(ActionEvent event) {
		// Histogram for orders and income for all restaurants
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/CEOReport.fxml"), resources);
			Stage stage = new Stage();
			stage.setTitle("Histogramt");
			stage.setScene(new Scene(root, 1000, 580));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void ReqReport(ActionEvent event) {

	}

	@FXML
	void UsersList(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("view users list", null));
		ViewUsersController vuc = new ViewUsersController();
		Stage primaryStage = new Stage();
		vuc.start(primaryStage);

	}

	@FXML
	void LogOut(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("Log out", user));
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		LoginScreenController login = new LoginScreenController();
		login.start(primaryStage);

	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/CEOScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e -> e.consume());
		primaryStage.setTitle("CEO");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		labelhello.setText("Hello " + user.getFname() + " " + user.getLname());
	}

}
