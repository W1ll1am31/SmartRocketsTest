package testing.genetic.models;

import lombok.Data;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

@Data
public class Rocket {
    private DNA dna;
    private int fitness;
    private PVector position;
    private PVector velocity;
    private PVector acceleration;
    private int lifespan;

    public Rocket() {
        this.position = new PVector(400, 400);
        this.velocity = new PVector();
        this.acceleration = new PVector();
        this.fitness = 0;
    }

    public Rocket(int lifespan) {
        this();
        this.lifespan = lifespan;
        this.dna = new DNA(lifespan);
    }

    public Rocket(DNA dna) {
        this();
        this.lifespan = dna.getGenes().size();
        this.dna = dna;
    }

    private void applyForce(PVector force) {
        this.acceleration.add(force);
    }

    private void update() {
        this.velocity.add(this.acceleration);
        this.position.add(this.velocity);
        this.acceleration.mult(0);
    }

    public List<RequiredInfo> fly() {
        List<RequiredInfo> positionList = new ArrayList<>();
        for(PVector gene : this.dna.getGenes()) {
            this.applyForce(gene);
            this.update();
            positionList.add(new RequiredInfo(this.position.x, this.position.y, this.velocity.heading()));
        }
        this.fitness = calculateFitness();
        return positionList;
    }

    private int calculateFitness() {
        return 1;
    }

}
