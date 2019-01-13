package genetic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import genetic.models.RocketPosition;
import genetic.service.GeneticAlgorithm;

import java.util.UUID;

@RestController
public class WebController {

    @Autowired
    private GeneticAlgorithm geneticAlgorithm;

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/start")
    public ResponseEntity<RocketPosition> startTest(@RequestParam(value = "genCount", required = false, defaultValue = "20") int genCount,
                                                    @RequestParam(value = "populationCount", required = false, defaultValue = "10") int populationCount,
                                                    @RequestParam(value = "lifespan", required = false, defaultValue = "200") int lifespan,
                                                    @RequestParam(value = "maxForce", required = false, defaultValue = "0.5") float maxForce) {
        String uuid = UUID.randomUUID().toString();
        RocketPosition rocketPosition = geneticAlgorithm.run(uuid, populationCount, genCount, lifespan, maxForce);
        rocketPosition.setUuid(uuid);
        return new ResponseEntity<>(rocketPosition, HttpStatus.ACCEPTED);
    }
}
