package org.jooq.impl;

import java.util.List;
import java.util.function.BiFunction;
import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.DerivedColumnList;
import org.jooq.DerivedColumnList1;
import org.jooq.DerivedColumnList10;
import org.jooq.DerivedColumnList11;
import org.jooq.DerivedColumnList12;
import org.jooq.DerivedColumnList13;
import org.jooq.DerivedColumnList14;
import org.jooq.DerivedColumnList15;
import org.jooq.DerivedColumnList16;
import org.jooq.DerivedColumnList17;
import org.jooq.DerivedColumnList18;
import org.jooq.DerivedColumnList19;
import org.jooq.DerivedColumnList2;
import org.jooq.DerivedColumnList20;
import org.jooq.DerivedColumnList21;
import org.jooq.DerivedColumnList22;
import org.jooq.DerivedColumnList3;
import org.jooq.DerivedColumnList4;
import org.jooq.DerivedColumnList5;
import org.jooq.DerivedColumnList6;
import org.jooq.DerivedColumnList7;
import org.jooq.DerivedColumnList8;
import org.jooq.DerivedColumnList9;
import org.jooq.Field;
import org.jooq.Select;

final class DerivedColumnListImpl extends AbstractQueryPart implements DerivedColumnList1, DerivedColumnList2, DerivedColumnList3, DerivedColumnList4, DerivedColumnList5, DerivedColumnList6, DerivedColumnList7, DerivedColumnList8, DerivedColumnList9, DerivedColumnList10, DerivedColumnList11, DerivedColumnList12, DerivedColumnList13, DerivedColumnList14, DerivedColumnList15, DerivedColumnList16, DerivedColumnList17, DerivedColumnList18, DerivedColumnList19, DerivedColumnList20, DerivedColumnList21, DerivedColumnList22, DerivedColumnList {
   private static final long serialVersionUID = -369633206858851863L;
   final String name;
   final String[] fieldNames;
   final BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction;

   DerivedColumnListImpl(String name, String[] fieldNames) {
      this.name = name;
      this.fieldNames = fieldNames;
      this.fieldNameFunction = null;
   }

   DerivedColumnListImpl(String name, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
      this.name = name;
      this.fieldNames = null;
      this.fieldNameFunction = fieldNameFunction;
   }

   public final CommonTableExpression as(Select select) {
      if (this.fieldNameFunction == null) {
         return new CommonTableExpressionImpl(this, select);
      } else {
         List<Field<?>> source = select.getSelect();
         String[] names = new String[source.size()];

         for(int i = 0; i < names.length; ++i) {
            names[i] = (String)this.fieldNameFunction.apply(source.get(i), i);
         }

         return new CommonTableExpressionImpl(new DerivedColumnListImpl(this.name, names), select);
      }
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(DSL.name(this.name));
      if (this.fieldNames != null && this.fieldNames.length > 0) {
         ctx.sql('(');

         for(int i = 0; i < this.fieldNames.length; ++i) {
            if (i > 0) {
               ctx.sql(", ");
            }

            ctx.visit(DSL.name(this.fieldNames[i]));
         }

         ctx.sql(')');
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }
}
