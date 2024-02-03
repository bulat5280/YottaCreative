package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;

final class Alias<Q extends QueryPart> extends AbstractQueryPart {
   private static final long serialVersionUID = -2456848365524191614L;
   private static final Clause[] CLAUSES_TABLE_REFERENCE;
   private static final Clause[] CLAUSES_TABLE_ALIAS;
   private static final Clause[] CLAUSES_FIELD_REFERENCE;
   private static final Clause[] CLAUSES_FIELD_ALIAS;
   private final Q wrapped;
   private final String alias;
   private final String[] fieldAliases;
   private final boolean wrapInParentheses;

   Alias(Q wrapped, String alias) {
      this(wrapped, alias, (String[])null, false);
   }

   Alias(Q wrapped, String alias, boolean wrapInParentheses) {
      this(wrapped, alias, (String[])null, wrapInParentheses);
   }

   Alias(Q wrapped, String alias, String[] fieldAliases) {
      this(wrapped, alias, fieldAliases, false);
   }

   Alias(Q wrapped, String alias, String[] fieldAliases, boolean wrapInParentheses) {
      this.wrapped = wrapped;
      this.alias = alias;
      this.fieldAliases = fieldAliases;
      this.wrapInParentheses = wrapInParentheses;
   }

   final Q wrapped() {
      return this.wrapped;
   }

   public final void accept(Context<?> context) {
      if (!context.declareAliases() || !context.declareFields() && !context.declareTables()) {
         context.literal(this.alias);
      } else {
         context.declareAliases(false);
         SQLDialect family = context.family();
         boolean emulatedDerivedColumnList = false;
         if (this.fieldAliases == null || !Arrays.asList(SQLDialect.CUBRID, SQLDialect.FIREBIRD).contains(family) || !(this.wrapped instanceof TableImpl) && !(this.wrapped instanceof CommonTableExpressionImpl)) {
            if (this.fieldAliases != null && Arrays.asList(SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(family)) {
               emulatedDerivedColumnList = true;
               SelectFieldList fields = new SelectFieldList();
               String[] var5 = this.fieldAliases;
               int var6 = var5.length;
               int var7 = 0;

               while(var7 < var6) {
                  String fieldAlias = var5[var7];
                  switch(family) {
                  default:
                     fields.add(DSL.field("null").as(fieldAlias));
                     ++var7;
                  }
               }

               Select<Record> select = DSL.select((Collection)fields).where(new Condition[]{DSL.falseCondition()}).unionAll((Select)(this.wrapped instanceof Select ? (Select)this.wrapped : (this.wrapped instanceof DerivedTable ? ((DerivedTable)this.wrapped).query() : DSL.select((SelectField)DSL.field("*")).from(((Table)this.wrapped).as(this.alias)))));
               context.sql('(').formatIndentStart().formatNewLine().visit(select).formatIndentEnd().formatNewLine().sql(')');
            } else {
               this.toSQLWrapped(context);
            }
         } else {
            Select<Record> select = DSL.select((Collection)Tools.list(DSL.field("*"))).from(((Table)this.wrapped).as(this.alias));
            context.sql('(').formatIndentStart().formatNewLine().visit(select).formatIndentEnd().formatNewLine().sql(')');
         }

         toSQLAs(context);
         context.sql(' ');
         context.literal(this.alias);
         if (this.fieldAliases != null && !emulatedDerivedColumnList) {
            this.toSQLDerivedColumnList(context);
         } else {
            switch(family) {
            case HSQLDB:
            case POSTGRES:
               Object o = this.wrapped;
               if (context.declareTables() && o instanceof ArrayTable) {
                  ArrayTable table = (ArrayTable)o;
                  context.sql('(');
                  Tools.fieldNames(context, table.fields());
                  context.sql(')');
               }
            }
         }

         context.declareAliases(true);
      }

   }

   static void toSQLAs(Context<?> context) {
      if (Arrays.asList(SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES).contains(context.family())) {
         context.sql(' ').keyword("as");
      }

   }

   private void toSQLWrapped(Context<?> context) {
      context.sql(this.wrapInParentheses ? "(" : "").visit(this.wrapped).sql(this.wrapInParentheses ? ")" : "");
   }

   private void toSQLDerivedColumnList(Context<?> context) {
      String separator = "";
      context.sql('(');

      for(int i = 0; i < this.fieldAliases.length; ++i) {
         context.sql(separator);
         context.literal(this.fieldAliases[i]);
         separator = ", ";
      }

      context.sql(')');
   }

   public final Clause[] clauses(Context<?> ctx) {
      if (!ctx.declareFields() && !ctx.declareTables()) {
         return this.wrapped instanceof Table ? CLAUSES_TABLE_REFERENCE : CLAUSES_FIELD_REFERENCE;
      } else {
         return this.wrapped instanceof Table ? CLAUSES_TABLE_ALIAS : CLAUSES_FIELD_ALIAS;
      }
   }

   public final boolean declaresFields() {
      return true;
   }

   public final boolean declaresTables() {
      return true;
   }

   static {
      CLAUSES_TABLE_REFERENCE = new Clause[]{Clause.TABLE, Clause.TABLE_REFERENCE};
      CLAUSES_TABLE_ALIAS = new Clause[]{Clause.TABLE, Clause.TABLE_ALIAS};
      CLAUSES_FIELD_REFERENCE = new Clause[]{Clause.FIELD, Clause.FIELD_REFERENCE};
      CLAUSES_FIELD_ALIAS = new Clause[]{Clause.FIELD, Clause.FIELD_ALIAS};
   }
}
