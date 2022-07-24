package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Item;
import logic.ItemForView;
import logic.OrderView;
import logic.Request;
import logic.Toppings;

public class ResMenuForCustomersController extends Application implements Initializable {
	static public boolean haveflag;
	static public boolean SizeFlag;
	static public boolean CoockingFlag;
	static public boolean TopingsFlag;
	static ItemForView selecteditem;

	int qtycurrvalue;
	final int initialvalue = 1;
	static String ResName;
	public static int id;
	public static Double totalprice = 0.0;
	public static String name;
	public static String type;
	public static Double price;
	public static ObservableList<ItemForView> list;
	public static ArrayList<OrderView> CartListAsArrayList;
	public static ObservableList<ItemForView> ChoosenList = FXCollections.observableArrayList();
	public static ArrayList<Item> items = new ArrayList<>();
	public ArrayList<Item> order = new ArrayList<Item>();
	ArrayList<ItemForView> itemsforview = new ArrayList<>();
	public static ObservableList<String> types = FXCollections.observableArrayList("All", "FirstMeal", "MainMeal",
			"Dessert", "Drinks", "Salad");
	public static String TypeStr;
	public static ArrayList<Toppings> TheSize;
	public static ObservableList<OrderView> CartList = FXCollections.observableArrayList();
	public static Stage respage;
	SpinnerValueFactory<Integer> valuefactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
	@FXML
	private TableColumn<ItemForView, Integer> IDCol;

	@FXML
	private TableView<ItemForView> ItemsTable;

	@FXML
	private TableColumn<ItemForView, String> NameCol;

	@FXML
	private TableColumn<ItemForView, String> PriceCol;

	@FXML
	private TableColumn<ItemForView, String> TypeCol;

	@FXML
	private ComboBox<String> CategoryList;

	@FXML
	private Button ShowItems;

	@FXML
	private Button checkout;

	@FXML
	private Label OrderLabel;

	@FXML
	private Label labelRes;

	@FXML
	private Label labelsel;

	@FXML
	private Label labelerr;

	@FXML
	private Label labelfororder;

	

	@FXML
	private Spinner<Integer> qtybtn;

	@FXML
	private TableView<OrderView> OrderTable;

	@FXML
	private TableColumn<OrderView, String> ItemNameCol;

	@FXML
	private TableColumn<OrderView, Double> ItemPriceCol;

	@FXML
	private TableColumn<OrderView, Integer> QtyCol;

	@FXML
	private TableColumn<OrderView, String> SizeICol;

	@FXML
	private TableColumn<OrderView, String> LevelOfCoockingCol;

	@FXML
	private TableColumn<OrderView, String> TopingsCol;

	@FXML
	private TableColumn<OrderView, String> DescriptionCol;

	@FXML
	private Button AddQty;

	@FXML
	private Button RemoveItem;

	@FXML
	void AddItem(ActionEvent event) throws Exception {

		totalprice = 0.0;
		
		System.out.println("the totalprice is:" + totalprice.toString());
		haveflag = false;
		if (labelsel.getText().isEmpty())
			labelerr.setText("You have to choose an item to add");

		else {

			selecteditem = ItemsTable.getSelectionModel().getSelectedItem();

			ConnectFormController.chat.accept(new Request("Get Flags	" + NewOrderController.resID,
					new Item(selecteditem.getId(), selecteditem.getName(), selecteditem.getType(),
							setpricefromstring(selecteditem), false, false, false)));

			if (SizeFlag || CoockingFlag || TopingsFlag) {

				if (SizeFlag) {
					ConnectFormController.chat.accept(new Request("Get Sizes	" + NewOrderController.resID,
							new Item(selecteditem.getId(), selecteditem.getName(), selecteditem.getType(),
									setpricefromstring(selecteditem), SizeFlag, CoockingFlag, TopingsFlag)));

				}

				if (TopingsFlag) {
					ConnectFormController.chat.accept(new Request("Get Toppings	" + NewOrderController.resID,
							new Item(selecteditem.getId(), selecteditem.getName(), selecteditem.getType(),
									setpricefromstring(selecteditem), SizeFlag, CoockingFlag, TopingsFlag)));
				}

				AdditionalChoicesController ACC = new AdditionalChoicesController();
				Stage primaryStage = new Stage();
				ACC.start(primaryStage);
				RefreshTableThread();
				OrderTable.refresh();
				OrderTable.setItems(CartList);

			} else {

				OrderView toaddwithoutadditional = new OrderView(selecteditem.getName(),
						Double.parseDouble(selecteditem.getPrice()), 1, "DEFAULT", "DEFAULT", "", "");
				toaddwithoutadditional.setType(selecteditem.getType());
				for (int i = 0; i < CartList.size(); i++) {
					if (CartList.get(i).getItemName().equals(selecteditem.getName())) {
						CartList.get(i).setQty(CartList.get(i).getQty() + 1);
						haveflag = true;
					}
				}

				if (!haveflag) {
					CartList.add(toaddwithoutadditional);
				}

			}

			OrderTable.setItems(CartList);
			OrderTable.refresh();
			printPrice();

			labelerr.setText("Item Successfully Added");
			
			valuefactory.setValue(1);
			qtybtn.setValueFactory(valuefactory);
		}
	}

