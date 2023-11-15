package pl.coderslab.workshop.dtoForRepository;

public class WingmanNemesisStatsDTO {
    private String name;
    private int wins;
    private int losses;
    private int winPercentage;

    public WingmanNemesisStatsDTO(String name, int wins, int losses, int winPercentage) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.winPercentage = winPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WingmanNemesisStatsDTO() {
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(int winPercentage) {
        this.winPercentage = winPercentage;
    }
}
