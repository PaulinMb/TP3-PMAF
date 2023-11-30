package logistique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainControlleur implements Initializable {

    private List<Client> clientList = new ArrayList<>();

    private List<Client> clientListSelected = new ArrayList<>();

    @FXML
    private ListView<Client> listAdresse;

    @FXML
    private ListView<Client> listAdresseSelected;

    @FXML
    void calculeRouteOptimal(ActionEvent event) {
        ApiCall apiCall = new ApiCall();
        apiCall.postCalculateOptimalRoute(clientListSelected);

    }

    @FXML
    void loadAdressesApi(ActionEvent event)   {


        try {
            ApiCall apiCall = new ApiCall();
            clientList.clear();
            clientList.addAll(apiCall.getAllAdresses());
            System.out.println(clientList.toString());
            ObservableList<Client> observableClientList = FXCollections.observableArrayList(clientList);
            listAdresse.setItems(observableClientList);
        }catch (IOException e){

        }

    }


    @FXML
    void addToList(ActionEvent event) {
        ObservableList<Client> selectedClients =listAdresse.getSelectionModel().getSelectedItems();

        for (Client selectedClient : selectedClients) {
            clientListSelected.add(selectedClient);
            listAdresse.getItems().remove(selectedClient);
            //System.out.println(selectedClient);
        }
        listAdresseSelected.getItems().setAll(clientListSelected);
        System.out.println(clientListSelected);
    }

    @FXML
    void removeFromList(ActionEvent event) {
        ObservableList<Client> selectedClients =listAdresseSelected.getSelectionModel().getSelectedItems();

        for (Client selectedClient : selectedClients) {
            clientListSelected.remove(selectedClient);
            listAdresse.getItems().add(selectedClient);
            //System.out.println(selectedClient);
        }
        listAdresseSelected.getItems().setAll(clientListSelected);
        System.out.println(clientListSelected);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {





    }
}
