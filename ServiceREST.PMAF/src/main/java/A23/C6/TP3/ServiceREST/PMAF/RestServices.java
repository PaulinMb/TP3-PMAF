package A23.C6.TP3.ServiceREST.PMAF;

import A23.C6.TP3.ServiceREST.PMAF.db.BestRoute;
import A23.C6.TP3.ServiceREST.PMAF.db.BestRouteService;
import org.hibernate.query.ReturnableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestServices {

    @Autowired
    private BestRouteService bestRouteService;


    @GetMapping("/getBestRoute")
    public ResponseEntity<BestRoute> getBestRoute() {
        List<BestRoute> routes = bestRouteService.getBestRouteRepo().findAllRoutes();

        if (!routes.isEmpty()) {
            return new ResponseEntity<>(routes.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // or another appropriate status
        }
    }
}

