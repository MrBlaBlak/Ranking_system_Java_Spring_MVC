package mrblablak.ranking.workshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "win_or_loose")
    private int winOrLoose;
    @NotNull
    @Column(name = "flag_advantage")
    private int flagAdvantage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWinOrLoose() {
        return winOrLoose;
    }

    public void setWinOrLoose(int winOrLoose) {
        this.winOrLoose = winOrLoose;
    }

    public int getFlagAdvantage() {
        return flagAdvantage;
    }

    public void setFlagAdvantage(int flagAdvantage) {
        this.flagAdvantage = flagAdvantage;
    }

    public Team() {
    }

    public Team(int id, int winOrLoose, int flagAdvantage) {
        this.id = id;
        this.winOrLoose = winOrLoose;
        this.flagAdvantage = flagAdvantage;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", winOrLoose=" + winOrLoose +
                ", flagAdvantage=" + flagAdvantage +
                '}';
    }
}
