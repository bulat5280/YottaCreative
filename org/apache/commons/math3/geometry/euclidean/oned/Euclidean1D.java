package org.apache.commons.math3.geometry.euclidean.oned;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.geometry.Space;

public class Euclidean1D implements Serializable, Space {
   private static final long serialVersionUID = -1178039568877797126L;

   private Euclidean1D() {
   }

   public static Euclidean1D getInstance() {
      return Euclidean1D.LazyHolder.INSTANCE;
   }

   public int getDimension() {
      return 1;
   }

   public Space getSubSpace() throws MathUnsupportedOperationException {
      throw new MathUnsupportedOperationException(LocalizedFormats.NOT_SUPPORTED_IN_DIMENSION_N, new Object[]{1});
   }

   private Object readResolve() {
      return Euclidean1D.LazyHolder.INSTANCE;
   }

   // $FF: synthetic method
   Euclidean1D(Object x0) {
      this();
   }

   private static class LazyHolder {
      private static final Euclidean1D INSTANCE = new Euclidean1D();
   }
}
