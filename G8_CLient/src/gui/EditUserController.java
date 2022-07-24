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
import logic.Item;
import logic.Request;
import logic.User;
import logic.UserForStatus;

public class EditUserController extends Application implements Initializable {
	public static ArrayList<UserForStatus> arr;
	public static ObservableList<UserForStatus> list;
	static String last;
	static String first;
	static String status;
	static int id;
	
	static UserForStatus current;
	@FXML
	private TableColumn<UserForStatus, String> colfirst;

	@FXML
	private TableColumn<UserForStatus, Integer> colid;

	@FXML
	private TableColumn<UserForStatus, String> collast;

	@FXML
	private TableColumn<UserForStatus, String> colstatus;

	@FXML
	private Label labelh1;

	@FXML
	private Label labelsel;
	
	@FXML
	private Label labelerr;
	
	@FXML
	private TableView<UserForStatus> tableuser;

	
	
	//back button return to the previous page 
	@FXML
	void BackButton(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		BMScreenController bm = new BMScreenController();
		Stage primaryStage = new Stage();
		bm.start(primaryStage);
	}
/**
 * @author gethe
 * this function saves the selected user from table saves its Features in relevant variables*/
	@FXML
	void getSelectedUser(MouseEvent event) {
		UserForStatus users = tableuser.getSelectionModel().getSelectedItem();
		labelerr.setText("");
		labelsel.setText("User ID: " + users.getId() + ",User First Name: " + users.getFirst() + ",User Last Name: "
				+ users.getLast() + ",User Status: " + users.getStatus());
		id = users.getId();
		first = users.getFirst();
		last = users.getLast();
		status = users.getStatus();
	}
	
	/**
	 * @author gethe
	 * this function checks if there is a selected user then send it to user to delete it from data base*/
	@FXML
	void DeleteUser(ActionEvent event) {
		current=tableuser.getSelectionModel().getSelectedItem();
		if (current == null)
			labelerr.setText("You have to choose a User to delete");
		else {
			ConnectFormController.chat.accept(new Request("Delete User", id));
			ConnectFormController.chat.accept(new Request("view users list", null));
			list.clear();
			loadData();
			tableuser.refresh();
		}
	}
	/**
	 * @author gethe
	 * this function changes the user status from frozen to active or from active to frozen*/
	@FXML
	void EditUser(ActionEvent event) throws Exception {
		if (labelsel.getText().isEmpty())
			labelerr.setText("You have to choose an user to edit");
		else {
			((Node) event.getSource()).getScene().getWindow().hide();
			SetStatusController eu = new SetStatusController();
			Stage primaryStage = new Stage();
			eu.start(primaryStage);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/EditUser.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("edit user");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 * @author gethe
	 * this function loads the data to the table view */
	private void loadData() {
		ConnectFormController.chat.accept(new Request("get users status", BMScreenController.loc));
		list = FXCollections.observableArrayList(arr);
		colid.setCellValueFactory(new PropertyValueFactory<UserForStatus, Integer>("id"));
		colfirst.setCellValueFactory(new PropertyValueFactory<UserForStatus, String>("first"));
		collast.setCellValueFactory(new PropertyValueFactory<UserForStatus, String>("last"));
		colstatus.setCellValueFactory(new PropertyValueFactory<UserForStatus, String>("status"));
		tableuser.setItems(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelh1.setText("Users for (" + BMScreenController.loc + ")");
		loadData();

	}

}
