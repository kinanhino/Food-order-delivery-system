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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Request;
import logic.User;

public class EmailVerificationController extends Application {
private boolean flag=false;
	@FXML
	private Button btnverify;
public static String email;
	@FXML
	private Label showlabel;
	@FXML
	private Label showlabel1;
	@FXML
	private TextField txtcode;
	@FXML
	private TextField txtusername;
	
/**
 * this method is a simulation of verification a code sent to the user -we put a constant code  "1111"
 * and when the code is verified the user can change the password*/
	@FXML
	void VerifyCode(ActionEvent event) throws Exception {
		if (txtcode.getText().isEmpty())
			showlabel1.setText("you have to enter code first");
		else if (!txtcode.getText().equals("1111"))
			showlabel1.setText("Wrong code !!!");

		else {
			if(flag)
			{
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			ResetPasswordController rp=new ResetPasswordController();
			Stage primarystage=new Stage();
			rp.start(primarystage);
		}else showlabel1.setText("You have to enter a username first");
	}
		}
/**
 * this method takes the username serach in database using it , find email and phone number to make the simulation  */
	@FXML
	void sendcode(ActionEvent event) {
		if (txtusername.getText().isEmpty())
			showlabel.setText("Please Enter your username first");
		else {
			ConnectFormController.chat.accept(new Request("get user",new User(txtusername.getText(),null)));
			if(email.equals("Username Not Found"))showlabel.setText(email);
			else showlabel.setText(
					"a code has sent to the Email " +email + " verify the code to reset your password");
			flag=true;
			ResetPasswordController.username=txtusername.getText();
		}
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/EmailVerification.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("Email Verification");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
