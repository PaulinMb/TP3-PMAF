package A23.C6.TP3.ServiceREST.PMAF.db;

//import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class BestRouteService {

    private  BestRouteRepo bestRouteRepo;
    @Autowired
    public void setBestRouteRepo(BestRouteRepo bestRouteRepo) {
        this.bestRouteRepo = bestRouteRepo;
    }

    public void saveBestRoute(String routeOptimale) {
        List<BestRoute> routeList = bestRouteRepo.findAllRoutes();
        if (routeList.isEmpty()) {
            BestRoute newRoute = new BestRoute();
            newRoute.setColumnName(routeOptimale);
            bestRouteRepo.save(newRoute);
        } else {
            BestRoute firstRoute = routeList.get(0);
            firstRoute.setColumnName(routeOptimale);
            bestRouteRepo.save(firstRoute);
        }
    }


    public void deleteBestRoute(Integer id){
        bestRouteRepo.deleteById(id);
    }

    public BestRoute getOneBestRoute(int id) {
        return bestRouteRepo.findById(id);
    }

    public List<BestRoute> getAllRoutes(){
        return bestRouteRepo.findAll();
    }
}
