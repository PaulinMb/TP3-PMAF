package A23.C6.TP3.ServiceREST.PMAF.gestionnaire;

import A23.C6.TP3.ServiceREST.PMAF.modele.Client;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class LireFichierEntrepot {

    protected static Client chargerEntrepot() {
        JSONParser jsonP = new JSONParser();

        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("entrepot.json"));

            return new Client((String) jsonO.get("nom"), (String) jsonO.get("adresse"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        JSONParser jsonP = new JSONParser();

        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("entrepot.json"));

            System.out.println("Nom: " + jsonO.get("nom"));
            System.out.println("Adresse: " + jsonO.get("adresse"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
