//package A23.C6.TP3.ServiceREST.PMAF.modele;
//import A23.C6.TP3.ServiceREST.PMAF.gestionnaire.GestionnaireClient;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class TSPSolver {
//
//    public static List<Client> solveTSP(Client entrepot, List<Client> clients) {
//        // Créer une liste de tous les clients (y compris l'entrepôt)
//        List<Client> allClients = new ArrayList<>(clients);
//        allClients.add(0, entrepot);
//
//        // Calculer toutes les permutations possibles
//        List<List<Client>> permutations = new ArrayList<>();
//        generatePermutations(allClients, allClients.size(), permutations);
//
//        // Calculer la distance de chaque permutation
//        double minDistance = Double.MAX_VALUE;
//        List<Client> optimalRoute = null;
//
//        for (List<Client> permutation : permutations) {
//            double distance = calculateTotalDistance(permutation);
//            if (distance < minDistance) {
//                minDistance = distance;
//                optimalRoute = new ArrayList<>(permutation);
//            }
//        }
//
//        return optimalRoute;
//    }
//
//    private static void generatePermutations(List<Client> clients, int size, List<List<Client>> result) {
//        if (size == 1) {
//            result.add(new ArrayList<>(clients));
//            return;
//        }
//
//        for (int i = 0; i < size; i++) {
//            generatePermutations(clients, size - 1, result);
//
//            if (size % 2 == 1) {
//                Collections.swap(clients, 0, size - 1);
//            } else {
//                Collections.swap(clients, i, size - 1);
//            }
//        }
//    }
//
//    public static double calculateTotalDistance(List<Client> route) {
//        double totalDistance = 0.0;
//
//        for (int i = 0; i < route.size() - 1; i++) {
//            totalDistance += calculateDistance(
//                    (Double) route.get(i).getLongitude(), (Double) route.get(i).getLatitude(),
//                    (Double) route.get(i + 1).getLongitude(), (Double) route.get(i + 1).getLatitude()
//            );
//        }
//
//        return totalDistance;
//    }
//
//    // Méthode pour calculer la distance entre deux points géographiques
//    private static double calculateDistance(double lon1, double lat1, double lon2, double lat2) {
//        final int R = 6371; // Rayon de la Terre en kilomètres
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        // Distance en kilomètres
//        return R * c;
//    }
//
//    public static void main(String[] args) {
//        // Exemple d'utilisation
//        Client entrepot = new Client("YUL5 - Amazon Centre de Tri", "5799 Rte de l'Aéroport, Saint-Hubert, QC J3Y 8Y9");
//        entrepot.setLatitude(51.52016005);
//        entrepot.setLongitude(-0.16030636023550826);
//        List<Client> clients = new ArrayList<>();
//        // Ajoutez tous les clients
//        clients.addAll(new GestionnaireClient().getClientList());
//
//        List<Client> optimalRoute = solveTSP(entrepot, clients);
//
//        // Affichez la route optimale
//        System.out.println("Route optimale : ");
//        for (Client client : optimalRoute) {
//            System.out.println(client.getNom() + " - " + client.getAdresse());
//        }
//
//        // Affichez la distance totale de la route optimale
//        System.out.println("Distance totale : " + calculateTotalDistance(optimalRoute) + " km");
//    }
//}
