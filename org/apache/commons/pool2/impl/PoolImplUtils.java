package org.apache.commons.pool2.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.apache.commons.pool2.PooledObjectFactory;

class PoolImplUtils {
   static Class<?> getFactoryType(Class<? extends PooledObjectFactory> factory) {
      return (Class)getGenericType(PooledObjectFactory.class, factory);
   }

   private static <T> Object getGenericType(Class<T> type, Class<? extends T> clazz) {
      Type[] interfaces = clazz.getGenericInterfaces();
      Type[] arr$ = interfaces;
      int len$ = interfaces.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Type iface = arr$[i$];
         if (iface instanceof ParameterizedType) {
            ParameterizedType pi = (ParameterizedType)iface;
            if (pi.getRawType() instanceof Class && type.isAssignableFrom((Class)pi.getRawType())) {
               return getTypeParameter(clazz, pi.getActualTypeArguments()[0]);
            }
         }
      }

      Class<? extends T> superClazz = clazz.getSuperclass();
      Object result = getGenericType(type, superClazz);
      if (result instanceof Class) {
         return result;
      } else if (result instanceof Integer) {
         ParameterizedType superClassType = (ParameterizedType)clazz.getGenericSuperclass();
         return getTypeParameter(clazz, superClassType.getActualTypeArguments()[(Integer)result]);
      } else {
         return null;
      }
   }

   private static Object getTypeParameter(Class<?> clazz, Type argType) {
      if (argType instanceof Class) {
         return argType;
      } else {
         TypeVariable<?>[] tvs = clazz.getTypeParameters();

         for(int i = 0; i < tvs.length; ++i) {
            if (tvs[i].equals(argType)) {
               return i;
            }
         }

         return null;
      }
   }
}
