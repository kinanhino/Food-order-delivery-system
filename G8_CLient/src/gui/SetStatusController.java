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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Request;

public class SetStatusController extends Application implements Initializable {

	@FXML
	private ComboBox<String> cmbstatus;

	@FXML
	private Label labelerr;

	@FXML
	private Label labelfirst;

	@FXML
	private Label labelid;

	@FXML
	private Label labellast;
	ObservableList<String> list;
	ArrayList<String> al = new ArrayList<>();// list for combo box
//back
	@FXML
	void BackButton(ActionEvent event) throws Exception {
		ConnectFormController.chat.accept(new Request("get users status", BMScreenController.loc));
		((Node) event.getSource()).getScene().getWindow().hide();
		EditUserController eu = new EditUserController();
		Stage primaryStage = new Stage();
		eu.start(primaryStage);
	}
/**
 * this method changes the status for the user according the value of the selected combo box*/
	@FXML
	void ChangeStatus(ActionEvent event) {
		if (cmbstatus.getSelectionModel().getSelectedItem().equals(null)) {
			labelerr.setText("you have to choose a status first ");

		} else
			ConnectFormController.chat.accept(new Request("set status	" + Integer.toString(EditUserController.id),
					cmbstatus.getSelectionModel().getSelectedItem()));
	}
/**
 * @author gethe 
 * set up the combo box values*/
	void setType() {

		al.add("Active");
		al.add("Frozen");
		list = FXCollections.observableArrayList(al);
		cmbstatus.setItems(list);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/SetStatus.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(e->e.consume());
		primaryStage.setTitle("SET STATUS");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelid.setText(Integer.toString(EditUserController.id));
		labelfirst.setText(EditUserController.first);
		labellast.setText(EditUserController.last);
		cmbstatus.setValue(EditUserController.status);
		setType();

	}

}
