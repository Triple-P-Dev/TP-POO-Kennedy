package model;

public class Telephone {
    private String imei;
    private int id;
    private String numSerie;
    private String marque;
    private String modele;
    private String description;
    private int utilisateurId;

    // Constructeurs
    public Telephone() {
    }

    public Telephone(int id, String imei, String numSerie, String marque, String modele, String description, int utilisateurId) {
        this.imei = String.valueOf(imei);
        this.id = id;
        this.numSerie = numSerie;
        this.marque = marque;
        this.modele = modele;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public Telephone(String imei) {
        this.imei = imei;
    }

    // Getters et setters
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "id=" + id +
                ", imei=" + imei +
                ", numSerie='" + numSerie + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", description='" + description + '\'' +
                ", utilisateurId=" + utilisateurId +
                '}';
    }
}
