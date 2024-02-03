package org.apache.commons.math3.ode.events;

import java.util.Arrays;

public class EventFilter implements EventHandler {
   private static final int HISTORY_SIZE = 100;
   private final EventHandler rawHandler;
   private final FilterType filter;
   private final Transformer[] transformers;
   private final double[] updates;
   private boolean forward;
   private double extremeT;

   public EventFilter(EventHandler rawHandler, FilterType filter) {
      this.rawHandler = rawHandler;
      this.filter = filter;
      this.transformers = new Transformer[100];
      this.updates = new double[100];
   }

   public void init(double t0, double[] y0, double t) {
      this.rawHandler.init(t0, y0, t);
      this.forward = t >= t0;
      this.extremeT = this.forward ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
      Arrays.fill(this.transformers, Transformer.UNINITIALIZED);
      Arrays.fill(this.updates, this.extremeT);
   }

   public double g(double t, double[] y) {
      double rawG = this.rawHandler.g(t, y);
      int i;
      Transformer next;
      if (this.forward) {
         i = this.transformers.length - 1;
         if (this.extremeT < t) {
            next = this.transformers[i];
            Transformer next = this.filter.selectTransformer(next, rawG, this.forward);
            if (next != next) {
               System.arraycopy(this.updates, 1, this.updates, 0, i);
               System.arraycopy(this.transformers, 1, this.transformers, 0, i);
               this.updates[i] = this.extremeT;
               this.transformers[i] = next;
            }

            this.extremeT = t;
            return next.transformed(rawG);
         } else {
            for(int i = i; i > 0; --i) {
               if (this.updates[i] <= t) {
                  return this.transformers[i].transformed(rawG);
               }
            }

            return this.transformers[0].transformed(rawG);
         }
      } else if (t < this.extremeT) {
         Transformer previous = this.transformers[0];
         next = this.filter.selectTransformer(previous, rawG, this.forward);
         if (next != previous) {
            System.arraycopy(this.updates, 0, this.updates, 1, this.updates.length - 1);
            System.arraycopy(this.transformers, 0, this.transformers, 1, this.transformers.length - 1);
            this.updates[0] = this.extremeT;
            this.transformers[0] = next;
         }

         this.extremeT = t;
         return next.transformed(rawG);
      } else {
         for(i = 0; i < this.updates.length - 1; ++i) {
            if (t <= this.updates[i]) {
               return this.transformers[i].transformed(rawG);
            }
         }

         return this.transformers[this.updates.length - 1].transformed(rawG);
      }
   }

   public EventHandler.Action eventOccurred(double t, double[] y, boolean increasing) {
      return this.rawHandler.eventOccurred(t, y, this.filter.getTriggeredIncreasing());
   }

   public void resetState(double t, double[] y) {
      this.rawHandler.resetState(t, y);
   }
}
