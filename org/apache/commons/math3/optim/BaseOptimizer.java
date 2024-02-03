package org.apache.commons.math3.optim;

import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.util.Incrementor;

public abstract class BaseOptimizer<PAIR> {
   protected final Incrementor evaluations;
   protected final Incrementor iterations;
   private ConvergenceChecker<PAIR> checker;

   protected BaseOptimizer(ConvergenceChecker<PAIR> checker) {
      this.checker = checker;
      this.evaluations = new Incrementor(0, new BaseOptimizer.MaxEvalCallback());
      this.iterations = new Incrementor(Integer.MAX_VALUE, new BaseOptimizer.MaxIterCallback());
   }

   public int getMaxEvaluations() {
      return this.evaluations.getMaximalCount();
   }

   public int getEvaluations() {
      return this.evaluations.getCount();
   }

   public int getMaxIterations() {
      return this.iterations.getMaximalCount();
   }

   public int getIterations() {
      return this.iterations.getCount();
   }

   public ConvergenceChecker<PAIR> getConvergenceChecker() {
      return this.checker;
   }

   public PAIR optimize(OptimizationData... optData) throws TooManyEvaluationsException, TooManyIterationsException {
      this.parseOptimizationData(optData);
      this.evaluations.resetCount();
      this.iterations.resetCount();
      return this.doOptimize();
   }

   protected abstract PAIR doOptimize();

   protected void incrementEvaluationCount() throws TooManyEvaluationsException {
      this.evaluations.incrementCount();
   }

   protected void incrementIterationCount() throws TooManyIterationsException {
      this.iterations.incrementCount();
   }

   protected void parseOptimizationData(OptimizationData... optData) {
      OptimizationData[] arr$ = optData;
      int len$ = optData.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         OptimizationData data = arr$[i$];
         if (data instanceof MaxEval) {
            this.evaluations.setMaximalCount(((MaxEval)data).getMaxEval());
         } else if (data instanceof MaxIter) {
            this.iterations.setMaximalCount(((MaxIter)data).getMaxIter());
         }
      }

   }

   private static class MaxIterCallback implements Incrementor.MaxCountExceededCallback {
      private MaxIterCallback() {
      }

      public void trigger(int max) {
         throw new TooManyIterationsException(max);
      }

      // $FF: synthetic method
      MaxIterCallback(Object x0) {
         this();
      }
   }

   private static class MaxEvalCallback implements Incrementor.MaxCountExceededCallback {
      private MaxEvalCallback() {
      }

      public void trigger(int max) {
         throw new TooManyEvaluationsException(max);
      }

      // $FF: synthetic method
      MaxEvalCallback(Object x0) {
         this();
      }
   }
}
