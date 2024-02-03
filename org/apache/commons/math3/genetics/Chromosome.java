package org.apache.commons.math3.genetics;

import java.util.Iterator;

public abstract class Chromosome implements Comparable<Chromosome>, Fitness {
   private static final double NO_FITNESS = Double.NEGATIVE_INFINITY;
   private double fitness = Double.NEGATIVE_INFINITY;

   public double getFitness() {
      if (this.fitness == Double.NEGATIVE_INFINITY) {
         this.fitness = this.fitness();
      }

      return this.fitness;
   }

   public int compareTo(Chromosome another) {
      return Double.valueOf(this.getFitness()).compareTo(another.getFitness());
   }

   protected boolean isSame(Chromosome another) {
      return false;
   }

   protected Chromosome findSameChromosome(Population population) {
      Iterator i$ = population.iterator();

      Chromosome anotherChr;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         anotherChr = (Chromosome)i$.next();
      } while(!this.isSame(anotherChr));

      return anotherChr;
   }

   public void searchForFitnessUpdate(Population population) {
      Chromosome sameChromosome = this.findSameChromosome(population);
      if (sameChromosome != null) {
         this.fitness = sameChromosome.getFitness();
      }

   }
}
