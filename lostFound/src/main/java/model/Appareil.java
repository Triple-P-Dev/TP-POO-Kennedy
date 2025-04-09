package model;

public class Appareil {
    private int id;
    private String numSerie;
    private String marque;
    private String modele;
    private String description;
    private int utilisateurId;

    // Constructeurs
    public Appareil() {
    }

    public Appareil(int id, String numSerie, String marque, String modele, String description, int utilisateurId) {
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

    @Override
    public String toString() {
        return "Appareil{" +
                "id=" + id +
                ", numSerie='" + numSerie + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", description='" + description + '\'' +
                ", utilisateurId=" + utilisateurId +
                '}';
    }
}
