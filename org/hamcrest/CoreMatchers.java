package org.hamcrest;

import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;

public class CoreMatchers {
   public static <T> Matcher<T> is(Matcher<T> matcher) {
      return Is.is(matcher);
   }

   public static <T> Matcher<T> is(T value) {
      return Is.is(value);
   }

   public static Matcher<Object> is(Class<?> type) {
      return Is.is(type);
   }

   public static <T> Matcher<T> not(Matcher<T> matcher) {
      return IsNot.not(matcher);
   }

   public static <T> Matcher<T> not(T value) {
      return IsNot.not(value);
   }

   public static <T> Matcher<T> equalTo(T operand) {
      return IsEqual.equalTo(operand);
   }

   public static Matcher<Object> instanceOf(Class<?> type) {
      return IsInstanceOf.instanceOf(type);
   }

   public static <T> Matcher<T> allOf(Matcher<? extends T>... matchers) {
      return AllOf.allOf(matchers);
   }

   public static <T> Matcher<T> allOf(Iterable<Matcher<? extends T>> matchers) {
      return AllOf.allOf(matchers);
   }

   public static <T> Matcher<T> anyOf(Matcher<? extends T>... matchers) {
      return AnyOf.anyOf(matchers);
   }

   public static <T> Matcher<T> anyOf(Iterable<Matcher<? extends T>> matchers) {
      return AnyOf.anyOf(matchers);
   }

   public static <T> Matcher<T> sameInstance(T object) {
      return IsSame.sameInstance(object);
   }

   public static <T> Matcher<T> anything() {
      return IsAnything.anything();
   }

   public static <T> Matcher<T> anything(String description) {
      return IsAnything.anything(description);
   }

   public static <T> Matcher<T> any(Class<T> type) {
      return IsAnything.any(type);
   }

   public static <T> Matcher<T> nullValue() {
      return IsNull.nullValue();
   }

   public static <T> Matcher<T> nullValue(Class<T> type) {
      return IsNull.nullValue(type);
   }

   public static <T> Matcher<T> notNullValue() {
      return IsNull.notNullValue();
   }

   public static <T> Matcher<T> notNullValue(Class<T> type) {
      return IsNull.notNullValue(type);
   }

   public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... values) {
      return DescribedAs.describedAs(description, matcher, values);
   }
}
