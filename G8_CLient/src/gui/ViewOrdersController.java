
package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import logic.Choice;
import logic.ItemForOrder;
import logic.OrderForRestaurant;
import logic.Request;
import java.time.LocalDateTime;

public class ViewOrdersController extends Application implements Initializable {
	public static String status;
	public static ArrayList<OrderForRestaurant> ready;
	public static ArrayList<OrderForRestaurant> working;
	public static ArrayList<ItemForOrder> items;
	public static ObservableList<OrderForRestaurant> listready;
	public static ObservableList<OrderForRestaurant> listworking;
	public static ObservableList<ItemForOrder> listitem;
	public static String email;
	public static String phone;
	@FXML
	private TableColumn<ItemForOrder, String> coldeg;

	@FXML
	private Line line;

	@FXML
	private TableColumn<OrderForRestaurant, String> coldelivery1;

	@FXML
	private TableColumn<OrderForRestaurant, String> coldelivery2;

	@FXML
	private TableColumn<OrderForRestaurant, Integer> colid1;

	@FXML
	private TableColumn<OrderForRestaurant, Integer> colid2;

	@FXML
	private TableColumn<ItemForOrder, String> colname;

	@FXML
	private TableColumn<ItemForOrder, Integer> colnum;

	@FXML
	private TableColumn<ItemForOrder, String> colother;

	@FXML
	private TableColumn<OrderForRestaurant, Double> colprice1;

	@FXML
	private TableColumn<OrderForRestaurant, Double> colprice2;

	@FXML
	private TableColumn<ItemForOrder, String> colsize;

	@FXML
	private TableColumn<OrderForRestaurant, Timestamp> coltime1;

	@FXML
	private TableColumn<OrderForRestaurant, Timestamp> coltime2;

	@FXML
	private Label labelitem;

	@FXML
	private Label labelitems;

	@FXML
	private Label labelres;

	@FXML
	private TableView<ItemForOrder> tableitems;

	@FXML
	private TableView<OrderForRestaurant> tableorders1;

	@FXML
	private TableView<OrderForRestaurant> tableorders2;

	private OrderForRestaurant readyOrder;
	private OrderForRestaurant workingOrder;
	private ItemForOrder item;

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		SupplierConfirmerController rm = new SupplierConfirmerController();
		Stage primaryStage = new Stage();
		rm.start(primaryStage);
	}

	@FXML
	void FinishOrder(ActionEvent event) {
		if (this.workingOrder == null)
			labelitem.setText("you have to choose an order to finish");
		else {
			ConnectFormController.chat.accept(new Request("set ready time for order	"
					+ SupplierConfirmerController.resturantID + "	" + workingOrder.getId(), LocalDateTime.now()));
			ConnectFormController.chat.accept(new Request("delete order", workingOrder));
			labelitem.setText(status);
			ConnectFormController.chat
					.accept(new Request("view Ready orders for restaurant", SupplierConfirmerController.resturantID));
			ConnectFormController.chat.accept(new Request("view working orders for restaurant", null));
			LoadDataForReadyOrders();
			LoadDataForWorkingOrders();
			ConnectFormController.chat.accept(new Request("get user info",workingOrder.getId()));
			MessageBox.DisplayMessage("an email has sent to \"+email+\" and SMS to \"+phone", "SIMULATION", "", AlertType.INFORMATION);
		}

	}

	@FXML
	void StartWorkingOnOrder(ActionEvent event) {
		if (this.readyOrder == null)
			labelitem.setText("you have to choose an order to start working on");
		else {
			ConnectFormController.chat.accept(new Request("set order as working	", readyOrder));
			labelitem.setText(status);
			if (status.equals("set as working Success")) {
				ConnectFormController.chat.accept(
						new Request("view Ready orders for restaurant", SupplierConfirmerController.resturantID));
				ConnectFormController.chat.accept(new Request("view working orders for restaurant", null));
				LoadDataForReadyOrders();
				LoadDataForWorkingOrders();
			}
		}
	}

	@FXML
	void getItem(MouseEvent event) {
		this.item = tableitems.getSelectionModel().getSelectedItem();
	}

	@FXML
	void getReadyOrder(MouseEvent event) {
		this.readyOrder = tableorders1.getSelectionModel().getSelectedItem();
		ConnectFormController.chat.accept(
				new Request("show items in order	" + SupplierConfirmerController.resturantID, readyOrder.getId()));

		tableitems.setVisible(true);
		labelitems.setVisible(true);
		labelitem.setVisible(true);
		if (items == null)
			labelitem.setText("items is null");
		else
			LoadDataForItems();
	}

	@FXML
	void getWorkingOrder(MouseEvent event) {
		this.workingOrder = tableorders2.getSelectionModel().getSelectedItem();
		if (workingOrder == null)
			labelitem.setText("working order is nul");
		else {

			ConnectFormController.chat.accept(new Request(
					"show items in order	" + SupplierConfirmerController.resturantID, workingOrder.getId()));
			tableitems.setVisible(true);
			labelitems.setVisible(true);
			labelitem.setVisible(true);
			if (items == null)
				labelitem.setText("items is null");
			else
				LoadDataForItems();

		}
	}

	public void LoadDataForItems() {

		listitem = FXCollections.observableArrayList(items);
		colname.setCellValueFactory(new PropertyValueFactory<ItemForOrder, String>("name"));
		colsize.setCellValueFactory(new PropertyValueFactory<ItemForOrder, String>("size"));
		coldeg.setCellValueFactory(new PropertyValueFactory<ItemForOrder, String>("degree"));
		colother.setCellValueFactory(new PropertyValueFactory<ItemForOrder, String>("other"));
		colnum.setCellValueFactory(new PropertyValueFactory<ItemForOrder, Integer>("quantity"));
		tableitems.setItems(listitem);
	}

	public void LoadDataForReadyOrders() {
		if (ready != null) {
			listready = FXCollections.observableArrayList(ready);
			colid1.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, Integer>("id"));
			colprice1.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, Double>("price"));
			coldelivery1.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, String>("deliveryType"));
			coltime1.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, Timestamp>("ordertime"));
			tableorders1.setItems(listready);
		}
	}

	public void LoadDataForWorkingOrders() {

		listworking = FXCollections.observableArrayList(working);
		colid2.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, Integer>("id"));
		colprice2.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, Double>("price"));
		coldelivery2.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, String>("deliveryType"));
		coltime2.setCellValueFactory(new PropertyValueFactory<OrderForRestaurant, Timestamp>("timestr"));
		tableorders2.setItems(listworking);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/ViewOrders.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("ORDERS");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoadDataForReadyOrders();
		LoadDataForWorkingOrders();
		labelres.setText("orders for restaurant " + SupplierConfirmerController.resturantName);
		tableitems.setVisible(false);
		labelitems.setVisible(false);
		labelitem.setVisible(false);
		status = "";
	}

}
