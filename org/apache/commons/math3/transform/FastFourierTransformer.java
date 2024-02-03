package org.apache.commons.math3.transform;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

public class FastFourierTransformer implements Serializable {
   static final long serialVersionUID = 20120210L;
   private static final double[] W_SUB_N_R = new double[]{1.0D, -1.0D, 6.123233995736766E-17D, 0.7071067811865476D, 0.9238795325112867D, 0.9807852804032304D, 0.9951847266721969D, 0.9987954562051724D, 0.9996988186962042D, 0.9999247018391445D, 0.9999811752826011D, 0.9999952938095762D, 0.9999988234517019D, 0.9999997058628822D, 0.9999999264657179D, 0.9999999816164293D, 0.9999999954041073D, 0.9999999988510269D, 0.9999999997127567D, 0.9999999999281892D, 0.9999999999820472D, 0.9999999999955118D, 0.999999999998878D, 0.9999999999997194D, 0.9999999999999298D, 0.9999999999999825D, 0.9999999999999957D, 0.9999999999999989D, 0.9999999999999998D, 0.9999999999999999D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D};
   private static final double[] W_SUB_N_I = new double[]{2.4492935982947064E-16D, -1.2246467991473532E-16D, -1.0D, -0.7071067811865475D, -0.3826834323650898D, -0.19509032201612825D, -0.0980171403295606D, -0.049067674327418015D, -0.024541228522912288D, -0.012271538285719925D, -0.006135884649154475D, -0.003067956762965976D, -0.0015339801862847655D, -7.669903187427045E-4D, -3.8349518757139556E-4D, -1.917475973107033E-4D, -9.587379909597734E-5D, -4.793689960306688E-5D, -2.396844980841822E-5D, -1.1984224905069705E-5D, -5.9921124526424275E-6D, -2.996056226334661E-6D, -1.4980281131690111E-6D, -7.490140565847157E-7D, -3.7450702829238413E-7D, -1.8725351414619535E-7D, -9.362675707309808E-8D, -4.681337853654909E-8D, -2.340668926827455E-8D, -1.1703344634137277E-8D, -5.8516723170686385E-9D, -2.9258361585343192E-9D, -1.4629180792671596E-9D, -7.314590396335798E-10D, -3.657295198167899E-10D, -1.8286475990839495E-10D, -9.143237995419748E-11D, -4.571618997709874E-11D, -2.285809498854937E-11D, -1.1429047494274685E-11D, -5.714523747137342E-12D, -2.857261873568671E-12D, -1.4286309367843356E-12D, -7.143154683921678E-13D, -3.571577341960839E-13D, -1.7857886709804195E-13D, -8.928943354902097E-14D, -4.4644716774510487E-14D, -2.2322358387255243E-14D, -1.1161179193627622E-14D, -5.580589596813811E-15D, -2.7902947984069054E-15D, -1.3951473992034527E-15D, -6.975736996017264E-16D, -3.487868498008632E-16D, -1.743934249004316E-16D, -8.71967124502158E-17D, -4.35983562251079E-17D, -2.179917811255395E-17D, -1.0899589056276974E-17D, -5.449794528138487E-18D, -2.7248972640692436E-18D, -1.3624486320346218E-18D};
   private final DftNormalization normalization;

   public FastFourierTransformer(DftNormalization normalization) {
      this.normalization = normalization;
   }

   private static void bitReversalShuffle2(double[] a, double[] b) {
      int n = a.length;

      assert b.length == n;

      int halfOfN = n >> 1;
      int j = 0;

      for(int i = 0; i < n; ++i) {
         if (i < j) {
            double temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            temp = b[i];
            b[i] = b[j];
            b[j] = temp;
         }

         int k;
         for(k = halfOfN; k <= j && k > 0; k >>= 1) {
            j -= k;
         }

         j += k;
      }

   }

   private static void normalizeTransformedData(double[][] dataRI, DftNormalization normalization, TransformType type) {
      double[] dataR = dataRI[0];
      double[] dataI = dataRI[1];
      int n = dataR.length;

      assert dataI.length == n;

      double scaleFactor;
      int i;
      switch(normalization) {
      case STANDARD:
         if (type == TransformType.INVERSE) {
            scaleFactor = 1.0D / (double)n;

            for(i = 0; i < n; ++i) {
               dataR[i] *= scaleFactor;
               dataI[i] *= scaleFactor;
            }
         }
         break;
      case UNITARY:
         scaleFactor = 1.0D / FastMath.sqrt((double)n);

         for(i = 0; i < n; ++i) {
            dataR[i] *= scaleFactor;
            dataI[i] *= scaleFactor;
         }

         return;
      default:
         throw new MathIllegalStateException();
      }

   }

