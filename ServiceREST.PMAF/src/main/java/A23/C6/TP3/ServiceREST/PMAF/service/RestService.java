package A23.C6.TP3.ServiceREST.PMAF.service;


import A23.C6.TP3.ServiceREST.PMAF.api.GeoapifyApi;
import A23.C6.TP3.ServiceREST.PMAF.db.BestRoute;
import A23.C6.TP3.ServiceREST.PMAF.db.BestRouteService;
import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireClient;
import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireEntrepot;
import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

@RestController
public class RestService {
    private GestionnaireClient gestionnaireClient;
    private GestionnaireEntrepot gestionnaireEntrepot;
    private A23.C6.TP3.ServiceREST.PMAF.db.BestRouteService bestRouteService;

    @Autowired
    private GeoapifyApi geoapifyApi;
    @Autowired
    public void setBestRouteService(BestRouteService bestRouteService) {
        this.bestRouteService = bestRouteService;
    }

    public RestService() {
        this.gestionnaireClient = new GestionnaireClient();
        this.gestionnaireEntrepot = new GestionnaireEntrepot();
    }

    @GetMapping(value = "/allClients")
    public List<Client> obtenirClients() {
        return gestionnaireClient.getClientList();
    }

    @GetMapping("/getBestRoute")
    public ResponseEntity<String> getBestRoute() {
        BestRoute bestRoute = bestRouteService.getRouteById();
        return new ResponseEntity<>(bestRoute.getColumnName(),HttpStatus.OK);
    }


    @PostMapping("/calculateOptimalRoute")
    public ResponseEntity<String> calculateOptimalRoute(@RequestBody List<Client> clients) {
        Client entrepot = gestionnaireEntrepot.getEntrepot();
        System.out.println(clients.toString());


        // 1. Geocodage des adresses des clients avec Geoapify
        for (Client client : clients) {
            geoapifyApi.geocodeWithGeoapify(client);
        }

        // 2. Optimisation de la route
        List<Client> optimalRoute = geoapifyApi.solveTSP(entrepot, clients);
        DecimalFormat df = new DecimalFormat("0.00");
        double distance = geoapifyApi.calculateTotalDistance(clients);


        // 3. Affichage de la route optimale dans la console
        StringBuilder resultStringBuilder = new StringBuilder();
        resultStringBuilder.append("Route optimale : \n");
        resultStringBuilder.append("Entrepot : " + entrepot.getNom() + " - " +  entrepot.getAdresse());

        int i = 0;
        for (Client client : optimalRoute) {
            i += 1;
            resultStringBuilder.append(i)
                    .append(" -> ")
                    .append(client.getNom())
                    .append(" - ")
                    .append(client.getAdresse())
                    .append("\n");
        }
        resultStringBuilder.append("Entrepot : " + entrepot.getNom() + " - " +  entrepot.getAdresse());

        resultStringBuilder.append("Distance totale : ")
                .append(df.format(distance))
                .append(" km");
        String showRoute = resultStringBuilder.toString();


        BestRoute bestRoute = bestRouteService.getRouteById();
        bestRoute.setColumnName(showRoute);
        bestRouteService.getBestRouteRepo().save(bestRoute);

        System.out.println(showRoute);

        logIpAddress();
        return new ResponseEntity<>("Route optimale calculée avec succès ... " + "\n" + showRoute, HttpStatus.OK);
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

}
