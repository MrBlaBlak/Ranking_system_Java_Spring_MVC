package com.mrblablak.rankingSystem.service.stats;

import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.model.Gamer;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamerStatsService {

    private final GamerRepository gamerRepository;
    private final ResourceLoader resourceLoader;

    public void addDataIfEmpty() throws IOException {
        if (gamerRepository.count() == 0) {
            Resource resource = resourceLoader.getResource("classpath:all_data.txt");
            List<Gamer> gamers = Files.lines(resource.getFile().toPath())
                    .map(line -> line.split(" "))
                    .map(values -> new Gamer(values[0],
                            Double.parseDouble(values[1]),
                            values[7],
                            values[6]))
                    .collect(Collectors.toList());
            gamerRepository.saveAll(gamers);
        }
    }

    public Gamer getGamer(int id) {
        return gamerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gamer with id " + id + " not found."));
    }

    public List<Gamer> getAllGamers() {
        return gamerRepository.findAllByOrderByMmrDesc();
    }
}