   public static void transformInPlace(double[][] dataRI, DftNormalization normalization, TransformType type) {
      if (dataRI.length != 2) {
         throw new DimensionMismatchException(dataRI.length, 2);
      } else {
         double[] dataR = dataRI[0];
         double[] dataI = dataRI[1];
         if (dataR.length != dataI.length) {
            throw new DimensionMismatchException(dataI.length, dataR.length);
         } else {
            int n = dataR.length;
            if (!ArithmeticUtils.isPowerOfTwo((long)n)) {
               throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, new Object[]{n});
            } else if (n != 1) {
               double wSubN0R;
               double wSubN0I;
               if (n == 2) {
                  double srcR0 = dataR[0];
                  double srcI0 = dataI[0];
                  wSubN0R = dataR[1];
                  wSubN0I = dataI[1];
                  dataR[0] = srcR0 + wSubN0R;
                  dataI[0] = srcI0 + wSubN0I;
                  dataR[1] = srcR0 - wSubN0R;
                  dataI[1] = srcI0 - wSubN0I;
                  normalizeTransformedData(dataRI, normalization, type);
               } else {
                  bitReversalShuffle2(dataR, dataI);
                  int lastN0;
                  int lastLogN0;
                  int n0;
                  int logN0;
                  double srcR1;
                  double wSubN0ToRR;
                  double wSubN0ToRI;
                  double srcI2;
                  double srcR3;
                  double srcI3;
                  if (type == TransformType.INVERSE) {
                     for(lastN0 = 0; lastN0 < n; lastN0 += 4) {
                        lastLogN0 = lastN0 + 1;
                        n0 = lastN0 + 2;
                        logN0 = lastN0 + 3;
                        wSubN0R = dataR[lastN0];
                        wSubN0I = dataI[lastN0];
                        srcR1 = dataR[n0];
                        wSubN0ToRR = dataI[n0];
                        wSubN0ToRI = dataR[lastLogN0];
                        srcI2 = dataI[lastLogN0];
                        srcR3 = dataR[logN0];
                        srcI3 = dataI[logN0];
                        dataR[lastN0] = wSubN0R + srcR1 + wSubN0ToRI + srcR3;
                        dataI[lastN0] = wSubN0I + wSubN0ToRR + srcI2 + srcI3;
                        dataR[lastLogN0] = wSubN0R - wSubN0ToRI + (srcI3 - wSubN0ToRR);
                        dataI[lastLogN0] = wSubN0I - srcI2 + (srcR1 - srcR3);
                        dataR[n0] = wSubN0R - srcR1 + wSubN0ToRI - srcR3;
                        dataI[n0] = wSubN0I - wSubN0ToRR + srcI2 - srcI3;
                        dataR[logN0] = wSubN0R - wSubN0ToRI + (wSubN0ToRR - srcI3);
                        dataI[logN0] = wSubN0I - srcI2 + (srcR3 - srcR1);
                     }
                  } else {
                     for(lastN0 = 0; lastN0 < n; lastN0 += 4) {
                        lastLogN0 = lastN0 + 1;
                        n0 = lastN0 + 2;
                        logN0 = lastN0 + 3;
                        wSubN0R = dataR[lastN0];
                        wSubN0I = dataI[lastN0];
                        srcR1 = dataR[n0];
                        wSubN0ToRR = dataI[n0];
                        wSubN0ToRI = dataR[lastLogN0];
                        srcI2 = dataI[lastLogN0];
                        srcR3 = dataR[logN0];
                        srcI3 = dataI[logN0];
                        dataR[lastN0] = wSubN0R + srcR1 + wSubN0ToRI + srcR3;
                        dataI[lastN0] = wSubN0I + wSubN0ToRR + srcI2 + srcI3;
                        dataR[lastLogN0] = wSubN0R - wSubN0ToRI + (wSubN0ToRR - srcI3);
                        dataI[lastLogN0] = wSubN0I - srcI2 + (srcR3 - srcR1);
                        dataR[n0] = wSubN0R - srcR1 + wSubN0ToRI - srcR3;
                        dataI[n0] = wSubN0I - wSubN0ToRR + srcI2 - srcI3;
                        dataR[logN0] = wSubN0R - wSubN0ToRI + (srcI3 - wSubN0ToRR);
                        dataI[logN0] = wSubN0I - srcI2 + (srcR1 - srcR3);
                     }
                  }

                  lastN0 = 4;

                  for(lastLogN0 = 2; lastN0 < n; lastLogN0 = logN0) {
                     n0 = lastN0 << 1;
                     logN0 = lastLogN0 + 1;
                     wSubN0R = W_SUB_N_R[logN0];
                     wSubN0I = W_SUB_N_I[logN0];
                     if (type == TransformType.INVERSE) {
                        wSubN0I = -wSubN0I;
                     }

                     for(int destEvenStartIndex = 0; destEvenStartIndex < n; destEvenStartIndex += n0) {
                        int destOddStartIndex = destEvenStartIndex + lastN0;
                        wSubN0ToRR = 1.0D;
                        wSubN0ToRI = 0.0D;

                        for(int r = 0; r < lastN0; ++r) {
                           double grR = dataR[destEvenStartIndex + r];
                           double grI = dataI[destEvenStartIndex + r];
                           double hrR = dataR[destOddStartIndex + r];
                           double hrI = dataI[destOddStartIndex + r];
                           dataR[destEvenStartIndex + r] = grR + wSubN0ToRR * hrR - wSubN0ToRI * hrI;
                           dataI[destEvenStartIndex + r] = grI + wSubN0ToRR * hrI + wSubN0ToRI * hrR;
                           dataR[destOddStartIndex + r] = grR - (wSubN0ToRR * hrR - wSubN0ToRI * hrI);
                           dataI[destOddStartIndex + r] = grI - (wSubN0ToRR * hrI + wSubN0ToRI * hrR);
                           double nextWsubN0ToRR = wSubN0ToRR * wSubN0R - wSubN0ToRI * wSubN0I;
                           double nextWsubN0ToRI = wSubN0ToRR * wSubN0I + wSubN0ToRI * wSubN0R;
                           wSubN0ToRR = nextWsubN0ToRR;
                           wSubN0ToRI = nextWsubN0ToRI;
                        }
                     }

                     lastN0 = n0;
                  }

                  normalizeTransformedData(dataRI, normalization, type);
               }
            }
         }
      }
   }

   public Complex[] transform(double[] f, TransformType type) {
      double[][] dataRI = new double[][]{MathArrays.copyOf(f, f.length), new double[f.length]};
      transformInPlace(dataRI, this.normalization, type);
      return TransformUtils.createComplexArray(dataRI);
   }

   public Complex[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
      double[] data = FunctionUtils.sample(f, min, max, n);
      return this.transform(data, type);
   }

   public Complex[] transform(Complex[] f, TransformType type) {
      double[][] dataRI = TransformUtils.createRealImaginaryArray(f);
      transformInPlace(dataRI, this.normalization, type);
      return TransformUtils.createComplexArray(dataRI);
   }

   /** @deprecated */
   @Deprecated
   public Object mdfft(Object mdca, TransformType type) {
      FastFourierTransformer.MultiDimensionalComplexMatrix mdcm = (FastFourierTransformer.MultiDimensionalComplexMatrix)(new FastFourierTransformer.MultiDimensionalComplexMatrix(mdca)).clone();
      int[] dimensionSize = mdcm.getDimensionSizes();

      for(int i = 0; i < dimensionSize.length; ++i) {
         this.mdfft(mdcm, type, i, new int[0]);
      }

      return mdcm.getArray();
   }

   /** @deprecated */
   @Deprecated
   private void mdfft(FastFourierTransformer.MultiDimensionalComplexMatrix mdcm, TransformType type, int d, int[] subVector) {
      int[] dimensionSize = mdcm.getDimensionSizes();
      int i;
      if (subVector.length == dimensionSize.length) {
         Complex[] temp = new Complex[dimensionSize[d]];

         for(i = 0; i < dimensionSize[d]; ++i) {
            subVector[d] = i;
            temp[i] = mdcm.get(subVector);
         }

         temp = this.transform(temp, type);

         for(i = 0; i < dimensionSize[d]; ++i) {
            subVector[d] = i;
            mdcm.set(temp[i], subVector);
         }
      } else {
         int[] vector = new int[subVector.length + 1];
         System.arraycopy(subVector, 0, vector, 0, subVector.length);
         if (subVector.length == d) {
            vector[d] = 0;
            this.mdfft(mdcm, type, d, vector);
         } else {
            for(i = 0; i < dimensionSize[subVector.length]; ++i) {
               vector[subVector.length] = i;
               this.mdfft(mdcm, type, d, vector);
            }
         }
      }

   }

   /** @deprecated */
   @Deprecated
   private static class MultiDimensionalComplexMatrix implements Cloneable {
      protected int[] dimensionSize;
      protected Object multiDimensionalComplexArray;

      public MultiDimensionalComplexMatrix(Object multiDimensionalComplexArray) {
         this.multiDimensionalComplexArray = multiDimensionalComplexArray;
         int numOfDimensions = 0;

         Object lastDimension;
         Object[] array;
         for(lastDimension = multiDimensionalComplexArray; lastDimension instanceof Object[]; lastDimension = array[0]) {
            array = (Object[])((Object[])lastDimension);
            ++numOfDimensions;
         }

         this.dimensionSize = new int[numOfDimensions];
         numOfDimensions = 0;

         for(lastDimension = multiDimensionalComplexArray; lastDimension instanceof Object[]; lastDimension = array[0]) {
            array = (Object[])((Object[])lastDimension);
            this.dimensionSize[numOfDimensions++] = array.length;
         }

      }

      public Complex get(int... vector) throws DimensionMismatchException {
         if (vector == null) {
            if (this.dimensionSize.length > 0) {
               throw new DimensionMismatchException(0, this.dimensionSize.length);
            } else {
               return null;
            }
         } else if (vector.length != this.dimensionSize.length) {
            throw new DimensionMismatchException(vector.length, this.dimensionSize.length);
         } else {
            Object lastDimension = this.multiDimensionalComplexArray;

            for(int i = 0; i < this.dimensionSize.length; ++i) {
               lastDimension = ((Object[])((Object[])lastDimension))[vector[i]];
            }

            return (Complex)lastDimension;
         }
      }

      public Complex set(Complex magnitude, int... vector) throws DimensionMismatchException {
         if (vector == null) {
            if (this.dimensionSize.length > 0) {
               throw new DimensionMismatchException(0, this.dimensionSize.length);
            } else {
               return null;
            }
         } else if (vector.length != this.dimensionSize.length) {
            throw new DimensionMismatchException(vector.length, this.dimensionSize.length);
         } else {
            Object[] lastDimension = (Object[])((Object[])this.multiDimensionalComplexArray);

            for(int i = 0; i < this.dimensionSize.length - 1; ++i) {
               lastDimension = (Object[])((Object[])lastDimension[vector[i]]);
            }

            Complex lastValue = (Complex)lastDimension[vector[this.dimensionSize.length - 1]];
            lastDimension[vector[this.dimensionSize.length - 1]] = magnitude;
            return lastValue;
         }
      }

      public int[] getDimensionSizes() {
         return (int[])this.dimensionSize.clone();
      }

      public Object getArray() {
         return this.multiDimensionalComplexArray;
      }

      public Object clone() {
         FastFourierTransformer.MultiDimensionalComplexMatrix mdcm = new FastFourierTransformer.MultiDimensionalComplexMatrix(Array.newInstance(Complex.class, this.dimensionSize));
         this.clone(mdcm);
         return mdcm;
      }

      private void clone(FastFourierTransformer.MultiDimensionalComplexMatrix mdcm) {
         int[] vector = new int[this.dimensionSize.length];
         int size = 1;

         for(int i = 0; i < this.dimensionSize.length; ++i) {
            size *= this.dimensionSize[i];
         }

         int[][] vectorList = new int[size][this.dimensionSize.length];
         int[][] arr$ = vectorList;
         int len$ = vectorList.length;

         int i$;
         int[] nextVector;
         for(i$ = 0; i$ < len$; ++i$) {
            nextVector = arr$[i$];
            System.arraycopy(vector, 0, nextVector, 0, this.dimensionSize.length);

            for(int i = 0; i < this.dimensionSize.length; ++i) {
               int var10002 = vector[i]++;
               if (vector[i] < this.dimensionSize[i]) {
                  break;
               }

               vector[i] = 0;
            }
         }

         arr$ = vectorList;
         len$ = vectorList.length;

         for(i$ = 0; i$ < len$; ++i$) {
            nextVector = arr$[i$];
            mdcm.set(this.get(nextVector), nextVector);
         }

      }
   }
}
