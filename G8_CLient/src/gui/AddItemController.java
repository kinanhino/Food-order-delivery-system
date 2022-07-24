package gui;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import logic.Item;
import logic.Request;
import logic.Toppings;

public class AddItemController extends Application implements Initializable {
	public static String status;
	public static boolean idflag;
	public static ArrayList<Toppings> toppings = new ArrayList<Toppings>();
	public static String toppingname;
	public static String toppingprice;
	public static String toppingsstatus;
	public static boolean flagdegree = false;
	public static boolean flagsize = false;
	private int count = 0;
	public static boolean flagtoppings = false;
	boolean confirmed = false;
	@FXML
	private TextField IDField;
	@FXML
	private Label labelidstatus;
	@FXML
	private TextField NameField;
	@FXML
	private Label labelname;

	@FXML
	private Label labelprice;
	@FXML
	private TextField PriceField;
	@FXML
	private Button btnaddtopping;
	@FXML
	private Label labelmsg;
	@FXML
	private Label labelmsg1;
	@FXML
	private ComboBox<String> Typecmb;
	@FXML
	private CheckBox degreeflag;

	@FXML
	private CheckBox differenttoppingflag;

	@FXML
	private CheckBox extrasize;
	@FXML
	private CheckBox largesize;

	@FXML
	private CheckBox meduimsize;

	@FXML
	private CheckBox sizeflag;

	@FXML
	private CheckBox smallsize;

	@FXML
	private TextField txttoppingname;

	@FXML
	private TextField txttoppingprice;
	@FXML
	private TextField txtpriceextra;

	@FXML
	private TextField txtpricelarge;

	@FXML
	private TextField txtpricemeduim;

	@FXML
	private TextField txtpricesmall;
	@FXML
	private Button btnprices;

	/**
	 * @author gethe this function adds an item for restaurant menu 
	 * starts when pressing add button checks if every text field is filled correctly if
	 *         not a message appears at gui , send to server an accept to add the
	 *         new item to the restaurant menu checks the status message received
	 *         from server and present it to gui to let the user know if the process
	 *         succeed or failed if item have toppings an accept sent to server to
	 *         save the toppings and its own price to the item and then refreshing
	 *         the item list shown in table
	 * 
	 */
	@FXML
	void Add(ActionEvent event) {
		if (IDField.getText().isEmpty() || PriceField.getText().isEmpty() || NameField.getText().isEmpty()
				|| Typecmb.getSelectionModel().getSelectedItem().equals(null))
			labelmsg.setText("You have to fill all Fields");
		ConnectFormController.chat.accept(new Request("add item to menu	" + SupplierEditorController.resturantID,
				new Item(Integer.parseInt(IDField.getText()), NameField.getText(),
						Typecmb.getSelectionModel().getSelectedItem(), Double.parseDouble(PriceField.getText()),
						flagsize, flagdegree, flagtoppings)));
		if (status.equals("item added")) {
			labelmsg.setText(status + " you can now go back to Restaurant Menu Page");
			System.out.println("Toppings for item are :");
			for (int i = 0; i < toppings.size(); i++)
				System.out.println(toppings.get(i));
			ConnectFormController.chat.accept(new Request(
					"add toppings for item	" + IDField.getText() + "	" + SupplierEditorController.resturantID,
					toppings));
			labelmsg1.setText(toppingsstatus);
			ConnectFormController.chat
					.accept(new Request("show items in restaurant", SupplierEditorController.resturantID));
		} else
			labelmsg.setText(status);
	}
	
	
//*******************************************lists for combo box******************************************
	ObservableList<String> list;

