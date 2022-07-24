package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javax.print.attribute.standard.DateTimeAtCompleted;
import java.time.LocalDate;
import java.time.Month;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Request;
import logic.ResNameAndOrderNum;
import logic.ResNameAndIncome;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * 
 * @author Ibraheem
 * This class controls the CEO Report screen
 * it's responsible for getting orders and income data for all restaurants
 * for the last year-quarter
 * @param Ordersdata : The array list that holds the orders data for each restaurant
 * @param incomedata : The array list that holds the income data for each restaurant
 * Shows a histogram for each data
 */
public class CEOReportController {

	public static ArrayList<ResNameAndOrderNum> Ordersdata = null;
	public static ArrayList<ResNameAndIncome> incomedata = null;

	public static String yQuarter;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label restaurantNameLBL;

	@FXML
	private Button returnBTN;

	@FXML
	private BarChart<String, Number> ordersBarChart;

	@FXML
	private CategoryAxis orestAxis;

	@FXML
	private NumberAxis ordersAxis;

	@FXML
	private BarChart<String, Number> incomeBarChart1;

	@FXML
	private CategoryAxis irestAxis1;

	@FXML
	private NumberAxis incomeAxis1;

	/**
	 * Return to "CEO Screen" screen
	 * @param event : default param from javafx
	 */
	@FXML
	void returnToCEOScreen(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/CEOScreen.fxml"), resources);
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 1200, 700));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays histogram for both orders data & income data for all restaurants
	 * Gets orders & income data from DB according to the last year-quarter
	 * Inject both histograms with relevant data
	 * @param currMonth : Current month number
	 * @param yQuarter : the relevant last year-quarter according to current month
	 */
	protected void initCharts() {

		// Orders Chart
		// Series 1

		LocalDate currentdate = LocalDate.now();
		int currMonth = currentdate.getMonthValue();

		currMonth -= 3;
		if (currMonth <= 0)
			currMonth += 12;

		if (currMonth > 0 && currMonth < 4)
			yQuarter = "1st quarter";
		else if (currMonth > 3 && currMonth < 7)
			yQuarter = "2nd quarter";
		else if (currMonth > 6 && currMonth < 10)
			yQuarter = "3rd quarter";
		else if (currMonth > 9 && currMonth < 13)
			yQuarter = "4th quarter";

		if (Ordersdata != null)
			Ordersdata = null;

		ordersBarChart.getData().clear();
		incomeBarChart1.getData().clear();
		ordersBarChart.layout();
		incomeBarChart1.layout();

		ConnectFormController.chat.accept(new Request("get quarter orders", yQuarter));

		XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
		dataSeries1.setName("Orders");
		for (ResNameAndOrderNum res : Ordersdata)
			dataSeries1.getData().add(new XYChart.Data<>(res.getRestaurantName(), res.getOrdersNum()));
		ordersBarChart.getData().add(dataSeries1);
		orestAxis.setAnimated(false);

		// Income Chart
		// Series 2
		ConnectFormController.chat.accept(new Request("get quarter income", yQuarter));

		XYChart.Series<String, Number> dataSeries11 = new XYChart.Series<>();
		dataSeries11.setName("Income");
		for (ResNameAndIncome res : incomedata)
			dataSeries11.getData().add(new XYChart.Data<>(res.getRestaurantName(), res.getIncome()));
		incomeBarChart1.getData().add(dataSeries11);
		irestAxis1.setAnimated(false);
	}

	@FXML
	void initialize() {

		// Initialize chart with right data
		initCharts();

		assert returnBTN != null : "fx:id=\"returnBTN\" was not injected: check your FXML file 'CEOReport.fxml'.";
		assert ordersBarChart != null
				: "fx:id=\"ordersBarChart\" was not injected: check your FXML file 'CEOReport.fxml'.";
		assert orestAxis != null : "fx:id=\"orestAxis\" was not injected: check your FXML file 'CEOReport.fxml'.";
		assert ordersAxis != null : "fx:id=\"ordersAxis\" was not injected: check your FXML file 'CEOReport.fxml'.";
		assert incomeBarChart1 != null
				: "fx:id=\"incomeBarChart1\" was not injected: check your FXML file 'CEOReport.fxml'.";
		assert irestAxis1 != null : "fx:id=\"irestAxis1\" was not injected: check your FXML file 'CEOReport.fxml'.";
		assert incomeAxis1 != null : "fx:id=\"incomeAxis1\" was not injected: check your FXML file 'CEOReport.fxml'.";

	}
}
