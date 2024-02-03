package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.SQLDialect;
import org.jooq.SelectField;

final class IsDistinctFrom<T> extends AbstractCondition {
   private static final long serialVersionUID = 4568269684824736461L;
   private final Field<T> lhs;
   private final Field<T> rhs;
   private final Comparator comparator;
   private transient QueryPartInternal mySQLCondition;
   private transient QueryPartInternal sqliteCondition;
   private transient QueryPartInternal compareCondition;
   private transient QueryPartInternal caseExpression;

   IsDistinctFrom(Field<T> lhs, Field<T> rhs, Comparator comparator) {
      this.lhs = lhs;
      this.rhs = rhs;
      this.comparator = comparator;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      if (Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY).contains(configuration.family())) {
         return this.comparator == Comparator.IS_DISTINCT_FROM ? (QueryPartInternal)DSL.notExists(DSL.select((SelectField)this.lhs).intersect(DSL.select((SelectField)this.rhs))) : (QueryPartInternal)DSL.exists(DSL.select((SelectField)this.lhs).intersect(DSL.select((SelectField)this.rhs)));
      } else if (Arrays.asList().contains(configuration.family())) {
         if (this.caseExpression == null) {
            if (this.comparator == Comparator.IS_DISTINCT_FROM) {
               this.caseExpression = (QueryPartInternal)DSL.decode().when(this.lhs.isNull().and(this.rhs.isNull()), (Field)DSL.zero()).when(this.lhs.isNull().and(this.rhs.isNotNull()), (Field)DSL.one()).when(this.lhs.isNotNull().and(this.rhs.isNull()), (Field)DSL.one()).when(this.lhs.equal(this.rhs), (Field)DSL.zero()).otherwise((Field)DSL.one()).equal((Field)DSL.one());
            } else {
               this.caseExpression = (QueryPartInternal)DSL.decode().when(this.lhs.isNull().and(this.rhs.isNull()), (Field)DSL.one()).when(this.lhs.isNull().and(this.rhs.isNotNull()), (Field)DSL.zero()).when(this.lhs.isNotNull().and(this.rhs.isNull()), (Field)DSL.zero()).when(this.lhs.equal(this.rhs), (Field)DSL.one()).otherwise((Field)DSL.zero()).equal((Field)DSL.one());
            }
         }

         return this.caseExpression;
      } else if (Arrays.asList(SQLDialect.MARIADB, SQLDialect.MYSQL).contains(configuration.family())) {
         if (this.mySQLCondition == null) {
            this.mySQLCondition = (QueryPartInternal)((QueryPartInternal)(this.comparator == Comparator.IS_DISTINCT_FROM ? DSL.condition("{not}({0} <=> {1})", this.lhs, this.rhs) : DSL.condition("{0} <=> {1}", this.lhs, this.rhs)));
         }

         return this.mySQLCondition;
      } else if (SQLDialect.SQLITE == configuration.family()) {
         if (this.sqliteCondition == null) {
            this.sqliteCondition = (QueryPartInternal)((QueryPartInternal)(this.comparator == Comparator.IS_DISTINCT_FROM ? DSL.condition("{0} {is not} {1}", this.lhs, this.rhs) : DSL.condition("{0} {is} {1}", this.lhs, this.rhs)));
         }

         return this.sqliteCondition;
      } else {
         if (this.compareCondition == null) {
            this.compareCondition = new CompareCondition(this.lhs, this.rhs, this.comparator);
         }

         return this.compareCondition;
      }
   }
}
