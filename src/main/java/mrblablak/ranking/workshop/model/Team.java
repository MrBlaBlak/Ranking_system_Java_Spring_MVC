package mrblablak.ranking.workshop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
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
    public Team(int id, int winOrLoose, int flagAdvantage) {
        this.id = id;
        this.winOrLoose = winOrLoose;
        this.flagAdvantage = flagAdvantage;
    }
}
