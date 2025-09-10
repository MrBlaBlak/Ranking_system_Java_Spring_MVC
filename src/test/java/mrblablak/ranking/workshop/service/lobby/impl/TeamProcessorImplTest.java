package mrblablak.ranking.workshop.service.lobby.impl;

import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.repository.GamerRepository;
import mrblablak.ranking.workshop.service.lobby.Matchmaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TeamProcessorImplTest {

    @Mock
    private GamerRepository gamerRepository;

    @Mock
    private Matchmaker matchmaker;

    @InjectMocks
    private TeamProcessorImpl teamProcessor;

    private GamersDTO gamersDTO;
    private static final int TEAM_SIZE = 5;

    @BeforeEach
    void setUp() {
        gamersDTO = new GamersDTO(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},"EU", false);
        // Mock GamerRepository behavior
        when(gamerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Gamer("Player", 1000.0, "EU", "1010101010")));
    }

    @Test
    void testProcessTeams_teamsAlreadyReady() {
        // Set up the DTO for teams already ready
        gamersDTO.setTeamsReady(true);

        // Execute the method under test
        boolean result = teamProcessor.processTeams(gamersDTO);

        // Verify that teams are prepared correctly (should not call matchmaker)
        assertTrue(result);

        // Verify that team1 and team2 are populated correctly
        Gamer[] team1 = teamProcessor.getTeam1();
        Gamer[] team2 = teamProcessor.getTeam2();

        assertEquals(TEAM_SIZE, team1.length);
        assertEquals(TEAM_SIZE, team2.length);

        for (int i = 0; i < TEAM_SIZE; i++) {
            assertEquals("Player", team1[i].getName());
            assertEquals("Player", team2[i].getName());
            assertEquals(1000.0, team1[i].getMmr());
            assertEquals(1000.0, team2[i].getMmr());
        }

        // Verify that matchmaker.calculateAndAssignTeams() was not called
        verify(matchmaker, times(0)).calculateAndAssignTeams(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void testProcessTeams_teamsNotReady() {
        doAnswer(invocation -> {
//            Gamer[] gamers = invocation.getArgument(0);
            Gamer[] team1gamers = invocation.getArgument(1);
            Gamer[] team2gamers = invocation.getArgument(2);

            for (int i = 0; i < TEAM_SIZE; i++) {
                team1gamers[i] = new Gamer("Player", 1000.0, "EU", "1010101010");
                team2gamers[i] = new Gamer("Player", 1000.0, "EU", "1010101010");
            }
            return null;
        }).when(matchmaker).calculateAndAssignTeams(any(), any(), any());
        boolean result = teamProcessor.processTeams(gamersDTO);

        // Verify that teams are prepared correctly using matchmaker
        assertTrue(result);

        // Verify that team1 and team2 are populated correctly
        Gamer[] team1 = teamProcessor.getTeam1();
        Gamer[] team2 = teamProcessor.getTeam2();

        assertEquals(TEAM_SIZE, team1.length);
        assertEquals(TEAM_SIZE, team2.length);
        for (int i = 0; i < TEAM_SIZE; i++) {
            assertEquals("Player", team1[i].getName());
            assertEquals("Player", team2[i].getName());
            assertEquals(1000.0, team1[i].getMmr());
            assertEquals(1000.0, team2[i].getMmr());
        }

        // Verify that matchmaker.calculateAndAssignTeams() was called once
        verify(matchmaker, times(1)).calculateAndAssignTeams(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

}