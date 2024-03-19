package mrblablak.ranking.workshop.model;

import javax.persistence.*;

@Entity
@Table(name = "match_gamer")
public class MatchGamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamer;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    public MatchGamer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "MatchGamer{" +
                "id=" + id +
                ", match=" + match +
                ", gamer=" + gamer +
                ", team=" + team +
                '}';
    }
}
