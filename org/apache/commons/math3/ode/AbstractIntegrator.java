package org.apache.commons.math3.ode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.ode.events.EventHandler;
import org.apache.commons.math3.ode.events.EventState;
import org.apache.commons.math3.ode.sampling.AbstractStepInterpolator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Incrementor;
import org.apache.commons.math3.util.Precision;

public abstract class AbstractIntegrator implements FirstOrderIntegrator {
   protected Collection<StepHandler> stepHandlers;
   protected double stepStart;
   protected double stepSize;
   protected boolean isLastStep;
   protected boolean resetOccurred;
   private Collection<EventState> eventsStates;
   private boolean statesInitialized;
   private final String name;
   private Incrementor evaluations;
   private transient ExpandableStatefulODE expandable;

   public AbstractIntegrator(String name) {
      this.name = name;
      this.stepHandlers = new ArrayList();
      this.stepStart = Double.NaN;
      this.stepSize = Double.NaN;
      this.eventsStates = new ArrayList();
      this.statesInitialized = false;
      this.evaluations = new Incrementor();
      this.setMaxEvaluations(-1);
      this.evaluations.resetCount();
   }

   protected AbstractIntegrator() {
      this((String)null);
   }

   public String getName() {
      return this.name;
   }

   public void addStepHandler(StepHandler handler) {
      this.stepHandlers.add(handler);
   }

   public Collection<StepHandler> getStepHandlers() {
      return Collections.unmodifiableCollection(this.stepHandlers);
   }

   public void clearStepHandlers() {
      this.stepHandlers.clear();
   }

   public void addEventHandler(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount) {
      this.addEventHandler(handler, maxCheckInterval, convergence, maxIterationCount, new BracketingNthOrderBrentSolver(convergence, 5));
   }

   public void addEventHandler(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
      this.eventsStates.add(new EventState(handler, maxCheckInterval, convergence, maxIterationCount, solver));
   }

   public Collection<EventHandler> getEventHandlers() {
      List<EventHandler> list = new ArrayList();
      Iterator i$ = this.eventsStates.iterator();

      while(i$.hasNext()) {
         EventState state = (EventState)i$.next();
         list.add(state.getEventHandler());
      }

      return Collections.unmodifiableCollection(list);
   }

   public void clearEventHandlers() {
      this.eventsStates.clear();
   }

   public double getCurrentStepStart() {
      return this.stepStart;
   }

   public double getCurrentSignedStepsize() {
      return this.stepSize;
   }

   public void setMaxEvaluations(int maxEvaluations) {
      this.evaluations.setMaximalCount(maxEvaluations < 0 ? Integer.MAX_VALUE : maxEvaluations);
   }

   public int getMaxEvaluations() {
      return this.evaluations.getMaximalCount();
   }

   public int getEvaluations() {
      return this.evaluations.getCount();
   }

   protected void initIntegration(double t0, double[] y0, double t) {
      this.evaluations.resetCount();
      Iterator i$ = this.eventsStates.iterator();

      while(i$.hasNext()) {
         EventState state = (EventState)i$.next();
         state.getEventHandler().init(t0, y0, t);
      }

      i$ = this.stepHandlers.iterator();

      while(i$.hasNext()) {
         StepHandler handler = (StepHandler)i$.next();
         handler.init(t0, y0, t);
      }

      this.setStateInitialized(false);
   }

   protected void setEquations(ExpandableStatefulODE equations) {
      this.expandable = equations;
   }

   protected ExpandableStatefulODE getExpandable() {
      return this.expandable;
   }

   protected Incrementor getEvaluationsCounter() {
      return this.evaluations;
   }

   public double integrate(FirstOrderDifferentialEquations equations, double t0, double[] y0, double t, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
      if (y0.length != equations.getDimension()) {
         throw new DimensionMismatchException(y0.length, equations.getDimension());
      } else if (y.length != equations.getDimension()) {
         throw new DimensionMismatchException(y.length, equations.getDimension());
      } else {
         ExpandableStatefulODE expandableODE = new ExpandableStatefulODE(equations);
         expandableODE.setTime(t0);
         expandableODE.setPrimaryState(y0);
         this.integrate(expandableODE, t);
         System.arraycopy(expandableODE.getPrimaryState(), 0, y, 0, y.length);
         return expandableODE.getTime();
      }
   }

   public abstract void integrate(ExpandableStatefulODE var1, double var2) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;

