package gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.OrderView;
import logic.Request;
import logic.Delivery;

import java.util.Date;

public class DeliveryDetailsController extends Application implements Initializable {

	public static Stage DeliveryDetailsScreen;
	public static ObservableList<Delivery> Deliveryinfo = FXCollections.observableArrayList();
	public static ObservableList<String> deliverytype = FXCollections.observableArrayList("TakeAway", "Delivery");
	public static ObservableList<String> orderway = FXCollections.observableArrayList("Regular", "Pre-Order");
	public static ObservableList<String> deliveryway = FXCollections.observableArrayList("Private", "Common", "Robot");
	public static ObservableList<String> deliverywayforregular = FXCollections.observableArrayList("Private", "Robot");
	public static Boolean Busflag = false;
	public static Boolean Timeflag = true;
	public static Boolean Dateflag = true;
	public static Boolean NameFlag = true;
	public static Boolean LastNameFlag = true;
	public static Boolean AddressFlag = true;
	public static String date;
	public static Integer NumOfDinners = 0;
	SpinnerValueFactory<Integer> valuefactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);

	SpinnerValueFactory<Integer> valuefactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);

	Date currentDate = new Date();
	SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat hourlater = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
	String datestr = dateFormat.format(currentDate);
	String timenow;

	public static int currentday;
	public static int currentmonth;
	public static int currentyear;
	public static String currentdate[];

	public static int ExpectedDay;
	public static int ExpectedMonth;
	public static int ExpectedYear;
	public static String ExpectedDate[];

	public static int currentHour;
	public static int currentMinutes;
	public static String currenttime[];
	public static int ExpectedHour;
	public static int ExpectedMinutes;
	public static String ExpectedTime[];
	public static Double PriceAfterDiscount = 0.0;
	public static Double discount = 0.0;
	public static String Name;
	public static String LastName;
	public static String address;

	public static String Time;
	public static Double DeliveryPrice = 0.0;
	public static Double Final;
	public static int CommonNumber;

	@FXML
	private AnchorPane DelPage;

	@FXML
	private AnchorPane ShippingDetails;

	@FXML
	private AnchorPane ExpectedOrderScreen;

	@FXML
	private AnchorPane DelWayPane;

	@FXML
	private DatePicker orderdate;

	@FXML
	private TextField CustomerName;

	@FXML
	private TextField CustomerLastName;

	@FXML
	private TextField DeliveryAddress;

	@FXML
	private TextField AppartmentNumber;

	@FXML
	private TextField CompanyName;

	@FXML
	private Label Discount;

	@FXML
	private Label DelCost;

	@FXML
	private Label CostLabel;

	@FXML
	private Label FirstNameERR;

	@FXML
	private Label LastNameERR;

	@FXML
	private Label AddressERR;

	@FXML
	private Label CompanyERR;

	@FXML
	private Label AppartmentERR;

	@FXML
	private Label ChooseDeiveryERR1;

	@FXML
	private Label TimeERR;

	@FXML
	private Label ChooseOrderERR;

	@FXML
	private Label ChooseDeiveryERR;

	@FXML
	private Button Back;

	@FXML
	private ComboBox<String> DeliveryWay;

	@FXML
	private ComboBox<String> OrderType;

	@FXML
	private ComboBox<String> DeliveryType;

	@FXML
	private DatePicker OrderWantedDate;

	@FXML
	private Label DateERR;

	@FXML
	private Label ErrlLabel;

	@FXML
	private Spinner<Integer> hourspinner;

	@FXML
	private Spinner<Integer> minutesspinner;

	/**
	 * @author ayal input: ActionEvent instance functionality: it hides the page and
	 *         return to the previous page output: null
	 */
	@FXML
	void Backbtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		ResMenuForCustomersController.respage.show();

	}

	/**
	 * @ayal input : ActionEvent instance Functionality : Select the order
	 *       way(delivery or takeaway) and display the appropriate anchorpanes ones
	 *       accordingly output :null
	 * @param event
	 */

	@FXML
	void ChooseDelWay(ActionEvent event) {
		if (DeliveryWay.getSelectionModel().getSelectedItem().equals("TakeAway")) {

			DelCost.setText("---------");
			ShippingDetails.setVisible(false);
			ChooseDeiveryERR.setVisible(false);
			DeliveryPrice = 0.0;

		}

		else {

			ShippingDetails.setVisible(true);

			ChooseDeiveryERR.setVisible(false);

		}

	}

	/**
	 * input : ActionEvent instance
	 *  functionality: We check the user's choices
	 * regarding the shipment and the dates calculate the cost and shipping details
	 * and update all the relevant information accordingly. Regarding incorrect
	 * choices, errors are displayed on the screen accordingly
	 * outout:null
	 * 
	 * @param event
	 * @throws Exception
	 */

	@FXML
	void Cofirmandcontinuebtn(ActionEvent event) throws Exception {

		Timeflag = true;

		if (ChooseOrderERR.isVisible() || ChooseDeiveryERR.isVisible()) {

			MessageBox.DisplayMessage("Please Check the required fields marked in red*", "WARNING", "",
					AlertType.WARNING);

//			ErrlLabel.setText("Please Check the required fields marked in red*");
		}
		// ******************************************************************************
		// **************************Pre-Order +
		// TakeAway********************************
		else {

			Final = PriceAfterDiscount + DeliveryPrice;
			if ((OrderType.getSelectionModel().getSelectedItem().equals("Pre-Order"))
					&& (DeliveryWay.getSelectionModel().getSelectedItem().equals("TakeAway"))) {

				if (DateERR.isVisible()) {
					Dateflag=false;
				}
				ExpectedHour = hourspinner.getValue();
				ExpectedMinutes = minutesspinner.getValue();

				System.out.println(ExpectedHour + ":" + ExpectedMinutes);

				if (ExpectedYear == currentyear && ExpectedMonth == currentmonth && ExpectedDay == currentday
						&& ExpectedHour - currentHour < 2) {

					Timeflag = false;
					TimeERR.setVisible(true);

//					MessageBox.DisplayMessage("Please Check the required fields marked in red*", "Warning", "",
//							AlertType.WARNING);

				}

				else if (ExpectedYear == currentyear && ExpectedMonth == currentmonth && ExpectedDay == currentday
						&& ExpectedHour - currentHour == 2 && ExpectedMinutes < currentMinutes) {

					Timeflag = false;
					TimeERR.setVisible(true);
					System.out.println("2");
//					MessageBox.DisplayMessage("Please Check the required fields marked in red*", "Warning", "",
//							AlertType.WARNING);

				}

				if (Timeflag && Dateflag) {

					Deliveryinfo.clear();
					System.out.println(currentday);

					timenow = clockFormat.format(currentDate);

					Delivery ToDel = new Delivery(NewOrderController.resname, "Pre-Order", "TakeAway", "N/A",
							hourlater.format(currentDate),
							OrderWantedDate.getValue().toString() + " " + hourspinner.getValue().toString() + ":"
									+ minutesspinner.getValue().toString() + ":00");
					Deliveryinfo.add(ToDel);

					ConnectFormController.chat
							.accept(new Request("Get Private Wallet	" + USERHSController.user.getId(), null));
					ConnectFormController.chat
							.accept(new Request("Get Buisness Wallet	" + USERHSController.user.getId(), null));
					((Node) event.getSource()).getScene().getWindow().hide();
					PaymentDetailsController PDC = new PaymentDetailsController();
					Stage primaryStage = new Stage();
					PDC.start(primaryStage);
				} else {

					MessageBox.DisplayMessage("Please Check the required fields marked in red*", "WARNING", "",
							AlertType.WARNING);
					ErrlLabel.setText("Please Check the required fields marked in red*");
				}

				// ******************************************************************************
				// **************************Pre-Order +
				// Delivery********************************

			} else if ((OrderType.getSelectionModel().getSelectedItem().equals("Pre-Order"))
					&& (DeliveryWay.getSelectionModel().getSelectedItem().equals("Delivery"))) {

				Name = CustomerName.getText();
				LastName = CustomerLastName.getText();
				address = DeliveryAddress.getText();
				FirstNameERR.setVisible(!(NameFlag = CheckName(Name)));
				LastNameERR.setVisible(!(LastNameFlag = CheckName(LastName)));

				ExpectedHour = hourspinner.getValue();
				ExpectedMinutes = minutesspinner.getValue();
				
				if (DateERR.isVisible()) {
					Dateflag=false;
				}

				if (ExpectedYear == currentyear && ExpectedMonth == currentmonth && ExpectedDay == currentday
						&& ExpectedHour - currentHour < 2) {

					System.out.println("1");
					Timeflag = false;
					TimeERR.setVisible(true);

				}

				else if (ExpectedYear == currentyear && ExpectedMonth == currentmonth && ExpectedDay == currentday
						&& ExpectedHour - currentHour == 2 && ExpectedMinutes < currentMinutes) {
					Timeflag = false;
					TimeERR.setVisible(true);
					System.out.println("2");

				}

				if (!Timeflag || !NameFlag || !LastNameFlag || !AddressFlag || !Dateflag
						|| ChooseDeiveryERR1.isVisible()) {

					MessageBox.DisplayMessage("Please Check the required fields marked in red*", "Warning", "",
							AlertType.WARNING);
					ErrlLabel.setText("Please Check the required fields marked in red*");

				} else {

					TimeERR.setVisible(false);
					Deliveryinfo.clear();
					Delivery ToDel = new Delivery(NewOrderController.resname, "Pre-Order", "Delivery",
							DeliveryType.getSelectionModel().getSelectedItem(), hourlater.format(currentDate),
							OrderWantedDate.getValue().toString() + " " + hourspinner.getValue().toString() + ":"
									+ minutesspinner.getValue().toString() + ":00");
					Deliveryinfo.add(ToDel);
					ConnectFormController.chat
							.accept(new Request("Get Private Wallet	" + USERHSController.user.getId(), null));
					ConnectFormController.chat
							.accept(new Request("Get Buisness Wallet	" + USERHSController.user.getId(), null));
					((Node) event.getSource()).getScene().getWindow().hide();
					PaymentDetailsController PDC = new PaymentDetailsController();
					Stage primaryStage = new Stage();
					PDC.start(primaryStage);
				}
			}

			// ******************************************************************************
			// **************************Regular + Delivery********************************

			else if (DeliveryWay.getSelectionModel().getSelectedItem().equals("Delivery")
					&& (OrderType.getSelectionModel().getSelectedItem().equals("Regular") )) {

				Name = CustomerName.getText();
				LastName = CustomerLastName.getText();
				address = DeliveryAddress.getText();
				FirstNameERR.setVisible(!(NameFlag = CheckName(Name)));
				LastNameERR.setVisible(!(LastNameFlag = CheckName(LastName)));

				if (!NameFlag || !LastNameFlag || !AddressFlag || ChooseDeiveryERR1.isVisible()) {

					MessageBox.DisplayMessage("Please Check the required fields marked in red*", "Warning", "",
							AlertType.WARNING);

					ErrlLabel.setText("Please Check the required fields marked in red*");
				} else {

					System.out.println(NameFlag);

					timenow = clockFormat.format(currentDate);

					Date newDate = new Date(currentDate.getTime() + 1 * (3600 * 1000));
					String HL = hourlater.format(newDate).toString();

					Deliveryinfo.clear();
					Delivery ToDel = new Delivery(NewOrderController.resname, "Regular", "Delivery",
							DeliveryType.getSelectionModel().getSelectedItem(), hourlater.format(currentDate), HL);
					Deliveryinfo.add(ToDel);

					ConnectFormController.chat
							.accept(new Request("Get Private Wallet	" + USERHSController.user.getId(), null));
					ConnectFormController.chat
							.accept(new Request("Get Buisness Wallet	" + USERHSController.user.getId(), null));
					((Node) event.getSource()).getScene().getWindow().hide();
					PaymentDetailsController PDC = new PaymentDetailsController();
					Stage primaryStage = new Stage();
					PDC.start(primaryStage);

				}
			} else if ((OrderType.getSelectionModel().getSelectedItem().equals("Regular"))
					&& (DeliveryWay.getSelectionModel().getSelectedItem().equals("TakeAway"))) {

				Date newDate = new Date(currentDate.getTime() + 1 * (3600 * 1000));
				String HL = hourlater.format(newDate).toString();

				Deliveryinfo.clear();
				Delivery ToDel = new Delivery(NewOrderController.resname, "Regular", "TakeAway", "N/A",
						hourlater.format(currentDate), HL);
				Deliveryinfo.add(ToDel);

				ConnectFormController.chat
						.accept(new Request("Get Private Wallet	" + USERHSController.user.getId(), null));
				ConnectFormController.chat
						.accept(new Request("Get Buisness Wallet	" + USERHSController.user.getId(), null));
				((Node) event.getSource()).getScene().getWindow().hide();
				PaymentDetailsController PDC = new PaymentDetailsController();
				Stage primaryStage = new Stage();
				PDC.start(primaryStage);
			} 
			else {
				MessageBox.DisplayMessage("Please Check the required fields marked in red*", "Warning", "",
						AlertType.WARNING);

				ErrlLabel.setText("Please Check the required fields marked in red*");
				
			}

		
		}

	}

	/**
	 * @ayal input : ActionEvent instance Functionality : Select the delivery
	 *       type(private or common or robbot) and save the relevant data output
	 *       :null
	 * @param event
	 */

	@FXML
	void DeliveryTypeBtn(ActionEvent event) {

		if (DeliveryType.getSelectionModel().getSelectedItem().equals("Private")) {

			DelCost.setText("25.00");
			DeliveryPrice = 25.00;
			ChooseDeiveryERR1.setVisible(false);

		}

		else if (DeliveryType.getSelectionModel().getSelectedItem().equals("Common")) {
			ChooseDeiveryERR1.setVisible(false);

			ConnectFormController.chat.accept(new Request("Check if its Commons Delivery	" + NewOrderController.resID
					+ "	" + USERHSController.user.getId(), datestr));
			System.out.println("the number is " + CommonNumber);
			switch (CommonNumber) {

			case 1:
				DelCost.setText("25.00");
				DeliveryPrice = 25.00;

				break;
			case 2:
				DelCost.setText("20.00");
				DeliveryPrice = 20.00;
				Final = PriceAfterDiscount + 20.00;
				break;
			case 3:
				DelCost.setText("15.00");
				DeliveryPrice = 15.00;

				break;

			default:
				ChooseDeiveryERR1.setVisible(true);
				MessageBox.DisplayMessage("You are not buisness account", "Warning", "", AlertType.WARNING);

			}
		} else if (DeliveryType.getSelectionModel().getSelectedItem().equals("Robot")) {
			ChooseDeiveryERR1.setVisible(true);
			MessageBox.DisplayMessage("Coming Soon", "ERROR", "", AlertType.ERROR);
		}

	}

	/**
	 * @ayal input : ActionEvent instance
	 *  Functionality :Choosing the date 
	 * output :null
	 *       
	 * @param event
	 */

	@FXML
	void OrderDatebtn(ActionEvent event) {

		DateERR.setVisible(false);

	}

	/**
	 * @ayal input : ActionEvent instance Functionality : Select the order
	 *       type(Pre-order or Regular) and save the relevant data output :null
	 * @param event
	 */

	@FXML
	void OrderTypebtn(ActionEvent event) {

		if (OrderType.getSelectionModel().getSelectedItem().equals("Pre-Order")) {

			ExpectedOrderScreen.setVisible(true);
			ChooseOrderERR.setVisible(false);
			discount = (Double) ResMenuForCustomersController.totalprice * (0.1);
			PriceAfterDiscount = ResMenuForCustomersController.totalprice - discount;
			Discount.setText(new DecimalFormat("##.##").format(discount));

			DelWayPane.setVisible(true);
		}

		else {
			PriceAfterDiscount = ResMenuForCustomersController.totalprice;
			discount = 0.0;
			Discount.setText(discount.toString());
			ExpectedOrderScreen.setVisible(false);
			ChooseOrderERR.setVisible(false);
			Discount.setText("-------");
			DelWayPane.setVisible(true);

		}

	}

	/**
	 * @ayal input : ActionEvent instance Functionality :textfield for the customer
	 *       name, checking if the field is empty or not and display *in accordance
	 *       output :null
	 * @param event
	 */

	@FXML
	void FirstNameField(MouseEvent event) {

		if (!CustomerName.getText().isEmpty()) {
			FirstNameERR.setVisible(false);
			NameFlag = true;
		}

		else {

			FirstNameERR.setVisible(true);
			NameFlag = false;
		}

	}

	/**
	 * @ayal input : ActionEvent instance Functionality :textfield for the customer
	 *       last name, checking if the field is empty or not and display * in
	 *       accordance output :null
	 * @param event
	 */

	@FXML
	void LastNameField(MouseEvent event) {
		if (!CustomerLastName.getText().isEmpty()) {
			LastNameERR.setVisible(false);
			LastNameFlag = true;
		} else {
			LastNameERR.setVisible(true);
			LastNameFlag = false;
		}

	}

	/**
	 * @ayal input : ActionEvent instance Functionality :textfield for the customer
	 *       address, checking if the field is empty or not and display *in
	 *       accordance output :null
	 * @param event
	 */

	@FXML
	void AddressFieldName(MouseEvent event) {

		if (!CustomerLastName.getText().isEmpty()) {
			AddressERR.setVisible(false);
			AddressFlag = true;
		}

		else {

			AddressERR.setVisible(true);
			AddressFlag = false;
		}

	}

	/**
	 * @ayal input : ActionEvent instance Functionality :Selecting the time and
	 *       saving it into variables to check the appropriate date before moving on
	 *       to the next page
	 * 
	 *       output :null
	 * @param event
	 */

	@FXML
	void OrderWantedDateBtn(ActionEvent event) {

		Dateflag=true;
		DateERR.setVisible(true);
		LocalDate mydate = OrderWantedDate.getValue();

		ExpectedDate = mydate.toString().split("-", 3);

		ExpectedYear = Integer.parseInt(ExpectedDate[0]);
		ExpectedMonth = Integer.parseInt(ExpectedDate[1]);
		ExpectedDay = Integer.parseInt(ExpectedDate[2]);

		System.out.println(ExpectedMonth);
		System.out.println(ExpectedDay);
		System.out.println(ExpectedYear);

		if (ExpectedYear < currentyear) {

			Dateflag = false;

		} else if (ExpectedYear == currentyear) {

			if (ExpectedMonth < currentmonth) {
				Dateflag = false;
			} else if (ExpectedMonth == currentmonth) {
				if (ExpectedDay < currentday)
					Dateflag = false;
			}

		}

		if (Dateflag) {
			DateERR.setVisible(false);
		}
	}

	/**
	 * @ayal input : String
	 *  instance Functionality :check if the customer name have only letters
	 *      
	 *       output : boolean
	 * @param event
	 */

	public boolean CheckName(String str) {

		return (str.matches("[a-zA-Z]+"));

	}

	/**
	 * @ayal input : String 
	 *  Functionality :check if the customer last name have only letters 
	 *       
	 *       output : boolean
	 * @param event
	 */
	public boolean CheckLastName(String str) {
		return (str.matches("[a-zA-Z]+"));
	}

	@Override

	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/DeliveryDetails.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e -> e.consume());
		primaryStage.setTitle("MenuForCustomer");
		primaryStage.setScene(scene);
		primaryStage.show();

		DeliveryDetailsScreen = primaryStage;

	}

	/**
	 * @author ayal input :(URL , ResourceBundle)
	 *  functionality: load the relevant
	 *         data about the customer and save the current time to check the
	 *         appropriate time before continue
	 *         output : null
	 * 
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		DeliveryWay.setItems(deliverytype);// Takeaway or Delivery
		OrderType.setItems(orderway);// regular or pre-order
		ConnectFormController.chat.accept(new Request("Check if Business", USERHSController.user));
		if (Busflag)
			DeliveryType.setItems(deliveryway);// private/common/robot
		else
			DeliveryType.setItems(deliverywayforregular);// private/robot
		DelWayPane.setVisible(false);

		ShippingDetails.setVisible(false);

		CostLabel.setText(ResMenuForCustomersController.totalprice.toString());

		currenttime = clockFormat.format(currentDate).split(":", 2);
		currentHour = Integer.parseInt(currenttime[0]);
		currentMinutes = Integer.parseInt(currenttime[1]);

		currentdate = dateFormat.format(currentDate).split("-", 3);
		currentyear = Integer.parseInt(currentdate[0]);
		currentmonth = Integer.parseInt(currentdate[1]);
		currentday = Integer.parseInt(currentdate[2]);

		hourspinner.setValueFactory(valuefactory1);
		minutesspinner.setValueFactory(valuefactory2);
		TimeERR.setVisible(false);

		ExpectedOrderScreen.setVisible(false);
		PriceAfterDiscount = ResMenuForCustomersController.totalprice;

		System.out.println(clockFormat.format(currentDate));
		System.out.println(hourlater.format(currentDate));
	}

}
