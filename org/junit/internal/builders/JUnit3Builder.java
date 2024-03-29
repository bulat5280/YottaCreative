package org.junit.internal.builders;

import junit.framework.TestCase;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class JUnit3Builder extends RunnerBuilder {
   public Runner runnerForClass(Class<?> testClass) throws Throwable {
      return this.isPre4Test(testClass) ? new JUnit38ClassRunner(testClass) : null;
   }

   boolean isPre4Test(Class<?> testClass) {
      return TestCase.class.isAssignableFrom(testClass);
   }
}
