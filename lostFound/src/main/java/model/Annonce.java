package model;

public class Annonce {

    private int id;
    private int utilisateurId;
    private String titre;
    private String description;
    private String dateCreation;
    private String datePerte;
    private String lieuPerte;
    private boolean estTrouve;

    // Informations de l'appareil associé
    private int idAppareil;
    private String marqueAppareil;
    private String modeleAppareil;
    private String numSerieAppareil;
    private String imeiAppareil;

    // Constructeur par défaut
    public Annonce() {
    }

    // Constructeur avec paramètres
    public Annonce(int id, int utilisateurId, String titre, String description,
                   String dateCreation, String datePerte, String lieuPerte, boolean estTrouve,
                   int idAppareil, String marqueAppareil, String modeleAppareil,
                   String numSerieAppareil, String imeiAppareil) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.datePerte = datePerte;
        this.lieuPerte = lieuPerte;
        this.estTrouve = estTrouve;
        this.idAppareil = idAppareil;
        this.marqueAppareil = marqueAppareil;
        this.modeleAppareil = modeleAppareil;
        this.numSerieAppareil = numSerieAppareil;
        this.imeiAppareil = imeiAppareil;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDatePerte() {
        return datePerte;
    }

    public void setDatePerte(String datePerte) {
        this.datePerte = datePerte;
    }

    public String getLieuPerte() {
        return lieuPerte;
    }

    public void setLieuPerte(String lieuPerte) {
        this.lieuPerte = lieuPerte;
    }

    public boolean isEstTrouve() {
        return estTrouve;
    }

    public void setEstTrouve(boolean estTrouve) {
        this.estTrouve = estTrouve;
    }

    public int getIdAppareil() {
        return idAppareil;
    }

    public void setIdAppareil(int idAppareil) {
        this.idAppareil = idAppareil;
    }

    public String getMarqueAppareil() {
        return marqueAppareil;
    }

    public void setMarqueAppareil(String marqueAppareil) {
        this.marqueAppareil = marqueAppareil;
    }

    public String getModeleAppareil() {
        return modeleAppareil;
    }

    public void setModeleAppareil(String modeleAppareil) {
        this.modeleAppareil = modeleAppareil;
    }

    public String getNumSerieAppareil() {
        return numSerieAppareil;
    }

    public void setNumSerieAppareil(String numSerieAppareil) {
        this.numSerieAppareil = numSerieAppareil;
    }

    public String getImeiAppareil() {
        return imeiAppareil;
    }

    public void setImeiAppareil(String imeiAppareil) {
        this.imeiAppareil = imeiAppareil;
    }



}