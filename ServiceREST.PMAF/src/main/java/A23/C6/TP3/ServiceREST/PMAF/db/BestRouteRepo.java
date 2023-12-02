package A23.C6.TP3.ServiceREST.PMAF.db;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestRouteRepo extends JpaRepository<BestRoute, Integer> {
    @Query("SELECT br FROM BestRoute br")
    List<BestRoute> findAllRoutes();

    BestRoute findById(int id);
}