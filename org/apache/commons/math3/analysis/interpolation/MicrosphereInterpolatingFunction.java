package org.apache.commons.math3.analysis.interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.random.UnitSphereRandomVectorGenerator;
import org.apache.commons.math3.util.FastMath;

public class MicrosphereInterpolatingFunction implements MultivariateFunction {
   private final int dimension;
   private final List<MicrosphereInterpolatingFunction.MicrosphereSurfaceElement> microsphere;
   private final double brightnessExponent;
   private final Map<RealVector, Double> samples;

   public MicrosphereInterpolatingFunction(double[][] xval, double[] yval, int brightnessExponent, int microsphereElements, UnitSphereRandomVectorGenerator rand) throws DimensionMismatchException, NoDataException, NullArgumentException {
      if (xval != null && yval != null) {
         if (xval.length == 0) {
            throw new NoDataException();
         } else if (xval.length != yval.length) {
            throw new DimensionMismatchException(xval.length, yval.length);
         } else if (xval[0] == null) {
            throw new NullArgumentException();
         } else {
            this.dimension = xval[0].length;
            this.brightnessExponent = (double)brightnessExponent;
            this.samples = new HashMap(yval.length);

            int i;
            for(i = 0; i < xval.length; ++i) {
               double[] xvalI = xval[i];
               if (xvalI == null) {
                  throw new NullArgumentException();
               }

               if (xvalI.length != this.dimension) {
                  throw new DimensionMismatchException(xvalI.length, this.dimension);
               }

               this.samples.put(new ArrayRealVector(xvalI), yval[i]);
            }

            this.microsphere = new ArrayList(microsphereElements);

            for(i = 0; i < microsphereElements; ++i) {
               this.microsphere.add(new MicrosphereInterpolatingFunction.MicrosphereSurfaceElement(rand.nextVector()));
            }

         }
      } else {
         throw new NullArgumentException();
      }
   }

   public double value(double[] point) throws DimensionMismatchException {
      RealVector p = new ArrayRealVector(point);
      Iterator i$ = this.microsphere.iterator();

      while(i$.hasNext()) {
         MicrosphereInterpolatingFunction.MicrosphereSurfaceElement md = (MicrosphereInterpolatingFunction.MicrosphereSurfaceElement)i$.next();
         md.reset();
      }

      i$ = this.samples.entrySet().iterator();

      while(i$.hasNext()) {
         Entry<RealVector, Double> sd = (Entry)i$.next();
         RealVector diff = ((RealVector)sd.getKey()).subtract(p);
         double diffNorm = diff.getNorm();
         if (FastMath.abs(diffNorm) < FastMath.ulp(1.0D)) {
            return (Double)sd.getValue();
         }

         Iterator i$ = this.microsphere.iterator();

         while(i$.hasNext()) {
            MicrosphereInterpolatingFunction.MicrosphereSurfaceElement md = (MicrosphereInterpolatingFunction.MicrosphereSurfaceElement)i$.next();
            double w = FastMath.pow(diffNorm, -this.brightnessExponent);
            md.store(this.cosAngle(diff, md.normal()) * w, sd);
         }
      }

      double value = 0.0D;
      double totalWeight = 0.0D;
      Iterator i$ = this.microsphere.iterator();

      while(i$.hasNext()) {
         MicrosphereInterpolatingFunction.MicrosphereSurfaceElement md = (MicrosphereInterpolatingFunction.MicrosphereSurfaceElement)i$.next();
         double iV = md.illumination();
         Entry<RealVector, Double> sd = md.sample();
         if (sd != null) {
            value += iV * (Double)sd.getValue();
            totalWeight += iV;
         }
      }

      return value / totalWeight;
   }

   private double cosAngle(RealVector v, RealVector w) {
      return v.dotProduct(w) / (v.getNorm() * w.getNorm());
   }

   private static class MicrosphereSurfaceElement {
      private final RealVector normal;
      private double brightestIllumination;
      private Entry<RealVector, Double> brightestSample;

      MicrosphereSurfaceElement(double[] n) {
         this.normal = new ArrayRealVector(n);
      }

      RealVector normal() {
         return this.normal;
      }

      void reset() {
         this.brightestIllumination = 0.0D;
         this.brightestSample = null;
      }

      void store(double illuminationFromSample, Entry<RealVector, Double> sample) {
         if (illuminationFromSample > this.brightestIllumination) {
            this.brightestIllumination = illuminationFromSample;
            this.brightestSample = sample;
         }

      }

      double illumination() {
         return this.brightestIllumination;
      }

      Entry<RealVector, Double> sample() {
         return this.brightestSample;
      }
   }
}
