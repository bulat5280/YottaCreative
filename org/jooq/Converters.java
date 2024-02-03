package org.jooq;

public class Converters<T, U> implements Converter<T, U> {
   private static final long serialVersionUID = -4307758248063822630L;
   final Converter[] chain;

   public static <T> Converter<T, T> identity(final Class<T> type) {
      return new Converter<T, T>() {
         private static final long serialVersionUID = -8331976721627671263L;

         public final T from(T t) {
            return t;
         }

         public final T to(T t) {
            return t;
         }

         public final Class<T> fromType() {
            return type;
         }

         public final Class<T> toType() {
            return type;
         }
      };
   }

   public static <T, U> Converter<T, U> of() {
      return new Converters(new Converter[0]);
   }

   public static <T, U> Converter<T, U> of(Converter<T, U> converter) {
      return new Converters(new Converter[]{converter});
   }

   public static <T, X1, U> Converter<T, U> of(Converter<T, ? extends X1> c1, Converter<? super X1, U> c2) {
      return new Converters(new Converter[]{c1, c2});
   }

   public static <T, X1, X2, U> Converter<T, U> of(Converter<T, ? extends X1> c1, Converter<? super X1, ? extends X2> c2, Converter<? super X2, U> c3) {
      return new Converters(new Converter[]{c1, c2, c3});
   }

   public static <T, X1, X2, X3, U> Converter<T, U> of(Converter<T, ? extends X1> c1, Converter<? super X1, ? extends X2> c2, Converter<? super X2, ? extends X3> c3, Converter<? super X3, U> c4) {
      return new Converters(new Converter[]{c1, c2, c3, c4});
   }

   public static <T, U> Converter<U, T> inverse(final Converter<T, U> converter) {
      return new Converter<U, T>() {
         private static final long serialVersionUID = -4307758248063822630L;

         public T from(U u) {
            return converter.to(u);
         }

         public U to(T t) {
            return converter.from(t);
         }

         public Class<U> fromType() {
            return converter.toType();
         }

         public Class<T> toType() {
            return converter.fromType();
         }

         public String toString() {
            return "InverseConverter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
         }
      };
   }

   Converters(Converter... chain) {
      this.chain = chain == null ? new Converter[0] : chain;
   }

   public final U from(T t) {
      Object result = t;

      for(int i = 0; i < this.chain.length; ++i) {
         result = this.chain[i].from(result);
      }

      return result;
   }

   public final T to(U u) {
      Object result = u;

      for(int i = this.chain.length - 1; i >= 0; --i) {
         result = this.chain[i].to(result);
      }

      return result;
   }

   public final Class<T> fromType() {
      return this.chain[0].fromType();
   }

   public final Class<U> toType() {
      return this.chain[this.chain.length - 1].toType();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      String separator = " -> ";
      sb.append("Converters [ ");
      sb.append(this.chain[0].fromType().getName());
      Converter[] var3 = this.chain;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Converter<?, ?> converter = var3[var5];
         sb.append(separator);
         sb.append(converter.toType().getName());
      }

      sb.append(" ]");
      return sb.toString();
   }
}
