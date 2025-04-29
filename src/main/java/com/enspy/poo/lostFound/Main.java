package com.enspy.poo.lostFound;

import javafx.application.Application;
import ui.LoginInterface;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage stage = new Stage();
		LoginInterface.display(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}