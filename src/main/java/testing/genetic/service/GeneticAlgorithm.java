package testing.genetic.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import testing.genetic.models.Population;
import testing.genetic.models.RocketPosition;

@Service
@Data
public class GeneticAlgorithm {

//    @Autowired
//    RocketDataRepository rocketDataRepository;

    public RocketPosition run(String uuid) {
        Population population = new Population(5, 10, 200);
        return population.run();
//        rocketDataRepository.saveAndFlush(rocketPosition);
    }
}
