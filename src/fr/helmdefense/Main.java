package fr.helmdefense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(this.getClass().getResource("view/Main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Helm Defense");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}