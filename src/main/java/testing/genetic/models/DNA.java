package testing.genetic.models;

import lombok.Data;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

@Data
public class DNA {
    private List<PVector> genes;

    public DNA(int lifespan) {
        genes = new ArrayList<>();
        for(int i = 0; i < lifespan; i++) {
            this.genes.add(PVector.random2D());
        }
    }

    public DNA(DNA dna1, DNA dna2) {
        this.genes = mergeDNA(dna1, dna2);
    }

    private List<PVector> mergeDNA(DNA dna1, DNA dna2) {
        int lifespan = dna1.getGenes().size();
        List<PVector> newGenes = new ArrayList<>(dna1.getGenes().subList(0, lifespan / 2));
        newGenes.addAll(dna2.getGenes().subList((lifespan/2), lifespan));
        return newGenes;
    }
}
