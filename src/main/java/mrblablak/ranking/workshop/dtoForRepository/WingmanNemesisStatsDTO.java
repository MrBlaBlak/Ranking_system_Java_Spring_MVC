package mrblablak.ranking.workshop.dtoForRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class WingmanNemesisStatsDTO {
    private String name;
    private int wins;
    private int losses;
    private int winPercentage;
}
