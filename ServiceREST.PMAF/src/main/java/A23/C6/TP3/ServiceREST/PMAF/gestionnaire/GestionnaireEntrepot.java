package A23.C6.TP3.ServiceREST.PMAF.gestionnaire;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;

public class GestionnaireEntrepot {

    Client entrepot;

    public void set(Client entrepot) {
        this.entrepot = entrepot;
    }

    public Client getEntrepot() {
        return LireFichierEntrepot.chargerEntrepot();
    }
}
