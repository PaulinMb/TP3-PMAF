package A23.C6.TP3.ServiceREST.PMAF.db;

//import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "fastestroute")
public class BestRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "bestroute")
    private String columnName;

    public BestRoute() {
    }

    public BestRoute(Integer id, String columnName) {
        this.id = id;
        this.columnName = columnName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
