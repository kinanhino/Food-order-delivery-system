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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Toppings;

public class AddToppingMsgController extends Application implements Initializable  {

    @FXML
    private Label label;

    @FXML
    void no(ActionEvent event) throws Exception {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	
    }

    @FXML
    void yes(ActionEvent event) throws Exception {
    	AddItemController.toppings.add(new Toppings(AddItemController.toppingname,Double.parseDouble(AddItemController.toppingprice)));
    	((Node) event.getSource()).getScene().getWindow().hide();
    
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		label.setText("Are you sure you want to add a toping "+AddItemController.toppingname+" with price "+AddItemController.toppingprice+" ?");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/AddToppingMsg.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Warning");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
