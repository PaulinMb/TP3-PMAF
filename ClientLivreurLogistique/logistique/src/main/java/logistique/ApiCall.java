package logistique;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiCall {

    private final String apiAddress = "http://localhost:8080";

    public List<Client> getAllAdresses() throws IOException {
        List<Client> clientListRet = new ArrayList<>();
        String url = apiAddress + "/allClients";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Code: " + httpResponse.statusCode());
            System.out.println("Response Body: " + httpResponse.body());
            clientListRet = readJson(httpResponse.body());

        }catch (Exception e){

        }
        return clientListRet;
    }

    public void postCalculateOptimalRoute(List<Client> clientList){
        String url = apiAddress + "/calculateOptimalRoute";

        String jsonArray = JSONArray.toJSONString(clientList);
        System.out.println("json arr"+jsonArray);
        HttpClient httpCLient =  HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().header("Content-Type","application/json").uri(URI.create(url)).POST(HttpRequest.BodyPublishers.ofString(jsonArray)).build();

        try {
            HttpResponse<String> httpResponse = httpCLient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Code: " + httpResponse.statusCode());
            System.out.println("Response Body: " + httpResponse.body());
            System.out.println(httpResponse.body());
        }catch (Exception e){

        }
    }

    public List<Client> readJson(String jsonObj){
        List<Client> clientListRet = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        try {

            JSONArray jsonA = (JSONArray)jsonP.parse(jsonObj);

            for (int i = 0;i<jsonA.size();i++) {
                JSONObject jsonO = (JSONObject)jsonP.parse(jsonA.get(i).toString());
                clientListRet.add(new Client((String) jsonO.get("nom"), (String) jsonO.get("adresse"), (Object) jsonO.get("latitude"), (Object) jsonO.get("longitude"), (Double) jsonO.get("distance")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  clientListRet;
    }
}
