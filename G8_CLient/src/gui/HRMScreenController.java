package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Business;
import logic.Request;
import logic.User;

public class HRMScreenController extends Application implements Initializable {
	public static User user;
	public static Business business;
	@FXML
	private Button BusinessAcc;

	@FXML
	private Button UserApprove;

	@FXML
	private Label labelhello;

	@FXML
	private Label errlabel;
	@FXML
	private Button logout;

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it log out the
	 *         user and returns to the log in page output: null
	 */
	@FXML
	void LogOut(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("Log out", user));
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		LoginScreenController login = new LoginScreenController();
		login.start(primaryStage);

	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it opens the
	 *         page of the Business account that waits for permission output: null
	 */
	@FXML
	void OpenUsersList(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		HRUserApprovalController hr = new HRUserApprovalController();
		Stage primaryStage = new Stage();
		hr.start(primaryStage);

	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it send a
	 *         request to make the business allowed to have workers accounts output:
	 *         null
	 */
	@FXML
	void AskForBusAcc(ActionEvent event) {
		ConnectFormController.chat.accept(new Request("GetHRBussiness", user));
		if (business.isAllowed().equals("NotAllowed")) {
			errlabel.setText("Your Business is not allowed ask the BM manager for premission");
			ConnectFormController.chat.accept(new Request("AskForBusAcc", business));
			UserApprove.setDisable(true);
		} else if (business.isAllowed().equals("Allowed")) {
			errlabel.setText("Your Business is already allowed");
			UserApprove.setDisable(false);
		} else if (business.isAllowed().equals("Waiting"))
			errlabel.setText("Please wait for BM manager to give you premission");

	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		Pane root = loader.load(getClass().getResource("/gui/HRMScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e -> e.consume());
		primaryStage.setTitle("HR");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it prepares the
	 *         welcome message and set the button of user approval disable if the
	 *         business was not allowed output: null
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelhello.setText("Hello " + user.getFname() + " " + user.getLname());
		if (business.isAllowed().equals("Allowed"))
			UserApprove.setDisable(false);
		else
			UserApprove.setDisable(true);
		System.out.print(business.getId());

	}
}
