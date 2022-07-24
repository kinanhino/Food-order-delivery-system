package gui;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import logic.MyFile;
import logic.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Ibraheem This class controls the Ready Reports screen Here the BM
 *         manager can send ready reports to CEO
 * @param chosenFile : The chosen report file from the managers local client
 */
public class ReadyReportsController {

	public static File chosenFile = null;
	FileInputStream input = null;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button returnBTN;

	@FXML
	private Button sendToCeoBTN;

	@FXML
	private TextField filePathTXTBX;

	@FXML
	private Button chooseFileBTN;

	/**
	 * Let the manager choose a ready report file from his local environment
	 * 
	 * @param event
	 */
	@FXML
	void chooseFile(ActionEvent event) {
		JFileChooser filechooser = new JFileChooser();
		int response = filechooser.showOpenDialog(null);

		if (response == JFileChooser.APPROVE_OPTION) {
			File file = new File(filechooser.getSelectedFile().getAbsolutePath());
			chosenFile = file;
			filePathTXTBX.setText(file.toString());
		}
	}

	/**
	 * Return to "BM screen" screen
	 * 
	 * @param event : default param from javafx
	 */
	@FXML
	void returnToBMscreen(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/BMScreen.fxml"), resources);
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 1200, 700));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Turn chosen report file to a BLOB and save it to DB
	 * 
	 * @param event
	 * @return 
	 */
	@FXML
	boolean sendToCEO(ActionEvent event) {
		// Save chosen file to DB as a BLOB
		if (chosenFile != null) {
			MyFile msg = new MyFile(chosenFile.getName());
			String LocalfilePath = chosenFile.getAbsolutePath();
			try {
				File newFile = new File(LocalfilePath);
				byte[] mybytearray = new byte[(int) newFile.length()];
				FileInputStream fis = new FileInputStream(newFile);
				BufferedInputStream bis = new BufferedInputStream(fis);

				msg.initArray(mybytearray.length);
				msg.setSize(mybytearray.length);

				bis.read(msg.getMybytearray(), 0, mybytearray.length);
				Object[] objs = { msg, BMScreenController.user.getFname() };
				ConnectFormController.chat.accept(new Request("write ceoBLOB", objs));
			} catch (Exception e) {
				System.out.println("Error sending ((Files)msg) to Server - ChatClient");
			}
			return true;
		}
		return false;
	}

	@FXML
	void initialize() {
		assert returnBTN != null : "fx:id=\"returnBTN\" was not injected: check your FXML file 'ReadyReports.fxml'.";
		assert sendToCeoBTN != null
				: "fx:id=\"sendToCeoBTN\" was not injected: check your FXML file 'ReadyReports.fxml'.";
		assert filePathTXTBX != null
				: "fx:id=\"filePathTXTBX\" was not injected: check your FXML file 'ReadyReports.fxml'.";
		assert chooseFileBTN != null
				: "fx:id=\"chooseFileBTN\" was not injected: check your FXML file 'ReadyReports.fxml'.";
	}
}
