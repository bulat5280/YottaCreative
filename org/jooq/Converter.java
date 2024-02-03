package org.jooq;

import java.io.Serializable;
import java.util.function.Function;

public interface Converter<T, U> extends Serializable {
   U from(T var1);

   T to(U var1);

   Class<T> fromType();

   Class<U> toType();

   default Converter<U, T> inverse() {
      return Converters.inverse(this);
   }

   default <X> Converter<T, X> andThen(Converter<? super U, X> converter) {
      return Converters.of(this, converter);
   }

   static <T, U> Converter<T, U> of(final Class<T> fromType, final Class<U> toType, final Function<? super T, ? extends U> from, final Function<? super U, ? extends T> to) {
      return new Converter<T, U>() {
         private static final long serialVersionUID = 8782437631959970693L;

         public final U from(T t) {
            return from.apply(t);
         }

         public final T to(U u) {
            return to.apply(u);
         }

         public final Class<T> fromType() {
            return fromType;
         }

         public final Class<U> toType() {
            return toType;
         }
      };
   }

   static <T, U> Converter<T, U> ofNullable(Class<T> fromType, Class<U> toType, Function<? super T, ? extends U> from, Function<? super U, ? extends T> to) {
      return of(fromType, toType, (t) -> {
         return t == null ? null : from.apply(t);
      }, (u) -> {
         return u == null ? null : to.apply(u);
      });
   }
}
