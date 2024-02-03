package org.hamcrest.core;

import java.util.Arrays;
import java.util.Iterator;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class AnyOf<T> extends BaseMatcher<T> {
   private final Iterable<Matcher<? extends T>> matchers;

   public AnyOf(Iterable<Matcher<? extends T>> matchers) {
      this.matchers = matchers;
   }

   public boolean matches(Object o) {
      Iterator i$ = this.matchers.iterator();

      Matcher matcher;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         matcher = (Matcher)i$.next();
      } while(!matcher.matches(o));

      return true;
   }

   public void describeTo(Description description) {
      description.appendList("(", " or ", ")", this.matchers);
   }

   @Factory
   public static <T> Matcher<T> anyOf(Matcher<? extends T>... matchers) {
      return anyOf((Iterable)Arrays.asList(matchers));
   }

   @Factory
   public static <T> Matcher<T> anyOf(Iterable<Matcher<? extends T>> matchers) {
      return new AnyOf(matchers);
   }
}
