package mrblablak.ranking.workshop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "gamers")
public class Gamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private double mmr;
    @NotNull
    private String server;
    @Column(name = "last_ten")
    @NotNull
    private String lastTen;

    public Gamer(String name, double mmr, String server, String lastTen) {
        this.name = name;
        this.mmr = mmr;
        this.server = server;
        this.lastTen = lastTen;
    }


    public void cloneValues(Gamer another) {

        this.setMmr(another.getMmr());
        this.setName(another.getName());
        this.setServer(another.getServer());
        this.setId(another.getId());
        this.setLastTen(another.getLastTen());
    }
    public String[] getAllServers(){
        return new String[]{"EU", "NY"};
    }
    public int serverHandicap(String game_server) {
        int handicap=0;
        switch (this.server) {
            case "EU1": {
                if(game_server.equals("NY")) handicap=5;
                else if(game_server.equals("EU")) handicap=0; break;
            }
            case "EU2": {
                if(game_server.equals("NY")) handicap=6;
                else if(game_server.equals("EU")) handicap=0; break;
            }
            case "EU3": {
                if(game_server.equals("NY")) handicap=7;
                else if(game_server.equals("EU")) handicap=0; break;
            }
            case "EU4": {
                if(game_server.equals("NY")) handicap=8;
                else if(game_server.equals("EU")) handicap=0; break;
            }
            case "EU5": {
                if(game_server.equals("NY")) handicap=10;
                else if(game_server.equals("EU")) handicap=5; break;
            }
            case "EU6": {
                if(game_server.equals("NY")) handicap=12;
                else if(game_server.equals("EU")) handicap=6; break;
            }
            case "NY1": {
                if(game_server.equals("EU")) handicap=5;
                else if(game_server.equals("NY")) handicap=0; break;
            }
            case "NY2": {
                if(game_server.equals("EU")) handicap=6;
                else if(game_server.equals("NY")) handicap=0; break;
            }
            case "NY3": {
                if(game_server.equals("EU")) handicap=7;
                else if(game_server.equals("NY")) handicap=0; break;
            }
            case "NY4": {
                if(game_server.equals("EU")) handicap=8;
                else if(game_server.equals("NY")) handicap=0; break;
            }
            case "NY5": {
                if(game_server.equals("EU")) handicap=10;
                else if(game_server.equals("NY")) handicap=5; break;
            }
            case "NY6": {
                if(game_server.equals("EU")) handicap=12;
                else if(game_server.equals("NY")) handicap=6; break;
            }
            default: {
                handicap=0;
            }

        }
        return handicap;
    }
}
