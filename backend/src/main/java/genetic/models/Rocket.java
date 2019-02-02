package genetic.models;

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
    private boolean bestFromLastAttempt;
    private List<Obstacle> obstacles;

    public Rocket() {
        this.position = new PVector(400, 700);
        this.velocity = new PVector();
        this.acceleration = new PVector();
        this.target = new PVector(400, 50);
        this.fitness = 0;
        this.crashed = false;
        this.arrived = false;
        this.bestFromLastAttempt = false;
        this.obstacles = new ArrayList<>();
        this.obstacles.add(new Obstacle(300, 350, 100, 200));

    }

    public Rocket(int lifespan, float maxForce) {
        this();
        this.lifespan = lifespan;
        this.dna = new DNA(lifespan, maxForce);
    }

    public Rocket(DNA dna) {
        this();
        this.dna = dna;
        this.lifespan = dna.getGenes().size();
        if(this.lifespan <= 0) {
            this.lifespan = 200;
        }
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
                positionList.addAll(Collections.nCopies(lifeLeft, (new RequiredInfo(this.position.x, this.position.y, this.velocity.heading(), "red"))));
                this.crashed = true;
                break;
            }
            for(Obstacle obstacle : this.obstacles) {
                if(this.hitsObstacle(obstacle)) {
                    int lifeLeft = this.lifespan - positionList.size();
                    positionList.addAll(Collections.nCopies(lifeLeft, (new RequiredInfo(this.position.x, this.position.y, this.velocity.heading(), "red"))));
                    this.crashed = true;
                    break;
                }
            }
            if(this.crashed) {
                break;
            }
            if(PVector.dist(this.position, this.target) <= 10) {
                int lifeLeft = this.lifespan - positionList.size();
                positionList.addAll(Collections.nCopies(lifeLeft, (new RequiredInfo(this.target.x, this.target.y, this.velocity.heading(), "green"))));
                System.err.println("Yay we hit the target! positionList is length: " + positionList.size());
                this.arrived = true;
                break;
            }

            if(this.bestFromLastAttempt) {
                positionList.add(new RequiredInfo(this.position.x, this.position.y, this.velocity.heading(), "gold"));
            } else {
                positionList.add(new RequiredInfo(this.position.x, this.position.y, this.velocity.heading(), "white"));
            }
        }
        this.fitness = calculateFitness();
        return positionList;
    }

    private int calculateFitness() {
        float dist = PVector.dist(this.position, this.target);
        int fitness = 100 - (int)(dist * 100 / 2000);
        if(this.crashed) {
            fitness /= 10;
        }
        if(this.arrived) {
            fitness *= 10;
        }
        return fitness;
    }

    public void mutate() {
        Random rand = new Random();
        boolean shouldMutate = rand.nextBoolean();
        List<PVector> newGenes = new ArrayList<>();
        if(shouldMutate) {
            for(PVector gene : this.getDna().getGenes()) {
                if(rand.nextInt(100) <= 5) {
                    gene = PVector.random2D();
                    System.err.println("Changing Gene");
                }
                newGenes.add(gene);
            }
            this.getDna().setGenes(newGenes);
        }
    }

    private boolean hitsObstacle(Obstacle obstacle) {
        return (this.position.x >= obstacle.getX()) &&
                (this.position.x <= (obstacle.getX() + obstacle.getWidth())) &&
                (this.position.y >= obstacle.getY()) &&
                (this.position.y <= obstacle.getY() + obstacle.getHeight());
    }
}
