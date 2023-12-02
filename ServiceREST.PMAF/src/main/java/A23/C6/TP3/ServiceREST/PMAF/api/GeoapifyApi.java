package A23.C6.TP3.ServiceREST.PMAF.api;

import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireClient;
import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireEntrepot;
import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GeoapifyApi {

    public void geocodeWithGeoapify(Client client) {
        try {
            String apiKey = "30eb601a40fa475886a9757fd17f3b91";
            String encodedAddress = URLEncoder.encode(client.getAdresse(), "UTF-8");

            String geocodingUrl = "https://api.geoapify.com/v1/geocode/search?text="
                    + encodedAddress + "&apiKey=" + apiKey;

            URL url = new URL(geocodingUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject responseObject = (JSONObject) new JSONParser().parse(response.toString());

                    updateClientInfo(client, responseObject);
                }
            } else {
                System.err.println("Erreur lors de la requête Geoapify Geocoding : " + http.getResponseCode() + " " + http.getResponseMessage());
            }

            http.disconnect();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateClientInfo(Client client, JSONObject geoapifyResponse) {
        // maj infos client en fonction de la réponse Geoapify
        JSONArray featuresArray = (JSONArray) geoapifyResponse.get("features");
        if (!featuresArray.isEmpty()) {
            JSONObject firstFeature = (JSONObject) featuresArray.get(0);
            JSONObject properties = (JSONObject) firstFeature.get("properties");

            client.setLatitude((Double) properties.get("lat"));
            client.setLongitude((Double) properties.get("lon"));
        }
    }
    public List<Client> solveTSP(Client entrepot, List<Client> clients) {
        List<Client> allClients = new ArrayList<>(clients);
        allClients.add(0, entrepot);

        List<List<Client>> permutations = new ArrayList<>();
        generatePermutations(allClients, allClients.size(), permutations);

        double minDistance = Double.MAX_VALUE;
        List<Client> optimalRoute = null;

        for (List<Client> permutation : permutations) {
            double distance = calculateTotalDistance(permutation);
            if (distance < minDistance) {
                minDistance = distance;
                optimalRoute = new ArrayList<>(permutation);
            }
        }

        return optimalRoute;
    }

    private void generatePermutations(List<Client> clients, int size, List<List<Client>> result) {
        if (size == 1) {
            result.add(new ArrayList<>(clients));
            return;
        }

        for (int i = 0; i < size; i++) {
            generatePermutations(clients, size - 1, result);

            if (size % 2 == 1) {
                Collections.swap(clients, 0, size - 1);
            } else {
                Collections.swap(clients, i, size - 1);
            }
        }
    }

    public double calculateTotalDistance(List<Client> route) {
        double totalDistance = 0.0;

        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += calculateDistance(
                    (Double) route.get(i).getLongitude(), (Double) route.get(i).getLatitude(),
                    (Double) route.get(i + 1).getLongitude(), (Double) route.get(i + 1).getLatitude()
            );
        }
        return totalDistance;
    }

    private double calculateDistance(double lon1, double lat1, double lon2, double lat2) {
        final int R = 6371; // Rayon de la Terre en km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance en km
        return R * c;
    }

}
