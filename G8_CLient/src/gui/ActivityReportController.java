package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Request;

/**
 * 
 * @author Ibraheem This class controls the Activity Report screen
 * @param onTimeP : Orders On Time percentage
 * @param DelayP  : Orders Delay percentage
 */
public class ActivityReportController {

	public static double onTimeP = 70, DelayP = 30;

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
	private Label monthLBL;

	@FXML
	private Button prevMonthBTN;

	@FXML
	private Button nextMonthBTN;

	@FXML
	private PieChart activityPieChart;

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
		getActivityData();
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
		getActivityData();
	}

	/**
	 * Return to "Pick Report" screen
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
			// Hide this current window (if this is what you want)
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get delay average for the restaurants orders and set Delay and OnTime
	 * percentages Set Pie chart data with those values
	 * 
	 * @param PickReportController.chosenResID : The restaurant to show report for
	 * @param currMonth                        : selected month to show report for
	 */
	protected void getActivityData() {

		Object[] objs = { PickReportController.chosenResID, PickReportController.currMonth + 1 };
		ConnectFormController.chat.accept(new Request("get activityReport", objs));

		DelayP *= 100;
		onTimeP = 100 - DelayP;

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("On Time: " + df.format(onTimeP) + "%", onTimeP),
				new PieChart.Data("Delay: " + df.format(DelayP) + "%", DelayP));
		activityPieChart.setData(pieChartData);
	}

	@FXML
	void initialize() {

		// init PieChart
		getActivityData();

		// Set label to the chosen restaurant
		restaurantNameLBL.setText("for " + PickReportController.chosenRes + " restaurant");

		assert restaurantNameLBL != null
				: "fx:id=\"restaurantNameLBL\" was not injected: check your FXML file 'ActivityReport.fxml'.";
		assert exportBTN != null : "fx:id=\"exportBTN\" was not injected: check your FXML file 'ActivityReport.fxml'.";
		assert returnBTN != null : "fx:id=\"returnBTN\" was not injected: check your FXML file 'ActivityReport.fxml'.";
		assert monthLBL != null : "fx:id=\"monthLBL\" was not injected: check your FXML file 'ActivityReport.fxml'.";
		assert prevMonthBTN != null
				: "fx:id=\"prevMonthBTN\" was not injected: check your FXML file 'ActivityReport.fxml'.";
		assert nextMonthBTN != null
				: "fx:id=\"nextMonthBTN\" was not injected: check your FXML file 'ActivityReport.fxml'.";
		assert activityPieChart != null
				: "fx:id=\"activityPieChart\" was not injected: check your FXML file 'ActivityReport.fxml'.";

	}
}
