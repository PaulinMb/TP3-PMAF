package A23.C6.TP3.ServiceREST.PMAF.db;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveBestRoute(BestRoute bestRoute){
        bestRouteRepo.save(bestRoute);
    }

    public void saveBestRoute(String optimalRoute) {
        BestRoute bestRoute = new BestRoute();
        bestRoute.setColumnName(optimalRoute);
        //bestRouteRepo.save(bestRoute);
        System.out.println("Route optimale enregistrée en base de données : " + optimalRoute);
    }

    public void deleteBestRoute(Integer id){
        bestRouteRepo.deleteById(id);
    }

    public List<BestRoute> getAllRoutes(){
        return bestRouteRepo.findAll();
    }
}
