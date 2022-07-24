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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class W4CController extends Application implements Initializable {

    @FXML
    private AnchorPane w4cpage;

    @FXML
    private Text w4ctext;

    @FXML
    private ImageView qrcode;

    @FXML
    private ImageView bitemeicon;

    @FXML
    private Text w4centertext;

    @FXML
    private Button continuebutton;

    @FXML
    private Button backbutton;

    @FXML
    private Label ErrorLabel;

    @FXML
    private TextField w4ctextfield;

    
    
    /**
	 * @author ayal
	 *  input: ActionEvent instance 
	 *  functionality: it hides the page and return to user home screen 
	 *  output: null
	 */

    @FXML
    void BackBtn(ActionEvent event) throws Exception {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	USERHSController rm = new USERHSController();
		Stage primaryStage = new Stage();
		rm.start(primaryStage);
    	

    }

    
    
    /**
	 * @author ayal
	 *  input: ActionEvent instance 
	 *  functionality: check if the w4c is return to user home screen 
	 *  output: null
	 */

    @FXML
    void ContinueBtn(ActionEvent event) throws Exception {
    	
    	if (w4ctextfield.getText().equals(USERHSController.user.getW4c())) {
    		
    		((Node) event.getSource()).getScene().getWindow().hide();
    		NewOrderController NO = new NewOrderController();
    	    Stage primaryStage = new Stage();
    		NO.start(primaryStage);
    	
    	}
    	else 
    		ErrorLabel.setText("Wrong W4C");
    	
    	
   
		
    }

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//w4ctextfield.setText(USERHSController.user.getW4c());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/W4C.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("User");
		primaryStage.setScene(scene);
		primaryStage.show();
	

	}

}
