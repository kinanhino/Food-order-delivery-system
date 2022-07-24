package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Item;
import logic.OrderView;
import logic.OrdersHistory;
import logic.Request;

public class OrdersListController extends Application implements Initializable {

	public static ObservableList<OrdersHistory> OrderHis = FXCollections.observableArrayList();
	public static ArrayList<OrdersHistory> orderhis = new ArrayList<>();
	Date currentDate = new Date();
	SimpleDateFormat clockFormat = new SimpleDateFormat("hh:mm");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	String Receivedtime = dateFormat.format(currentDate);
	public static String status;
	@FXML
	private TableView<OrdersHistory> History;

	@FXML
	private TableColumn<OrdersHistory, String> DateCol;

	@FXML
	private TableColumn<OrdersHistory, String> RestNameCol;

	@FXML
	private TableColumn<OrdersHistory, String> TypeCol;

	@FXML
	private TableColumn<OrdersHistory, String> PCol;

	@FXML
	private Button BackBtn;

	@FXML
	private Button Received;

	@FXML
	private Button OrderInfo;

	@FXML
	private Label info;

	@FXML
	private Label err;

	/**
	 * * @author ayal input: ActionEvent instance functionality: it hides the page
	 * and return to previous screen output: null
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void Back(ActionEvent event) throws Exception {

		((Node) event.getSource()).getScene().getWindow().hide();
		USERHSController Us = new USERHSController();
		Stage primaryStage = new Stage();
		Us.start(primaryStage);

	}

	/**
	 * input:MouseEvent functionality: get the selected item from the table
	 * output:null
	 * 
	 * @param event
	 */
	@FXML
	void GetSeletedItem(MouseEvent event) {

		OrdersHistory order = History.getSelectionModel().getSelectedItem();

		info.setText("Date: " + order.getDate() + ",Restaurant: " + order.getRestaurant() + ",Transaction Type: "
				+ order.getTransactiontype() + "Price:" + order.getPrice());

	}

	/**
	 * send to server that order received
	 * 
	 * @param event
	 */
	@FXML
	void Receivedbtn(ActionEvent event) {

		OrdersHistory order = History.getSelectionModel().getSelectedItem();

		if (info.getText().isEmpty()) {
			err.setText("You Must Choose Order");
		} else {
			ConnectFormController.chat.accept(new Request("Order Received	" + order.getOrderid(), Receivedtime));
			info.setText(status);
		}

	}

	@FXML
	void ShowOrderInfo(ActionEvent event) {

	}

	/**
	 * Initialize the table and view the order history
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		OrderHis = FXCollections.observableArrayList(orderhis);
		DateCol.setCellValueFactory(new PropertyValueFactory<OrdersHistory, String>("Date"));
		RestNameCol.setCellValueFactory(new PropertyValueFactory<OrdersHistory, String>("Restaurant"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<OrdersHistory, String>("Transactiontype"));
		PCol.setCellValueFactory(new PropertyValueFactory<OrdersHistory, String>("Price"));

		History.setItems(OrderHis);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/OrdersList.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Orders History");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
