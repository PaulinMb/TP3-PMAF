package A23.C6.TP3.ServiceREST.PMAF.gestionnaire;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireClient {
    List<Client> clientList;

    public GestionnaireClient() {
        this.clientList = new ArrayList<>();
        LireFichierClient.chargerClients(clientList);
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void modifierClient(int id, Client client) {
        clientList.set(id, client);
    }

}
