package mrblablak.ranking.workshop.service.lobby.impl;

import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.model.KillsAndCaps;
import mrblablak.ranking.workshop.model.MatchGamer;
import mrblablak.ranking.workshop.model.Team;
import mrblablak.ranking.workshop.model.Match;
import mrblablak.ranking.workshop.repository.*;
import mrblablak.ranking.workshop.service.lobby.MmrCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class DataHandlerImplTest {
    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;
    @Mock
    private GamerRepository gamerRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private KillsAndCapsRepository killsAndCapsRepository;

    @Mock
    private MatchGamerRepository matchGamerRepository;

    @Mock
    private MmrCalculator mmrCalculator;

    @InjectMocks
    private DataHandlerImpl dataHandler;

    @BeforeEach
    public void setUp()  {

    }
    @Test
    public void testValidDataHandling() {
        // Prepare valid input
        GamersMatchStatsDTO gamersMatchStatsDTO = createValidGamersMatchStatsDTO();
        Gamer[] team1gamers = createValidTeam1GamersArray();
        Gamer[] team2gamers = createValidTeam2GamersArray();

        // Mock GamerRepository behavior
        for (int i = 0; i < TEAM_SIZE; i++) {
            when(gamerRepository.findById(gamersMatchStatsDTO.getTeam1gamersId()[i]))
                    .thenReturn(Optional.of(team1gamers[i]));
            when(gamerRepository.findById(gamersMatchStatsDTO.getTeam2gamersId()[i]))
                    .thenReturn(Optional.of(team2gamers[i]));
        }

        // Mock MmrCalculator behavior
        when(mmrCalculator.calculateMmr(anyInt(), any(), any(), any(), any(), anyBoolean()))
                .thenReturn(true);

        // Mock repository save methods
        Match mockMatch = mock(Match.class);
        Team mockTeam = mock(Team.class);
        Gamer mockGamer = mock(Gamer.class);
        MatchGamer mockMatchGamer = mock(MatchGamer.class);
        KillsAndCaps mockKillsAndCaps = mock(KillsAndCaps.class);

        when(matchRepository.save(any())).thenReturn(mockMatch);
        when(teamRepository.save(any())).thenReturn(mockTeam);
        when(gamerRepository.save(any())).thenReturn(mockGamer);
        when(matchGamerRepository.save(any())).thenReturn(mockMatchGamer);
        when(killsAndCapsRepository.save(any())).thenReturn(mockKillsAndCaps);

        // Execute the method under test
        boolean result = dataHandler.setData(gamersMatchStatsDTO, team1gamers, team2gamers);

        assertTrue(result);
        // Add further assertions to verify data saving and state
        verify(matchRepository, times(1)).save(any());
        verify(teamRepository, times(2)).save(any());
        verify(gamerRepository, times(LOBBY_SIZE)).save(any());
        verify(matchGamerRepository, times(LOBBY_SIZE)).save(any());
        verify(killsAndCapsRepository, times(LOBBY_SIZE)).save(any());
    }
    @Test
    public void testExceptionHandling() {

        GamersMatchStatsDTO gamersMatchStatsDTO = createValidGamersMatchStatsDTO();
        Gamer[] team1gamers = createinValidTeam1GamersArray();
        Gamer[] team2gamers = createinValidTeam2GamersArray();

        for (int i = 0; i < TEAM_SIZE; i++) {
            when(gamerRepository.findById(gamersMatchStatsDTO.getTeam1gamersId()[i]))
                    .thenReturn(Optional.of(team1gamers[i]));
            when(gamerRepository.findById(gamersMatchStatsDTO.getTeam2gamersId()[i]))
                    .thenReturn(Optional.of(team2gamers[i]));
        }
        when(mmrCalculator.calculateMmr(anyInt(), any(), any(), any(), any(), anyBoolean()))
                .thenReturn(true);

        // Execute and expect an exception to be thrown
        assertThrows(Exception.class, () -> dataHandler.setData(gamersMatchStatsDTO, team1gamers, team2gamers));

    }


    private GamersMatchStatsDTO createValidGamersMatchStatsDTO() {
        String server = "Server1";
        boolean suddenDeath = false;
        String suddenDeathWhoWon = null;
        String[] team1titans = {"ion", "tone", "monarch", "northstar", "legion"};
        String[] team2titans = {"ion", "tone", "monarch", "northstar", "legion"};
        int[] team1gamersId = {1, 2, 3, 4, 5};
        int[] team1elims = {10, 15, 20, 25, 30};
        int[] team1flags = {1, 2, 3, 4, 5};
        int[] team2gamersId = {6, 7, 8, 9, 10};
        int[] team2elims = {5, 10, 15, 20, 25};
        int[] team2flags = {2, 3, 4, 5, 6};
        String mapPlayed = "boomtown";

        return new GamersMatchStatsDTO(server, suddenDeath, suddenDeathWhoWon, team1titans, team2titans, team1gamersId, team1elims, team1flags, team2gamersId, team2elims, team2flags, mapPlayed);
    }


    private Gamer[] createValidTeam1GamersArray() {

        Gamer player1 = new Gamer("Player1", 1500.0, "Server1", "1010101010");
        player1.setId(1);

        Gamer player2 = new Gamer("Player2", 1600.0, "Server1", "1010101010");
        player2.setId(2);

        Gamer player3 = new Gamer("Player3", 1700.0, "Server1", "1010101010");
        player3.setId(3);

        Gamer player4 = new Gamer("Player4", 1800.0, "Server1", "1010101010");
        player4.setId(4);

        Gamer player5 = new Gamer("Player5", 1900.0, "Server1", "1010101010");
        player5.setId(5);

        return new Gamer[] { player1, player2, player3, player4, player5 };
    }

    private Gamer[] createValidTeam2GamersArray() {

        Gamer player6 = new Gamer("Player6", 1500.0, "Server1", "1010101010");
        player6.setId(6);

        Gamer player7 = new Gamer("Player7", 1600.0, "Server1", "1010101010");
        player7.setId(7);

        Gamer player8 = new Gamer("Player8", 1700.0, "Server1", "1010101010");
        player8.setId(8);

        Gamer player9 = new Gamer("Player9", 1800.0, "Server1", "1010101010");
        player9.setId(9);

        Gamer player10 = new Gamer("Player10", 1900.0, "Server1", "1010101010");
        player10.setId(10);

        return new Gamer[] { player6, player7, player8, player9, player10 };
    }
    private Gamer[] createinValidTeam1GamersArray() {

        Gamer player1 = new Gamer("Player1", 1500.0, "Server1", "1010101010");
        Gamer player2 = new Gamer("Player2", 1600.0, "Server1", "1010101010");
        Gamer player3 = new Gamer("Player3", 1700.0, "Server1", "1010101010");
        Gamer player4 = new Gamer("Player4", 1800.0, "Server1", "1010101010");
        Gamer player5 = new Gamer("Player5", 1900.0, "Server1", "1010101010");
        return new Gamer[] { player1, player2, player3, player4, player5 };
    }

    private Gamer[] createinValidTeam2GamersArray() {

        Gamer player6 = new Gamer("Player6", 1500.0, "Server1", "1010101010");
        Gamer player7 = new Gamer("Player7", 1600.0, "Server1", "1010101010");
        Gamer player8 = new Gamer("Player8", 1700.0, "Server1", "1010101010");
        Gamer player9 = new Gamer("Player9", 1800.0, "Server1", "1010101010");
        Gamer player10 = new Gamer("Player10", 1900.0, "Server1", "1010101010");
        return new Gamer[] { player6, player7, player8, player9, player10 };
    }
}

