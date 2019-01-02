package testing.genetic.models;

import lombok.Data;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class Rocket {
    private DNA dna;
    private int fitness;
    private PVector position;
    private PVector velocity;
    private PVector acceleration;
    private int lifespan;
    private boolean crashed;
    private PVector target;
    private boolean arrived;

    public Rocket() {
        this.position = new PVector(400, 700);
        this.velocity = new PVector();
        this.acceleration = new PVector();
        this.target = new PVector(400, 50);
        this.fitness = 0;
        this.crashed = false;
        this.arrived = false;
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
            if(this.position.x <= 0 || this.position.x >= 800 || this.position.y <= 0 || this.position.y >= 800) {
                int lifeLeft = this.lifespan - positionList.size();
                positionList.addAll(Collections.nCopies(lifeLeft, (new RequiredInfo(this.position.x, this.position.y, this.velocity.heading()))));
                this.crashed = true;
                break;
            }
            if(PVector.dist(this.position, this.target) <= 10) {
                System.out.println("HIT TARGET!!");
                int lifeLeft = this.lifespan - positionList.size();
                System.err.println(lifeLeft + ", " + positionList.size());
                positionList.addAll(Collections.nCopies(lifeLeft, (new RequiredInfo(this.target.x, this.target.y, this.velocity.heading()))));
                this.arrived = true;
                break;
            }
            positionList.add(new RequiredInfo(this.position.x, this.position.y, this.velocity.heading()));
        }
        this.fitness = calculateFitness();
        return positionList;
    }

    private int calculateFitness() {
        float dist = PVector.dist(this.position, this.target);
        int fitness = 100 - (int)(dist * 100 / 1000);
        if(this.crashed) {
            fitness /= 10;
        }
        if(this.arrived) {
            fitness *= 10;
        }
        System.out.println("For rocket " + this.position + " it got " + dist + " from the target " + this.target + " so fitness = " + fitness);
        return fitness;
    }

    public void mutate() {
        Random rand = new Random();
        boolean shouldMutate = rand.nextBoolean();
        if(shouldMutate) {
            for(PVector gene : this.getDna().getGenes()) {
                if(rand.nextInt(100) <= 5) {
                    gene = PVector.random2D();
                }
            }
        }
    }
}
