package A23.C6.TP3.ServiceREST.PMAF.db;

//import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BestRouteService {

    private BestRouteRepo bestRouteRepo;

    @Autowired
    public void setBestRouteRepo(BestRouteRepo bestRouteRepo) {
        this.bestRouteRepo = bestRouteRepo;
    }

    public void saveBestRoute(String routeOptimale) {
        BestRoute existingRoute = bestRouteRepo.findByColumnName(routeOptimale);

        if (existingRoute == null) {
            BestRoute newBestRoute = new BestRoute();
            newBestRoute.setColumnName(routeOptimale);
            bestRouteRepo.save(newBestRoute);
        }
    }

    public void deleteBestRoute(Integer id) {
        bestRouteRepo.deleteById(id);
    }

    public BestRoute getRouteById(int i) {
        return bestRouteRepo.findById(i);
    }

    public List<BestRoute> getAllRoutes() {
        return bestRouteRepo.findAll();
    }
}
