package A23.C6.TP3.ServiceREST.PMAF.gestionnaire;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import A23.C6.TP3.ServiceREST.PMAF.api.GeoapifyApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class LireFichierClient {

    protected static void chargerClients(List<Client> list) {
        try {
            ClassLoader classLoader = LireFichierClient.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("clients.json");

            if (inputStream != null) {
                try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
                    JSONArray jsonArray = (JSONArray) jsonObject.get("clients");

                    GeoapifyApi geoapifyApi = new GeoapifyApi();

                    for (Object clientObject : jsonArray) {
                        JSONObject clientJson = (JSONObject) clientObject;
                        String nom = (String) clientJson.get("nom");
                        String adresse = (String) clientJson.get("adresse");

                        Client client = new Client(nom, adresse);
                        geoapifyApi.geocodeWithGeoapify(client);

                        // ajouter le client si les coordonnées sont valides
                        if (client.getLatitude() != null && client.getLongitude() != null) {
                            list.add(client);
                        } else {
                            System.err.println("Les coordonnées sont null pour l'adresse : " + adresse);
                        }
                    }
                }
            } else {
                System.err.println("Le fichier clients.json n'a pas été trouvé dans les ressources.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Utiliser le ClassLoader pour accéder aux ressources
            ClassLoader classLoader = LireFichierClient.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("clients.json");

            if (inputStream != null) {
                try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
                    JSONArray jsonArray = (JSONArray) jsonObject.get("clients");

                    Iterator<JSONObject> iterator = jsonArray.iterator();
                    iterator.forEachRemaining(System.out::println);
                }
            } else {
                System.err.println("Le fichier clients.json n'a pas été trouvé dans les ressources.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
