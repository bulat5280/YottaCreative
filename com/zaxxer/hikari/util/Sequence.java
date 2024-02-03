package com.zaxxer.hikari.util;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public interface Sequence {
   void increment();

   long get();

   public static final class Java8Sequence extends LongAdder implements Sequence {
      public long get() {
         return this.sum();
      }
   }

   public static final class Java7Sequence extends AtomicLong implements Sequence {
      public void increment() {
         this.incrementAndGet();
      }
   }

   public static final class Factory {
      public static Sequence create() {
         return (Sequence)(!Boolean.getBoolean("com.zaxxer.hikari.useAtomicLongSequence") ? new Sequence.Java8Sequence() : new Sequence.Java7Sequence());
      }
   }
}
