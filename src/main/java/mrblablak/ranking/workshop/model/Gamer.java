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
}
