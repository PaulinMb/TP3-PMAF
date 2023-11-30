package A23.C6.TP3.ServiceREST.PMAF.service;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GeoapifyService {

    public void geocodeWithGeoapify(Client client) {
        try {
            String apiKey = "30eb601a40fa475886a9757fd17f3b91";
            String encodedAddress = URLEncoder.encode(client.getAdresse(), "UTF-8");

            String geocodingUrl = "https://api.geoapify.com/v1/geocode/search?text=38%20Upper%20Montagu%20Street%2C%20Westminster%20W1H%201LJ%2C%20United%20Kingdom"
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
        // maj  inflos client en fonction de la réponse Geoapify
        JSONArray featuresArray = (JSONArray) geoapifyResponse.get("features");
        if (!featuresArray.isEmpty()) {
            JSONObject firstFeature = (JSONObject) featuresArray.get(0);
            JSONObject properties = (JSONObject) firstFeature.get("properties");

            client.setLatitude(properties.get("lat"));
            client.setLongitude(properties.get("lon"));
        }
    }
}
