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
import org.slf4j.Logger;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class DataHandlerImplTest {

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

    @Mock
    private Logger logger;

    @InjectMocks
    private DataHandlerImpl dataHandler;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testValidDataHandling() {
        // Prepare valid input
        GamersMatchStatsDTO gamersMatchStatsDTO = createValidGamersMatchStatsDTO();
        Gamer[] team1gamers = createValidTeam1GamersArray();
        Gamer[] team2gamers = createValidTeam2GamersArray();

        // Mock GamerRepository behavior
        for (int i = 0; i < team1gamers.length; i++) {
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
        verify(teamRepository, times(2)).save(any()); // team1 and team2
        verify(gamerRepository, times(2 * team1gamers.length)).save(any()); // team1gamers and team2gamers
        verify(matchGamerRepository, times(10)).save(any()); // 10 matchGamers
        verify(killsAndCapsRepository, times(10)).save(any()); // 10 killsAndCaps
    }
    @Test
    public void testExceptionHandling() {

        GamersMatchStatsDTO gamersMatchStatsDTO = createValidGamersMatchStatsDTO();
        Gamer[] team1gamers = createValidTeam1GamersArray();
        Gamer[] team2gamers = createValidTeam2GamersArray();

        for (int i = 0; i < team1gamers.length; i++) {
            when(gamerRepository.findById(gamersMatchStatsDTO.getTeam1gamersId()[i]))
                    .thenReturn(Optional.of(team1gamers[i]));
            when(gamerRepository.findById(gamersMatchStatsDTO.getTeam2gamersId()[i]))
                    .thenReturn(Optional.of(team2gamers[i]));
        }
        when(mmrCalculator.calculateMmr(anyInt(), any(), any(), any(), any(), anyBoolean()))
                .thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(matchRepository).save(any());

        // Execute and expect an exception to be thrown
        assertThrows(Exception.class, () -> dataHandler.setData(gamersMatchStatsDTO, team1gamers, team2gamers));

        // Verify that logger.error() was called with the exception

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
        // Implement method to create a valid Gamer[] for team1
        Gamer player1 = new Gamer("Player1", 1500.0, "Server1", "WWLLWWLLWW");
        player1.setId(1);

        Gamer player2 = new Gamer("Player2", 1600.0, "Server1", "LWLWLWLWLW");
        player2.setId(2);

        Gamer player3 = new Gamer("Player3", 1700.0, "Server1", "WLWLWLWLWL");
        player3.setId(3);

        Gamer player4 = new Gamer("Player4", 1800.0, "Server1", "WWWWLLLLWW");
        player4.setId(4);

        Gamer player5 = new Gamer("Player5", 1900.0, "Server1", "LLLWWWWLLL");
        player5.setId(5);

        return new Gamer[] { player1, player2, player3, player4, player5 };
    }

    private Gamer[] createValidTeam2GamersArray() {
        // Implement method to create a valid Gamer[] for team2
        Gamer player6 = new Gamer("Player6", 1500.0, "Server1", "LLWWLLWWLL");
        player6.setId(6);

        Gamer player7 = new Gamer("Player7", 1600.0, "Server1", "WLWLWLWLWL");
        player7.setId(7);

        Gamer player8 = new Gamer("Player8", 1700.0, "Server1", "LWLWLWLWLW");
        player8.setId(8);

        Gamer player9 = new Gamer("Player9", 1800.0, "Server1", "LLLLWWWWLL");
        player9.setId(9);

        Gamer player10 = new Gamer("Player10", 1900.0, "Server1", "WWWWLLLLWW");
        player10.setId(10);

        return new Gamer[] { player6, player7, player8, player9, player10 };
    }
}

