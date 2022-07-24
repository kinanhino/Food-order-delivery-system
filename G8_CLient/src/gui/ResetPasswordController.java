package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Request;
import logic.User;

public class ResetPasswordController extends Application {
public static String username;

    @FXML
    private Button btnreset;
    @FXML
    private Button btnback;
    @FXML
    private Label showlabel;

    @FXML
    private TextField txtpass;

    @FXML
    private TextField txtpass1;

    @FXML
    void Resetpassword(ActionEvent event) {
if(txtpass.getText().isEmpty()||txtpass1.getText().isEmpty())
	showlabel.setText("you have to fill all fields");
else if (!txtpass.getText().equals(txtpass1.getText()))
	showlabel.setText("The passwords do not match!!");
else 
	{
	ConnectFormController.chat.accept(new Request("reset password",new User(username,txtpass1.getText())));
    showlabel.setText("Password Updated Succefully");
	}
    }
    @FXML
    void getback(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide();
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/ResetPassword.fxml"));
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Reset Password");
		primaryStage.setScene(scene);		
		primaryStage.show();
		
	}

}
