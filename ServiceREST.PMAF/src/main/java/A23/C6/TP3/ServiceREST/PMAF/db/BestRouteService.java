package A23.C6.TP3.ServiceREST.PMAF.db;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class BestRouteService {


    private  BestRouteRepo bestRouteRepo;

    public BestRouteRepo getBestRouteRepo() {
        return bestRouteRepo;
    }

    @Autowired
    public void setBestRouteRepo(BestRouteRepo bestRouteRepo) {
        this.bestRouteRepo = bestRouteRepo;
    }

    public void saveBestRoute(Double totalDistance) {
        BestRoute bestRoute = new BestRoute();
        bestRoute.setColumnName(MessageFormat.format("Total Distance: {totalDistance} meters", totalDistance));
        bestRouteRepo.save(bestRoute);
        System.out.println("Route optimale enregistrée en base de données : " + bestRoute.getColumnName());
    }
    public void deleteBestRoute(Integer id){
        bestRouteRepo.deleteById(id);
    }

    public List<BestRoute> getAllRoutes(){
        return bestRouteRepo.findAll();
    }
}
