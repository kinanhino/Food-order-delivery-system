package gui;

import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import MySQLconnection.ExternalDB;
import MySQLconnection.SQLconnection;
import server.EchoServer;
import server.ServerUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.User;
import logic.UserForImport;
import logic.clients;

public class ServerPortFrameController implements Initializable {
	ExternalDB exdb = new ExternalDB();

	public static ArrayList<clients> arr = new ArrayList<>();
	ObservableList<clients> list;
	@FXML
	private Button btnExit;

	@FXML
	private Label iplabel;

	

	public static Label label1;
	@FXML
	private TableView<clients> tblorder;

	@FXML
	private TableColumn<clients, String> HName;

	@FXML
	private TableColumn<clients, String> colIp;

	@FXML
	private TableColumn<clients, String> colStatus;

	@FXML
	void ImportData(ActionEvent event) throws Exception{
         exdb.connectToDB("jdbc:mysql://localhost/externaldb?serverTimezone=IST","12332100","root");
         ArrayList<User> arrayList=ExternalDB.Import();
    
         while (!arrayList.isEmpty()) {
        	 SQLconnection.InsertUser(arrayList.remove(0));
        	 
         }
         ArrayList<UserForImport> arr=ExternalDB.ImportElse();
         while (!arr.isEmpty()) {
        	 SQLconnection.InsertElseUser( arr.remove(0));
        	 
         }
	
	}

	@FXML
	void ExitServer(ActionEvent event) {
		System.out.println("Exiting server");
		System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		label1 = iplabel;
		String[] iparr;
		try {
			iparr = InetAddress.getLocalHost().toString().split("/", 2);
			label1.setText(iparr[1]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 *@author gethe
		 * This thread runs as the server is working updating the Clients list */
		Thread t = new Thread() {
			public void run() {
				while (true) {
					if (list != null)
						list.clear();
					list = FXCollections.observableArrayList(arr);
					HName.setCellValueFactory(new PropertyValueFactory<>("name"));
					colIp.setCellValueFactory(new PropertyValueFactory<>("IP"));
					colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
					tblorder.setItems(list);
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

}
