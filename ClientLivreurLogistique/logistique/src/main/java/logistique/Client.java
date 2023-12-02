package logistique;


import java.util.Objects;

public class Client {
    private String nom;
    private String adresse;
    private double latitude;
    private double longitude;
    private double distance;

    public Client(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    public Client(String nom, String adresse, double latitude, double longitude, double distance) {
        this.nom = nom;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "{"
                +"\""+"nom\": \"" + nom + "\", "
                +"\""+"adresse\": \"" + adresse + "\""
                +", \""+"latitude\": " + latitude
                +", \""+"longitude\": " + longitude
                +", \""+"distance\": " + distance +
                '}';
    }
}

