package A23.C6.TP3.ServiceREST.PMAF.modele;

import java.util.Objects;

public class Client {
    private String nom;
    private String adresse;
    private Object latitude;
    private Object longitude;
    private double distance;

    public Client(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}