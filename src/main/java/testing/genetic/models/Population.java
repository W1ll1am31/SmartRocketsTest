package testing.genetic.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import testing.genetic.repository.RocketDataRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class Population {
    private List<Rocket> rockets;
    private int populationSize;
    private int generationSize;
    private int lifespan;

    @Autowired
    RocketDataRepository repository;

    public Population() {
        this(20, 50, 100);
    }

    public Population(int populationSize, int generationSize, int lifespan) {
        this.populationSize = populationSize;
        this.generationSize = generationSize;
        this.lifespan = lifespan;
        this.rockets = new ArrayList<>();

        for(int i = 0; i < this.populationSize; i++) {
            this.rockets.add(new Rocket(lifespan));
        }
    }

    public RocketPosition run() {
        RocketPosition allRocketsPositions = new RocketPosition();
        allRocketsPositions.setAllRocketPositions(new ArrayList<>());
        for(int generation = 0; generation < this.generationSize; generation++) {
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
        for(Rocket rocket : this.rockets) {
            matingPool.addAll(Collections.nCopies(rocket.getFitness(), rocket.getDna()));
        }
        Random random = new Random();
        this.rockets.clear();
        for(int dnaIndex = 0; dnaIndex < this.populationSize; dnaIndex++) {
            DNA dna1 = matingPool.get(random.nextInt(matingPool.size()));
            DNA dna2 = matingPool.get(random.nextInt(matingPool.size()));
            DNA newDna = new DNA(dna1, dna2);
            this.rockets.add(new Rocket(newDna));
        }

    }
}