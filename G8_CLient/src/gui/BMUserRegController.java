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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Request;
import logic.User;
import logic.UserForImport;

public class BMUserRegController extends Application implements Initializable {
	public static ArrayList<UserForImport> users = new ArrayList<>();
	public static ObservableList<UserForImport> list;
	public static String lblString;
	public static boolean w4cflag;
	@FXML
	private TableColumn<UserForImport, String> Fname;

	@FXML
	private TableColumn<UserForImport, Integer> Id;

	@FXML
	private TableColumn<UserForImport, String> Lname;

	@FXML
	private TableColumn<UserForImport, String> Pass;

	@FXML
	private TextField SearchField;

	@FXML
	private TextField w4cField;

	@FXML
	private TextField PaymentField;

	@FXML
	private Label w4clbl;

	@FXML
	private Label Paymentlbl;

	@FXML
	private Label errlbl;

	@FXML
	private TableView<UserForImport> UserTable;

	@FXML
	private TableColumn<UserForImport, String> Username;

	@FXML
	private TableColumn<UserForImport, String> email;

	@FXML
	private TableColumn<UserForImport, String> phone;

	@FXML
	private TableColumn<UserForImport, String> role;

	/**
	 * @author salmanamer input: ActionEvent instance functionality: if the BM
	 *         manager has selected a user it will accept it and insert it to the
	 *         system according to his role output: null
	 */
	@FXML
	void Accept(ActionEvent event) {
		if (!w4cField.getText().equals("")) {
			UserForImport selected = UserTable.getSelectionModel().getSelectedItem();
			if (selected == null) {
				errlbl.setText("Choose a User first");
			} else {
				selected.setW4c(w4cField.getText());
				if (selected.getRole().equals("Costumer") && selected.getBus() != null) {
					ConnectFormController.chat
							.accept(new Request("Waiting Business User	" + PaymentField.getText(), selected));

				} else {

					ConnectFormController.chat.accept(new Request("Allow User", selected));
				}
				errlbl.setText(lblString);
				ConnectFormController.chat.accept(new Request("Waiting Approval List", BMScreenController.loc));

				loadData();
			}
		} else
			errlbl.setText("Enter a W4C Code");
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it will hide
	 *         the page output: null
	 */
	@FXML
	void Back(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: every time the
	 *         BM manager clicks on the table it will save the user and it will
	 *         prepare some text fields and labels according to what the user's role
	 *         output: null
	 */
	@FXML
	void Select(MouseEvent event) {
		UserForImport selected = UserTable.getSelectionModel().getSelectedItem();
		w4cField.setVisible(true);
		w4clbl.setVisible(true);
		System.out.println("cutomer is a:" + selected.getRole() + " and he have a business name :" + selected.getBus());
		if (selected.getRole().equals("Costumer") && selected.getBus() != null) {
			PaymentField.setVisible(true);
			Paymentlbl.setVisible(true);
		} else {
			PaymentField.setVisible(false);
			Paymentlbl.setVisible(false);

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
			Predicate<UserForImport> byID = user -> Integer.toString(user.getId()).startsWith(SearchField.getText());
			ArrayList<UserForImport> filteredArrayList = (ArrayList<UserForImport>) users.stream().filter(byID)
					.collect(Collectors.toList());
			list = FXCollections.observableArrayList(filteredArrayList);
			Id.setCellValueFactory(new PropertyValueFactory<UserForImport, Integer>("id"));
			Username.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("Username"));
			Pass.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("Password"));
			Fname.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("fname"));
			Lname.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("lname"));
			email.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("email"));
			phone.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("phone"));
			role.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("Role"));
			UserTable.setItems(list);
			ConnectFormController.chat.accept(new Request("Waiting Approval List", BMScreenController.loc));
		} else
			loadData();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		PaymentField.setVisible(false);
		Paymentlbl.setVisible(false);
		w4cField.setVisible(false);
		w4clbl.setVisible(false);
		ConnectFormController.chat.accept(new Request("Waiting Approval List", BMScreenController.loc));
		loadData();
	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: loads the data
	 *         into the user table output: null
	 */
	private void loadData() {
		// TODO Auto-generated method stub
		list = FXCollections.observableArrayList(users);
		Id.setCellValueFactory(new PropertyValueFactory<UserForImport, Integer>("id"));
		Username.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("Username"));
		Pass.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("Password"));
		Fname.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("fname"));
		Lname.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("lname"));
		email.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("email"));
		phone.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("phone"));
		role.setCellValueFactory(new PropertyValueFactory<UserForImport, String>("Role"));
		UserTable.setItems(list);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/BMUserReg.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("List View");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * @author salmanamer input: ActionEvent instance functionality: it checks if
	 *         the W4C Code is valid and turns the border green or red according to
	 *         the result output: null
	 */
	@FXML
	void CheckW4C(MouseEvent event) {
		ConnectFormController.chat.accept(new Request("Check W4C", w4cField.getText()));
		if (!w4cflag)
			w4cField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000; -fx-background-radius: 30;");

		else
			w4cField.setStyle("-fx-text-box-border: #00ff00; -fx-focus-color: #00ff00; -fx-background-radius: 30;");

	}

}
