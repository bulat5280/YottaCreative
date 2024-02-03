package org.apache.commons.math3.fitting;

import org.apache.commons.math3.analysis.function.HarmonicOscillator;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
import org.apache.commons.math3.util.FastMath;

public class HarmonicFitter extends CurveFitter<HarmonicOscillator.Parametric> {
   public HarmonicFitter(MultivariateVectorOptimizer optimizer) {
      super(optimizer);
   }

   public double[] fit(double[] initialGuess) {
      return this.fit(new HarmonicOscillator.Parametric(), initialGuess);
   }

   public double[] fit() {
      return this.fit((new HarmonicFitter.ParameterGuesser(this.getObservations())).guess());
   }

   public static class ParameterGuesser {
      private final double a;
      private final double omega;
      private final double phi;

      public ParameterGuesser(WeightedObservedPoint[] observations) {
         if (observations.length < 4) {
            throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, observations.length, 4, true);
         } else {
            WeightedObservedPoint[] sorted = this.sortObservations(observations);
            double[] aOmega = this.guessAOmega(sorted);
            this.a = aOmega[0];
            this.omega = aOmega[1];
            this.phi = this.guessPhi(sorted);
         }
      }

      public double[] guess() {
         return new double[]{this.a, this.omega, this.phi};
      }

      private WeightedObservedPoint[] sortObservations(WeightedObservedPoint[] unsorted) {
         WeightedObservedPoint[] observations = (WeightedObservedPoint[])unsorted.clone();
         WeightedObservedPoint curr = observations[0];

         for(int j = 1; j < observations.length; ++j) {
            WeightedObservedPoint prec = curr;
            curr = observations[j];
            if (curr.getX() < prec.getX()) {
               int i = j - 1;
               WeightedObservedPoint mI = observations[i];

               while(i >= 0 && curr.getX() < mI.getX()) {
                  observations[i + 1] = mI;
                  if (i-- != 0) {
                     mI = observations[i];
                  }
               }

               observations[i + 1] = curr;
               curr = observations[j];
            }
         }

         return observations;
      }

      private double[] guessAOmega(WeightedObservedPoint[] observations) {
         double[] aOmega = new double[2];
         double sx2 = 0.0D;
         double sy2 = 0.0D;
         double sxy = 0.0D;
         double sxz = 0.0D;
         double syz = 0.0D;
         double currentX = observations[0].getX();
         double currentY = observations[0].getY();
         double f2Integral = 0.0D;
         double fPrime2Integral = 0.0D;
         double startX = currentX;

         double xRange;
         double yMin;
         double yMax;
         for(int i = 1; i < observations.length; ++i) {
            double previousX = currentX;
            double previousY = currentY;
            currentX = observations[i].getX();
            currentY = observations[i].getY();
            double dx = currentX - previousX;
            xRange = currentY - previousY;
            yMin = dx * (previousY * previousY + previousY * currentY + currentY * currentY) / 3.0D;
            yMax = xRange * xRange / dx;
            double x = currentX - startX;
            f2Integral += yMin;
            fPrime2Integral += yMax;
            sx2 += x * x;
            sy2 += f2Integral * f2Integral;
            sxy += x * f2Integral;
            sxz += x * fPrime2Integral;
            syz += f2Integral * fPrime2Integral;
         }

         double c1 = sy2 * sxz - sxy * syz;
         double c2 = sxy * sxz - sx2 * syz;
         double c3 = sx2 * sy2 - sxy * sxy;
         if (!(c1 / c2 < 0.0D) && !(c2 / c3 < 0.0D)) {
            if (c2 == 0.0D) {
               throw new MathIllegalStateException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
            }

            aOmega[0] = FastMath.sqrt(c1 / c2);
            aOmega[1] = FastMath.sqrt(c2 / c3);
         } else {
            int last = observations.length - 1;
            xRange = observations[last].getX() - observations[0].getX();
            if (xRange == 0.0D) {
               throw new ZeroException();
            }

            aOmega[1] = 6.283185307179586D / xRange;
            yMin = Double.POSITIVE_INFINITY;
            yMax = Double.NEGATIVE_INFINITY;

            for(int i = 1; i < observations.length; ++i) {
               double y = observations[i].getY();
               if (y < yMin) {
                  yMin = y;
               }

               if (y > yMax) {
                  yMax = y;
               }
            }

            aOmega[0] = 0.5D * (yMax - yMin);
         }

         return aOmega;
      }

      private double guessPhi(WeightedObservedPoint[] observations) {
         double fcMean = 0.0D;
         double fsMean = 0.0D;
         double currentX = observations[0].getX();
         double currentY = observations[0].getY();

         for(int i = 1; i < observations.length; ++i) {
            double previousX = currentX;
            double previousY = currentY;
            currentX = observations[i].getX();
            currentY = observations[i].getY();
            double currentYPrime = (currentY - previousY) / (currentX - previousX);
            double omegaX = this.omega * currentX;
            double cosine = FastMath.cos(omegaX);
            double sine = FastMath.sin(omegaX);
            fcMean += this.omega * currentY * cosine - currentYPrime * sine;
            fsMean += this.omega * currentY * sine + currentYPrime * cosine;
         }

         return FastMath.atan2(-fsMean, fcMean);
      }
   }
}
