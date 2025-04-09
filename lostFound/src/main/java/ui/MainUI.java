package ui;

import controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import model.Annonce;
import model.Notification;
import model.Telephone;

public class MainUI extends Application {
    private MainController controller;
    private TabPane tabPane;
    private TableView<Annonce> allAnnoncesTable;

    @Override
    public void start(Stage primaryStage) {
        controller = new MainController();

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab allAnnoncesTab = createAllAnnoncesTab();
        Tab myAnnoncesTab = createMyAnnoncesTab();
        Tab notificationsTab = createNotificationsTab();
        Tab appareilsTab = createAppareilsTab();

        tabPane.getTabs().addAll(allAnnoncesTab, myAnnoncesTab, notificationsTab, appareilsTab);

        AnchorPane root = new AnchorPane(tabPane);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Gestion des Annonces");
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.setAllAnnoncesTable(allAnnoncesTable);

        controller.initialize();
    }

    private Tab createAllAnnoncesTab() {
        Tab tab = new Tab("Toutes les annonces");
        BorderPane borderPane = new BorderPane();

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(10));
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher par numéro de série");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        Button searchButton = new Button("Rechercher");

        TableView<Annonce> tableView = new TableView<>();
        controller.setAllAnnoncesTable(tableView);
        controller.setRechercheField(searchField);

        searchButton.setOnAction(e -> {
            controller.handleRecherche(e);
        });
        searchBox.getChildren().addAll(searchField, searchButton);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        Button detailsButton = new Button("Voir détails");
        detailsButton.setOnAction(e -> controller.handleVoirDetails(e));
        bottomBox.getChildren().add(detailsButton);

        borderPane.setTop(searchBox);
        borderPane.setCenter(tableView);
        borderPane.setBottom(bottomBox);

        tab.setContent(borderPane);
        controller.loadAllAnnonces();
        return tab;
    }

    private Tab createMyAnnoncesTab() {
        Tab tab = new Tab("Mes annonces");
        BorderPane borderPane = new BorderPane();

        TableView<Annonce> tableView = new TableView<>();
        controller.setMyAnnoncesTable(tableView);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        Button createButton = new Button("Créer une annonce");
        Button modifyButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");
        Button foundButton = new Button("Marquer comme trouvé");

        createButton.setOnAction(this::handle);
        modifyButton.setOnAction(controller::handleModifierAnnonce);
        deleteButton.setOnAction(e -> {
            controller.handleSupprimerAnnonce(e);
            controller.loadMyAnnonces();
        });
        foundButton.setOnAction(e -> {
            controller.handleMarquerTrouve(e);
            controller.loadMyAnnonces();
        });

        bottomBox.getChildren().addAll(createButton, modifyButton, deleteButton, foundButton);

        borderPane.setCenter(tableView);
        borderPane.setBottom(bottomBox);

        tab.setContent(borderPane);
        return tab;
    }

    private Tab createNotificationsTab() {
        Tab tab = new Tab("Notifications");
        BorderPane borderPane = new BorderPane();

        TableView<Notification> tableView = new TableView<>();
        controller.setNotificationsTable(tableView);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        Button markReadButton = new Button("Marquer comme lu");
        Button refreshButton = new Button("Rafraîchir");
        markReadButton.setOnAction(e -> {
            controller.handleMarquerLu(e);
            controller.loadNotifications();
        });
        refreshButton.setOnAction(controller::handleRafraichirNotifications);
        bottomBox.getChildren().addAll(markReadButton, refreshButton);

        borderPane.setCenter(tableView);
        borderPane.setBottom(bottomBox);

        tab.setContent(borderPane);
        return tab;
    }

    private Tab createAppareilsTab() {
        Tab tab = new Tab("Mes appareils");
        BorderPane borderPane = new BorderPane();

        TableView<Telephone> tableView = new TableView<>();
        controller.setAppareilsTable(tableView);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10));
        Button addButton = new Button("Ajouter un appareil");
        Button modifyButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        addButton.setOnAction(e -> {
            AddAppareilInterface.display((Stage) tabPane.getScene().getWindow(), controller);
            controller.loadAppareils();
        });
        modifyButton.setOnAction(controller::handleModifierAppareil);
        deleteButton.setOnAction(e -> {
            controller.handleSupprimerAppareil(e);
            controller.loadAppareils();
        });
        bottomBox.getChildren().addAll(addButton, modifyButton, deleteButton);

        borderPane.setCenter(tableView);
        borderPane.setBottom(bottomBox);

        tab.setContent(borderPane);
        return tab;
    }

    private void handle(ActionEvent e) {
        AddAnnonceInterface.display((Stage) tabPane.getScene().getWindow(), controller);
        controller.loadMyAnnonces();
    }

    public static void main(String[] args) {
        launch(args);
    }
}