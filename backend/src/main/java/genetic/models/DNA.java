package genetic.models;

import lombok.Data;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class DNA {
    private List<PVector> genes;
    private int maxForce;

    public DNA(int lifespan, float maxForce) {
        genes = new ArrayList<>();
        for(int i = 0; i < lifespan; i++) {
            this.genes.add(PVector.random2D().setMag(maxForce));
        }
    }

    public DNA(DNA dna1, DNA dna2) {
        this.genes = mergeDNA(dna1, dna2);
    }

    private List<PVector> mergeDNA(DNA dna1, DNA dna2) {
        int lifespan = dna1.getGenes().size();
        List<PVector> newGenes = new ArrayList<>(dna1.getGenes().subList(0, lifespan / 2));
        newGenes.addAll(dna2.getGenes().subList((lifespan/2), lifespan));

        Random rand = new Random();
        int midPoint = rand.nextInt(dna1.getGenes().size());
        List<PVector> newGenes2 = new ArrayList<>(dna1.getGenes().subList(0, midPoint));
        newGenes2.addAll(dna2.getGenes().subList(midPoint, dna2.getGenes().size()));
        return newGenes2;
    }
}
