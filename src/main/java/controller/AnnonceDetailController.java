package controller;

import dao.NotificationDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Annonce;
import model.Notification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnnonceDetailController {

    @FXML private Label titreLabel;
    @FXML private Label marqueLabel;
    @FXML private Label modeleLabel;
    @FXML private Label numSerieLabel;
    @FXML private Label imeiLabel;
    @FXML private Label dateCreationLabel;
    @FXML private Label datePerteLabel;
    @FXML private Label lieuPerteLabel;
    @FXML private Label statutLabel;
    @FXML private TextArea descriptionArea;
    @FXML private Button contacterBtn;
    @FXML private Button fermerBtn;
    @FXML private TextArea messageArea;

    private Annonce annonce;
    private NotificationDAO notificationDAO;
    private int utilisateurConnecteId = 1; // Par défaut, à remplacer par l'ID de l'utilisateur connecté

    @FXML
    public void initialize() {
        notificationDAO = new NotificationDAO();
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;

        // Affichage des détails de l'annonce
        titreLabel.setText(annonce.getTitre());
        marqueLabel.setText(annonce.getMarqueAppareil());
        modeleLabel.setText(annonce.getModeleAppareil());
        numSerieLabel.setText(annonce.getNumSerieAppareil());
        imeiLabel.setText(annonce.getImeiAppareil());
        dateCreationLabel.setText(annonce.getDateCreation());
        datePerteLabel.setText(annonce.getDatePerte());
        lieuPerteLabel.setText(annonce.getLieuPerte());
        statutLabel.setText(annonce.isEstTrouve() ? "Trouvé" : "Non trouvé");
        descriptionArea.setText(annonce.getDescription());

        // Désactiver le bouton contacter si l'utilisateur est le propriétaire de l'annonce
        if (annonce.getUtilisateurId() == utilisateurConnecteId) {
            contacterBtn.setDisable(true);
            messageArea.setDisable(true);
        }

        // Désactiver le bouton contacter si l'appareil est marqué comme trouvé
        if (annonce.isEstTrouve()) {
            contacterBtn.setDisable(true);
            messageArea.setDisable(true);
        }
    }

    @FXML
    public void handleContacter(ActionEvent event) {
        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Message vide", "Veuillez saisir un message",
                    "Vous devez saisir un message pour contacter le propriétaire de l'appareil.");
            return;
        }

        try {
            // Création d'une nouvelle notification
            Notification notification = new Notification();
            notification.setExpediteurId(utilisateurConnecteId);
            notification.setDestinataireId(annonce.getUtilisateurId());
            notification.setAnnonceId(annonce.getId());
            notification.setMessage(message);

            // Formatage de la date d'envoi
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String dateEnvoi = LocalDateTime.now().format(formatter);
            notification.setDateEnvoi(dateEnvoi);

            // Statut initial : non lu
            notification.setEstLu(false);

            // Enregistrement de la notification
            notificationDAO.ajouter(notification);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Message envoyé",
                    "Votre message a été envoyé au propriétaire de l'appareil.");

            // Fermer la fenêtre
            fermerFenetre();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'envoi du message", e.getMessage());
        }
    }

    @FXML
    public void handleFermer(ActionEvent event) {
        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) fermerBtn.getScene().getWindow();
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