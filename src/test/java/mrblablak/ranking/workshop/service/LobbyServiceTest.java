package mrblablak.ranking.workshop.service;

import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LobbyServiceTest {
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

    @InjectMocks
    private LobbyService lobbyService;

    @BeforeEach
    void setUp() {

    }
    @Test
    void getAllGamers() {
        List<Gamer> expectedGamers = Arrays.asList(new Gamer(), new Gamer());
        when(gamerRepository.findAll()).thenReturn(expectedGamers);
        List<Gamer> actualGamers = lobbyService.getAllGamers();
        assertEquals(expectedGamers, actualGamers);
        verify(gamerRepository, times(1)).findAll();

    }

    @Test
    void setHandicap() {
    }

    @Test
    void checkPerfectBalance() {

    }
    @Test
    void checkTeam() {

    }
    @Test
    void calculateMmr() {
    }
    @Test
    void setMatch() {
    }

    @Test
    void setTeams() {
    }

    @Test
    void updateGamers() {
    }

    @Test
    void setMatchGamers() {
    }

    @Test
    void setKillsAndCaps() {
    }

    @Test
    void saveData() {
    }


}