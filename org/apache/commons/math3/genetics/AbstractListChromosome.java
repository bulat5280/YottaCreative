package org.apache.commons.math3.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractListChromosome<T> extends Chromosome {
   private final List<T> representation;

   public AbstractListChromosome(List<T> representation) throws InvalidRepresentationException {
      this.checkValidity(representation);
      this.representation = Collections.unmodifiableList(new ArrayList(representation));
   }

   public AbstractListChromosome(T[] representation) throws InvalidRepresentationException {
      this(Arrays.asList(representation));
   }

   protected abstract void checkValidity(List<T> var1) throws InvalidRepresentationException;

   protected List<T> getRepresentation() {
      return this.representation;
   }

   public int getLength() {
      return this.getRepresentation().size();
   }

   public abstract AbstractListChromosome<T> newFixedLengthChromosome(List<T> var1);

   public String toString() {
      return String.format("(f=%s %s)", this.getFitness(), this.getRepresentation());
   }
}
