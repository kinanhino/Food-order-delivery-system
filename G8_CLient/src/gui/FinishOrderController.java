package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FinishOrderController {

    @FXML
    private Button HS;

    @FXML
    void HSBtn(ActionEvent event) throws Exception {
  
    	
    	ResMenuForCustomersController.respage.close();
    	DeliveryDetailsController.DeliveryDetailsScreen.close();
    	PaymentDetailsController.payPage.close();
    	
      	
    	((Node) event.getSource()).getScene().getWindow().hide();
		USERHSController Us = new USERHSController();
		Stage primaryStage = new Stage();
		Us.start(primaryStage);

    }

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		 FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/FinishOrder.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setOnCloseRequest(e->e.consume());
			primaryStage.setTitle("Start a new order");
			primaryStage.setScene(scene);		
			primaryStage.show();
		
	}

}
