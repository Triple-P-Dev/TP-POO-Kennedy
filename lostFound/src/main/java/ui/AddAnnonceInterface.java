package ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import controller.MainController;
import model.Annonce;
import model.Telephone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddAnnonceInterface {
    public static void display(Stage owner, MainController controller) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Créer une nouvelle annonce");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label titreLabel = new Label("Titre:");
        TextField titreField = new TextField();
        Label marqueLabel = new Label("Marque:");
        TextField marqueField = new TextField();
        marqueField.setEditable(false);
        Label modeleLabel = new Label("Modèle:");
        TextField modeleField = new TextField();
        modeleField.setEditable(false);
        Label numSerieLabel = new Label("N° Série:");
        TextField numSerieField = new TextField();
        numSerieField.setEditable(false);
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(3);
        Label statutLabel = new Label("Statut:");
        ToggleGroup statusGroup = new ToggleGroup();
        RadioButton perduRadio = new RadioButton("Perdu");
        perduRadio.setToggleGroup(statusGroup);
        perduRadio.setSelected(true);
        RadioButton trouveRadio = new RadioButton("Trouvé");
        trouveRadio.setToggleGroup(statusGroup);
        HBox statutBox = new HBox(10, perduRadio, trouveRadio);

        LocalDate now = LocalDate.now();
        String dateCreation = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Label appareilLabel = new Label("Appareil:");
        ComboBox<Telephone> appareilComboBox = new ComboBox<>();
        appareilComboBox.setPromptText("Sélectionner un appareil");

        try {
            List<Telephone> userDevices = controller.getTelephoneDAO().findByUtilisateur(controller.getUtilisateurId());
            appareilComboBox.setItems(FXCollections.observableArrayList(userDevices));

            appareilComboBox.setCellFactory(param -> new ListCell<Telephone>() {
                @Override
                protected void updateItem(Telephone telephone, boolean empty) {
                    super.updateItem(telephone, empty);
                    if (empty || telephone == null) {
                        setText(null);
                    } else {
                        setText(telephone.getMarque() + " " + telephone.getModele() + " (" + telephone.getNumSerie() + ")");
                    }
                }
            });
            appareilComboBox.setButtonCell(appareilComboBox.getCellFactory().call(null));

            appareilComboBox.setOnAction(e -> {
                Telephone selectedDevice = appareilComboBox.getValue();
                if (selectedDevice != null) {
                    marqueField.setText(selectedDevice.getMarque());
                    modeleField.setText(selectedDevice.getModele());
                    numSerieField.setText(selectedDevice.getNumSerie());
                }
            });
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement des appareils: " + ex.getMessage()).showAndWait();
        }

        grid.add(titreLabel, 0, 0);
        grid.add(titreField, 1, 0);
        grid.add(appareilLabel, 0, 1);
        grid.add(appareilComboBox, 1, 1);
        grid.add(marqueLabel, 0, 2);
        grid.add(marqueField, 1, 2);
        grid.add(modeleLabel, 0, 3);
        grid.add(modeleField, 1, 3);
        grid.add(numSerieLabel, 0, 4);
        grid.add(numSerieField, 1, 4);
        grid.add(descriptionLabel, 0, 5);
        grid.add(descriptionArea, 1, 5);
        grid.add(statutLabel, 0, 6);
        grid.add(statutBox, 1, 6);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Enregistrer");
        Button cancelButton = new Button("Annuler");
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        root.getChildren().addAll(title, grid, buttonBox);

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.setTitle("Nouvelle annonce");
        stage.show();

        cancelButton.setOnAction(e -> stage.close());
        saveButton.setOnAction(e -> {
            Telephone selectedDevice = appareilComboBox.getValue();
            if (selectedDevice == null) {
                new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner un appareil").showAndWait();
                return;
            }

            if (titreField.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez saisir un titre pour l'annonce").showAndWait();
                return;
            }

            Annonce annonce = new Annonce();
            annonce.setTitre(titreField.getText());
            annonce.setMarqueAppareil(selectedDevice.getMarque());
            annonce.setModeleAppareil(selectedDevice.getModele());
            annonce.setNumSerieAppareil(selectedDevice.getNumSerie());
            annonce.setDescription(descriptionArea.getText());
            annonce.setDateCreation(dateCreation);
            annonce.setEstTrouve(trouveRadio.isSelected());
            annonce.setUtilisateurId(controller.getUtilisateurId());
            annonce.setIdAppareil(selectedDevice.getId());

            try {
                controller.getAnnonceDAO().ajouter(annonce);
                controller.loadAllAnnonces();
                controller.loadMyAnnonces();
                stage.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erreur lors de la création: " + ex.getMessage()).showAndWait();
            }
        });
    }
}