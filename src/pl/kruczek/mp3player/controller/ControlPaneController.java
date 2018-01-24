
package pl.kruczek.mp3player.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class ControlPaneController implements Initializable {

	@FXML
	private Button prevButton;


	@FXML
	private ToggleButton playButton;

	@FXML
	private Button nextButton;

	@FXML
	private Slider volumeSlider;

	@FXML
	private Slider songSlider;

	public Slider getSongSlider() {
		return songSlider;
	}

	public Button getPrevButton() {
		return prevButton;
	}

	public ToggleButton getPlayButton() {
		return playButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public Slider getVolumeSlider() {
		return volumeSlider;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
