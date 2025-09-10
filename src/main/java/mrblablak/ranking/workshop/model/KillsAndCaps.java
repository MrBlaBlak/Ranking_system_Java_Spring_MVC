package mrblablak.ranking.workshop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "kills_and_caps")
public class KillsAndCaps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(optional=false)
    @JoinColumn(name = "match_gamer_id")
    private MatchGamer matchGamer;
    @NotNull
    private int kills;
    @NotNull
    private int caps;
    @Enumerated(EnumType.STRING)
    private Titan_Name titan;

    public KillsAndCaps(int kills, int caps, Titan_Name titan, MatchGamer matchGamer) {
        this.kills = kills;
        this.caps = caps;
        this.titan = titan;
        this.matchGamer = matchGamer;
    }

    public enum Titan_Name{
        ion, tone, ronin, northstar, monarch, legion, scorch, none
    }

}
