package A23.C6.TP3.ServiceREST.PMAF.db;

//import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BestRouteService {

    private BestRouteRepo bestRouteRepo;
    // siado123

    @Autowired
    public void setBestRouteRepo(BestRouteRepo bestRouteRepo) {
        this.bestRouteRepo = bestRouteRepo;
    }

    public void saveBestRoute(String routeOptimale) {
        BestRoute existingBestRoute = bestRouteRepo.findById(1);

        if (existingBestRoute != null) {
            // Si la BestRoute existe, la mettre à jour
            existingBestRoute.setColumnName(routeOptimale);
            bestRouteRepo.save(existingBestRoute);
        } else {
            // Si la BestRoute n'existe pas, la créer
            existingBestRoute = new BestRoute(1,routeOptimale);
            bestRouteRepo.save(existingBestRoute);
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
