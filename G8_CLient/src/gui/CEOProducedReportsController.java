package gui;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.Document;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.CeoReport;
import logic.Request;
import logic.orderReport;

/**
 * 
 * @author Ibraheem This class controls the CEO Produced screen
 */
public class CEOProducedReportsController {

	public static byte[] blob;
	public static ArrayList<CeoReport> reportsArr = new ArrayList<>();
	protected ObservableList<CeoReport> reportsOL = null;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label restaurantNameLBL;

	@FXML
	private Button returnBTN;

	@FXML
	private TableView<CeoReport> readyReportsTBLVIEW;

	@FXML
	private TableColumn<?, ?> id;

	@FXML
	private TableColumn<?, ?> bmmanager;

	@FXML
	private TableColumn<?, ?> dateissued;

	@FXML
	private Button viewReportBTN;

	/**
	 * Return to "CEO Screen" screen
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void returnToPickReport(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/CEOScreen.fxml"), resources);
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 1200, 700));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the file selected from table view Get file from DB by it's id and then
	 * display it
	 * 
	 * @param event
	 */
	@FXML
	void viewSelectedReport1(ActionEvent event) {
		CeoReport temp = readyReportsTBLVIEW.getSelectionModel().getSelectedItem();
		ConnectFormController.chat.accept(new Request("get CEOBlob", temp.getId()));
		File file = new File("file.txt");
		try {
			FileOutputStream os = new FileOutputStream(file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os);
			try {
				System.out.println(blob.length);
				bufferedOutputStream.write(blob, 0, blob.length);
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void viewSelectedReport(ActionEvent event) {
		System.out.println("Eminem");
		CeoReport temp = readyReportsTBLVIEW.getSelectionModel().getSelectedItem();
		ConnectFormController.chat.accept(new Request("get CEOBlob", temp.getId()));
		try {
			File f = new File("filename.pdf");
			FileOutputStream fileOuputStream = new FileOutputStream(f);
			fileOuputStream.write(blob);
			Desktop desktop = Desktop.getDesktop();

			if (f.exists()) // Check if file exists or not
				desktop.open(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void getAllCeoReports() {
		if (reportsOL != null)
			reportsOL.clear();
		if (reportsArr != null)
			reportsArr.clear();

		ConnectFormController.chat.accept(new Request("get all ceoReports", null));

		reportsOL = FXCollections.observableArrayList(reportsArr);
		bmmanager.setCellValueFactory(new PropertyValueFactory<>("bmName"));
		dateissued.setCellValueFactory(new PropertyValueFactory<>("date"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		readyReportsTBLVIEW.setItems(reportsOL);
	}

	@FXML
	void initialize() {

		// Get all reports that had been sent by bm managers
		getAllCeoReports();

		assert restaurantNameLBL != null
				: "fx:id=\"restaurantNameLBL\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";
		assert returnBTN != null
				: "fx:id=\"returnBTN\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";
		assert readyReportsTBLVIEW != null
				: "fx:id=\"readyReportsTBLVIEW\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";
		assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";
		assert bmmanager != null
				: "fx:id=\"bmmanager\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";
		assert dateissued != null
				: "fx:id=\"dateissued1\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";
		assert viewReportBTN != null
				: "fx:id=\"viewReportBTN\" was not injected: check your FXML file 'CEOProducedReports.fxml'.";

	}
}
