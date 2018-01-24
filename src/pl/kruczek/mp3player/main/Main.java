package pl.kruczek.mp3player.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static final String APP_VERSION = "Mp3_Player v.09";

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/pl/kruczek/mp3player/view/MainPane.fxml"));
		Scene scene = new Scene(parent);
		primaryStage.setTitle(APP_VERSION);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
