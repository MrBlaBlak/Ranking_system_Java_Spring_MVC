package mrblablak.ranking.workshop.service;

import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import mrblablak.ranking.workshop.service.lobby.impl.LobbyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LobbyServiceTest {
    @Mock
    private GamerRepository gamerRepository;

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
}