package mrblablak.ranking.workshop.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.service.lobby.MmrCalculator;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MmrCalculatorImpl implements MmrCalculator {

    private static final int TEAM_SIZE = 5;

    @Override
    public boolean calculateMmr(int whoWon, Gamer[] team1gamers, Gamer[] team2gamers, Team team1, Team team2, boolean suddenDeath) {
        int streak = 0, streak2 = 0;
        for (int i = 0; i < TEAM_SIZE; i++) {

            //lastTen is the binary representation of last 10 games where 0 represents a loss and 1 represents a win - so e.g. 1011 is: win loss win win
            int countDown = Integer.parseInt(team1gamers[i].getLastTen(), 2);
            int countDown2 = Integer.parseInt(team2gamers[i].getLastTen(), 2);

            for (int a = 0; a < 10; a++) {
                if ((countDown & 1) == 1) streak++;
                countDown = countDown >> 1;
                if ((countDown2 & 1) == 1) streak2++;
                countDown2 = countDown2 >> 1;
            }
            //apply bonus from last 10 winrate
            double points = 0, points2 = 0;
            if ((streak == 7 || streak == 8) && whoWon == 1) points = 1.2d;
            else if ((streak == 2 || streak == 3) && whoWon == 2) points = -1.2d;
            else if (streak > 1 && whoWon == 2) points = -1;
            else if (streak >= 9 && whoWon == 1) points = 1.5d;
            else if (streak <= 1 && whoWon == 2) points = -1.5d;
            else if (streak < 9 && whoWon == 1) points = 1;

            if ((streak2 == 7 || streak2 == 8) && whoWon == 2) points2 = 1.2d;
            else if ((streak2 == 2 || streak2 == 3) && whoWon == 1) points2 = -1.2d;
            else if (streak2 > 1 && whoWon == 1) points2 = -1;
            else if (streak2 >= 9 && whoWon == 2) points2 = 1.5d;
            else if (streak2 <= 1 && whoWon == 1) points2 = -1.5d;
            else if (streak2 < 9 && whoWon == 2) points2 = 1;

            //apply bonus from flag advantage
            if (whoWon == 1) {
                points = points + (team1.getFlagAdvantage() / 5.0d) - 0.2d;
                points2 = points2 + (team2.getFlagAdvantage() / 5.0d) + 0.2d;
                //change score if suddenDeath
                if (suddenDeath) {
                    points = 0.5d;
                    points2 = -0.5d;
                    //update last ten; suddenDeath result is not counted neither as win nor loss for last 10
                } else {
                    team1gamers[i].setLastTen(Integer.toBinaryString((Integer.parseInt(team1gamers[i].getLastTen(), 2) >> 1) | 512));
                    team2gamers[i].setLastTen(Integer.toBinaryString(Integer.parseInt(team2gamers[i].getLastTen(), 2) >> 1));
                }
            }
            if (whoWon == 2) {
                points2 = points2 + team2.getFlagAdvantage() / 5.0d - 0.2d;
                points = points + team1.getFlagAdvantage() / 5.0d + 0.2d;
                //change score if suddenDeath
                if (suddenDeath) {
                    points = -0.5d;
                    points2 = 0.5d;
                    //update last ten; suddenDeath result is not counted neither as win nor loss for last 10
                } else {
                    team1gamers[i].setLastTen(Integer.toBinaryString(Integer.parseInt(team1gamers[i].getLastTen(), 2) >> 1));
                    team2gamers[i].setLastTen(Integer.toBinaryString((Integer.parseInt(team2gamers[i].getLastTen(), 2) >> 1) | 512));
                }
            }
            team1gamers[i].setMmr(Math.round((team1gamers[i].getMmr() + points) * 10) / 10d);
            team2gamers[i].setMmr(Math.round((team2gamers[i].getMmr() + points2) * 10) / 10d);
            streak = 0;
            streak2 = 0;
        }
        return true;
    }
}
