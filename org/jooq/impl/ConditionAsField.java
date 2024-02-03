package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class ConditionAsField extends AbstractFunction<Boolean> {
   private static final long serialVersionUID = -5921673852489483721L;
   private final Condition condition;

   ConditionAsField(Condition condition) {
      super(condition.toString(), SQLDataType.BOOLEAN);
      this.condition = condition;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case FIREBIRD:
         return DSL.when(this.condition, (Field)DSL.inline(true)).when(DSL.not(this.condition), (Field)DSL.inline(false)).otherwise((Field)DSL.inline((Boolean)null));
      case DERBY:
      case H2:
      case HSQLDB:
      case MARIADB:
      case MYSQL:
      case POSTGRES:
      case SQLITE:
      default:
         return DSL.sql("({0})", this.condition);
      }
   }
}