	@FXML
	void GetSeletedItem(MouseEvent event) {
		if (list != null) {
			ItemForView item = ItemsTable.getSelectionModel().getSelectedItem();
			labelerr.setText("");
			labelsel.setText("Item Name: " + item.getName() + ",Item Type: " + item.getType() + ",Item Price: "
					+ item.getPrice());
			id = item.getId();
			name = item.getName();
			type = item.getType();
			price = setpricefromstring(item);

		}
	}

	double setpricefromstring(ItemForView item) {
		if (item.getPrice().contains("/"))
			return 0.0;
		else
			return Double.parseDouble(item.getPrice());
	}

	@FXML
	void GetSelectedFromOrder(MouseEvent event) {
		OrderView ItemOrdered = OrderTable.getSelectionModel().getSelectedItem();

		OrderLabel.setText("Item Name: " + ItemOrdered.getItemName() + "Qty:" + ItemOrdered.getQty());

	}

	@FXML
	void AddQtyBtn(ActionEvent event) {

		OrderView AddItemQty = OrderTable.getSelectionModel().getSelectedItem();
		if (OrderLabel.getText().isEmpty()) {
			labelerr.setText("You have to choose an item to Edit");
		}
		for (int i = 0; i < CartList.size(); i++) {
			if (CartList.get(i).equals(AddItemQty)) {
				CartList.get(i).setQty(CartList.get(i).getQty() + qtybtn.getValue());
				System.out.println(CartList.get(i).getQty());
			}

		}

		OrderTable.setItems(CartList);
		OrderTable.refresh();
		valuefactory.setValue(1);
		qtybtn.setValueFactory(valuefactory);

//		for (int i = 0; i < CartList.size(); i++) {
//			totalprice = totalprice + (CartList.get(i).getPrice() * CartList.get(i).getQty());
//		}
		printPrice();
	}

	@FXML
	void RemoveItemBtn(ActionEvent event) {
		totalprice = 0.0;

		OrderView ItemToRemove = OrderTable.getSelectionModel().getSelectedItem();

		if (labelsel.getText().isEmpty())
			labelerr.setText("You have to choose an item to Remove");
		else {

			int toremove = qtybtn.getValue();

			for (int i = 0; i < CartList.size(); i++) {
				int currqty = CartList.get(i).getQty();
				if (CartList.get(i).equals(ItemToRemove)) {

					if (currqty > toremove) {

						CartList.get(i).setQty(CartList.get(i).getQty() - toremove);
					}

					else if (currqty == toremove) {
						CartList.remove(ItemToRemove);

					} else {
						labelerr.setText("The quantity of this item is smaller in your order");
					}
				}
			}

			OrderTable.setItems(CartList);
			OrderTable.refresh();

			printPrice();
			valuefactory.setValue(1);
			qtybtn.setValueFactory(valuefactory);
			

		}

	}

	@FXML
	void BackBtn(ActionEvent event) throws Exception {
		CartList.clear();

		((Node) event.getSource()).getScene().getWindow().hide();
		NewOrderController NOC = new NewOrderController();
		Stage primaryStage = new Stage();
		NOC.start(primaryStage);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		labelRes.setText("Menu for " + NewOrderController.resname);
		loadData();
		// RefreshTableThread();
		qtybtn.setValueFactory(valuefactory);

	}

