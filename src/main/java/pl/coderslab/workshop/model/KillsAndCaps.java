package pl.coderslab.workshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Titan_Name titan;
    public enum Titan_Name{
        ion, tone, ronin, northstar, monarch, legion, scorch, none
    }

    public KillsAndCaps() {
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getCaps() {
        return caps;
    }

    public void setCaps(int caps) {
        this.caps = caps;
    }
    public Titan_Name getTitan() {
        return titan;
    }

    public void setTitan(Titan_Name titan) {
        this.titan = titan;
    }

    public MatchGamer getMatchGamer() {
        return matchGamer;
    }

    public void setMatchGamer(MatchGamer matchGamer) {
        this.matchGamer = matchGamer;
    }
}
