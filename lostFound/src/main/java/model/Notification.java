package model;

public class Notification {
    private int id;
    private int expediteurId;
    private int destinataireId;
    private int annonceId;
    private String message;
    private String dateEnvoi;
    private boolean estLu;

    // Constructeurs
    public Notification() {
    }

    public Notification(int id, int expediteurId, int destinataireId, int annonceId, String message, String dateEnvoi, boolean estLu) {
        this.id = id;
        this.expediteurId = expediteurId;
        this.destinataireId = destinataireId;
        this.annonceId = annonceId;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
        this.estLu = estLu;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(int expediteurId) {
        this.expediteurId = expediteurId;
    }

    public int getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(int destinataireId) {
        this.destinataireId = destinataireId;
    }

    public int getAnnonceId() {
        return annonceId;
    }

    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(String dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public boolean isEstLu() {
        return estLu;
    }

    public void setEstLu(boolean estLu) {
        this.estLu = estLu;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", expediteurId=" + expediteurId +
                ", destinataireId=" + destinataireId +
                ", annonceId=" + annonceId +
                ", message='" + message + '\'' +
                ", dateEnvoi='" + dateEnvoi + '\'' +
                ", estLu=" + estLu +
                '}';
    }
}
