package gui;

//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
//import logic.MyFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Request;

/**
 * 
 * @author Ibraheem This class controls the Income Report screen
 * @param total : total income of the restaurant
 * @param tax   : final tax value (total * 0.17)
 */
public class IncomeReportController {

	public static double total, tax = 0.17;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label restaurantNameLBL;

	@FXML
	private Button exportBTN;

	@FXML
	private Button returnBTN;

	@FXML
	private Label totFoodLBL;

	@FXML
	private Label taxLBL;

	@FXML
	private Label totLBL;

	@FXML
	private Label monthLBL;

	@FXML
	private Button prevMonthBTN;

	@FXML
	private Button nextMonthBTN;

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
	 * Show view relevant to next selected month Reloads report data
	 * @param event : default param from javafx
	 */
	@FXML
	void goToNextMonth(ActionEvent event) {
		if (PickReportController.currMonth >= 11)
			PickReportController.currMonth = -1;
		monthLBL.setText(PickReportController.months[++PickReportController.currMonth]);
		fillIncomeReport();
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
		fillIncomeReport();
	}

	/**
	 * Get restaurants total income for the chosen month calculate and display the
	 * total, tax and total after tax
	 * 
	 * @param PickReportController.chosenResID : The restaurant to show report for
	 * @param currMonth                        : selected month to show report for
	 */
	protected void fillIncomeReport() {

		Object[] objs = { PickReportController.chosenResID, PickReportController.currMonth + 1 };
		ConnectFormController.chat.accept(new Request("get incometotal", objs));

		tax = 0.17 * total;

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		totFoodLBL.setText("" + total + " $");
		if (tax == 0)
			taxLBL.setText("" + df.format(tax) + " $");
		else
			taxLBL.setText("-" + df.format(tax) + " $");
		totLBL.setText("" + (total - tax) + " $");
	}

	/**
	 * Save report to DB as a BLOB
	 * 
	 * @param event
	 */
	@FXML
	void exportReport(ActionEvent event) {

//		MyFile msg = new MyFile("");
//		String LocalfilePath = "";
//
//		try {
//			File newFile = new File(LocalfilePath);
//
//			byte[] mybytearray = new byte[(int) newFile.length()];
//			FileInputStream fis = new FileInputStream(newFile);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//
//			msg.initArray(mybytearray.length);
//			msg.setSize(mybytearray.length);
//			bis.read(msg.getMybytearray(), 0, mybytearray.length);
//
//			ConnectFormController.chat.accept(new Request("write incomeBLOB", newFile));
//		} catch (Exception e) {
//			System.out.println("Error sending ((Files)msg) to Server - ChatClient");
//		}
	}

	@FXML
	void initialize() {

		monthLBL.setText(PickReportController.months[PickReportController.currMonth]);

		// Get food & drinks total price and calculate taxes
		fillIncomeReport();

		restaurantNameLBL.setText("for " + PickReportController.chosenRes + " restaurant");

		assert restaurantNameLBL != null
				: "fx:id=\"restaurantNameLBL\" was not injected: check your FXML file 'IncomeReport.fxml'.";
		assert exportBTN != null : "fx:id=\"exportBTN\" was not injected: check your FXML file 'IncomeReport.fxml'.";
		assert returnBTN != null : "fx:id=\"returnBTN\" was not injected: check your FXML file 'IncomeReport.fxml'.";
		assert totFoodLBL != null : "fx:id=\"totFoodLBL\" was not injected: check your FXML file 'IncomeReport.fxml'.";
		assert taxLBL != null : "fx:id=\"taxLBL\" was not injected: check your FXML file 'IncomeReport.fxml'.";
		assert totLBL != null : "fx:id=\"totLBL\" was not injected: check your FXML file 'IncomeReport.fxml'.";

	}
}
