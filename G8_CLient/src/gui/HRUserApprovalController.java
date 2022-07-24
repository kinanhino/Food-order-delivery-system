package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.BusinessUser;
import logic.Request;

public class HRUserApprovalController extends Application implements Initializable {
	public static ArrayList<BusinessUser> users = new ArrayList<>();
	public static ObservableList<BusinessUser> list;
	public static String lblString;
	@FXML
	private TableColumn<BusinessUser, String> Fname;

	@FXML
	private TableColumn<BusinessUser, Integer> Id;

	@FXML
	private TableColumn<BusinessUser, String> Lname;

	@FXML
	private TableColumn<BusinessUser, String> Pass;

	@FXML
	private TextField SearchField;

	@FXML
	private Label errlbl;

	@FXML
	private TableView<BusinessUser> UserTable;

	@FXML
	private TableColumn<BusinessUser, String> Username;

	@FXML
	private TableColumn<BusinessUser, String> email;

	@FXML
	private TableColumn<BusinessUser, String> phone;

	@FXML
	private TableColumn<BusinessUser, Integer> PaymentLimit;

	/**
	 * @author salmanamer input: ActionEvent instance functionality: if the HR
	 *         manager has selected a Business user it will accept it and insert it
	 *         to the system as Business User output: null
	 */
	@FXML
	void Accept(ActionEvent event) {
		BusinessUser selected = UserTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			errlbl.setText("Choose a User first");
		} else {
			ConnectFormController.chat.accept(new Request("Allow BusinessUser", selected));
			errlbl.setText(lblString);
			ConnectFormController.chat.accept(new Request("Waiting HR Approval List", HRMScreenController.business));
			loadData();
		}
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it will hide
	 *         the page output: null
	 */
	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		HRMScreenController hr = new HRMScreenController();
		Stage primaryStage = new Stage();
		hr.start(primaryStage);
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: if the BM
	 *         manager has selected a user it will Reject it and remove it from the
	 *         table output: null
	 */
	@FXML
	void Reject(ActionEvent event) {
		BusinessUser selected = UserTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			errlbl.setText("Choose a User first");
		} else {
			ConnectFormController.chat.accept(new Request("Reject BusinessUser", selected));
			errlbl.setText(lblString);
			ConnectFormController.chat.accept(new Request("Waiting HR Approval List", HRMScreenController.business));
			loadData();
		}
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it will filter
	 *         the ArrayList that is showed in the table according to what is the
	 *         prefix of his id number output: null
	 */
	@FXML
	void Search(KeyEvent event) {
		if (!SearchField.getText().isEmpty()) {
			Predicate<BusinessUser> byID = user -> Integer.toString(user.getId()).startsWith(SearchField.getText());
			ArrayList<BusinessUser> filteredArrayList = (ArrayList<BusinessUser>) users.stream().filter(byID)
					.collect(Collectors.toList());
			list = FXCollections.observableArrayList(filteredArrayList);
			Id.setCellValueFactory(new PropertyValueFactory<BusinessUser, Integer>("id"));
			Username.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("Username"));
			Pass.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("Password"));
			Fname.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("fname"));
			Lname.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("lname"));
			email.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("email"));
			phone.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("phone"));
			PaymentLimit.setCellValueFactory(new PropertyValueFactory<BusinessUser, Integer>("paymentlimit"));
			UserTable.setItems(list);
			ConnectFormController.chat.accept(new Request("Waiting HR Approval List", HRMScreenController.business));
		} else
			loadData();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ConnectFormController.chat.accept(new Request("Waiting HR Approval List", HRMScreenController.business));
		loadData();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: loads the data
	 *         into the user table output: null
	 */
	private void loadData() {
		// TODO Auto-generated method stub
		System.out.print(users);
		list = FXCollections.observableArrayList(users);
		Id.setCellValueFactory(new PropertyValueFactory<BusinessUser, Integer>("id"));
		Username.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("Username"));
		Pass.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("Password"));
		Fname.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("fname"));
		Lname.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("lname"));
		email.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("email"));
		phone.setCellValueFactory(new PropertyValueFactory<BusinessUser, String>("phone"));
		PaymentLimit.setCellValueFactory(new PropertyValueFactory<BusinessUser, Integer>("paymentlimit"));
		UserTable.setItems(list);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/HRUserApproval.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("List View");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
