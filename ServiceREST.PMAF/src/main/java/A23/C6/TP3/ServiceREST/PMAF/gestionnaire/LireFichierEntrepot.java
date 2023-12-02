package A23.C6.TP3.ServiceREST.PMAF.gestionnaire;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import A23.C6.TP3.ServiceREST.PMAF.api.GeoapifyApi;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class LireFichierEntrepot {

    protected static Client chargerEntrepot() {
        JSONParser jsonP = new JSONParser();

        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("entrepot.json"));

            if (jsonO.containsKey("nom") && jsonO.containsKey("adresse")) {
                String nom = (String) jsonO.get("nom");
                String adresse = (String) jsonO.get("adresse");

                // Utiliser Geoapify pour valider les coordonnées de l'entrepôt
                GeoapifyApi geoapifyApi = new GeoapifyApi();
                Client entrepot = new Client(nom, adresse);
                geoapifyApi.geocodeWithGeoapify(entrepot);

                if (entrepot.getLatitude() != null && entrepot.getLongitude() != null) {
                    return entrepot;
                } else {
                    System.err.println("Les coordonnées de l'entrepôt sont null pour l'adresse : " + adresse);
                }
            } else {
                System.err.println("Le fichier entrepot.json ne contient pas les champs nécessaires.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Client entrepot = chargerEntrepot();
        if (entrepot != null) {
            System.out.println(entrepot.toString());
            System.out.println("Coordonnées de l'entrepôt : Latitude = " + entrepot.getLatitude() + ", Longitude = " + entrepot.getLongitude());
        } else {
            System.err.println("Impossible de charger les coordonnées de l'entrepôt.");
        }
    }
}
