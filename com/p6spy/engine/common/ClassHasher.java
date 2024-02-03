package com.p6spy.engine.common;

public class ClassHasher implements Hasher {
   public int getHashCode(Object object) {
      return object.getClass().hashCode();
   }
}
