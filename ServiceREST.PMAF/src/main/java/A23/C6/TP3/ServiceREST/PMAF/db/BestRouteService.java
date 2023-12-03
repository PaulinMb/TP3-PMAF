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

    public BestRouteRepo getBestRouteRepo() {
        return bestRouteRepo;
    }

    public void deleteBestRoute(Integer id) {
        bestRouteRepo.deleteById(id);
    }

    public BestRoute getRouteById() {
        return bestRouteRepo.findById(1);
    }

    public List<BestRoute> getAllRoutes() {
        return bestRouteRepo.findAll();
    }
}
