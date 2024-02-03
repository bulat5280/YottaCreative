package org.apache.commons.math3.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.math3.exception.MaxCountExceededException;

public class IterationManager {
   private final Incrementor iterations;
   private final Collection<IterationListener> listeners;

   public IterationManager(int maxIterations) {
      this.iterations = new Incrementor(maxIterations);
      this.listeners = new CopyOnWriteArrayList();
   }

   public IterationManager(int maxIterations, Incrementor.MaxCountExceededCallback callBack) {
      this.iterations = new Incrementor(maxIterations, callBack);
      this.listeners = new CopyOnWriteArrayList();
   }

   public void addIterationListener(IterationListener listener) {
      this.listeners.add(listener);
   }

   public void fireInitializationEvent(IterationEvent e) {
      Iterator i$ = this.listeners.iterator();

      while(i$.hasNext()) {
         IterationListener l = (IterationListener)i$.next();
         l.initializationPerformed(e);
      }

   }

   public void fireIterationPerformedEvent(IterationEvent e) {
      Iterator i$ = this.listeners.iterator();

      while(i$.hasNext()) {
         IterationListener l = (IterationListener)i$.next();
         l.iterationPerformed(e);
      }

   }

   public void fireIterationStartedEvent(IterationEvent e) {
      Iterator i$ = this.listeners.iterator();

      while(i$.hasNext()) {
         IterationListener l = (IterationListener)i$.next();
         l.iterationStarted(e);
      }

   }

   public void fireTerminationEvent(IterationEvent e) {
      Iterator i$ = this.listeners.iterator();

      while(i$.hasNext()) {
         IterationListener l = (IterationListener)i$.next();
         l.terminationPerformed(e);
      }

   }

   public int getIterations() {
      return this.iterations.getCount();
   }

   public int getMaxIterations() {
      return this.iterations.getMaximalCount();
   }

   public void incrementIterationCount() throws MaxCountExceededException {
      this.iterations.incrementCount();
   }

   public void removeIterationListener(IterationListener listener) {
      this.listeners.remove(listener);
   }

   public void resetIterationCount() {
      this.iterations.resetCount();
   }
}
