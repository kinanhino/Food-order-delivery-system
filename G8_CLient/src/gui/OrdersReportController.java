package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Request;
import logic.orderReport;

/**
 * @author Ibraheem Orders Report screen controller Prepare specific branch
 *         orders report
 * @param orderArr  : array list that holds the data for all orders (Grouped by
 *                  Itemtype)
 * @param reportsOL : an observable array to inject the data into the table view
 */
public class OrdersReportController implements Initializable {

	public static ArrayList<orderReport> orderArr = new ArrayList<>();
	protected ObservableList<orderReport> reportsOL = null;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label restaurantNameLBL;

	@FXML
	private Label monthLBL;

	@FXML
	private Button exportBTN;

	@FXML
	private Button returnBTN;

	@FXML
	private TableView<orderReport> orderTBLVIEW;

	@FXML
	private TableColumn<?, ?> itemType;

	@FXML
	private TableColumn<?, ?> quantity;

	/**
	 * Save report to DB as a BLOB
	 * 
	 * @param event
	 */
	@FXML
	void exportOrderReport(ActionEvent event) {

	}

	/**
	 * Return to "Pick Report" screen
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void returnToPickReport(ActionEvent event) {
		PickReportController.currMonth = 0;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/PickReport.fxml"), resources);
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 750, 500));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Ibraheem Get orders report data from DB through a server request
	 *         Inject that data to the observable list & then to table view
	 * @param PickReportController.chosenResID : The restaurant to show report for
	 * @param currMonth                        : selected month to show report for
	 */
	protected void getOrdersReport() {
		// TODO: fetch orders report for this branch manager from server
		if (reportsOL != null)
			reportsOL.clear();
		if (orderArr != null)
			orderArr.clear();

		Object[] objs = { PickReportController.chosenResID, PickReportController.currMonth + 1 };
		ConnectFormController.chat.accept(new Request("get ordersReport", objs));

		reportsOL = FXCollections.observableArrayList(orderArr);
		itemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		orderTBLVIEW.setItems(reportsOL);
	}

	/**
	 * Show view relevant to next selected month Reloads report data
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void goToNextMonth(ActionEvent event) {
		if (PickReportController.currMonth >= 11)
			PickReportController.currMonth = -1;
		monthLBL.setText(PickReportController.months[++PickReportController.currMonth]);
		getOrdersReport();
	}

	/**
	 * Show view relevant to previous selected month Reloads report data
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void goToPrevMonth(ActionEvent event) {
		if (PickReportController.currMonth < 1)
			PickReportController.currMonth = 12;
		monthLBL.setText(PickReportController.months[--PickReportController.currMonth]);
		getOrdersReport();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Inject data into table view
		getOrdersReport();

		// Set label to the chosen restaurant
		restaurantNameLBL.setText("for " + PickReportController.chosenRes + " restaurant");

		assert restaurantNameLBL != null
				: "fx:id=\"restaurantNameLBL\" was not injected: check your FXML file 'OrdersReport.fxml'.";
		assert exportBTN != null : "fx:id=\"exportBTN\" was not injected: check your FXML file 'OrdersReport.fxml'.";
		assert returnBTN != null : "fx:id=\"returnBTN\" was not injected: check your FXML file 'OrdersReport.fxml'.";
		assert orderTBLVIEW != null
				: "fx:id=\"orderTBLVIEW\" was not injected: check your FXML file 'OrdersReport.fxml'.";
		assert itemType != null : "fx:id=\"itemType\" was not injected: check your FXML file 'OrdersReport.fxml'.";
		assert quantity != null : "fx:id=\"quantity\" was not injected: check your FXML file 'OrdersReport.fxml'.";
	}
}
