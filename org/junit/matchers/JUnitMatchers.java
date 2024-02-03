package org.junit.matchers;

import org.hamcrest.Matcher;
import org.junit.internal.matchers.CombinableMatcher;
import org.junit.internal.matchers.Each;
import org.junit.internal.matchers.IsCollectionContaining;
import org.junit.internal.matchers.StringContains;

public class JUnitMatchers {
   public static <T> Matcher<Iterable<T>> hasItem(T element) {
      return IsCollectionContaining.hasItem(element);
   }

   public static <T> Matcher<Iterable<T>> hasItem(Matcher<? extends T> elementMatcher) {
      return IsCollectionContaining.hasItem(elementMatcher);
   }

   public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
      return IsCollectionContaining.hasItems(elements);
   }

   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? extends T>... elementMatchers) {
      return IsCollectionContaining.hasItems(elementMatchers);
   }

   public static <T> Matcher<Iterable<T>> everyItem(Matcher<T> elementMatcher) {
      return Each.each(elementMatcher);
   }

   public static Matcher<String> containsString(String substring) {
      return StringContains.containsString(substring);
   }

   public static <T> CombinableMatcher<T> both(Matcher<T> matcher) {
      return new CombinableMatcher(matcher);
   }

   public static <T> CombinableMatcher<T> either(Matcher<T> matcher) {
      return new CombinableMatcher(matcher);
   }
}
