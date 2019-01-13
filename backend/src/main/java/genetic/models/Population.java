package genetic.models;

import genetic.repository.RocketDataRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class Population {
    private List<Rocket> rockets;
    private int populationSize;
    private int numberOfGenerations;
    private int lifespan;

    @Autowired
    RocketDataRepository repository;

    public Population(int populationSize, int numberOfGenerations, int lifespan, float maxForce) {
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.lifespan = lifespan;
        this.rockets = new ArrayList<>();

        for(int i = 0; i < this.populationSize; i++) {
            this.rockets.add(new Rocket(lifespan, maxForce));
        }
    }

    public RocketPosition run() {
        RocketPosition allRocketsPositions = new RocketPosition();
        allRocketsPositions.setAllRocketPositions(new ArrayList<>());
        for(int generation = 0; generation < this.numberOfGenerations; generation++) {
            List<List<RequiredInfo>> thisGenerationsPositions = new ArrayList<>();
            for(Rocket rocket : this.rockets) {
                thisGenerationsPositions.add(rocket.fly());
            }
            this.evolve();
            allRocketsPositions.getAllRocketPositions().add(thisGenerationsPositions);
        }
        return allRocketsPositions;
    }

    private void evolve() {
        List<DNA> matingPool = new ArrayList<>();
        Rocket bestRocket = new Rocket();
        bestRocket.setBestFromLastAttempt(true);
        for(Rocket rocket : this.rockets) {
            if(rocket.getFitness() > bestRocket.getFitness()) {
                bestRocket.setDna(rocket.getDna());
                bestRocket.setFitness(rocket.getFitness());
                bestRocket.setLifespan(this.lifespan);
            }
            matingPool.addAll(Collections.nCopies(rocket.getFitness(), rocket.getDna()));
        }
        Random random = new Random();
        this.rockets.clear();
        for(int dnaIndex = 0; dnaIndex < this.populationSize-1; dnaIndex++) {
            DNA dna1 = matingPool.get(random.nextInt(matingPool.size()));
            DNA dna2 = matingPool.get(random.nextInt(matingPool.size()));
            DNA newDna = new DNA(dna1, dna2);
            this.rockets.add(new Rocket(newDna));
        }

        for(Rocket rocket : this.rockets) {
            rocket.mutate();
        }
        this.rockets.add(bestRocket);
    }
}