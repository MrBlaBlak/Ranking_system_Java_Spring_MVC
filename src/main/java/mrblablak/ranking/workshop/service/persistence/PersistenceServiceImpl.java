package mrblablak.ranking.workshop.service.persistence;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final GamerRepository gamerRepository;
    private final MatchGamerRepository matchGamerRepository;
    private final KillsAndCapsRepository killsAndCapsRepository;

    @Transactional
    public void saveData(
            MatchGamer[] matchGamers,
            KillsAndCaps[] killsAndCaps,
            Match match,
            Team team1,
            Team team2,
            Gamer[] team1gamers,
            Gamer[] team2gamers
    ) {
        matchRepository.save(match);
        teamRepository.save(team1);
        teamRepository.save(team2);
        gamerRepository.saveAll(Stream.concat(Arrays.stream(team1gamers), Arrays.stream(team2gamers))
                .collect(Collectors.toList()));
        matchGamerRepository.saveAll(Arrays.asList(matchGamers));
        killsAndCapsRepository.saveAll(Arrays.asList(killsAndCaps));
    }
}
