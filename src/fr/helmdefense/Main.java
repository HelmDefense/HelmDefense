package fr.helmdefense;

import fr.helmdefense.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Controller controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/Main.fxml"));
		loader.setController(this.controller = new Controller(primaryStage));
		BorderPane root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void stop() {
		if (this.controller != null)
			this.controller.closeHandler();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}