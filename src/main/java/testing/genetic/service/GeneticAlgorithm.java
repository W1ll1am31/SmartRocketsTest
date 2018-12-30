package testing.genetic.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testing.genetic.models.Population;
import testing.genetic.models.RocketPosition;
import testing.genetic.repository.RocketDataRepository;

@Service
@Data
public class GeneticAlgorithm {

//    @Autowired
//    RocketDataRepository rocketDataRepository;

    public RocketPosition run(String uuid) {
        Population population = new Population();
        return population.run();
//        rocketDataRepository.saveAndFlush(rocketPosition);
    }
}
