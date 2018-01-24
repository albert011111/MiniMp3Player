package pl.kruczek.mp3player.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuPaneController implements Initializable {

	@FXML
	private Menu fileMenu;

	@FXML
	private MenuItem openMp3Item;

	@FXML
	private MenuItem openDirItem;

	@FXML
	private MenuItem closeItem;

	@FXML
	private Menu helpMenu;

	@FXML
	private MenuItem aboutItem;

	
	public Menu getFileMenu() {
		return fileMenu;
	}

	public Menu getHelpMenu() {
		return helpMenu;
	}

	public MenuItem getOpenMp3Item() {
		return openMp3Item;
	}

	public MenuItem getOpenDirItem() {
		return openDirItem;
	}

	public MenuItem getCloseItem() {
		return closeItem;
	}

	public MenuItem getAboutItem() {
		return aboutItem;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
