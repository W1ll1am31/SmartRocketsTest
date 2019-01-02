package testing.genetic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testing.genetic.models.RequiredInfo;
import testing.genetic.models.Rocket;
import testing.genetic.models.RocketPosition;
import testing.genetic.service.GeneticAlgorithm;

import java.util.List;
import java.util.UUID;

@RestController
public class WebController {

    @Autowired
    private GeneticAlgorithm geneticAlgorithm;

    @RequestMapping(value = "/start")
    public ResponseEntity<RocketPosition> startTest(@RequestParam(value = "genCount", required = false, defaultValue = "20") int genCount,
                                                    @RequestParam(value = "populationCount", required = false, defaultValue = "10") int populationCount,
                                                    @RequestParam(value = "lifespan", required = false, defaultValue = "200") int lifespan) {
        String uuid = UUID.randomUUID().toString();
        RocketPosition rocketPosition = geneticAlgorithm.run(uuid, populationCount, genCount, lifespan);
        rocketPosition.setUuid(uuid);
        return new ResponseEntity<>(rocketPosition, HttpStatus.ACCEPTED);
    }
}
