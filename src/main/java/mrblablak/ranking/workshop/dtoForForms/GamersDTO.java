package mrblablak.ranking.workshop.dtoForForms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GamersDTO {
    private int[] gamersList;
    private String server;
    private boolean teamsReady;
}