	ArrayList<String> al = new ArrayList<>();
//********************************************************************************************************
	
	
	/**
	 * @author gethe
	 * this function sets the combo box values
	 * */
	void setType() {

		al.add("MainMeal");
		al.add("Drinks");
		al.add("FirstMeal");
		al.add("Salad");
		al.add("Dessert");
		list = FXCollections.observableArrayList(al);
		Typecmb.setItems(list);
	}
//back button returns to previous page
	@FXML
	void Cancel(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		RestaurantMenuController rm = new RestaurantMenuController();
		Stage primaryStage = new Stage();
		rm.start(primaryStage);
	}
/**
 * this function initialise the page extra buttons and labels to not visible*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setType();
		smallsize.setVisible(false);
		meduimsize.setVisible(false);
		largesize.setVisible(false);
		extrasize.setVisible(false);
		btnaddtopping.setVisible(false);
		txttoppingname.setVisible(false);
		txttoppingprice.setVisible(false);
		txtpriceextra.setVisible(false);
		txtpricelarge.setVisible(false);
		txtpricemeduim.setVisible(false);
		txtpricesmall.setVisible(false);
		labelname.setVisible(false);
		labelprice.setVisible(false);
		btnprices.setVisible(false);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/AddItem.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("Add Item");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	/**
	 * @author gethe
	 * this function saves topping name and price opens a page to verify the info
	 * starts when pressing the button save changes 
	 * 
	 * */
	@FXML
	void addtopping(ActionEvent event) throws Exception {
		if (txttoppingname.getText().isEmpty() || txttoppingprice.getText().isEmpty())
			labelmsg1.setText("you have to add a name and a price to youe topping");
		else {
			if (count > 5)
				labelmsg1.setText("you can add up to 5 toppings");
			toppingname = txttoppingname.getText();
			toppingprice = txttoppingprice.getText();
			txttoppingname.setText("");
			txttoppingprice.setText("");
			AddToppingMsgController rm = new AddToppingMsgController();
			Stage primaryStage = new Stage();
			rm.start(primaryStage);
		}
	}
	/**
	 * @author gethe
	 * this function opens degree check boxes 
	 * starts when pressing the conbobox "have degree"
	 * 
	 * */
	@FXML
	void adddegree(ActionEvent event) {
		if (degreeflag.isSelected()) {
			flagdegree = true;
			toppings.add(new Toppings("Rare", 0));
			toppings.add(new Toppings("Meduim", 0));
			toppings.add(new Toppings("Well Done", 0));
			flagdegree = true;
		} else {
			flagdegree = false;
			toppings.remove(new Toppings("Rare", 0));
			toppings.remove(new Toppings("Meduim", 0));
			toppings.remove(new Toppings("Well Done", 0));
			flagdegree = false;
		}
	}
	/**
	 * @author gethe
	 * this function adds "extra large" size to item
	 * 
	 * */
	@FXML
	void addextrasize(ActionEvent event) {
		if (extrasize.isSelected()) {

			txtpriceextra.setVisible(true);
		} else {

			txtpriceextra.setVisible(false);
		}
	}
	/**
	 * @author gethe
	 * this function adds "large" size to item
	 * 
	 * */
	@FXML
	void addlargesize(ActionEvent event) {
		if (largesize.isSelected()) {

			txtpricelarge.setVisible(true);
		} else {

			txtpricelarge.setVisible(false);
		}
	}
	/**
	 * @author gethe
	 * this function adds "meduim" size to item
	 * 
	 * */
	@FXML
	void addmeduimsize(ActionEvent event) {
		if (meduimsize.isSelected()) {

			txtpricemeduim.setVisible(true);
		} else {

			txtpricemeduim.setVisible(false);
		}
	}
	/**
	 * @author gethe
	 * this function adds "small" size to item
	 * 
	 * */
	@FXML
	void addsmallsize(ActionEvent event) {
		if (smallsize.isSelected()) {

			txtpricesmall.setVisible(true);
		} else {

			txtpricesmall.setVisible(false);
		}
	}
	/**
	 * @author gethe
	 * this function opens toppings check boxes 
	 * starts when pressing the conbobox "have extra toppings"
	 * 
	 * */
	@FXML
	void addtoppingsoption(ActionEvent event) {
		if (differenttoppingflag.isSelected()) {
			btnaddtopping.setVisible(true);
			txttoppingname.setVisible(true);
			txttoppingprice.setVisible(true);
			flagtoppings = true;
			labelname.setVisible(true);
			labelprice.setVisible(true);
			flagtoppings = true;
		} else {
			btnaddtopping.setVisible(false);
			txttoppingname.setVisible(false);
			txttoppingprice.setVisible(false);
			flagtoppings = false;
			labelname.setVisible(false);
			labelprice.setVisible(false);
			flagtoppings = false;
		}
	}
	/**
	 * @author gethe
	 * this function opens size check boxes 
	 * starts when pressing the conbobox "have size"
	 * 
	 * */
	@FXML
	void showSizeOptions(ActionEvent event) {
		if (sizeflag.isSelected()) {
			smallsize.setVisible(true);
			meduimsize.setVisible(true);
			largesize.setVisible(true);
			extrasize.setVisible(true);
			flagsize = true;
			PriceField.setText("0");
			PriceField.setDisable(true);
			btnprices.setVisible(true);
			smallsize.setSelected(false);
			meduimsize.setSelected(false);
			largesize.setSelected(false);
			extrasize.setSelected(false);
			flagsize = true;
		} else {
			smallsize.setVisible(false);
			meduimsize.setVisible(false);
			largesize.setVisible(false);
			extrasize.setVisible(false);
			flagsize = false;
			PriceField.setText("");
			PriceField.setDisable(false);
			btnprices.setVisible(false);
			txtpriceextra.setText("");
			txtpriceextra.setVisible(false);
			txtpricelarge.setText("");
			txtpricelarge.setVisible(false);
			txtpricemeduim.setText("");
			txtpricemeduim.setVisible(false);
			txtpricesmall.setText("");
			txtpricesmall.setVisible(false);
			flagsize = false;
		}
	}
	/**
	 * @author gethe
	 * this function saves the selected sizes and its prices and save them in datat base 
	 * 
	 * */
	@FXML
	void addprices(ActionEvent event) {
		if (!confirmed) {
			confirmed = true;
			System.out.println("small=" + txtpricesmall.getText() + " meduim=" + txtpricemeduim.getText() + " large="
					+ txtpricelarge.getText() + " extra =" + txtpriceextra.getText());
			if (txtpriceextra.isVisible()) {
				if (txtpriceextra.getText().isEmpty())
					labelmsg1.setText("please fill price for extra large size ");
				else {
					System.out.println("extra==" + txtpriceextra.getText());
					toppings.add(new Toppings("XL", Double.parseDouble(txtpriceextra.getText())));
				}
			}
			if (txtpricelarge.isVisible()) {
				if (txtpricelarge.getText().isEmpty())
					labelmsg1.setText("please fill price for large size ");
				else {
					System.out.println("large==" + txtpricelarge.getText());
					toppings.add(new Toppings("L", Double.parseDouble(txtpricelarge.getText())));
				}
			}
			if (txtpricemeduim.isVisible()) {
				if (txtpricemeduim.getText().isEmpty())
					labelmsg1.setText("please fill price for meduim size ");
				else {
					System.out.println("meduim==" + txtpricemeduim.getText());
					toppings.add(new Toppings("M", Double.parseDouble(txtpricemeduim.getText())));
				}
			}
			if (txtpricesmall.isVisible()) {
				if (txtpricesmall.getText().isEmpty())
					labelmsg1.setText("please fill price for small size ");
				else {
					System.out.println("small==" + txtpricesmall.getText());
					toppings.add(new Toppings("S", Double.parseDouble(txtpricesmall.getText())));
				}
			}
		}

	}
	/**
	 * @author gethe
	 * this function checks the id for item if its already exists in data base the border of the text field lights with red light 
	 * and if its not in DB it lights with green light 
	 * 
	 * */
	@FXML
	void checkID(MouseEvent event) {
		if (IDField.getText().length() > 9)
			labelidstatus.setText("id must be less than 9 chracters");
		else if (!IDField.getText().isEmpty()) {
			ConnectFormController.chat.accept(new Request(
					"check id for restaurant	" + SupplierEditorController.resturantID + "	" + IDField.getText(),
					null));
			if (idflag)
				IDField.setStyle("-fx-text-box-border: #00ff00; -fx-focus-color: #00ff00;");
			else
				IDField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
		}
	}
}
