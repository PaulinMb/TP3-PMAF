package A23.C6.TP3.ServiceREST.PMAF.db;

import jakarta.persistence.*;

@Entity
@Table(name = "fastestRoute")
public class BestRouteService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bestRoute")
    private String columnName;

    public BestRouteService() {
    }

    public BestRouteService(Long id, String columnName) {
        this.id = id;
        this.columnName = columnName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
