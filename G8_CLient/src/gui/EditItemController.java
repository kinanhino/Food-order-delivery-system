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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Choice;
import logic.Item;
import logic.Request;
import logic.Toppings;

public class EditItemController extends Application implements Initializable {

	public static ObservableList<Choice> Choiceslist;
	public static ArrayList<Choice> Choicesarr;
	public static String status;

	@FXML
	private TableColumn<Choice, String> ChoiceCol;

	@FXML
	private Label AdditionalChoicesLabel;

	@FXML
	private TableView<Choice> ChoicesTable;

	@FXML
	private TableColumn<Choice, Double> PriceCol;

	@FXML
	private TextField PriceField;

	@FXML
	private Label IdLabel;

	@FXML
	private Label labelerr;

	@FXML
	private TextField NameField;

	@FXML
	private TextField ChoicePrice;

	@FXML
	private Button DeleteBtn;

	@FXML
	private Button PriceBtn;

	@FXML
	private ComboBox<String> Typecmb;

	ObservableList<String> list;

	ArrayList<String> al = new ArrayList<>();// list for combo box

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it loads the
	 *         data into the combo box output: null
	 */
	void setType() {

		al.add("MainMeal");
		al.add("Drinks");
		al.add("FirstMeal");
		al.add("Salad");
		al.add("Dessert");
		list = FXCollections.observableArrayList(al);
		Typecmb.setItems(list);
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it changes the
	 *         price of an item and reload the table output: null
	 */
	@FXML
	void ChangePrice(ActionEvent event) {
		if (!PriceField.getText().isEmpty()) {
			Choice c = ChoicesTable.getSelectionModel().getSelectedItem();
			ConnectFormController.chat.accept(new Request("Change choice price\t" + "	" + ChoicePrice.getText(), c));
			ConnectFormController.chat.accept(new Request("get choices	" + SupplierConfirmerController.resturantID,
					RestaurantMenuController.id));
			loadData();
		}
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it deletes an
	 *         item from the list and reload the table output: null
	 */
	@FXML
	void DeleteChoice(ActionEvent event) {
		Choice c = ChoicesTable.getSelectionModel().getSelectedItem();
		ConnectFormController.chat.accept(new Request("Delete choice\t", c));
		ConnectFormController.chat.accept(
				new Request("get choices	" + SupplierConfirmerController.resturantID, RestaurantMenuController.id));
		loadData();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it hides the
	 *         page and return to Restaurant menu page output: null
	 */
	@FXML
	void Cancel(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		RestaurantMenuController rm = new RestaurantMenuController();
		Stage primaryStage = new Stage();
		rm.start(primaryStage);

	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it saves all
	 *         the changes that occurred output: null
	 */
	@FXML
	void Save(ActionEvent event) {
		if (PriceField.getText().isEmpty() || NameField.getText().isEmpty()
				|| Typecmb.getSelectionModel().getSelectedItem().equals(null))
			labelerr.setText("You have to fill all Fields");
		ConnectFormController.chat
				.accept(new Request("edit item	" + Integer.toString(SupplierConfirmerController.resturantID),
						new Item(RestaurantMenuController.id, NameField.getText(),
								Typecmb.getSelectionModel().getSelectedItem(), Double.parseDouble(PriceField.getText()),
								true, true, true)));
		if (status.equals("item updated")) {
			labelerr.setText(status + " you can now go back to Restaurant Menu Page");
			ConnectFormController.chat
					.accept(new Request("show items in restaurant", SupplierConfirmerController.resturantID));
		} else
			labelerr.setText(status);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/EditItem.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("Edit");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it loads the
	 *         data into the table output: null
	 */
	private void loadData() {
		// TODO Auto-generated method stub
		Choiceslist = FXCollections.observableArrayList(Choicesarr);
		ChoiceCol.setCellValueFactory(new PropertyValueFactory<Choice, String>("name"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<Choice, Double>("price"));
		ChoicesTable.setItems(Choiceslist);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		IdLabel.setText(Integer.toString(RestaurantMenuController.id));
		NameField.setText(RestaurantMenuController.name);
		PriceField.setText(Double.toString(RestaurantMenuController.price));
		Typecmb.setValue(RestaurantMenuController.type);
		if (RestaurantMenuController.choicesflag)
			loadData();
		ChoicesTable.setVisible(RestaurantMenuController.choicesflag);
		DeleteBtn.setVisible(RestaurantMenuController.choicesflag);
		PriceBtn.setVisible(RestaurantMenuController.choicesflag);
		ChoicePrice.setVisible(RestaurantMenuController.choicesflag);
		AdditionalChoicesLabel.setVisible(RestaurantMenuController.choicesflag);
		if (!RestaurantMenuController.choicesflag)
			labelerr.setText("This Item does not have any choices");

		setType();
	}

}