   public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
      this.evaluations.incrementCount();
      this.expandable.computeDerivatives(t, y, yDot);
   }

   protected void setStateInitialized(boolean stateInitialized) {
      this.statesInitialized = stateInitialized;
   }

   protected double acceptStep(AbstractStepInterpolator interpolator, double[] y, double[] yDot, double tEnd) throws MaxCountExceededException, DimensionMismatchException, NoBracketingException {
      double previousT = interpolator.getGlobalPreviousTime();
      double currentT = interpolator.getGlobalCurrentTime();
      if (!this.statesInitialized) {
         Iterator i$ = this.eventsStates.iterator();

         while(i$.hasNext()) {
            EventState state = (EventState)i$.next();
            state.reinitializeBegin(interpolator);
         }

         this.statesInitialized = true;
      }

      final int orderingSign = interpolator.isForward() ? 1 : -1;
      SortedSet<EventState> occuringEvents = new TreeSet(new Comparator<EventState>() {
         public int compare(EventState es0, EventState es1) {
            return orderingSign * Double.compare(es0.getEventTime(), es1.getEventTime());
         }
      });
      Iterator iterator = this.eventsStates.iterator();

      EventState currentEvent;
      while(iterator.hasNext()) {
         currentEvent = (EventState)iterator.next();
         if (currentEvent.evaluateStep(interpolator)) {
            occuringEvents.add(currentEvent);
         }
      }

      while(!occuringEvents.isEmpty()) {
         iterator = occuringEvents.iterator();
         currentEvent = (EventState)iterator.next();
         iterator.remove();
         double eventT = currentEvent.getEventTime();
         interpolator.setSoftPreviousTime(previousT);
         interpolator.setSoftCurrentTime(eventT);
         interpolator.setInterpolatedTime(eventT);
         double[] eventYPrimary = (double[])interpolator.getInterpolatedState().clone();
         double[] eventYComplete = new double[y.length];
         this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), eventYComplete);
         int index = 0;
         EquationsMapper[] arr$ = this.expandable.getSecondaryMappers();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            EquationsMapper secondary = arr$[i$];
            secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index++), eventYComplete);
         }

         Iterator i$;
         EventState state;
         for(i$ = this.eventsStates.iterator(); i$.hasNext(); this.isLastStep = this.isLastStep || state.stop()) {
            state = (EventState)i$.next();
            state.stepAccepted(eventT, eventYPrimary);
         }

         i$ = this.stepHandlers.iterator();

         while(i$.hasNext()) {
            StepHandler handler = (StepHandler)i$.next();
            handler.handleStep(interpolator, this.isLastStep);
         }

         if (this.isLastStep) {
            System.arraycopy(eventYComplete, 0, y, 0, y.length);
            return eventT;
         }

         boolean needReset = false;

         EventState state;
         for(Iterator i$ = this.eventsStates.iterator(); i$.hasNext(); needReset = needReset || state.reset(eventT, eventYComplete)) {
            state = (EventState)i$.next();
         }

         if (needReset) {
            interpolator.setInterpolatedTime(eventT);
            System.arraycopy(eventYComplete, 0, y, 0, y.length);
            this.computeDerivatives(eventT, y, yDot);
            this.resetOccurred = true;
            return eventT;
         }

         previousT = eventT;
         interpolator.setSoftPreviousTime(eventT);
         interpolator.setSoftCurrentTime(currentT);
         if (currentEvent.evaluateStep(interpolator)) {
            occuringEvents.add(currentEvent);
         }
      }

      interpolator.setInterpolatedTime(currentT);
      double[] currentY = interpolator.getInterpolatedState();

      Iterator i$;
      EventState state;
      for(i$ = this.eventsStates.iterator(); i$.hasNext(); this.isLastStep = this.isLastStep || state.stop()) {
         state = (EventState)i$.next();
         state.stepAccepted(currentT, currentY);
      }

      this.isLastStep = this.isLastStep || Precision.equals(currentT, tEnd, 1);
      i$ = this.stepHandlers.iterator();

      while(i$.hasNext()) {
         StepHandler handler = (StepHandler)i$.next();
         handler.handleStep(interpolator, this.isLastStep);
      }

      return currentT;
   }

   protected void sanityChecks(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException {
      double threshold = 1000.0D * FastMath.ulp(FastMath.max(FastMath.abs(equations.getTime()), FastMath.abs(t)));
      double dt = FastMath.abs(equations.getTime() - t);
      if (dt <= threshold) {
         throw new NumberIsTooSmallException(LocalizedFormats.TOO_SMALL_INTEGRATION_INTERVAL, dt, threshold, false);
      }
   }
}
