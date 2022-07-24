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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Choice;
import logic.Item;
import logic.Request;
import logic.Toppings;

public class RestaurantMenuController extends Application implements Initializable {
	public static boolean choicesflag;
	public static boolean[] flags;
	static String ResName;
	public static int id;
	public static String name;
	public static String type;
	public static Double price;
	public static ObservableList<Item> list;
	public static ArrayList<Item> items;
	public static ArrayList<Choice> toppings;
	@FXML
	private TableColumn<Item, Integer> IDCol;

	@FXML
	private TableView<Item> ItemsTable;

	@FXML
	private TableColumn<Item, String> NameCol;

	@FXML
	private TableColumn<Item, Double> PriceCol;

	@FXML
	private TableColumn<Item, String> TypeCol;

	@FXML
	private Label labelRes;

	@FXML
	private Label labelsel;

	@FXML
	private Label labelerr;

	@FXML
	void AddItem(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		AddItemController add = new AddItemController();
		Stage primaryStage = new Stage();
		add.start(primaryStage);
	}

	@FXML
	void BackBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		SupplierEditorController supp = new SupplierEditorController();
		Stage primaryStage = new Stage();
		supp.start(primaryStage);

	}

	@FXML
	void EditItem(ActionEvent event) throws Exception {

		if (labelsel.getText().isEmpty())
			labelerr.setText("You have to choose an item to edit");
		else {
			ConnectFormController.chat
					.accept(new Request("Check choices	" + SupplierEditorController.resturantID, id));
			System.out.println(
					"array of flags is ....size=" + flags[0] + " degree+ " + flags[1] + " toppings= " + flags[2]);
			choicesflag = flags[0] || flags[1] || flags[2];
			if (choicesflag) {
				System.out.println("id is:" + id);
				ConnectFormController.chat
						.accept(new Request("get choices	" + SupplierEditorController.resturantID, id));
				System.out.println("printing toppings arraylist............");
				System.out.println(toppings);
			}
			((Node) event.getSource()).getScene().getWindow().hide();
			EditItemController edit = new EditItemController();
			Stage primaryStage = new Stage();
			edit.start(primaryStage);
		}
	}

	@FXML
	void GetSeletedItem(MouseEvent event) {
		Item item = ItemsTable.getSelectionModel().getSelectedItem();
		labelerr.setText("");
		labelsel.setText("Item ID: " + item.getId() + ",Item Name: " + item.getName() + ",Item Type: " + item.getType()
				+ ",Item Price: " + item.getPrice());
		id = item.getId();
		name = item.getName();
		type = item.getType();
		price = item.getPrice();
	}

	@FXML
	void RemoveItem(ActionEvent event) {
		if (labelsel.getText().isEmpty())
			labelerr.setText("You have to choose an item to edit");
		else {
			ConnectFormController.chat
					.accept(new Request("remove item from menu	" + SupplierEditorController.resturantID,
							new Item(id, name, type, price, true, true, true)));
			ConnectFormController.chat
					.accept(new Request("show items in restaurant", SupplierEditorController.resturantID));
			list.clear();
			loadData();
			ItemsTable.refresh();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		labelRes.setText("Menu for " + ResName);
		loadData();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		list = FXCollections.observableArrayList(items);
		IDCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
		NameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Type"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
		ItemsTable.setItems(list);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		Pane root = loader.load(getClass().getResource("/gui/RestaurantMenu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
