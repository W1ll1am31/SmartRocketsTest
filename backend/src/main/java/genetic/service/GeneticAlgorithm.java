package genetic.service;

import genetic.models.Population;
import lombok.Data;
import org.springframework.stereotype.Service;
import genetic.models.RocketPosition;

@Service
@Data
public class GeneticAlgorithm {

//    @Autowired
//    RocketDataRepository rocketDataRepository;

    public RocketPosition run(String uuid, int populationCount, int genCount, int lifespan, float maxForce) {
        Population population = new Population(populationCount, genCount, lifespan, maxForce);
        return population.run();
//        rocketDataRepository.saveAndFlush(rocketPosition);
    }
}
