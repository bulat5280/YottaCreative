package org.jooq.impl;

import java.util.List;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;

final class CommonTableExpressionImpl<R extends Record> extends AbstractTable<R> implements CommonTableExpression<R> {
   private static final long serialVersionUID = 2520235151216758958L;
   private final DerivedColumnListImpl name;
   private final Select<R> select;
   private final Fields<R> fields;

   CommonTableExpressionImpl(DerivedColumnListImpl name, Select<R> select) {
      super(name.name);
      this.name = name;
      this.select = select;
      this.fields = this.fields1();
   }

   public final Class<? extends R> getRecordType() {
      return this.select.getRecordType();
   }

   public final Table<R> as(String alias) {
      return new TableAlias(this, alias);
   }

   public final Table<R> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases);
   }

   public final boolean declaresCTE() {
      return true;
   }

   public final void accept(Context<?> ctx) {
      if (ctx.declareCTE()) {
         boolean subquery = ctx.subquery();
         ctx.visit(this.name).sql(' ').keyword("as").sql(" (").subquery(true).formatIndentStart().formatNewLine().visit(this.select).formatIndentEnd().formatNewLine().subquery(subquery).sql(')');
      } else {
         ctx.visit(DSL.name(this.name.name));
      }

   }

   final Fields<R> fields0() {
      return this.fields;
   }

   final Fields<R> fields1() {
      List<Field<?>> s = this.select.getSelect();
      Field<?>[] f = new Field[s.size()];

      for(int i = 0; i < f.length; ++i) {
         f[i] = DSL.field(DSL.name(this.name.name, this.name.fieldNames.length > 0 ? this.name.fieldNames[i] : ((Field)s.get(i)).getName()), ((Field)s.get(i)).getDataType());
      }

      Fields<R> result = new Fields(f);
      return result;
   }
}