	public void printPrice() {
		for (int i = 0; i < CartList.size(); i++) {
			totalprice = totalprice + (CartList.get(i).getPrice() * CartList.get(i).getQty());

		}
		
	}

	private void RefreshTableThread() {
		Thread t = new Thread() {
			public void run() {
				while (true) {
					OrderTable.refresh();
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();

	}

	void setup() {
		for (int i = 0; i < items.size(); i++) {
			itemsforview.add(new ItemForView(items.get(i).getId(), items.get(i).getName(), items.get(i).getType(),
					pricetostring(items.get(i))));
		}
	}

	private String pricetostring(Item price2) {
		// TODO Auto-generated method stub
		if (price2.getPrice() != 0) {
			return Double.toString(price2.getPrice());
		} else {
			ConnectFormController.chat.accept(new Request("Get Sizes\t" + NewOrderController.resID, price2));
			String newprice = "" + (TheSize.get(TheSize.size() - 1)).getPrice();
			for (int i = TheSize.size() - 2; i >= 0; i--) {
				newprice += "/" + TheSize.get(i).getPrice();
			}

			return newprice;
		}
	}

	private void loadData() {
		// TODO Auto-generated method stub
		setup();
		list = FXCollections.observableArrayList(itemsforview);
		IDCol.setCellValueFactory(new PropertyValueFactory<ItemForView, Integer>("id"));
		NameCol.setCellValueFactory(new PropertyValueFactory<ItemForView, String>("Name"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<ItemForView, String>("Type"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<ItemForView, String>("Price"));

		ItemsTable.setItems(list);
		CategoryList.setItems(types);

		ItemNameCol.setCellValueFactory(new PropertyValueFactory<OrderView, String>("ItemName"));
		ItemPriceCol.setCellValueFactory(new PropertyValueFactory<OrderView, Double>("Price"));
		QtyCol.setCellValueFactory(new PropertyValueFactory<OrderView, Integer>("Qty"));
		SizeICol.setCellValueFactory(new PropertyValueFactory<OrderView, String>("Size"));
		LevelOfCoockingCol.setCellValueFactory(new PropertyValueFactory<OrderView, String>("LevelOfCoocking"));
		TopingsCol.setCellValueFactory(new PropertyValueFactory<OrderView, String>("topings"));
		DescriptionCol.setCellValueFactory(new PropertyValueFactory<OrderView, String>("Description"));

	}

	@FXML
	void ShowRelevantItems(ActionEvent event) {
		itemsforview.clear();
		loadData();
		TypeStr = CategoryList.getSelectionModel().getSelectedItem();
		ChoosenList.clear();
		if (TypeStr.equals("All")) {
			
			list = FXCollections.observableArrayList(itemsforview);
			ItemsTable.setItems(list);
		}

		else {
			for (int i = 0; i < list.size(); i++) {
				if (TypeStr.equals(list.get(i).getType()))
					ChoosenList.add(list.get(i));
			}
			ItemsTable.setItems(ChoosenList);
		}

	}

	@FXML
	void payment(ActionEvent event) throws Exception {
		
		if (!CartList.isEmpty()) {
		CartListAsArrayList = new ArrayList<OrderView>();
		totalprice=0.0;
		for(int i=0;i<CartList.size();i++) {
			totalprice=totalprice+(CartList.get(i).getPrice()*CartList.get(i).getQty());
		}
       	
	
	  for (int i=0;i<CartList.size();i++) {
		  CartListAsArrayList.add(CartList.get(i));
	  }

	  
		MessageBox.DisplayMessage("Order Price="+totalprice, "Your Order", "", AlertType.INFORMATION);
	  
	


	    ((Node) event.getSource()).getScene().getWindow().hide();
		DeliveryDetailsController DDC = new DeliveryDetailsController();
		Stage primaryStage = new Stage();
		DDC.start(primaryStage);
		}
		else {
			MessageBox.DisplayMessage("Your Cart is Empty", "Warning", "",
					AlertType.WARNING);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		Pane root = loader.load(getClass().getResource("/gui/ResMenuForCustomers.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("MenuForCustomer");
		primaryStage.setScene(scene);
		primaryStage.show();
		respage = primaryStage;
	}

}
