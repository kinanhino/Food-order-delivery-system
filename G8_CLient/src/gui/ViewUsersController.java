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
import logic.Request;
import logic.User;

public class ViewUsersController extends Application implements Initializable {
	public static ArrayList<User> users = new ArrayList<>();
	public static ObservableList<User> list;
	@FXML
	private TableColumn<User, String> EmailCol;

	static User current;
	@FXML
	private Label ErrLabel;

	@FXML
	private TableColumn<User, String> FnameCol;

	@FXML
	private TableColumn<User, String> IDCol;

	@FXML
	private TableColumn<User, String> LnameCol;

	@FXML
	private TableColumn<User, String> PassCol;

	@FXML
	private TableColumn<User, String> PhoneCol;

	@FXML
	private TableColumn<User, String> RoleCol;

	@FXML
	private TableColumn<User, String> USCol;

	@FXML
	private TableView<User> UsersTable;

	@FXML
	private TableColumn<User, String> W4cCol;



	@FXML
	void BackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	void DeleteUser(ActionEvent event) {
		if (current == null)
			ErrLabel.setText("You have to choose a User to delete");
		else {
			ConnectFormController.chat.accept(new Request("Delete User", current));
			ConnectFormController.chat.accept(new Request("view users list", null));
			list.clear();
			loadData();
			UsersTable.refresh();
		}
	}

	@FXML
	void GetSelectedUser(MouseEvent event) {
		current = UsersTable.getSelectionModel().getSelectedItem();
		ErrLabel.setText("");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		list = FXCollections.observableArrayList(users);
		IDCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		USCol.setCellValueFactory(new PropertyValueFactory<User, String>("Username"));
		PassCol.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		FnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("fname"));
		LnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lname"));
		EmailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		PhoneCol.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
		RoleCol.setCellValueFactory(new PropertyValueFactory<User, String>("Role"));
		W4cCol.setCellValueFactory(new PropertyValueFactory<User, String>("w4c"));
		UsersTable.setItems(list);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/ViewUsers.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("List View");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
