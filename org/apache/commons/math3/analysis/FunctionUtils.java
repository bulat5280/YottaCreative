package org.apache.commons.math3.analysis;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.function.Identity;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public class FunctionUtils {
   private FunctionUtils() {
   }

   public static UnivariateFunction compose(final UnivariateFunction... f) {
      return new UnivariateFunction() {
         public double value(double x) {
            double r = x;

            for(int i = f.length - 1; i >= 0; --i) {
               r = f[i].value(r);
            }

            return r;
         }
      };
   }

   public static UnivariateDifferentiableFunction compose(final UnivariateDifferentiableFunction... f) {
      return new UnivariateDifferentiableFunction() {
         public double value(double t) {
            double r = t;

            for(int i = f.length - 1; i >= 0; --i) {
               r = f[i].value(r);
            }

            return r;
         }

         public DerivativeStructure value(DerivativeStructure t) {
            DerivativeStructure r = t;

            for(int i = f.length - 1; i >= 0; --i) {
               r = f[i].value(r);
            }

            return r;
         }
      };
   }

   /** @deprecated */
   public static DifferentiableUnivariateFunction compose(final DifferentiableUnivariateFunction... f) {
      return new DifferentiableUnivariateFunction() {
         public double value(double x) {
            double r = x;

            for(int i = f.length - 1; i >= 0; --i) {
               r = f[i].value(r);
            }

            return r;
         }

         public UnivariateFunction derivative() {
            return new UnivariateFunction() {
               public double value(double x) {
                  double p = 1.0D;
                  double r = x;

                  for(int i = f.length - 1; i >= 0; --i) {
                     p *= f[i].derivative().value(r);
                     r = f[i].value(r);
                  }

                  return p;
               }
            };
         }
      };
   }

   public static UnivariateFunction add(final UnivariateFunction... f) {
      return new UnivariateFunction() {
         public double value(double x) {
            double r = f[0].value(x);

            for(int i = 1; i < f.length; ++i) {
               r += f[i].value(x);
            }

            return r;
         }
      };
   }

   public static UnivariateDifferentiableFunction add(final UnivariateDifferentiableFunction... f) {
      return new UnivariateDifferentiableFunction() {
         public double value(double t) {
            double r = f[0].value(t);

            for(int i = 1; i < f.length; ++i) {
               r += f[i].value(t);
            }

            return r;
         }

         public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
            DerivativeStructure r = f[0].value(t);

            for(int i = 1; i < f.length; ++i) {
               r = r.add(f[i].value(t));
            }

            return r;
         }
      };
   }

   /** @deprecated */
   @Deprecated
   public static DifferentiableUnivariateFunction add(final DifferentiableUnivariateFunction... f) {
      return new DifferentiableUnivariateFunction() {
         public double value(double x) {
            double r = f[0].value(x);

            for(int i = 1; i < f.length; ++i) {
               r += f[i].value(x);
            }

            return r;
         }

         public UnivariateFunction derivative() {
            return new UnivariateFunction() {
               public double value(double x) {
                  double r = f[0].derivative().value(x);

                  for(int i = 1; i < f.length; ++i) {
                     r += f[i].derivative().value(x);
                  }

                  return r;
               }
            };
         }
      };
   }

   public static UnivariateFunction multiply(final UnivariateFunction... f) {
      return new UnivariateFunction() {
         public double value(double x) {
            double r = f[0].value(x);

            for(int i = 1; i < f.length; ++i) {
               r *= f[i].value(x);
            }

            return r;
         }
      };
   }

   public static UnivariateDifferentiableFunction multiply(final UnivariateDifferentiableFunction... f) {
      return new UnivariateDifferentiableFunction() {
         public double value(double t) {
            double r = f[0].value(t);

            for(int i = 1; i < f.length; ++i) {
               r *= f[i].value(t);
            }

            return r;
         }

         public DerivativeStructure value(DerivativeStructure t) {
            DerivativeStructure r = f[0].value(t);

            for(int i = 1; i < f.length; ++i) {
               r = r.multiply(f[i].value(t));
            }

            return r;
         }
      };
   }

   /** @deprecated */
   public static DifferentiableUnivariateFunction multiply(final DifferentiableUnivariateFunction... f) {
      return new DifferentiableUnivariateFunction() {
         public double value(double x) {
            double r = f[0].value(x);

            for(int i = 1; i < f.length; ++i) {
               r *= f[i].value(x);
            }

            return r;
         }

         public UnivariateFunction derivative() {
            return new UnivariateFunction() {
               public double value(double x) {
                  double sum = 0.0D;

                  for(int i = 0; i < f.length; ++i) {
                     double prod = f[i].derivative().value(x);

                     for(int j = 0; j < f.length; ++j) {
                        if (i != j) {
                           prod *= f[j].value(x);
                        }
                     }

                     sum += prod;
                  }

                  return sum;
               }
            };
         }
      };
   }

   public static UnivariateFunction combine(final BivariateFunction combiner, final UnivariateFunction f, final UnivariateFunction g) {
      return new UnivariateFunction() {
         public double value(double x) {
            return combiner.value(f.value(x), g.value(x));
         }
      };
   }

   public static MultivariateFunction collector(final BivariateFunction combiner, final UnivariateFunction f, final double initialValue) {
      return new MultivariateFunction() {
         public double value(double[] point) {
            double result = combiner.value(initialValue, f.value(point[0]));

            for(int i = 1; i < point.length; ++i) {
               result = combiner.value(result, f.value(point[i]));
            }

            return result;
         }
      };
   }

   public static MultivariateFunction collector(BivariateFunction combiner, double initialValue) {
      return collector(combiner, new Identity(), initialValue);
   }

   public static UnivariateFunction fix1stArgument(final BivariateFunction f, final double fixed) {
      return new UnivariateFunction() {
         public double value(double x) {
            return f.value(fixed, x);
         }
      };
   }

   public static UnivariateFunction fix2ndArgument(final BivariateFunction f, final double fixed) {
      return new UnivariateFunction() {
         public double value(double x) {
            return f.value(x, fixed);
         }
      };
   }

   public static double[] sample(UnivariateFunction f, double min, double max, int n) throws NumberIsTooLargeException, NotStrictlyPositiveException {
      if (n <= 0) {
         throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, n);
      } else if (min >= max) {
         throw new NumberIsTooLargeException(min, max, false);
      } else {
         double[] s = new double[n];
         double h = (max - min) / (double)n;

         for(int i = 0; i < n; ++i) {
            s[i] = f.value(min + (double)i * h);
         }

         return s;
      }
   }

   /** @deprecated */
   @Deprecated
   public static DifferentiableUnivariateFunction toDifferentiableUnivariateFunction(final UnivariateDifferentiableFunction f) {
      return new DifferentiableUnivariateFunction() {
         public double value(double x) {
            return f.value(x);
         }

         public UnivariateFunction derivative() {
            return new UnivariateFunction() {
               public double value(double x) {
                  return f.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1);
               }
            };
         }
      };
   }

   /** @deprecated */
   @Deprecated
   public static UnivariateDifferentiableFunction toUnivariateDifferential(final DifferentiableUnivariateFunction f) {
      return new UnivariateDifferentiableFunction() {
         public double value(double x) {
            return f.value(x);
         }

         public DerivativeStructure value(DerivativeStructure t) throws NumberIsTooLargeException {
            switch(t.getOrder()) {
            case 0:
               return new DerivativeStructure(t.getFreeParameters(), 0, f.value(t.getValue()));
            case 1:
               int parameters = t.getFreeParameters();
               double[] derivatives = new double[parameters + 1];
               derivatives[0] = f.value(t.getValue());
               double fPrime = f.derivative().value(t.getValue());
               int[] orders = new int[parameters];

               for(int i = 0; i < parameters; ++i) {
                  orders[i] = 1;
                  derivatives[i + 1] = fPrime * t.getPartialDerivative(orders);
                  orders[i] = 0;
               }

               return new DerivativeStructure(parameters, 1, derivatives);
            default:
               throw new NumberIsTooLargeException(t.getOrder(), 1, true);
            }
         }
      };
   }

   /** @deprecated */
   @Deprecated
   public static DifferentiableMultivariateFunction toDifferentiableMultivariateFunction(final MultivariateDifferentiableFunction f) {
      return new DifferentiableMultivariateFunction() {
         public double value(double[] x) {
            return f.value(x);
         }

         public MultivariateFunction partialDerivative(final int k) {
            return new MultivariateFunction() {
               public double value(double[] x) {
                  int n = x.length;
                  DerivativeStructure[] dsX = new DerivativeStructure[n];

                  for(int i = 0; i < n; ++i) {
                     if (i == k) {
                        dsX[i] = new DerivativeStructure(1, 1, 0, x[i]);
                     } else {
                        dsX[i] = new DerivativeStructure(1, 1, x[i]);
                     }
                  }

                  DerivativeStructure y = f.value(dsX);
                  return y.getPartialDerivative(1);
               }
            };
         }

         public MultivariateVectorFunction gradient() {
            return new MultivariateVectorFunction() {
               public double[] value(double[] x) {
                  int n = x.length;
                  DerivativeStructure[] dsX = new DerivativeStructure[n];

                  for(int ix = 0; ix < n; ++ix) {
                     dsX[ix] = new DerivativeStructure(n, 1, ix, x[ix]);
                  }

                  DerivativeStructure y = f.value(dsX);
                  double[] gradient = new double[n];
                  int[] orders = new int[n];

                  for(int i = 0; i < n; ++i) {
                     orders[i] = 1;
                     gradient[i] = y.getPartialDerivative(orders);
                     orders[i] = 0;
                  }

                  return gradient;
               }
            };
         }
      };
   }

   /** @deprecated */
   @Deprecated
   public static MultivariateDifferentiableFunction toMultivariateDifferentiableFunction(final DifferentiableMultivariateFunction f) {
      return new MultivariateDifferentiableFunction() {
         public double value(double[] x) {
            return f.value(x);
         }

         public DerivativeStructure value(DerivativeStructure[] t) throws DimensionMismatchException, NumberIsTooLargeException {
            int parameters = t[0].getFreeParameters();
            int order = t[0].getOrder();
            int n = t.length;
            if (order > 1) {
               throw new NumberIsTooLargeException(order, 1, true);
            } else {
               for(int ix = 0; ix < n; ++ix) {
                  if (t[ix].getFreeParameters() != parameters) {
                     throw new DimensionMismatchException(t[ix].getFreeParameters(), parameters);
                  }

                  if (t[ix].getOrder() != order) {
                     throw new DimensionMismatchException(t[ix].getOrder(), order);
                  }
               }

               double[] point = new double[n];

               for(int ixx = 0; ixx < n; ++ixx) {
                  point[ixx] = t[ixx].getValue();
               }

               double value = f.value(point);
               double[] gradient = f.gradient().value(point);
               double[] derivatives = new double[parameters + 1];
               derivatives[0] = value;
               int[] orders = new int[parameters];

               for(int i = 0; i < parameters; ++i) {
                  orders[i] = 1;

                  for(int j = 0; j < n; ++j) {
                     derivatives[i + 1] += gradient[j] * t[j].getPartialDerivative(orders);
                  }

                  orders[i] = 0;
               }

               return new DerivativeStructure(parameters, order, derivatives);
            }
         }
      };
   }

   /** @deprecated */
   @Deprecated
   public static DifferentiableMultivariateVectorFunction toDifferentiableMultivariateVectorFunction(final MultivariateDifferentiableVectorFunction f) {
      return new DifferentiableMultivariateVectorFunction() {
         public double[] value(double[] x) {
            return f.value(x);
         }

         public MultivariateMatrixFunction jacobian() {
            return new MultivariateMatrixFunction() {
               public double[][] value(double[] x) {
                  int n = x.length;
                  DerivativeStructure[] dsX = new DerivativeStructure[n];

                  for(int ix = 0; ix < n; ++ix) {
                     dsX[ix] = new DerivativeStructure(n, 1, ix, x[ix]);
                  }

                  DerivativeStructure[] y = f.value(dsX);
                  double[][] jacobian = new double[y.length][n];
                  int[] orders = new int[n];

                  for(int i = 0; i < y.length; ++i) {
                     for(int j = 0; j < n; ++j) {
                        orders[j] = 1;
                        jacobian[i][j] = y[i].getPartialDerivative(orders);
                        orders[j] = 0;
                     }
                  }

                  return jacobian;
               }
            };
         }
      };
   }

   /** @deprecated */
   @Deprecated
   public static MultivariateDifferentiableVectorFunction toMultivariateDifferentiableVectorFunction(final DifferentiableMultivariateVectorFunction f) {
      return new MultivariateDifferentiableVectorFunction() {
         public double[] value(double[] x) {
            return f.value(x);
         }

         public DerivativeStructure[] value(DerivativeStructure[] t) throws DimensionMismatchException, NumberIsTooLargeException {
            int parameters = t[0].getFreeParameters();
            int order = t[0].getOrder();
            int n = t.length;
            if (order > 1) {
               throw new NumberIsTooLargeException(order, 1, true);
            } else {
               for(int ix = 0; ix < n; ++ix) {
                  if (t[ix].getFreeParameters() != parameters) {
                     throw new DimensionMismatchException(t[ix].getFreeParameters(), parameters);
                  }

                  if (t[ix].getOrder() != order) {
                     throw new DimensionMismatchException(t[ix].getOrder(), order);
                  }
               }

               double[] point = new double[n];

               for(int ixx = 0; ixx < n; ++ixx) {
                  point[ixx] = t[ixx].getValue();
               }

               double[] value = f.value(point);
               double[][] jacobian = f.jacobian().value(point);
               DerivativeStructure[] merged = new DerivativeStructure[value.length];

               for(int k = 0; k < merged.length; ++k) {
                  double[] derivatives = new double[parameters + 1];
                  derivatives[0] = value[k];
                  int[] orders = new int[parameters];

                  for(int i = 0; i < parameters; ++i) {
                     orders[i] = 1;

                     for(int j = 0; j < n; ++j) {
                        derivatives[i + 1] += jacobian[k][j] * t[j].getPartialDerivative(orders);
                     }

                     orders[i] = 0;
                  }

                  merged[k] = new DerivativeStructure(parameters, order, derivatives);
               }

               return merged;
            }
         }
      };
   }
}
