package org.apache.commons.math3.distribution;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.Pair;

public class MixtureMultivariateRealDistribution<T extends MultivariateRealDistribution> extends AbstractMultivariateRealDistribution {
   private final double[] weight;
   private final List<T> distribution;

   public MixtureMultivariateRealDistribution(List<Pair<Double, T>> components) {
      this(new Well19937c(), components);
   }

   public MixtureMultivariateRealDistribution(RandomGenerator rng, List<Pair<Double, T>> components) {
      super(rng, ((MultivariateRealDistribution)((Pair)components.get(0)).getSecond()).getDimension());
      int numComp = components.size();
      int dim = this.getDimension();
      double weightSum = 0.0D;

      int i;
      Pair comp;
      for(i = 0; i < numComp; ++i) {
         comp = (Pair)components.get(i);
         if (((MultivariateRealDistribution)comp.getSecond()).getDimension() != dim) {
            throw new DimensionMismatchException(((MultivariateRealDistribution)comp.getSecond()).getDimension(), dim);
         }

         if ((Double)comp.getFirst() < 0.0D) {
            throw new NotPositiveException((Number)comp.getFirst());
         }

         weightSum += (Double)comp.getFirst();
      }

      if (Double.isInfinite(weightSum)) {
         throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
      } else {
         this.distribution = new ArrayList();
         this.weight = new double[numComp];

         for(i = 0; i < numComp; ++i) {
            comp = (Pair)components.get(i);
            this.weight[i] = (Double)comp.getFirst() / weightSum;
            this.distribution.add(comp.getSecond());
         }

      }
   }

   public double density(double[] values) {
      double p = 0.0D;

      for(int i = 0; i < this.weight.length; ++i) {
         p += this.weight[i] * ((MultivariateRealDistribution)this.distribution.get(i)).density(values);
      }

      return p;
   }

   public double[] sample() {
      double[] vals = null;
      double randomValue = this.random.nextDouble();
      double sum = 0.0D;

      for(int i = 0; i < this.weight.length; ++i) {
         sum += this.weight[i];
         if (randomValue <= sum) {
            vals = ((MultivariateRealDistribution)this.distribution.get(i)).sample();
            break;
         }
      }

      if (vals == null) {
         vals = ((MultivariateRealDistribution)this.distribution.get(this.weight.length - 1)).sample();
      }

      return vals;
   }

   public void reseedRandomGenerator(long seed) {
      super.reseedRandomGenerator(seed);

      for(int i = 0; i < this.distribution.size(); ++i) {
         ((MultivariateRealDistribution)this.distribution.get(i)).reseedRandomGenerator((long)(i + 1) + seed);
      }

   }

   public List<Pair<Double, T>> getComponents() {
      List<Pair<Double, T>> list = new ArrayList();

      for(int i = 0; i < this.weight.length; ++i) {
         list.add(new Pair(this.weight[i], this.distribution.get(i)));
      }

      return list;
   }
}
