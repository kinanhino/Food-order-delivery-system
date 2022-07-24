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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Business;
import logic.Request;

public class BusinessConfirmationController extends Application implements Initializable {
	public static ArrayList<Business> businesses = new ArrayList<>();
	public static ObservableList<Business> list;
	@FXML
	private TableView<Business> BusTable;

	@FXML
	private TableColumn<Business, Integer> id;

	@FXML
	private TableColumn<Business, String> name;

	/**
	 * @author salmanamer input: ActionEvent instance functionality:it will accept
	 *         the Business and remove it from the table output: null
	 */
	@FXML
	void Acception(ActionEvent event) {
		ConnectFormController.chat
				.accept(new Request("Accept Business", BusTable.getSelectionModel().getSelectedItem()));
		loadData();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality:it will reject
	 *         the Business and remove it from the table output: null
	 */
	@FXML
	void Rejection(ActionEvent event) {
		ConnectFormController.chat
				.accept(new Request("Reject Business", BusTable.getSelectionModel().getSelectedItem()));
		loadData();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality:it will hide the
	 *         page output: null
	 */
	@FXML
	void Back(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();

	}
	/**
	 * @author salmanamer input: ActionEvent instance functionality: loads the data
	 *         into the Business table output: null
	 */
	private void loadData() {
		ConnectFormController.chat.accept(new Request("Waiting Business", BMScreenController.user.getLocation()));
		list = FXCollections.observableArrayList(businesses);
		id.setCellValueFactory(new PropertyValueFactory<Business, Integer>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Business, String>("name"));
		BusTable.setItems(list);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		Pane root = loader.load(getClass().getResource("/gui/BussinessConfirmation.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("Confirm");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
