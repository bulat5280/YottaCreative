package org.jooq.impl;

import java.util.Arrays;
import java.util.function.BiFunction;
import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.tools.StringUtils;

final class NameImpl extends AbstractQueryPart implements Name {
   private static final long serialVersionUID = 8562325639223483938L;
   private final String[] qualifiedName;

   NameImpl(String[] qualifiedName) {
      this.qualifiedName = nonEmpty(qualifiedName);
   }

   private static final String[] nonEmpty(String[] qualifiedName) {
      int nulls = 0;

      int i;
      for(i = 0; i < qualifiedName.length; ++i) {
         if (StringUtils.isEmpty(qualifiedName[i])) {
            ++nulls;
         }
      }

      String[] result;
      if (nulls > 0) {
         result = new String[qualifiedName.length - nulls];

         for(i = qualifiedName.length - 1; i >= 0; --i) {
            if (StringUtils.isEmpty(qualifiedName[i])) {
               --nulls;
            } else {
               result[i - nulls] = qualifiedName[i];
            }
         }
      } else {
         result = (String[])qualifiedName.clone();
      }

      return result;
   }

   public final void accept(Context<?> ctx) {
      if (ctx.qualify()) {
         String separator = "";
         String[] var3 = this.qualifiedName;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            ctx.sql(separator).literal(name);
            separator = ".";
         }
      } else {
         ctx.literal(this.last());
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final String first() {
      return this.qualifiedName.length > 0 ? this.qualifiedName[0] : null;
   }

   public final String last() {
      return this.qualifiedName.length > 0 ? this.qualifiedName[this.qualifiedName.length - 1] : null;
   }

   public final boolean qualified() {
      return this.qualifiedName.length > 1;
   }

   public final String[] getName() {
      return this.qualifiedName;
   }

   public final WindowDefinition as(WindowSpecification window) {
      return new WindowDefinitionImpl(this, window);
   }

   public final <R extends Record> CommonTableExpression<R> as(Select<R> select) {
      return this.fields().as(select);
   }

   public final DerivedColumnListImpl fields(String... fieldNames) {
      if (this.qualifiedName.length != 1) {
         throw new IllegalStateException("Cannot create a DerivedColumnList from a qualified name : " + Arrays.asList(this.qualifiedName));
      } else {
         return new DerivedColumnListImpl(this.first(), fieldNames);
      }
   }

   public final DerivedColumnListImpl fields(java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return this.fields((f, i) -> {
         return (String)fieldNameFunction.apply(f);
      });
   }

   public final DerivedColumnListImpl fields(BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
      return new DerivedColumnListImpl(this.first(), fieldNameFunction);
   }

   public final DerivedColumnListImpl fields(String fieldName1) {
      return this.fields(fieldName1);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2) {
      return this.fields(fieldName1, fieldName2);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3) {
      return this.fields(fieldName1, fieldName2, fieldName3);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19, String fieldName20) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19, String fieldName20, String fieldName21) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20, fieldName21);
   }

   public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19, String fieldName20, String fieldName21, String fieldName22) {
      return this.fields(fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20, fieldName21, fieldName22);
   }

   public int hashCode() {
      return Arrays.hashCode(this.getName());
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else {
         return that instanceof NameImpl ? Arrays.equals(this.getName(), ((NameImpl)that).getName()) : super.equals(that);
      }
   }

   public final boolean equalsIgnoreCase(Name that) {
      if (this == that) {
         return true;
      } else {
         String[] thisName = this.getName();
         String[] thatName = that.getName();
         if (thisName.length != thatName.length) {
            return false;
         } else {
            for(int i = 0; i < thisName.length; ++i) {
               if (thisName[i] != null || thatName[i] != null) {
                  if (thisName[i] == null || thatName[i] == null) {
                     return false;
                  }

                  if (!thisName[i].equalsIgnoreCase(thatName[i])) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }
}
