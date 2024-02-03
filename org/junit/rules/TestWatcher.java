package org.junit.rules;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class TestWatcher implements TestRule {
   public Statement apply(final Statement base, final Description description) {
      return new Statement() {
         public void evaluate() throws Throwable {
            TestWatcher.this.starting(description);

            try {
               base.evaluate();
               TestWatcher.this.succeeded(description);
            } catch (AssumptionViolatedException var6) {
               throw var6;
            } catch (Throwable var7) {
               TestWatcher.this.failed(var7, description);
               throw var7;
            } finally {
               TestWatcher.this.finished(description);
            }

         }
      };
   }

   protected void succeeded(Description description) {
   }

   protected void failed(Throwable e, Description description) {
   }

   protected void starting(Description description) {
   }

   protected void finished(Description description) {
   }
}
