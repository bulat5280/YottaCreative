package org.apache.commons.math3.genetics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public class CycleCrossover<T> implements CrossoverPolicy {
   private final boolean randomStart;

   public CycleCrossover() {
      this(false);
   }

   public CycleCrossover(boolean randomStart) {
      this.randomStart = randomStart;
   }

   public boolean isRandomStart() {
      return this.randomStart;
   }

   public ChromosomePair crossover(Chromosome first, Chromosome second) throws DimensionMismatchException, MathIllegalArgumentException {
      if (first instanceof AbstractListChromosome && second instanceof AbstractListChromosome) {
         return this.mate((AbstractListChromosome)first, (AbstractListChromosome)second);
      } else {
         throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, new Object[0]);
      }
   }

   protected ChromosomePair mate(AbstractListChromosome<T> first, AbstractListChromosome<T> second) throws DimensionMismatchException {
      int length = first.getLength();
      if (length != second.getLength()) {
         throw new DimensionMismatchException(second.getLength(), length);
      } else {
         List<T> parent1Rep = first.getRepresentation();
         List<T> parent2Rep = second.getRepresentation();
         List<T> child1Rep = new ArrayList(second.getRepresentation());
         List<T> child2Rep = new ArrayList(first.getRepresentation());
         Set<Integer> visitedIndices = new HashSet(length);
         List<Integer> indices = new ArrayList(length);
         int idx = this.randomStart ? GeneticAlgorithm.getRandomGenerator().nextInt(length) : 0;
         int var11 = 1;

         while(visitedIndices.size() < length) {
            indices.add(idx);
            T item = parent2Rep.get(idx);

            for(idx = parent1Rep.indexOf(item); idx != (Integer)indices.get(0); idx = parent1Rep.indexOf(item)) {
               indices.add(idx);
               item = parent2Rep.get(idx);
            }

            if (var11++ % 2 != 0) {
               Iterator i$ = indices.iterator();

               while(i$.hasNext()) {
                  int i = (Integer)i$.next();
                  T tmp = child1Rep.get(i);
                  child1Rep.set(i, child2Rep.get(i));
                  child2Rep.set(i, tmp);
               }
            }

            visitedIndices.addAll(indices);
            idx = ((Integer)indices.get(0) + 1) % length;

            while(visitedIndices.contains(idx) && visitedIndices.size() < length) {
               ++idx;
               if (idx >= length) {
                  idx = 0;
               }
            }

            indices.clear();
         }

         return new ChromosomePair(first.newFixedLengthChromosome(child1Rep), second.newFixedLengthChromosome(child2Rep));
      }
   }
}
