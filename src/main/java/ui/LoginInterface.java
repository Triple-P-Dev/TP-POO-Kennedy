package ui;

import controller.MainController;
import dao.UtilisateurDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Utilisateur;

public class LoginInterface {

    public static void display(Stage stage) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Connexion");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button loginButton = new Button("Se connecter");
        Button registerButton = new Button("S'inscrire");
        buttonBox.getChildren().addAll(loginButton, registerButton);

        root.getChildren().addAll(title, grid, buttonBox);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();

        // Action pour aller vers l'inscription
        registerButton.setOnAction(e -> RegistrationInterface.display(new Stage()));

        // Action pour la connexion
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String motDePasse = passwordField.getText();

            // Vérifier que les champs ne sont pas vides
            if (email.isEmpty() || motDePasse.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs vides");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            try {
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                Utilisateur utilisateur = utilisateurDAO.authentifier(email, motDePasse);

                if (utilisateur != null) {
                    // Connexion réussie
                    stage.close();
                    MainController controller = new MainController();
                    new MainUI().start(new Stage()); // Ouvre MainUI
                } else {
                    // Identifiants incorrects
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Identifiants incorrects");
                    alert.setContentText("L'email ou le mot de passe est incorrect.");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de la connexion");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });
    }

}

