package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class RegistrationInterface {
    public static void display(Stage stage) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Inscription");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label nomLabel = new Label("Nom:");
        TextField nomField = new TextField();
        Label prenomLabel = new Label("Prénom:");
        TextField prenomField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label telLabel = new Label("Téléphone:");
        TextField telField = new TextField();
        Label passLabel = new Label("Mot de passe:");
        PasswordField passField = new PasswordField();
        Label confirmLabel = new Label("Confirmer mot de passe:");
        PasswordField confirmField = new PasswordField();

        grid.add(nomLabel, 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(prenomLabel, 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(telLabel, 0, 3);
        grid.add(telField, 1, 3);
        grid.add(passLabel, 0, 4);
        grid.add(passField, 1, 4);
        grid.add(confirmLabel, 0, 5);
        grid.add(confirmField, 1, 5);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button registerButton = new Button("S'inscrire");
        Button cancelButton = new Button("Annuler");
        buttonBox.getChildren().addAll(registerButton, cancelButton);

        root.getChildren().addAll(title, grid, buttonBox);

        Scene scene = new Scene(root, 500, 450);
        stage.setScene(scene);
        stage.setTitle("Inscription");
        stage.show();

        cancelButton.setOnAction(e -> stage.close());
    }
}
