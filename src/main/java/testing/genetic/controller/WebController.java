package testing.genetic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import processing.core.PVector;
import testing.genetic.models.Rocket;
import testing.genetic.models.RocketPosition;
import testing.genetic.service.GeneticAlgorithm;

import java.util.List;
import java.util.UUID;

@RestController
public class WebController {

    @Autowired
    GeneticAlgorithm geneticAlgorithm;

    @RequestMapping(value = "/start")
    public ResponseEntity<RocketPosition> startTest() {
        String uuid = UUID.randomUUID().toString();
        RocketPosition rocketPosition = geneticAlgorithm.run(uuid);
        rocketPosition.setUuid(uuid);
        return new ResponseEntity<>(rocketPosition, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/test")
    ResponseEntity<List<PVector>> testMovement() {
        Rocket rocket = new Rocket(5);
        return ResponseEntity.ok(rocket.fly());
    }
}
