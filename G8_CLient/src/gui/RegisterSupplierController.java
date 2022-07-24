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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Request;
import logic.User;

public class RegisterSupplierController extends Application implements Initializable {

	public static String lbl;
	@FXML
	private Label Errlabel;

	@FXML
	private TextField email;

	@FXML
	private TextField fname;

	@FXML
	private TextField lname;

	@FXML
	private TextField pass;

	@FXML
	private TextField phone;

	@FXML
	private TextField repass;

	@FXML
	private TextField resid;

	@FXML
	private TextField resname;

	@FXML
	private TextField userid;

	@FXML
	private TextField username;

	@FXML
	private TextField w4c;

	@FXML
	void CancelBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	void SaveBtn(ActionEvent event) {
		if (email.getText().isEmpty() || fname.getText().isEmpty() || lname.getText().isEmpty()
				|| phone.getText().isEmpty() || username.getText().isEmpty() || pass.getText().isEmpty()
				|| repass.getText().isEmpty() || resid.getText().isEmpty() || resname.getText().isEmpty()
				|| w4c.getText().isEmpty()) {
			Errlabel.setText("Please fill all fields");
		} else if (!pass.getText().equals(repass.getText()))
			Errlabel.setText("The Password do not match");
		else {
			User u = new User(username.getText(), repass.getText());
			u.setEmail(email.getText());
			u.setFname(fname.getText());
			u.setId(Integer.parseInt(userid.getText()));
			u.setPhone(phone.getText());
			u.setLname(lname.getText());
			u.setRole("Supplier");
			u.setW4c(w4c.getText());
			ConnectFormController.chat.accept(new Request("Add user	"+resid.getText(), u));
			if (!lbl.equals("User is already exist")) {
				ConnectFormController.chat.accept(
						new Request("Add Restaurant", u.getId() + "	" + resid.getText() + "	" + resname.getText()));
			}
			Errlabel.setText(lbl);

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/RegisterSupplier.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("New Supplier");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
