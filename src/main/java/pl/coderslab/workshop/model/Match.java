package pl.coderslab.workshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private Map_Name map;
    @NotNull
    private String server;
    @NotNull
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }
    public Match() {
    }

    public enum Map_Name{
        boomtown, exo, eden, drydock, angel, colony, glitch, none
//        private String mapName;
//        Map_Name(String mapName) {
//            this.mapName = mapName;
//        }
//        public String getMapName() {
//            return mapName;
//        }
    }

//    public Match(int id, Map_Name map, String server) {
//        this.map = map;
//        this.server = server;
//        this.id=id;
//    }




    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getMap() {
        return map.name();
    }

    public void setMap(Map_Name map) {
        this.map = map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", map=" + map +
                '}';
    }
}
