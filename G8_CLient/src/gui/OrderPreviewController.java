package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Delivery;
import logic.Item;
import logic.OrderView;
import logic.Payment;
import logic.Request;

public class OrderPreviewController extends Application implements Initializable {
	
	
    public static int orderid;
	public static Stage OrderPreviewPage;

    @FXML
    private AnchorPane heresyourorder;

    @FXML
    private TextField HereisYourOrder;

    @FXML
    private TableView<OrderView> MealsTable2;


    @FXML
    private TableColumn<OrderView, String> MealNameCol2;

    @FXML
    private TableColumn<OrderView, Integer> QuantityCol2;

    @FXML
    private TableColumn<OrderView,Double> PriceCol2;
   
    @FXML
    private TableColumn<OrderView,String> LevelOfCoockingCol2;
    
    @FXML
    private TableColumn<OrderView,String> Sizecol2;
    
    @FXML
    private TableColumn<OrderView,String> TopingsCol2;

    @FXML
    private TableColumn<OrderView,String> DescriptionCol2;
    
    @FXML
    private Button BackButton;

    @FXML
    private Button ContinueButton;
    
    @FXML
    private TableView<Delivery> DeliveryTable;

    @FXML
    private TableColumn<Delivery,String> RestaurantNameCol;

    @FXML
    private TableColumn<Delivery,String> OrderTypeCol;

    @FXML
    private TableColumn<Delivery,String> DeliveryWayCol;

    @FXML
    private TableColumn<Delivery,String> DeliverTypeCol;

    @FXML
    private TableColumn<Delivery,String> ExpectedTimeCol;

    @FXML
    private TableColumn<Delivery,String> ExpectedArrivalTimeCol;

    @FXML
    private TableView<Payment> PaymentTable;

    @FXML
    private TableColumn<Payment,String> AccountTypeCpl;

    @FXML
    private TableColumn<Payment,String> DeliveryCostCol;

    @FXML
    private TableColumn<Payment,String> OrderPriceCol;

    @FXML
    private TableColumn<Payment,String> DiscountsCol;

    @FXML
    private TableColumn<Payment,String> FinalPriceCol;

    
    /**
	 * @author ayal input: ActionEvent instance functionality: it hides the page and
	 *         return to the previous page output: null
	 */
    
    @FXML
    void backbtn(ActionEvent event) throws Exception {
    	
    	
    ((Node) event.getSource()).getScene().getWindow().hide();	
    PaymentDetailsController.payPage.show();

    }
    
    /**
     * input: ActionEvent
     * functionality:Continue Button,approve the order and 
     * output: null
     * @param event
     * @throws Exception
     */

    @FXML
    void confirmandcontinuebtn(ActionEvent event) throws Exception {
    	
    	
    	ConnectFormController.chat.accept(new Request("Delivery Details	"+NewOrderController.resID+"	"+USERHSController.user.getId()+"	"+PaymentDetailsController.paymeth.getFinalPrice(),DeliveryDetailsController.Deliveryinfo.get(0)));
    	ConnectFormController.chat.accept(new Request("Meals Table	"+NewOrderController.resID+"	"+orderid,ResMenuForCustomersController.CartListAsArrayList));
    	ConnectFormController.chat.accept(new Request("Payment Method	"+USERHSController.user.getId()+"	"+orderid,PaymentDetailsController.paymeth));
    	ResMenuForCustomersController.CartList.clear();
    	((Node) event.getSource()).getScene().getWindow().hide();
		FinishOrderController FOC = new FinishOrderController();
		Stage primaryStage = new Stage();
		
		FOC.start(primaryStage);
    	
    	
    
    	
    }

    
	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loadData();
	
	
	
	}

	
	/**
	 * input: null
	 * functionality:Initializes the time overview tables showing shipping information, account information, selected items
	 * output:null
	 */
	private void loadData() {
		// TODO Auto-generated method stub
		
		

		  MealNameCol2.setCellValueFactory(new PropertyValueFactory<OrderView, String>("ItemName"));
		  PriceCol2.setCellValueFactory(new PropertyValueFactory<OrderView, Double>("Price"));
		  QuantityCol2.setCellValueFactory(new PropertyValueFactory<OrderView, Integer>("Qty"));
		  Sizecol2.setCellValueFactory(new PropertyValueFactory<OrderView, String>("Size"));
		  LevelOfCoockingCol2.setCellValueFactory(new PropertyValueFactory<OrderView, String>("LevelOfCoocking"));
		  TopingsCol2.setCellValueFactory(new PropertyValueFactory<OrderView, String>("Topings"));
		  DescriptionCol2.setCellValueFactory(new PropertyValueFactory<OrderView, String>("Description"));

		  MealsTable2.setItems(ResMenuForCustomersController.CartList);
		  
		  RestaurantNameCol.setCellValueFactory(new PropertyValueFactory<Delivery, String>("RestarauntName"));
		  OrderTypeCol.setCellValueFactory(new PropertyValueFactory<Delivery, String>("OrderType"));
		  DeliveryWayCol.setCellValueFactory(new PropertyValueFactory<Delivery, String>("DeliverWay"));
		  DeliverTypeCol.setCellValueFactory(new PropertyValueFactory<Delivery, String>("DeliveryType"));
		  ExpectedTimeCol.setCellValueFactory(new PropertyValueFactory<Delivery, String>("ExecutedTime"));
		  ExpectedArrivalTimeCol.setCellValueFactory(new PropertyValueFactory<Delivery, String>("ExpectedArrivalTime"));
		 
		  
		  DeliveryTable.setItems(DeliveryDetailsController.Deliveryinfo);
		  
		  
		  AccountTypeCpl.setCellValueFactory(new PropertyValueFactory<Payment,String>("AccountType"));
		  DeliveryCostCol.setCellValueFactory(new PropertyValueFactory<Payment,String>("DeliveryCost"));
		  OrderPriceCol.setCellValueFactory(new PropertyValueFactory<Payment,String>("OrderPrice"));
		  DiscountsCol.setCellValueFactory(new PropertyValueFactory<Payment,String>("Discounts"));
		  FinalPriceCol.setCellValueFactory(new PropertyValueFactory<Payment,String>("FinalPrice"));
		 
		  

		  PaymentTable.setItems(PaymentDetailsController.Payementmethod);
		
	}




	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		FXMLLoader loader = new FXMLLoader();
  
		Pane root = loader.load(getClass().getResource("/gui/OrderPreview.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("OrderPreview");
		primaryStage.setScene(scene);
		primaryStage.show();
		OrderPreviewPage=primaryStage;
		
	}
   
    
}
