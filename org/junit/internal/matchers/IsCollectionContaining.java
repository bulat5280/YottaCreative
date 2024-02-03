package org.junit.internal.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;

public class IsCollectionContaining<T> extends TypeSafeMatcher<Iterable<T>> {
   private final Matcher<? extends T> elementMatcher;

   public IsCollectionContaining(Matcher<? extends T> elementMatcher) {
      this.elementMatcher = elementMatcher;
   }

   public boolean matchesSafely(Iterable<T> collection) {
      Iterator i$ = collection.iterator();

      Object item;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         item = i$.next();
      } while(!this.elementMatcher.matches(item));

      return true;
   }

   public void describeTo(Description description) {
      description.appendText("a collection containing ").appendDescriptionOf(this.elementMatcher);
   }

   @Factory
   public static <T> Matcher<Iterable<T>> hasItem(Matcher<? extends T> elementMatcher) {
      return new IsCollectionContaining(elementMatcher);
   }

   @Factory
   public static <T> Matcher<Iterable<T>> hasItem(T element) {
      return hasItem(IsEqual.equalTo(element));
   }

   @Factory
   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? extends T>... elementMatchers) {
      Collection<Matcher<? extends Iterable<T>>> all = new ArrayList(elementMatchers.length);
      Matcher[] arr$ = elementMatchers;
      int len$ = elementMatchers.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Matcher<? extends T> elementMatcher = arr$[i$];
         all.add(hasItem(elementMatcher));
      }

      return AllOf.allOf((Iterable)all);
   }

   @Factory
   public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
      Collection<Matcher<? extends Iterable<T>>> all = new ArrayList(elements.length);
      Object[] arr$ = elements;
      int len$ = elements.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         T element = arr$[i$];
         all.add(hasItem(element));
      }

      return AllOf.allOf((Iterable)all);
   }
}
