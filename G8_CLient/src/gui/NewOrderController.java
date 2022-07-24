package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Item;
import logic.Request;
import logic.Restaurant;
import logic.User;

public class NewOrderController extends Application implements Initializable {

	ObservableList<String> list = FXCollections.observableArrayList();
	public static ArrayList<Restaurant> restaurants;

	public static String resname;
	public static int resID;
	int i;

	@FXML
	private Button logoutbutton;

	@FXML
	private ImageView logoutlogo;

	@FXML
	private ImageView bitemelogo;

	@FXML
	private TextField letsordertext;

	@FXML
	private ImageView hungrysmiley;

	@FXML
	private ImageView bbblogo;

	@FXML
	private ImageView burgerkinglogo;

	@FXML
	private ImageView shawarmalogo;

	@FXML
	private RadioButton allradio;

	@FXML
	private RadioButton homeradio;

	@FXML
	private ImageView fishandchipslogo;

	@FXML
	private ImageView milanologo;

	@FXML
	private ImageView steakhouselogo;

	@FXML
	private Button backbutton;

	@FXML
	private Label pickresturantlabel;

	@FXML
	private Button continuebutton;

	@FXML
	private Text hungrysen;

	@FXML
	private ImageView ordermenuimage;

	@FXML
	private Label yorchoiselabel;

	@FXML
	private ImageView contimage;

	@FXML
	private ComboBox<String> ResList;

	
	 
    /**
	 * @author ayal
	 *  input: ActionEvent instance 
	 *  functionality: it hides the page and return to previous screen 
	 *  output: null
	 */
	@FXML
	void BackBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		W4CController Us = new W4CController();
		Stage primaryStage = new Stage();
		Us.start(primaryStage);
	}

	
	/**
	 * input: ActionEvent
	 * functionality:check if the user have choose restaurant, save the restaurant id and continue to restaurant menu 
	 * output: null
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void continuebutton(ActionEvent event ) throws Exception {
		

		resname = ResList.getSelectionModel().getSelectedItem();
		
		if (resname!=null) {
		System.out.println(resname);
		System.out.println("restuarnts are");

		for (i = 0; i < restaurants.size(); i++) {
			System.out.println(restaurants.get(i).getRestaurantName());
			if (resname.equals(restaurants.get(i).getRestaurantName()))
				resID = restaurants.get(i).getRestaurantID();
		}

		ConnectFormController.chat.accept(new Request("show items in restaurant", resID));
		((Node) event.getSource()).getScene().getWindow().hide();
		ResMenuForCustomersController rmfc = new ResMenuForCustomersController();
		Stage primaryStage = new Stage();
		rmfc.start(primaryStage);
		}
		else {
			MessageBox.DisplayMessage("Please Choose Restaurant First.", "Warning", "",
					AlertType.WARNING);
			
		}

	}
	
	
	
	/**
	 * input: null
	 * functionality:load the restaurant menu according to location choosed
	 * output: null
	 * 
	 * 
	 */
	
	void loaddata() {
		list.clear();
		for (i = 0; i < restaurants.size(); i++)
			list.add(restaurants.get(i).getRestaurantName());

		ResList.setItems(list);
	}

	
	

	/**
	 * input: null
	 * functionality:load all restaurants from the server
	 * output: null
	 * @param event
	 * @throws Exception
	 */
	
	@FXML
	void getAllRestaurants(ActionEvent event) {
		if (allradio.isSelected()) {
			homeradio.setSelected(false);
			allradio.setSelected(true);
			ConnectFormController.chat.accept(new Request("Get Resturant List", null));
			loaddata();
		} else {
			homeradio.setSelected(true);
			allradio.setSelected(false);
			ConnectFormController.chat
					.accept(new Request("Get Resturant List with location", USERHSController.user.getLocation()));
			loaddata();
		}
	}
	
	/**
	 * input: null
	 * functionality:load home branch restaurants from the server
	 * output: null
	 * @param event
	 * @throws Exception
	 */

	@FXML
	void getHomeRestaurants(ActionEvent event) {
		if (homeradio.isSelected()) {
			homeradio.setSelected(true);
			allradio.setSelected(false);
			ConnectFormController.chat
					.accept(new Request("Get Resturant List with location", USERHSController.user.getLocation()));
			loaddata();
		} else {
			homeradio.setSelected(false);
			allradio.setSelected(true);
			ConnectFormController.chat.accept(new Request("Get Resturant List", null));
			loaddata();
		}
	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/NewOrder.fxml"));
		Scene scene = new Scene(root);
		// Stage primaryStage = new Stage();
		primaryStage.setTitle("Start a new order");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		homeradio.setSelected(true);
		allradio.setSelected(false);
		ConnectFormController.chat
				.accept(new Request("Get Resturant List with location", USERHSController.user.getLocation()));
		loaddata();

	}

	

}
