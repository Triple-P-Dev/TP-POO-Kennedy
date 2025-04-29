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

public class ModifyTelephoneInterface {
    public static void display(Stage owner, MainController controller, Telephone telephone) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Modifier un appareil");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label marqueLabel = new Label("Marque:");
        TextField marqueField = new TextField(telephone.getMarque());
        Label modeleLabel = new Label("Modèle:");
        TextField modeleField = new TextField(telephone.getModele());
        Label numSerieLabel = new Label("N° Série:");
        TextField numSerieField = new TextField(telephone.getNumSerie());
        Label imeiLabel = new Label("IMEI:");
        TextField imeiField = new TextField(telephone.getImei());
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea(telephone.getDescription());
        descriptionArea.setPrefRowCount(3);

        grid.add(marqueLabel, 0, 0);
        grid.add(marqueField, 1, 0);
        grid.add(modeleLabel, 0, 1);
        grid.add(modeleField, 1, 1);
        grid.add(numSerieLabel, 0, 2);
        grid.add(numSerieField, 1, 2);
        grid.add(imeiLabel, 0, 3);
        grid.add(imeiField, 1, 3);
        grid.add(descriptionLabel, 0, 4);
        grid.add(descriptionArea, 1, 4);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Enregistrer");
        Button cancelButton = new Button("Annuler");
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        root.getChildren().addAll(title, grid, buttonBox);

        Scene scene = new Scene(root, 400, 350);
        stage.setScene(scene);
        stage.setTitle("Modifier l'appareil");
        stage.show();

        cancelButton.setOnAction(e -> stage.close());
        saveButton.setOnAction(e -> {
            telephone.setMarque(marqueField.getText());
            telephone.setModele(modeleField.getText());
            telephone.setNumSerie(numSerieField.getText());
            telephone.setImei(imeiField.getText());
            telephone.setDescription(descriptionArea.getText());

            try {
                controller.getTelephoneDAO().modifier(telephone);
                controller.loadAppareils();
                stage.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification: " + ex.getMessage()).showAndWait();
            }
        });
    }
}