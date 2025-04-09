package controller;
import dao.AnnonceDAO;
import dao.NotificationDAO;
import dao.TelephoneDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.List;
import java.util.Optional;

import javafx.stage.Stage;
import model.Annonce;
import model.Notification;
import model.Telephone;
import ui.ModifyAnnonceInterface;
import ui.ModifyTelephoneInterface;

public class MainController {

    private TableView<Annonce> allAnnoncesTable = null;
    private TableView<Annonce> myAnnoncesTable;
    private TableView<Notification> notificationsTable;
    private TableView<Telephone> appareilsTable;
    private TextField rechercheField;

    private AnnonceDAO annonceDAO;
    private NotificationDAO notificationDAO;
    private TelephoneDAO telephoneDAO;

    private ObservableList<Annonce> allAnnonces;
    private ObservableList<Annonce> myAnnonces;
    private ObservableList<Notification> notifications;
    private ObservableList<Telephone> appareils;

    private int utilisateurId = 1;

    private void setupAllAnnoncesTable() {
        TableColumn<Annonce, Integer> allIdCol = new TableColumn<>("ID");
        allIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        TableColumn<Annonce, String> allTitreCol = new TableColumn<>("Titre");
        allTitreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        TableColumn<Annonce, String> allMarqueCol = new TableColumn<>("Marque");
        allMarqueCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarqueAppareil()));
        TableColumn<Annonce, String> allModeleCol = new TableColumn<>("Modèle");
        allModeleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModeleAppareil()));
        TableColumn<Annonce, String> allNumSerieCol = new TableColumn<>("N° Série");
        allNumSerieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumSerieAppareil()));
        TableColumn<Annonce, String> allDateCol = new TableColumn<>("Date");
        allDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCreation()));
        TableColumn<Annonce, Boolean> allStatutCol = new TableColumn<>("Statut");
        allStatutCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isEstTrouve()));
        allAnnoncesTable.getColumns().setAll(allIdCol, allTitreCol, allMarqueCol, allModeleCol, allNumSerieCol, allDateCol, allStatutCol);
    }

    private void setupMyAnnoncesTable() {
        TableColumn<Annonce, Integer> myIdCol = new TableColumn<>("ID");
        myIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        TableColumn<Annonce, String> myTitreCol = new TableColumn<>("Titre");
        myTitreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        TableColumn<Annonce, String> myMarqueCol = new TableColumn<>("Marque");
        myMarqueCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarqueAppareil()));
        TableColumn<Annonce, String> myModeleCol = new TableColumn<>("Modèle");
        myModeleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModeleAppareil()));
        TableColumn<Annonce, String> myNumSerieCol = new TableColumn<>("N° Série");
        myNumSerieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumSerieAppareil()));
        TableColumn<Annonce, String> myDateCol = new TableColumn<>("Date");
        myDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCreation()));
        TableColumn<Annonce, Boolean> myStatutCol = new TableColumn<>("Statut");
        myStatutCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isEstTrouve()));
        myAnnoncesTable.getColumns().setAll(myIdCol, myTitreCol, myMarqueCol, myModeleCol, myNumSerieCol, myDateCol, myStatutCol);
    }

    private void setupNotificationsTable() {
        TableColumn<Notification, Integer> notifIdCol = new TableColumn<>("ID");
        notifIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        TableColumn<Notification, String> notifMessageCol = new TableColumn<>("Message");
        notifMessageCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));
        TableColumn<Notification, String> notifDateCol = new TableColumn<>("Date");
        notifDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateEnvoi()));
        TableColumn<Notification, Boolean> notifStatutCol = new TableColumn<>("Lu");
        notifStatutCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isEstLu()));
        notificationsTable.getColumns().setAll(notifIdCol, notifMessageCol, notifDateCol, notifStatutCol);
    }

    private void setupAppareilsTable() {
        TableColumn<Telephone, Integer> appIdCol = new TableColumn<>("ID");
        appIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        TableColumn<Telephone, String> appMarqueCol = new TableColumn<>("Marque");
        appMarqueCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        TableColumn<Telephone, String> appModeleCol = new TableColumn<>("Modèle");
        appModeleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        TableColumn<Telephone, String> appNumSerieCol = new TableColumn<>("N° Série");
        appNumSerieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumSerie()));
        TableColumn<Telephone, String> appImeiCol = new TableColumn<>("IMEI");
        appImeiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImei()));
        appareilsTable.getColumns().setAll(appIdCol, appMarqueCol, appModeleCol, appNumSerieCol, appImeiCol);
    }

    private boolean checkTablesInitialized() {
        return allAnnoncesTable != null && myAnnoncesTable != null &&
                notificationsTable != null && appareilsTable != null;
    }

    public void initialize() {
        annonceDAO = new AnnonceDAO();
        notificationDAO = new NotificationDAO();
        telephoneDAO = new TelephoneDAO();

        allAnnonces = FXCollections.observableArrayList();
        myAnnonces = FXCollections.observableArrayList();
        notifications = FXCollections.observableArrayList();
        appareils = FXCollections.observableArrayList();

        // Vérifier que les tableaux sont assignés avant de configurer les colonnes
        if (allAnnoncesTable != null) {
            setupAllAnnoncesTable();
            allAnnoncesTable.setItems(allAnnonces);
            loadAllAnnonces();
        }

        if (myAnnoncesTable != null) {
            setupMyAnnoncesTable();
            myAnnoncesTable.setItems(myAnnonces);
            loadMyAnnonces();
        }

        if (notificationsTable != null) {
            setupNotificationsTable();
            notificationsTable.setItems(notifications);
            loadNotifications();
        }

        if (appareilsTable != null) {
            setupAppareilsTable();
            appareilsTable.setItems(appareils);
            loadAppareils();
        }
    }



    public void loadAllAnnonces() {
        if (annonceDAO == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "DAO non initialisé", "L'objet annonceDAO n'a pas été initialisé.");
            return;
        }
        try {
            List<Annonce> annoncesList = annonceDAO.findAll();
            allAnnonces.clear();
            allAnnonces.addAll(annoncesList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les annonces", e.getMessage());
        }
    }

    public void loadMyAnnonces() {
        try {
            List<Annonce> annoncesList = annonceDAO.findByUtilisateur(utilisateurId);
            myAnnonces.clear();
            myAnnonces.addAll(annoncesList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger vos annonces", e.getMessage());
        }
    }

    public void loadNotifications() {
        try {
            List<Notification> notificationsList = notificationDAO.findByDestinataire(utilisateurId);
            notifications.clear();
            notifications.addAll(notificationsList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les notifications", e.getMessage());
        }
    }

    public void loadAppareils() {
        try {
            List<Telephone> telephonesList = telephoneDAO.findByUtilisateur(utilisateurId);
            appareils.clear();
            appareils.addAll(telephonesList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger vos appareils", e.getMessage());
        }
    }


    public void handleRecherche(ActionEvent event) {
        String numSerie = rechercheField.getText().trim();
        if (numSerie.isEmpty()) {
            loadAllAnnonces();
            return;
        }
        try {
            List<Annonce> annoncesList = annonceDAO.rechercher(numSerie);
            allAnnonces.clear();
            allAnnonces.addAll(annoncesList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche", e.getMessage());
        }
    }


    public void handleVoirDetails(ActionEvent event) {
        Annonce selected = allAnnoncesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune annonce sélectionnée", "Veuillez sélectionner une annonce.");
            return;
        }
        showAlert(Alert.AlertType.INFORMATION, "Détails", "Détails de l'annonce",
                "Titre: " + selected.getTitre() + "\n" +
                        "Marque: " + selected.getMarqueAppareil() + "\n" +
                        "Modèle: " + selected.getModeleAppareil() + "\n" +
                        "N° Série: " + selected.getNumSerieAppareil() + "\n" +
                        "Date: " + selected.getDateCreation() + "\n" +
                        "Statut: " + (selected.isEstTrouve() ? "Trouvé" : "Non trouvé"));
    }


    public void handleCreerAnnonce(ActionEvent event) {
        // Implémenté via l'interface séparée AddAnnonceInterface
    }


    public void handleModifierAnnonce(ActionEvent event) {
        Annonce selected = myAnnoncesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune annonce sélectionnée", "Veuillez sélectionner une annonce à modifier.");
            return;
        }

        Stage owner = (Stage) ((Button) event.getSource()).getScene().getWindow();
        ModifyAnnonceInterface.display(owner, this, selected);
    }


    public void handleSupprimerAnnonce(ActionEvent event) {
        Annonce selected = myAnnoncesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune annonce sélectionnée", "Veuillez sélectionner une annonce à supprimer.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez-vous la suppression de l'annonce '" + selected.getTitre() + "' ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    annonceDAO.supprimer(selected.getId());
                    loadMyAnnonces();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression", e.getMessage());
                }
            }
        });
    }


    public void handleMarquerTrouve(ActionEvent event) {
        Annonce selected = myAnnoncesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune annonce sélectionnée", "Veuillez sélectionner une annonce.");
            return;
        }
        try {
            selected.setEstTrouve(!selected.isEstTrouve());
            annonceDAO.modifier(selected);
            loadMyAnnonces();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour du statut", e.getMessage());
        }
    }


    public void handleMarquerLu(ActionEvent event) {
        Notification selected = notificationsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune notification sélectionnée", "Veuillez sélectionner une notification.");
            return;
        }
        try {
            selected.setEstLu(true);
            notificationDAO.marquerCommeLu(selected); // Vous devez implémenter cette méthode dans NotificationDAO
            loadNotifications();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour", e.getMessage());
        }
    }


    public void handleRafraichirNotifications(ActionEvent event) {
        loadNotifications();
    }


    public void handleAjouterAppareil(ActionEvent event) {
        // Implémenté via l'interface séparée AddAppareilInterface
    }


    public void handleModifierAppareil(ActionEvent event) {
        Telephone selected = appareilsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucun appareil sélectionné", "Veuillez sélectionner un appareil à modifier.");
            return;
        }

        // Obtenir le Stage de l'événement
        Stage owner = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Afficher l'interface de modification
        ModifyTelephoneInterface.display(owner, this, selected);
    }


    public void handleSupprimerAppareil(ActionEvent event) {
        Telephone selected = appareilsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucun appareil sélectionné", "Veuillez sélectionner un appareil à supprimer.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirmez-vous la suppression de l'appareil '" + selected.getMarque() + " " + selected.getModele() + "' ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    telephoneDAO.supprimer(selected.getId());
                    loadAppareils();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression", e.getMessage());
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Accesseurs pour l'intégration avec l'interface
    public TableView<Annonce> getAllAnnoncesTable() { return allAnnoncesTable; }
    public TableView<Annonce> getMyAnnoncesTable() { return myAnnoncesTable; }
    public TableView<Notification> getNotificationsTable() { return notificationsTable; }
    public TableView<Telephone> getAppareilsTable() { return appareilsTable; }
    public AnnonceDAO getAnnonceDAO() { return annonceDAO; }
    public TelephoneDAO getTelephoneDAO() { return telephoneDAO; }
    public int getUtilisateurId() { return utilisateurId; }

    public void setAllAnnoncesTable(TableView<Annonce> tableView) {
        this.allAnnoncesTable = tableView;
    }

    public void setRechercheField(TextField searchField) {
        this.rechercheField = searchField;
    }

    public void setMyAnnoncesTable(TableView<Annonce> tableView) {
        this.myAnnoncesTable = tableView;
    }

    public void setNotificationsTable(TableView<Notification> tableView) {
        this.notificationsTable = tableView;
    }

    public void setAppareilsTable(TableView<Telephone> tableView) {
        this.appareilsTable = tableView;
    }
    
}