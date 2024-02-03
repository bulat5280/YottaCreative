package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.ExceptionContext;
import org.apache.commons.math3.util.IterationManager;

public class ConjugateGradient extends PreconditionedIterativeLinearSolver {
   public static final String OPERATOR = "operator";
   public static final String VECTOR = "vector";
   private boolean check;
   private final double delta;

   public ConjugateGradient(int maxIterations, double delta, boolean check) {
      super(maxIterations);
      this.delta = delta;
      this.check = check;
   }

   public ConjugateGradient(IterationManager manager, double delta, boolean check) throws NullArgumentException {
      super(manager);
      this.delta = delta;
      this.check = check;
   }

   public final boolean getCheck() {
      return this.check;
   }

   public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x0) throws NullArgumentException, NonPositiveDefiniteOperatorException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException, NonPositiveDefiniteOperatorException {
      checkParameters(a, m, b, x0);
      IterationManager manager = this.getIterationManager();
      manager.resetIterationCount();
      double rmax = this.delta * b.getNorm();
      RealVector bro = RealVector.unmodifiableRealVector(b);
      manager.incrementIterationCount();
      RealVector x = x0;
      RealVector xro = RealVector.unmodifiableRealVector(x0);
      RealVector p = x0.copy();
      RealVector q = a.operate(p);
      RealVector r = b.combine(1.0D, -1.0D, q);
      RealVector rro = RealVector.unmodifiableRealVector(r);
      double rnorm = r.getNorm();
      RealVector z;
      if (m == null) {
         z = r;
      } else {
         z = null;
      }

      IterativeLinearSolverEvent evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
      manager.fireInitializationEvent(evt);
      if (rnorm <= rmax) {
         manager.fireTerminationEvent(evt);
         return x0;
      } else {
         double rhoPrev = 0.0D;

         do {
            manager.incrementIterationCount();
            evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
            manager.fireIterationStartedEvent(evt);
            if (m != null) {
               z = m.operate(r);
            }

            double rhoNext = r.dotProduct(z);
            if (this.check && rhoNext <= 0.0D) {
               NonPositiveDefiniteOperatorException e = new NonPositiveDefiniteOperatorException();
               ExceptionContext context = e.getContext();
               context.setValue("operator", m);
               context.setValue("vector", r);
               throw e;
            }

            if (manager.getIterations() == 2) {
               p.setSubVector(0, z);
            } else {
               p.combineToSelf(rhoNext / rhoPrev, 1.0D, z);
            }

            q = a.operate(p);
            double pq = p.dotProduct(q);
            if (this.check && pq <= 0.0D) {
               NonPositiveDefiniteOperatorException e = new NonPositiveDefiniteOperatorException();
               ExceptionContext context = e.getContext();
               context.setValue("operator", a);
               context.setValue("vector", p);
               throw e;
            }

            double alpha = rhoNext / pq;
            x.combineToSelf(1.0D, alpha, p);
            r.combineToSelf(1.0D, -alpha, q);
            rhoPrev = rhoNext;
            rnorm = r.getNorm();
            evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
            manager.fireIterationPerformedEvent(evt);
         } while(!(rnorm <= rmax));

         manager.fireTerminationEvent(evt);
         return x;
      }
   }
}
