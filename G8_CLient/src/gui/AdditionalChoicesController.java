package gui;

import java.io.IOException;
import java.net.URL;
import java.security.Identity;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.OrderView;
import logic.Request;
import logic.Restaurant;
import logic.Toppings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class AdditionalChoicesController extends Application implements Initializable {

	public static boolean addflag = false;
	public static boolean sizedlag = false;
	public static boolean levelofcocking = false;
	public static boolean flag = false;

	public static Double topingsprice = 0.0;
	public static Double sizeprice = 0.0;
	public static ArrayList<Toppings> Thetopings;
	public static ArrayList<Toppings> TheSize;
	public static boolean sizeselectedflag;
	// public static ObservableList<OrderView> CartList =
	// FXCollections.observableArrayList();
	OrderView itemtoadd = new OrderView(" ", 0.0, 0, " ", " ", " ", " ");
	public static String selectedsize;
	public static String selectedlevel;
	public static ArrayList<String> selectedtopings = new ArrayList<String>();
	public static String topingsasstring = "";
	// public static Double AdditionalPrice;

	@FXML
	private AnchorPane Topingspane;
	@FXML
	private AnchorPane MealSizePane;

	@FXML
	private RadioButton SmallSize;

	@FXML
	private RadioButton MediumSize;

	@FXML
	private RadioButton largeSize;

	@FXML
	private RadioButton ExtraLargeSize;

	@FXML
	private AnchorPane LevelOfCookinPane;
	@FXML
	private Label labelerr;
	@FXML
	private RadioButton Rare;

	@FXML
	private RadioButton Medium;

	@FXML
	private RadioButton WD;

	@FXML
	private TextField DescriptionText;

	@FXML
	private CheckBox Top1;

	@FXML
	private CheckBox Top2;

	@FXML
	private CheckBox Top4;

	@FXML
	private CheckBox Top5;

	@FXML
	private CheckBox Top3;

	@FXML
	private Button ADDItem;

	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified size is choosen and update in accordance
	 *output: null
	 * @param event
	 */
	@FXML
	void ExtraLargeSizebtn(ActionEvent event) {
		if (ExtraLargeSize.isSelected()) {
			sizedlag = true;

			SmallSize.setSelected(false);
			MediumSize.setSelected(false);
			largeSize.setSelected(false);

			itemtoadd.setSize("XL");
			sizeselectedflag = true;
			for (int i = 0; i < TheSize.size(); i++) {
				if (TheSize.get(i).getTopping().equals("XL")) {
					sizeprice = TheSize.get(i).getPrice();
				}

			}

		} else
			sizedlag = false;

	}

	
	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified size is choosen and update in accordance
	 *output: null
	 * @param event
	 */
	@FXML
	void MediumSizebtn(ActionEvent event) {
		if (MediumSize.isSelected()) {

			sizedlag = true;
			SmallSize.setSelected(false);
			ExtraLargeSize.setSelected(false);
			largeSize.setSelected(false);
			itemtoadd.setSize("M");
			sizeselectedflag = true;
			for (int i = 0; i < TheSize.size(); i++) {
				if (TheSize.get(i).getTopping().equals("M")) {
					sizeprice = TheSize.get(i).getPrice();
				}

			}
		} else
			sizedlag = false;

	}

	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified level of coocking is choosen and update in accordance
	 *output: null
	 * @param event
	 */
	@FXML
	void Mediumbtn(ActionEvent event) {
		if (Medium.isSelected()) {
			levelofcocking = true;

			WD.setSelected(false);
			Rare.setSelected(false);

			itemtoadd.setLevelOfCoocking("Medium");
		} else
			levelofcocking = false;
	}

	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified level of coocking is choosen and update in accordance
	 *output: null
	 * @param event
	 */
	@FXML
	void Rarebtn(ActionEvent event) {
		if (Rare.isSelected()) {
			levelofcocking = true;

			WD.setSelected(false);
			Medium.setSelected(false);

			itemtoadd.setLevelOfCoocking("Rare");

			System.out.println(itemtoadd.getLevelOfCoocking());
		} else
			levelofcocking = false;
	}
	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified size is choosen and update in accordance
	 *output: null
	 * @param event
	 */

	@FXML
	void SmallSizebtn(ActionEvent event) {
		if (SmallSize.isSelected()) {

			sizedlag = true;
			ExtraLargeSize.setSelected(false);
			MediumSize.setSelected(false);
			largeSize.setSelected(false);
			itemtoadd.setSize("S");
			sizeselectedflag = true;

			for (int i = 0; i < TheSize.size(); i++) {
				if (TheSize.get(i).getTopping().equals("S")) {
					sizeprice = TheSize.get(i).getPrice();
				}

			}

		} else
			sizedlag = false;
	}

	@FXML
	void Top1btn(ActionEvent event) {

	}

	@FXML
	void Top2btn(ActionEvent event) {

	}

	@FXML
	void Top3btn(ActionEvent event) {

	}

	@FXML
	void Top4btn(ActionEvent event) {

	}

	@FXML
	void Top5btn(ActionEvent event) {

	}

	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified  level of coocking is choosen and update in accordance
	 *output: null
	 * @param event
	 */
	@FXML
	void WellDonebtn(ActionEvent event) {
		if (WD.isSelected()) {

			levelofcocking = true;

			Medium.setSelected(false);
			Rare.setSelected(false);
			itemtoadd.setLevelOfCoocking("Well Done");
		} else
			levelofcocking = false;
	}

	
	/**@author ayal
	 * input:ActionEvent \
	 *funtionality: check if the specified size is choosen and update in accordance
	 *output: null
	 * @param event
	 */
	@FXML
	void largeSizebtn(ActionEvent event) {
		if (largeSize.isSelected()) {

			sizedlag = true;
			SmallSize.setSelected(false);
			MediumSize.setSelected(false);
			ExtraLargeSize.setSelected(false);
			sizeselectedflag = true;
			itemtoadd.setSize("L");

			for (int i = 0; i < TheSize.size(); i++) {
				if (TheSize.get(i).getTopping().equals("L")) {
					sizeprice = TheSize.get(i).getPrice();
				}

			}
		} else
			sizedlag = false;

	}

	/**
	 * input:null
	 * functionality:Check the option he chose and accordingly add to the
	 * table the item with the selected additions, if it already exists then just
	 * change its quantity.
	 * output: null
	 * 
	 * @param event
	 */
	@FXML
	void AddItemBtn(ActionEvent event) {
		flag = false;
		if (MealSizePane.isDisable()) {
			flag = levelofcocking;
		} else if (LevelOfCookinPane.isDisable()) {
			flag = sizedlag;
		} else {
			flag = levelofcocking && sizedlag;
		}

		if (flag) {
			topingsprice = 0.0;
			String topingsasstring = "";
			selectedtopings.clear();
			addflag = false;

			if (Top1.isSelected()) {
				String str = Top1.getText().split(" ", 2)[0];

				selectedtopings.add(str);

				System.out.println(selectedtopings.toString());

				for (int i = 0; i < Thetopings.size(); i++) {

					if (Thetopings.get(i).getTopping().equals(Top1.getText().split(" ", 2)[0]))
						topingsprice = topingsprice + Thetopings.get(i).getPrice();

				}
			}
			if (Top2.isSelected()) {
				selectedtopings.add(Top2.getText().split(" ", 2)[0]);

				for (int i = 0; i < Thetopings.size(); i++) {
					if (Thetopings.get(i).getTopping().equals(Top2.getText().split(" ", 2)[0]))
						topingsprice = topingsprice + Thetopings.get(i).getPrice();
				}

			}
			if (Top3.isSelected()) {

				selectedtopings.add(Top3.getText().split(" ", 2)[0]);
				{
					for (int i = 0; i < Thetopings.size(); i++) {
						if (Thetopings.get(i).getTopping().equals(Top3.getText().split(" ", 2)[0]))
							topingsprice = topingsprice + Thetopings.get(i).getPrice();
					}
				}
			}
			if (Top4.isSelected()) {
				selectedtopings.add(Top4.getText().split(" ", 2)[0]);

				for (int i = 0; i < Thetopings.size(); i++) {
					if (Thetopings.get(i).getTopping().equals(Top4.getText().split(" ", 2)[0]))
						topingsprice = topingsprice + Thetopings.get(i).getPrice();
				}
			}
			if (Top5.isSelected()) {

				selectedtopings.add(Top5.getText().split(" ", 2)[0]);
				for (int i = 0; i < Thetopings.size(); i++) {
					if (Thetopings.get(i).getTopping().equals(Top5.getText().split(" ", 2)[0]))
						topingsprice = topingsprice + Thetopings.get(i).getPrice();
				}
			}

			for (int i = 0; i < selectedtopings.size(); i++) {
				if (selectedtopings.size() - 1 == i) {
					topingsasstring = topingsasstring + selectedtopings.get(i) + ".";
				} else {
					topingsasstring = topingsasstring + selectedtopings.get(i) + ",";
				}
			}

			itemtoadd.settopings(topingsasstring);
			itemtoadd.setPrice(
					(SetPrice(ResMenuForCustomersController.selecteditem.getPrice())) + topingsprice + sizeprice);
			itemtoadd.setDescription(DescriptionText.getText());
			itemtoadd.setQty(1);
			itemtoadd.setItemName(ResMenuForCustomersController.selecteditem.getName());

			for (int i = 0; i < ResMenuForCustomersController.CartList.size(); i++) {
				if (ResMenuForCustomersController.CartList.get(i).getItemName().equals(itemtoadd.getItemName())
						&& ResMenuForCustomersController.CartList.get(i).getSize().equals(itemtoadd.getSize())
						&& ResMenuForCustomersController.CartList.get(i).getTopings().equals(itemtoadd.getTopings())
						&& ResMenuForCustomersController.CartList.get(i).getLevelOfCoocking()
								.equals(itemtoadd.getLevelOfCoocking())
						&& ResMenuForCustomersController.CartList.get(i).getDescription()
								.equals(itemtoadd.getDescription())) {

					ResMenuForCustomersController.CartList.get(i)
							.setQty(ResMenuForCustomersController.CartList.get(i).getQty() + 1);

					((Node) event.getSource()).getScene().getWindow().hide();

					addflag = true;
				}

			}

			if (!addflag) {
				itemtoadd.setType(ResMenuForCustomersController.selecteditem.getType());
				ResMenuForCustomersController.CartList.add(itemtoadd);

				((Node) event.getSource()).getScene().getWindow().hide();
			}
		} else {
			MessageBox.DisplayMessage("Please Choose The Relevant fields", "Warning", "", AlertType.WARNING);
		}

	}

	double SetPrice(String str) {
		if (str.contains("/"))
			return 0.0;
		else
			return Double.parseDouble(str);
	}

	/**
	 * initialize the toppings page , according the information that he received
	 * from server
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		levelofcocking = false;
		sizedlag = false;
		itemtoadd = new OrderView(" ", 0.0, 0, "DEFAULT", "DEFAULT", " ", " ");
		SmallSize.setDisable(true);
		MediumSize.setDisable(true);
		largeSize.setDisable(true);
		ExtraLargeSize.setDisable(true);
		System.out.println(ResMenuForCustomersController.TopingsFlag);
		

		if (!ResMenuForCustomersController.SizeFlag) {

			MealSizePane.setDisable(true);

		} else {

			for (int i = 0; i < TheSize.size(); i++) {

				if (TheSize.get(i).getTopping().equals("S")) {
					SmallSize.setDisable(false);
				}
				if (TheSize.get(i).getTopping().equals("M")) {
					MediumSize.setDisable(false);
				}
				if (TheSize.get(i).getTopping().equals("L")) {
					largeSize.setDisable(false);
				}
				if (TheSize.get(i).getTopping().equals("XL")) {
					ExtraLargeSize.setDisable(false);
				}

			}

		}
		if (!ResMenuForCustomersController.CoockingFlag) {

			LevelOfCookinPane.setDisable(true);
		}

		if (!ResMenuForCustomersController.TopingsFlag) {

			Topingspane.setDisable(true);
		} else {
			int j = Thetopings.size();

			switch (j) {

			case 1:

				Top1.setText(Thetopings.get(0).getTopping() + " " + "+" + Thetopings.get(0).getPrice());
				Top2.setVisible(false);
				Top3.setVisible(false);
				Top4.setVisible(false);
				Top5.setVisible(false);

				break;
			case 2:
				Top1.setText(Thetopings.get(0).getTopping() + " " + "+" + Thetopings.get(0).getPrice());
				Top2.setText(Thetopings.get(1).getTopping() + " " + "+" + Thetopings.get(1).getPrice());
				Top3.setVisible(false);
				Top4.setVisible(false);
				Top5.setVisible(false);
				break;
			case 3:
				Top1.setText(Thetopings.get(0).getTopping() + " " + "+" + Thetopings.get(0).getPrice());
				Top2.setText(Thetopings.get(1).getTopping() + " " + "+" + Thetopings.get(1).getPrice());
				Top3.setText(Thetopings.get(2).getTopping() + " " + "+" + Thetopings.get(2).getPrice());
				Top4.setVisible(false);
				Top5.setVisible(false);
				break;
			case 4:
				Top1.setText(Thetopings.get(0).getTopping() + " " + "+" + Thetopings.get(0).getPrice());
				Top2.setText(Thetopings.get(1).getTopping() + " " + "+" + Thetopings.get(1).getPrice());
				Top3.setText(Thetopings.get(2).getTopping() + " " + "+" + Thetopings.get(2).getPrice());
				Top4.setText(Thetopings.get(3).getTopping() + " " + "+" + Thetopings.get(3).getPrice());
				Top5.setVisible(false);
				break;
			case 5:
				Top1.setText(Thetopings.get(0).getTopping() + " " + "+" + Thetopings.get(0).getPrice());
				Top2.setText(Thetopings.get(1).getTopping() + " " + "+" + Thetopings.get(1).getPrice());
				Top3.setText(Thetopings.get(2).getTopping() + " " + "+" + Thetopings.get(2).getPrice());
				Top4.setText(Thetopings.get(3).getTopping() + " " + "+" + Thetopings.get(3).getPrice());
				Top5.setText(Thetopings.get(4).getTopping() + " " + "+" + Thetopings.get(4).getPrice());
				break;
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();

		Pane root = loader.load(getClass().getResource("/gui/AdditionalChoices.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Additional Choices");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
