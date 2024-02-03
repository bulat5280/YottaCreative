package org.apache.commons.math3.genetics;

import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.FastMath;

public class ElitisticListPopulation extends ListPopulation {
   private double elitismRate = 0.9D;

   public ElitisticListPopulation(List<Chromosome> chromosomes, int populationLimit, double elitismRate) throws NullArgumentException, NotPositiveException, NumberIsTooLargeException, OutOfRangeException {
      super(chromosomes, populationLimit);
      this.setElitismRate(elitismRate);
   }

   public ElitisticListPopulation(int populationLimit, double elitismRate) throws NotPositiveException, OutOfRangeException {
      super(populationLimit);
      this.setElitismRate(elitismRate);
   }

   public Population nextGeneration() {
      ElitisticListPopulation nextGeneration = new ElitisticListPopulation(this.getPopulationLimit(), this.getElitismRate());
      List<Chromosome> oldChromosomes = this.getChromosomeList();
      Collections.sort(oldChromosomes);
      int boundIndex = (int)FastMath.ceil((1.0D - this.getElitismRate()) * (double)oldChromosomes.size());

      for(int i = boundIndex; i < oldChromosomes.size(); ++i) {
         nextGeneration.addChromosome((Chromosome)oldChromosomes.get(i));
      }

      return nextGeneration;
   }

   public void setElitismRate(double elitismRate) throws OutOfRangeException {
      if (!(elitismRate < 0.0D) && !(elitismRate > 1.0D)) {
         this.elitismRate = elitismRate;
      } else {
         throw new OutOfRangeException(LocalizedFormats.ELITISM_RATE, elitismRate, 0, 1);
      }
   }

   public double getElitismRate() {
      return this.elitismRate;
   }
}
