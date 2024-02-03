package org.apache.commons.math3.ode.events;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.AllowedSolution;
import org.apache.commons.math3.analysis.solvers.BracketedUnivariateSolver;
import org.apache.commons.math3.analysis.solvers.PegasusSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.ode.sampling.StepInterpolator;
import org.apache.commons.math3.util.FastMath;

public class EventState {
   private final EventHandler handler;
   private final double maxCheckInterval;
   private final double convergence;
   private final int maxIterationCount;
   private double t0;
   private double g0;
   private boolean g0Positive;
   private boolean pendingEvent;
   private double pendingEventTime;
   private double previousEventTime;
   private boolean forward;
   private boolean increasing;
   private EventHandler.Action nextAction;
   private final UnivariateSolver solver;

   public EventState(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
      this.handler = handler;
      this.maxCheckInterval = maxCheckInterval;
      this.convergence = FastMath.abs(convergence);
      this.maxIterationCount = maxIterationCount;
      this.solver = solver;
      this.t0 = Double.NaN;
      this.g0 = Double.NaN;
      this.g0Positive = true;
      this.pendingEvent = false;
      this.pendingEventTime = Double.NaN;
      this.previousEventTime = Double.NaN;
      this.increasing = true;
      this.nextAction = EventHandler.Action.CONTINUE;
   }

   public EventHandler getEventHandler() {
      return this.handler;
   }

   public double getMaxCheckInterval() {
      return this.maxCheckInterval;
   }

   public double getConvergence() {
      return this.convergence;
   }

   public int getMaxIterationCount() {
      return this.maxIterationCount;
   }

   public void reinitializeBegin(StepInterpolator interpolator) throws MaxCountExceededException {
      this.t0 = interpolator.getPreviousTime();
      interpolator.setInterpolatedTime(this.t0);
      this.g0 = this.handler.g(this.t0, interpolator.getInterpolatedState());
      if (this.g0 == 0.0D) {
         double epsilon = FastMath.max(this.solver.getAbsoluteAccuracy(), FastMath.abs(this.solver.getRelativeAccuracy() * this.t0));
         double tStart = this.t0 + 0.5D * epsilon;
         interpolator.setInterpolatedTime(tStart);
         this.g0 = this.handler.g(tStart, interpolator.getInterpolatedState());
      }

      this.g0Positive = this.g0 >= 0.0D;
   }

   public boolean evaluateStep(final StepInterpolator interpolator) throws MaxCountExceededException, NoBracketingException {
      try {
         this.forward = interpolator.isForward();
         double t1 = interpolator.getCurrentTime();
         double dt = t1 - this.t0;
         if (FastMath.abs(dt) < this.convergence) {
            return false;
         } else {
            int n = FastMath.max(1, (int)FastMath.ceil(FastMath.abs(dt) / this.maxCheckInterval));
            double h = dt / (double)n;
            UnivariateFunction f = new UnivariateFunction() {
               public double value(double t) throws EventState.LocalMaxCountExceededException {
                  try {
                     interpolator.setInterpolatedTime(t);
                     return EventState.this.handler.g(t, interpolator.getInterpolatedState());
                  } catch (MaxCountExceededException var4) {
                     throw new EventState.LocalMaxCountExceededException(var4);
                  }
               }
            };
            double ta = this.t0;
            double ga = this.g0;

            for(int i = 0; i < n; ++i) {
               double tb = this.t0 + (double)(i + 1) * h;
               interpolator.setInterpolatedTime(tb);
               double gb = this.handler.g(tb, interpolator.getInterpolatedState());
               if (this.g0Positive ^ gb >= 0.0D) {
                  this.increasing = gb >= ga;
                  double root;
                  if (this.solver instanceof BracketedUnivariateSolver) {
                     BracketedUnivariateSolver<UnivariateFunction> bracketing = (BracketedUnivariateSolver)this.solver;
                     root = this.forward ? bracketing.solve(this.maxIterationCount, f, ta, tb, AllowedSolution.RIGHT_SIDE) : bracketing.solve(this.maxIterationCount, f, tb, ta, AllowedSolution.LEFT_SIDE);
                  } else {
                     double baseRoot = this.forward ? this.solver.solve(this.maxIterationCount, f, ta, tb) : this.solver.solve(this.maxIterationCount, f, tb, ta);
                     int remainingEval = this.maxIterationCount - this.solver.getEvaluations();
                     BracketedUnivariateSolver<UnivariateFunction> bracketing = new PegasusSolver(this.solver.getRelativeAccuracy(), this.solver.getAbsoluteAccuracy());
                     root = this.forward ? UnivariateSolverUtils.forceSide(remainingEval, f, bracketing, baseRoot, ta, tb, AllowedSolution.RIGHT_SIDE) : UnivariateSolverUtils.forceSide(remainingEval, f, bracketing, baseRoot, tb, ta, AllowedSolution.LEFT_SIDE);
                  }

                  if (!Double.isNaN(this.previousEventTime) && FastMath.abs(root - ta) <= this.convergence && FastMath.abs(root - this.previousEventTime) <= this.convergence) {
                     ta = this.forward ? ta + this.convergence : ta - this.convergence;
                     ga = f.value(ta);
                     --i;
                  } else {
                     if (Double.isNaN(this.previousEventTime) || FastMath.abs(this.previousEventTime - root) > this.convergence) {
                        this.pendingEventTime = root;
                        this.pendingEvent = true;
                        return true;
                     }

                     ta = tb;
                     ga = gb;
                  }
               } else {
                  ta = tb;
                  ga = gb;
               }
            }

            this.pendingEvent = false;
            this.pendingEventTime = Double.NaN;
            return false;
         }
      } catch (EventState.LocalMaxCountExceededException var25) {
         throw var25.getException();
      }
   }

   public double getEventTime() {
      return this.pendingEvent ? this.pendingEventTime : (this.forward ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
   }

   public void stepAccepted(double t, double[] y) {
      this.t0 = t;
      this.g0 = this.handler.g(t, y);
      if (this.pendingEvent && FastMath.abs(this.pendingEventTime - t) <= this.convergence) {
         this.previousEventTime = t;
         this.g0Positive = this.increasing;
         this.nextAction = this.handler.eventOccurred(t, y, !(this.increasing ^ this.forward));
      } else {
         this.g0Positive = this.g0 >= 0.0D;
         this.nextAction = EventHandler.Action.CONTINUE;
      }

   }

   public boolean stop() {
      return this.nextAction == EventHandler.Action.STOP;
   }

   public boolean reset(double t, double[] y) {
      if (this.pendingEvent && FastMath.abs(this.pendingEventTime - t) <= this.convergence) {
         if (this.nextAction == EventHandler.Action.RESET_STATE) {
            this.handler.resetState(t, y);
         }

         this.pendingEvent = false;
         this.pendingEventTime = Double.NaN;
         return this.nextAction == EventHandler.Action.RESET_STATE || this.nextAction == EventHandler.Action.RESET_DERIVATIVES;
      } else {
         return false;
      }
   }

   private static class LocalMaxCountExceededException extends RuntimeException {
      private static final long serialVersionUID = 20120901L;
      private final MaxCountExceededException wrapped;

      public LocalMaxCountExceededException(MaxCountExceededException exception) {
         this.wrapped = exception;
      }

      public MaxCountExceededException getException() {
         return this.wrapped;
      }
   }
}
