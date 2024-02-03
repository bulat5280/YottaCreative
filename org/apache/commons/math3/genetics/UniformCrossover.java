package org.apache.commons.math3.genetics;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class UniformCrossover<T> implements CrossoverPolicy {
   private final double ratio;

   public UniformCrossover(double ratio) throws OutOfRangeException {
      if (!(ratio < 0.0D) && !(ratio > 1.0D)) {
         this.ratio = ratio;
      } else {
         throw new OutOfRangeException(LocalizedFormats.CROSSOVER_RATE, ratio, 0.0D, 1.0D);
      }
   }

   public double getRatio() {
      return this.ratio;
   }

   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
      if (first instanceof AbstractListChromosome && second instanceof AbstractListChromosome) {
         return this.mate((AbstractListChromosome)first, (AbstractListChromosome)second);
      } else {
         throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
      }
   }

   private ChromosomePair mate(AbstractListChromosome<T> first, AbstractListChromosome<T> second) throws DimensionMismatchException {
      int length = first.getLength();
      if (length != second.getLength()) {
         throw new DimensionMismatchException(second.getLength(), length);
      } else {
         List<T> parent1Rep = first.getRepresentation();
         List<T> parent2Rep = second.getRepresentation();
         List<T> child1Rep = new ArrayList(first.getLength());
         List<T> child2Rep = new ArrayList(second.getLength());
         RandomGenerator random = GeneticAlgorithm.getRandomGenerator();

         for(int index = 0; index < length; ++index) {
            if (random.nextDouble() < this.ratio) {
               child1Rep.add(parent2Rep.get(index));
               child2Rep.add(parent1Rep.get(index));
            } else {
               child1Rep.add(parent1Rep.get(index));
               child2Rep.add(parent2Rep.get(index));
            }
         }

         return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
      }
   }
}
