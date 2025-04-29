package model;

public class Ordinateur {
    private String adresseMac;

    // Constructeurs
    public Ordinateur() {
    }

    public Ordinateur(String adresseMac) {
        this.adresseMac = adresseMac;
    }

    // Getters et setters
    public String getAdresseMac() {
        return adresseMac;
    }

    public void setAdresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
    }

    @Override
    public String toString() {
        return "Ordinateur{" +
                "adresseMac='" + adresseMac + '\'' +
                '}';
    }
}
