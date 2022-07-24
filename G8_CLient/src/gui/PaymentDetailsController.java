package gui;

import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Delivery;
import logic.Payment;
import logic.Request;

public class PaymentDetailsController extends Application implements Initializable {

	public static Stage payPage;
	public static Double PrivateWallet;
	public static Double BuisnessWallet;
	public static Double NewWallet;
	public static Double Needtopay;
	public static Double FinalPrivatePrice;
	public static Double RemainaingDis;
	public static Payment paymeth = new Payment("", "", "", "", "");
	public static boolean FlagAccount;
	public static ObservableList<Payment> Payementmethod = FXCollections.observableArrayList();
	public static String BuisnessWalletString;
	public static String isbusiness;
	public static String EmployerName;
	public static String QRCode;

	public static boolean Employerflag;
	public static boolean qrcodeflag;

	@FXML
	private Button Confirmandcontinue;

	@FXML
	private Button Back;

	@FXML
	private TextField Employername;

	@FXML
	private TextField Employerw4c;

	@FXML
	private RadioButton PrivateAcc;

	@FXML
	private RadioButton BuisnessAcc;

	@FXML
	private AnchorPane BusinessDetails;

	@FXML
	private Label WFOURCERR;

	@FXML
	private Label AccPayERR;

	@FXML
	private Label EmployerNameERR;

	@FXML
	private Label ErrorPayLabel;
	@FXML
	private AnchorPane BuisnessWalletPane;
	@FXML
	private Label RelevantAccount;

	@FXML
	private Label RemainaingWallet;

	@FXML
	private Label NewRemWallet;

	@FXML
	private Label payNeeded;

	@FXML
	private AnchorPane PrivateWalletpane;

	@FXML
	private Label RemCredit;

	@FXML
	private Label PrivateNewprice;

	@FXML
	private Label RemDiscounts;

	@FXML
	private Label Finalprice;

	
	
	/**
	 * input: Action Event 
	 * functionality : Buisness Radio button, select the button and Displays the financial card to the customer screen and calculates the final price accordingly
	 * output: null
	 * @param event
	 */
	@FXML
	void BusinessBtn(ActionEvent event) {
		PrivateAcc.setSelected(false);
		PrivateWalletpane.setVisible(false);
		BuisnessWalletPane.setVisible(true);
		BusinessDetails.setVisible(true);
		paymeth.setAccountType("Business Account");
		RemainaingWallet.setText(BuisnessWallet.toString());

		NewWallet = BuisnessWallet - DeliveryDetailsController.Final;

		if (NewWallet >= 0) {
			NewRemWallet.setText(NewWallet.toString());
			payNeeded.setText("0.0");

		} else {

			NewRemWallet.setText("0.0");
			Needtopay = Math.abs(NewWallet);
			payNeeded.setText(Needtopay.toString());

		}
	}


	
	/**
	 * input: Action Event 
	 * functionality : private Radio button, select the button and Displays the credit to the customer screen and calculates the final price accordingly
	 * output: null
	 * @param event
	 */
	@FXML
	void PrivateBtn(ActionEvent event) {

		BuisnessAcc.setSelected(false);
		PrivateWalletpane.setVisible(true);
		BusinessDetails.setVisible(false);
		BuisnessWalletPane.setVisible(false);
		paymeth.setAccountType("Private Account");
		RemCredit.setText(PrivateWallet.toString());

		FinalPrivatePrice = DeliveryDetailsController.Final - PrivateWallet;

		if (FinalPrivatePrice > 0) {
			PrivateNewprice.setText(FinalPrivatePrice.toString());
			RemDiscounts.setText("0.0");

		} else if (FinalPrivatePrice == 0) {
			PrivateNewprice.setText("0.0");
			RemDiscounts.setText("0.0");

		} else {
			PrivateNewprice.setText("0.0");
			RemainaingDis = Math.abs(FinalPrivatePrice);
			RemDiscounts.setText(RemainaingDis.toString());

		}

	}
	
	
	/**
	 * @author ayal input: ActionEvent instance functionality: it hides the page and
	 *         return to the previous page output: null
	 */
	@FXML
	void Backbtn(ActionEvent event) {

		((Node) event.getSource()).getScene().getWindow().hide();
		DeliveryDetailsController.DeliveryDetailsScreen.show();

	}
	
	/**
	 * input : ActionEvent instance
	 *  functionality: We check the user's choices regarding the Account type 
	 * and update all the relevant information accordingly. Regarding incorrect
	 * choices, errors are displayed on the screen accordingly
	 * outout:null
	 * 
	 * @param event
	 * @throws Exception
	 */
	

