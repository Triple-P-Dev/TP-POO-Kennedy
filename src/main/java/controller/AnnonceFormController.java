package controller;

import dao.AnnonceDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Annonce;
import model.Telephone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnnonceFormController {

    @FXML private TextField titreField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<Telephone> telephoneComboBox;
    @FXML private TextField lieuPerteField;
    @FXML private DatePicker datePerteField;
    @FXML private Button enregistrerBtn;
    @FXML private Button annulerBtn;

    private AnnonceDAO annonceDAO;
    private Annonce annonce;
    private int utilisateurId;
    private boolean modeEdition = false;

    @FXML
    public void initialize() {
        annonceDAO = new AnnonceDAO();

        // Configuration de l'affichage des téléphones dans ComboBox
        telephoneComboBox.setCellFactory(param -> new ListCell<Telephone>() {
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

        telephoneComboBox.setButtonCell(new ListCell<Telephone>() {
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

        // Par défaut, la date de perte est aujourd'hui
        datePerteField.setValue(LocalDate.now());
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public void setTelephonesList(ObservableList<Telephone> telephones) {
        telephoneComboBox.setItems(telephones);
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
        this.modeEdition = true;

        // Remplir les champs avec les données de l'annonce
        titreField.setText(annonce.getTitre());
        descriptionArea.setText(annonce.getDescription());
        lieuPerteField.setText(annonce.getLieuPerte());

        // Configurer la date de perte
        if (annonce.getDatePerte() != null && !annonce.getDatePerte().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate datePerte = LocalDate.parse(annonce.getDatePerte(), formatter);
                datePerteField.setValue(datePerte);
            } catch (Exception e) {
                // En cas d'erreur de parsing, utiliser la date actuelle
                datePerteField.setValue(LocalDate.now());
            }
        }

        // Sélectionner le téléphone associé
        for (Telephone telephone : telephoneComboBox.getItems()) {
            if (telephone.getNumSerie().equals(annonce.getNumSerieAppareil())) {
                telephoneComboBox.getSelectionModel().select(telephone);
                break;
            }
        }
    }

    @FXML
    public void handleEnregistrer(ActionEvent event) {
        if (!validerFormulaire()) {
            return;
        }

        try {
            if (modeEdition) {
                // Mise à jour d'une annonce existante
                annonce.setTitre(titreField.getText().trim());
                annonce.setDescription(descriptionArea.getText().trim());
                annonce.setLieuPerte(lieuPerteField.getText().trim());

                // Formatage de la date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String datePerte = datePerteField.getValue().format(formatter);
                annonce.setDatePerte(datePerte);

                // Mettre à jour les informations du téléphone
                Telephone selectedPhone = telephoneComboBox.getSelectionModel().getSelectedItem();
                annonce.setIdAppareil(selectedPhone.getId());
                annonce.setMarqueAppareil(selectedPhone.getMarque());
                annonce.setModeleAppareil(selectedPhone.getModele());
                annonce.setNumSerieAppareil(selectedPhone.getNumSerie());
                annonce.setImeiAppareil(selectedPhone.getImei());

                annonceDAO.modifier(annonce);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Annonce mise à jour",
                        "L'annonce a été mise à jour avec succès.");
            } else {
                // Création d'une nouvelle annonce
                Annonce nouvelleAnnonce = new Annonce();
                nouvelleAnnonce.setUtilisateurId(utilisateurId);
                nouvelleAnnonce.setTitre(titreField.getText().trim());
                nouvelleAnnonce.setDescription(descriptionArea.getText().trim());
                nouvelleAnnonce.setLieuPerte(lieuPerteField.getText().trim());

                // Formatage de la date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String datePerte = datePerteField.getValue().format(formatter);
                nouvelleAnnonce.setDatePerte(datePerte);

                // Date de création (aujourd'hui)
                String dateCreation = LocalDate.now().format(formatter);
                nouvelleAnnonce.setDateCreation(dateCreation);

                // Statut initial : non trouvé
                nouvelleAnnonce.setEstTrouve(false);

                // Informations du téléphone
                Telephone selectedPhone = telephoneComboBox.getSelectionModel().getSelectedItem();
                nouvelleAnnonce.setIdAppareil(selectedPhone.getId());
                nouvelleAnnonce.setMarqueAppareil(selectedPhone.getMarque());
                nouvelleAnnonce.setModeleAppareil(selectedPhone.getModele());
                nouvelleAnnonce.setNumSerieAppareil(selectedPhone.getNumSerie());
                nouvelleAnnonce.setImeiAppareil(selectedPhone.getImei());

                annonceDAO.ajouter(nouvelleAnnonce);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Annonce créée",
                        "L'annonce a été créée avec succès.");
            }

            // Fermer la fenêtre
            fermerFenetre();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'enregistrement", e.getMessage());
        }
    }

    @FXML
    public void handleAnnuler(ActionEvent event) {
        fermerFenetre();
    }

    private boolean validerFormulaire() {
        StringBuilder erreurs = new StringBuilder();

        if (titreField.getText().trim().isEmpty()) {
            erreurs.append("- Le titre est obligatoire.\n");
        }

        if (descriptionArea.getText().trim().isEmpty()) {
            erreurs.append("- La description est obligatoire.\n");
        }

        if (telephoneComboBox.getSelectionModel().isEmpty()) {
            erreurs.append("- Veuillez sélectionner un appareil.\n");
        }

        if (lieuPerteField.getText().trim().isEmpty()) {
            erreurs.append("- Le lieu de perte est obligatoire.\n");
        }

        if (datePerteField.getValue() == null) {
            erreurs.append("- La date de perte est obligatoire.\n");
        }

        if (erreurs.length() > 0) {
            showAlert(Alert.AlertType.WARNING, "Formulaire incomplet", "Veuillez corriger les erreurs suivantes :",
                    erreurs.toString());
            return false;
        }

        return true;
    }

    private void fermerFenetre() {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}