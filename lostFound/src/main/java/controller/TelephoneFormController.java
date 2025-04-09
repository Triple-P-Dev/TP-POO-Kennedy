package controller;

import dao.TelephoneDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Telephone;

public class TelephoneFormController {

    @FXML private TextField marqueField;
    @FXML private TextField modeleField;
    @FXML private TextField numSerieField;
    @FXML private TextField imeiField;
    @FXML private Button enregistrerBtn;
    @FXML private Button annulerBtn;

    private TelephoneDAO telephoneDAO;
    private Telephone telephone;
    private int utilisateurId;
    private boolean modeEdition = false;

    @FXML
    public void initialize() {
        telephoneDAO = new TelephoneDAO();
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
        this.modeEdition = true;

        // Remplir les champs avec les données de l'appareil
        marqueField.setText(telephone.getMarque());
        modeleField.setText(telephone.getModele());
        numSerieField.setText(telephone.getNumSerie());
        imeiField.setText(telephone.getImei());
    }

    @FXML
    public void handleEnregistrer(ActionEvent event) {
        if (!validerFormulaire()) {
            return;
        }

        try {
            if (modeEdition) {
                // Mise à jour d'un appareil existant
                telephone.setMarque(marqueField.getText().trim());
                telephone.setModele(modeleField.getText().trim());
                telephone.setNumSerie(numSerieField.getText().trim());
                telephone.setImei(imeiField.getText().trim());

                telephoneDAO.modifier(telephone);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Appareil mis à jour",
                        "L'appareil a été mis à jour avec succès.");
            } else {
                // Création d'un nouvel appareil
                Telephone nouveauTelephone = new Telephone();
                nouveauTelephone.setUtilisateurId(utilisateurId);
                nouveauTelephone.setMarque(marqueField.getText().trim());
                nouveauTelephone.setModele(modeleField.getText().trim());
                nouveauTelephone.setNumSerie(numSerieField.getText().trim());
                nouveauTelephone.setImei(imeiField.getText().trim());

                telephoneDAO.ajouter(nouveauTelephone);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Appareil ajouté",
                        "L'appareil a été ajouté avec succès.");
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

        if (marqueField.getText().trim().isEmpty()) {
            erreurs.append("- La marque est obligatoire.\n");
        }

        if (modeleField.getText().trim().isEmpty()) {
            erreurs.append("- Le modèle est obligatoire.\n");
        }

        if (numSerieField.getText().trim().isEmpty()) {
            erreurs.append("- Le numéro de série est obligatoire.\n");
        }

        if (imeiField.getText().trim().isEmpty()) {
            erreurs.append("- L'IMEI est obligatoire.\n");
        } else if (!validerIMEI(imeiField.getText().trim())) {
            erreurs.append("- L'IMEI doit contenir 15 chiffres.\n");
        }

        if (erreurs.length() > 0) {
            showAlert(Alert.AlertType.WARNING, "Formulaire incomplet", "Veuillez corriger les erreurs suivantes :",
                    erreurs.toString());
            return false;
        }

        return true;
    }

    private boolean validerIMEI(String imei) {
        // Vérifier que l'IMEI est composé de 15 chiffres
        return imei.matches("\\d{15}");
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