package A23.C6.TP3.ServiceREST.PMAF.service;



import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireClient;
import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireEntrepot;
import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
public class RestService {
    private GestionnaireClient gestionnaireClient;
    private GestionnaireEntrepot gestionnaireEntrepot;

   /* @Autowired
    private BestRouteService bestRouteService;*/

    public RestService() {
        this.gestionnaireClient = new GestionnaireClient();
        this.gestionnaireEntrepot = new GestionnaireEntrepot();
    }

    @GetMapping(value = "/allClients")
    public List<Client> obtenirClients(){
        return gestionnaireClient.getClientList();
    }

    @GetMapping("/getBestRoute")
    public ResponseEntity<String> getBestRoute() {  //ResponseEntity<BestRoute>
        //List<BestRoute> routes = bestRouteService.getBestRouteRepo().findAllRoutes();
        String route = "depart / wp1 / wp2 / retourDepart";

        if (!route.isEmpty()) {  //!routes.isEmpty()
            //BestRoute bestRoute = routes.get(0);
            if (route!= null) { //bestRoute.getColumnName() != null
                return new ResponseEntity<>(route, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // or another appropriate status
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/calculateOptimalRoute")
    public ResponseEntity<String> calculateOptimalRoute(@RequestBody List<Client> clientList) {
        if (clientList!=null){
            System.out.println(clientList.toString());
        }
       try {
            List<Client> clients = gestionnaireClient.getClientList();
            Client entrepot = gestionnaireEntrepot.getEntrepot();

            GeoapifyService geoapifyService = new GeoapifyService();

            // 1. Géocodage des adresses des clients
            for (Client client : clients) {
                geoapifyService.geocodeWithGeoapify(client);
            }

            // 2. Construction des waypoints pour l'API de routage
            StringBuilder waypointsBuilder = new StringBuilder();
            waypointsBuilder.append(entrepot.getLatitude()).append(",").append(entrepot.getLongitude()).append("|");
            for (Client client : clients) {
                waypointsBuilder.append(client.getLatitude()).append(",").append(client.getLongitude()).append("|");
            }
            // Supprimer le dernier caractère "|"
            waypointsBuilder.deleteCharAt(waypointsBuilder.length() - 1);

            // 3. Appel à l'API planner ditineraire de routage Geoapify
            String apiUrl = "https://api.geoapify.com/v1/routeplanner?apiKey=427ba5112e3a44178d8161dd05279f16";
            HttpURLConnection routePlannerConnection = null;
            OutputStream routePlannerOutputStream = null;

            try {
                routePlannerConnection = (HttpURLConnection) new URL(apiUrl).openConnection();
                routePlannerConnection.setRequestMethod("POST");
                routePlannerConnection.setDoOutput(true);
                routePlannerConnection.setRequestProperty("Content-Type", "application/json");

                // Construction du corps de la requête pour l'API de routage
                String routePlannerRequestBody = constructRoutePlannerRequestBody(entrepot, clients);
                byte[] routePlannerRequestBytes = routePlannerRequestBody.getBytes(StandardCharsets.UTF_8);

                routePlannerOutputStream = routePlannerConnection.getOutputStream();
                routePlannerOutputStream.write(routePlannerRequestBytes);

                // Récupération de la réponse de l'API de routage
                int routePlannerResponseCode = routePlannerConnection.getResponseCode();
                if (routePlannerResponseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader routePlannerReader = new BufferedReader(new InputStreamReader(routePlannerConnection.getInputStream()))) {
                        StringBuilder routePlannerResponse = new StringBuilder();
                        String line;
                        while ((line = routePlannerReader.readLine()) != null) {
                            routePlannerResponse.append(line);
                        }

                        // Parse la réponse pour obtenir la distance de la meilleure route
                        JSONObject routePlannerJson = (JSONObject) new JSONParser().parse(routePlannerResponse.toString());
                        Double bestRouteDistance = (Double) routePlannerJson.get("distance");

                        // Enregistre la meilleure route dans la base de données
                         //bestRouteService.saveBestRoute(bestRouteDistance);

                        // Réponse à renvoyer en cas de succès
                        return new ResponseEntity<>("Route optimale calculée et enregistrée avec succès", HttpStatus.OK);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // En cas d'échec de la requête à l'API de routage
                    return new ResponseEntity<>("Erreur lors de la requête à l'API de routage Geoapify : " + routePlannerResponseCode, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Erreur lors du calcul de la route optimale", HttpStatus.INTERNAL_SERVER_ERROR);
            } finally {
                // Fermeture des flux et déconnexion
                if (routePlannerOutputStream != null) {
                    routePlannerOutputStream.close();
                }
                if (routePlannerConnection != null) {
                    routePlannerConnection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors du calcul de la route optimale", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    private void logIpAddress() {
        try {
            // Emplacement du fichier de log
            //String logFilePath = "c:/temp/adresseip.log"; Pour le tp
            String logFilePath = "adresseip.log"; // Test

            File logFile = new File(logFilePath);
            FileWriter fileWriter = new FileWriter(logFile, true);

            // recuperer de l'adresse IP
            String ipAddress = "";

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                if (request != null) {
                    ipAddress = request.getHeader("X-FORWARDED-FOR");
                    if (ipAddress == null || "".equals(ipAddress)) {
                        ipAddress = request.getRemoteAddr();
                    }
                }
            }

            // save ip dans le adresseip.log
            fileWriter.append(ipAddress).append("\n");
            fileWriter.close();

            System.out.println("Adresse IP enregistrée dans le fichier de log : " + ipAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour construire le corps de la requête pour l'API de routage
    private String constructRoutePlannerRequestBody(Client entrepot, List<Client> clients) {
        JSONObject routePlannerRequestJson = new JSONObject();
        routePlannerRequestJson.put("mode", "drive");

        JSONArray agentsArray = new JSONArray();
        JSONObject entrepotAgent = new JSONObject();
        entrepotAgent.put("start_location", Arrays.asList(entrepot.getLongitude(), entrepot.getLatitude()));
        entrepotAgent.put("end_location", Arrays.asList(entrepot.getLongitude(), entrepot.getLatitude()));
        entrepotAgent.put("pickup_capacity", 0);
        agentsArray.add(entrepotAgent);

        for (Client client : clients) {
            JSONObject clientAgent = new JSONObject();
            clientAgent.put("start_location", Arrays.asList(client.getLongitude(), client.getLatitude()));
            clientAgent.put("end_location", Arrays.asList(client.getLongitude(), client.getLatitude()));
            clientAgent.put("pickup_capacity", 1);
            agentsArray.add(clientAgent);
        }

        routePlannerRequestJson.put("agents", agentsArray);

        JSONArray jobsArray = new JSONArray();
        for (Client client : clients) {
            JSONObject job = new JSONObject();
            job.put("location", Arrays.asList(client.getLongitude(), client.getLatitude()));
            job.put("duration", 300);
            job.put("pickup_amount", 1);
            jobsArray.add(job);
        }

        routePlannerRequestJson.put("jobs", jobsArray);

        return routePlannerRequestJson.toJSONString();
    }

}
