package A23.C6.TP3.ServiceREST.PMAF.gestionnaire;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import A23.C6.TP3.ServiceREST.PMAF.service.GeoapifyService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class LireFichierClient {

    protected static void chargerClients(List<Client> list) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("clients.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("clients");

            GeoapifyService geoapifyService = new GeoapifyService();

            for (Object clientObject : jsonArray) {
                JSONObject clientJson = (JSONObject) clientObject;
                String nom = (String) clientJson.get("nom");
                String adresse = (String) clientJson.get("adresse");

                Client client = new Client(nom, adresse);
                geoapifyService.geocodeWithGeoapify(client);

                // add le client si les coordonnées sont valides
                if (client.getLatitude() != null && client.getLongitude() != null) {
                    list.add(client);
                } else {
                    System.err.println("Les coordonnées sont null pour l'adresse : " + adresse);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("clients.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("clients");

            Iterator<JSONObject> iterator = jsonArray.iterator();
            iterator.forEachRemaining(System.out::println);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}