	@FXML
	void ConfirmAndContinueBtn(ActionEvent event) throws Exception {

		if (BuisnessAcc.isSelected()) {

			QRCode = Employerw4c.getText();
			EmployerName = Employername.getText();
			EmployerNameERR.setVisible(!(Employerflag = Checkemployee(EmployerName)));
			CheckQR(QRCode);
			WFOURCERR.setVisible(!qrcodeflag);

			if (!qrcodeflag || !Employerflag) {
				MessageBox.DisplayMessage("Please Check the required fields marked in red*", "Warning", "", AlertType.WARNING);
				
			} else {

				paymeth.setOrderPrice(ResMenuForCustomersController.totalprice.toString());
				paymeth.setDiscounts(new DecimalFormat("##.##").format(DeliveryDetailsController.discount).toString());
				paymeth.setFinalPrice(DeliveryDetailsController.Final.toString());

				if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliverWay().equals("TakeAway")) {
					paymeth.setDeliveryCost("0.0");
				} else if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliverWay().equals("Delivery")) {
					if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliveryType().equals("Private")) {
						paymeth.setDeliveryCost("25.00");
					} else {
						paymeth.setDeliveryCost(DeliveryDetailsController.DeliveryPrice.toString());
					}

					Payementmethod.clear();
					Payementmethod.add(paymeth);

				}

				((Node) event.getSource()).getScene().getWindow().hide();
				OrderPreviewController OPC = new OrderPreviewController();
				Stage primaryStage = new Stage();
				OPC.start(primaryStage);
			}

		}

		else if (PrivateAcc.isSelected()) {
			paymeth.setOrderPrice(ResMenuForCustomersController.totalprice.toString());
			paymeth.setDiscounts(new DecimalFormat("##.##").format(DeliveryDetailsController.discount).toString());
			paymeth.setFinalPrice(DeliveryDetailsController.Final.toString());

			if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliverWay().equals("TakeAway")) {
				paymeth.setDeliveryCost("0.0");
			} else if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliverWay().equals("Delivery")) {
				if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliveryType().equals("Private")) {
					paymeth.setDeliveryCost("25.00");
				}
			}
			Payementmethod.clear();
			Payementmethod.add(paymeth);

			((Node) event.getSource()).getScene().getWindow().hide();
			OrderPreviewController OPC = new OrderPreviewController();
			Stage primaryStage = new Stage();
			OPC.start(primaryStage);

		}

		else {
			MessageBox.DisplayMessage("Please choose account type first", "Warning", "", AlertType.WARNING);
		}
	}
	
	

	/**
	 * @ayal input : MouseEvent instance
	 *  Functionality : checking if the field is empty or not and display *in accordance
	 *       output :null
	 * @param event
	 */


	@FXML
	void GetEmployerName(MouseEvent event) {

		if (!Employername.getText().isEmpty()) {
			EmployerNameERR.setVisible(false);

		}

		else {
			EmployerNameERR.setVisible(true);

		}

	}
	


	/**
	 * @ayal input : MouseEvent instance
	 *  Functionality : checking if the field is empty or not and display *in accordance
	 *       output :null
	 * @param event
	 */

	@FXML
	void GetEmployerW4c(MouseEvent event) {

		if (!Employerw4c.getText().isEmpty()) {

			WFOURCERR.setVisible(false);

		}

		else {
			WFOURCERR.setVisible(true);

		}

	}
	
	/**
	 * @ayal input : String
	 *  Functionality : check if the customer last name have only letters 
	 * output : boolean
	 * @param event
	 */
	
	public boolean Checkemployee(String str) {

		return (str.matches("[a-zA-Z]+"));

	}
	
	/**
	 * @ayal input : String
	 *  Functionality : check if the customer last name have only letters 
	 * output : boolean
	 * @param event
	 */

	public void CheckQR(String str) {

		if (str.matches("[0-9]+")) {
			ConnectFormController.chat
					.accept(new Request("Check Qr	" + USERHSController.user.getId(), str + "," + EmployerName));

		} else

			qrcodeflag = false;

	}
	
	
	
	/**
	 * @author ayal input :(URL , ResourceBundle)
	 *  functionality: load the relevant data according to the delivery way (specific in common delivery)
	 *  output: null
	 *       
	 * 
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Payementmethod.clear();

		if (DeliveryDetailsController.Deliveryinfo.get(0).getDeliveryType().equals("Common"))
			PrivateAcc.setDisable(true);

		Finalprice.setText(DeliveryDetailsController.Final.toString());

		isbusiness = BuisnessWalletString.split(",", 2)[0];
		System.out.println(BuisnessWalletString);
		if (isbusiness.equals("FAILED")) {
			FlagAccount = false;
		} else if (isbusiness.equals("No")) {
			BuisnessAcc.setDisable(true);
			FlagAccount = true;
		}

		else {
			FlagAccount = true;
			BuisnessWallet = Double.parseDouble(BuisnessWalletString.split(",", 2)[1]);
		}

		BusinessDetails.setVisible(false);
		PrivateWalletpane.setVisible(false);
		BuisnessWalletPane.setVisible(false);
		Payementmethod.clear();

	}

	

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		FXMLLoader loader = new FXMLLoader();

		Pane root = loader.load(getClass().getResource("/gui/PaymentDetails.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("PaymentDetails");
		primaryStage.setScene(scene);
		primaryStage.show();
		payPage = primaryStage;

	}

}
