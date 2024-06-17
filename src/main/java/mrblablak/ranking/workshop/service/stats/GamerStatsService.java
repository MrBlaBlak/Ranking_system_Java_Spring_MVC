package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.repository.GamerRepository;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GamerStatsService {

    private final GamerRepository gamerRepository;

    public void addDataIfEmpty() throws IOException {
        long gamerCount = gamerRepository.count();
        if (gamerCount == 0) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:all_data.txt");

            try (InputStream inputStream = resource.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] actualValue = line.split(" ");
                    gamerRepository.save(new Gamer(actualValue[0], Double.parseDouble(actualValue[1]), actualValue[7], actualValue[6]));
                }
            }
        }
    }

    public Gamer getGamer(int id) {
        return gamerRepository.findById(id).get();
    }

    public List<Gamer> getAllGamers() {
        return gamerRepository.findAllByOrderByMmrDesc();
    }
}