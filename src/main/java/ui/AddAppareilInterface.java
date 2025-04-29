package ui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import controller.MainController;
import model.Telephone;

class AddAppareilInterface {
    public static void display(Stage owner, MainController controller) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Ajouter un appareil");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label marqueLabel = new Label("Marque:");
        TextField marqueField = new TextField();
        Label modeleLabel = new Label("Modèle:");
        TextField modeleField = new TextField();
        Label numSerieLabel = new Label("N° Série:");
        TextField numSerieField = new TextField();
        Label imeiLabel = new Label("IMEI:");
        TextField imeiField = new TextField();

        grid.add(marqueLabel, 0, 0);
        grid.add(marqueField, 1, 0);
        grid.add(modeleLabel, 0, 1);
        grid.add(modeleField, 1, 1);
        grid.add(numSerieLabel, 0, 2);
        grid.add(numSerieField, 1, 2);
        grid.add(imeiLabel, 0, 3);
        grid.add(imeiField, 1, 3);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Enregistrer");
        Button cancelButton = new Button("Annuler");
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        root.getChildren().addAll(title, grid, buttonBox);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Nouvel appareil");
        stage.show();

        cancelButton.setOnAction(e -> stage.close());
        saveButton.setOnAction(e -> {
            Telephone appareil = new Telephone();
            appareil.setMarque(marqueField.getText());
            appareil.setModele(modeleField.getText());
            appareil.setNumSerie(numSerieField.getText());
            appareil.setImei(imeiField.getText());
            appareil.setUtilisateurId(controller.getUtilisateurId());

            try {
                controller.getTelephoneDAO().ajouter(appareil);
                controller.loadAppareils();
                stage.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la création: " + ex.getMessage()).showAndWait();
            }
        });

    }
}
