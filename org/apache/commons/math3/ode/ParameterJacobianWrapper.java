package org.apache.commons.math3.ode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

class ParameterJacobianWrapper implements ParameterJacobianProvider {
   private final FirstOrderDifferentialEquations fode;
   private final ParameterizedODE pode;
   private final Map<String, Double> hParam;

   public ParameterJacobianWrapper(FirstOrderDifferentialEquations fode, ParameterizedODE pode, ParameterConfiguration[] paramsAndSteps) {
      this.fode = fode;
      this.pode = pode;
      this.hParam = new HashMap();
      ParameterConfiguration[] arr$ = paramsAndSteps;
      int len$ = paramsAndSteps.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ParameterConfiguration param = arr$[i$];
         String name = param.getParameterName();
         if (pode.isSupported(name)) {
            this.hParam.put(name, param.getHP());
         }
      }

   }

   public Collection<String> getParametersNames() {
      return this.pode.getParametersNames();
   }

   public boolean isSupported(String name) {
      return this.pode.isSupported(name);
   }

   public void computeParameterJacobian(double t, double[] y, double[] yDot, String paramName, double[] dFdP) throws DimensionMismatchException, MaxCountExceededException {
      int n = this.fode.getDimension();
      if (this.pode.isSupported(paramName)) {
         double[] tmpDot = new double[n];
         double p = this.pode.getParameter(paramName);
         double hP = (Double)this.hParam.get(paramName);
         this.pode.setParameter(paramName, p + hP);
         this.fode.computeDerivatives(t, y, tmpDot);

         for(int i = 0; i < n; ++i) {
            dFdP[i] = (tmpDot[i] - yDot[i]) / hP;
         }

         this.pode.setParameter(paramName, p);
      } else {
         Arrays.fill(dFdP, 0, n, 0.0D);
      }

   }
}
