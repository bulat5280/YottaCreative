package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public interface SelectQuery<R extends Record> extends Select<R>, ConditionProvider {
   @Support
   void addSelect(SelectField<?>... var1);

   @Support
   void addSelect(Collection<? extends SelectField<?>> var1);

   @Support
   void setDistinct(boolean var1);

   @Support({SQLDialect.POSTGRES})
   void addDistinctOn(SelectField<?>... var1);

   @Support({SQLDialect.POSTGRES})
   void addDistinctOn(Collection<? extends SelectField<?>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void setInto(Table<?> var1);

   @Support
   void addFrom(TableLike<?> var1);

   @Support
   void addFrom(TableLike<?>... var1);

   @Support
   void addFrom(Collection<? extends TableLike<?>> var1);

   @Support
   void addJoin(TableLike<?> var1, Condition... var2);

   @Support
   void addJoin(TableLike<?> var1, JoinType var2, Condition... var3);

   @Support
   void addJoinUsing(TableLike<?> var1, Collection<? extends Field<?>> var2);

   @Support
   void addJoinUsing(TableLike<?> var1, JoinType var2, Collection<? extends Field<?>> var3);

   @Support
   void addJoinOnKey(TableLike<?> var1, JoinType var2) throws DataAccessException;

   @Support
   void addJoinOnKey(TableLike<?> var1, JoinType var2, TableField<?, ?>... var3) throws DataAccessException;

   @Support
   void addJoinOnKey(TableLike<?> var1, JoinType var2, ForeignKey<?, ?> var3);

   @Support
   void addGroupBy(GroupField... var1);

   @Support
   void addGroupBy(Collection<? extends GroupField> var1);

   @Support
   void addHaving(Condition... var1);

   @Support
   void addHaving(Collection<? extends Condition> var1);

   @Support
   void addHaving(Operator var1, Condition... var2);

   @Support
   void addHaving(Operator var1, Collection<? extends Condition> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   void addWindow(WindowDefinition... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   void addWindow(Collection<? extends WindowDefinition> var1);

   @Support
   void addHint(String var1);

   @Support
   void addOption(String var1);

   @Support({SQLDialect.CUBRID})
   void addConnectBy(Condition var1);

   @Support({SQLDialect.CUBRID})
   void addConnectByNoCycle(Condition var1);

   @Support({SQLDialect.CUBRID})
   void setConnectByStartWith(Condition var1);

   @Support
   void addConditions(Condition... var1);

   @Support
   void addConditions(Collection<? extends Condition> var1);

   @Support
   void addConditions(Operator var1, Condition... var2);

   @Support
   void addConditions(Operator var1, Collection<? extends Condition> var2);

   @Support
   void addOrderBy(Field<?>... var1);

   @Support
   void addOrderBy(SortField<?>... var1);

   @Support
   void addOrderBy(Collection<? extends SortField<?>> var1);

   @Support
   void addOrderBy(int... var1);

   @Support({SQLDialect.CUBRID})
   void setOrderBySiblings(boolean var1);

   @Support
   void addSeekAfter(Field<?>... var1);

   @Support
   void addSeekAfter(Collection<? extends Field<?>> var1);

   @Support
   void addSeekBefore(Field<?>... var1);

   @Support
   void addSeekBefore(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addOffset(int var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addOffset(Param<Integer> var1);

   @Support
   void addLimit(int var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addLimit(Param<Integer> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addLimit(int var1, int var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addLimit(Param<Integer> var1, int var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addLimit(int var1, Param<Integer> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void addLimit(Param<Integer> var1, Param<Integer> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   void setForUpdate(boolean var1);

   @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
   void setForUpdateOf(Field<?>... var1);

   @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
   void setForUpdateOf(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   void setForUpdateOf(Table<?>... var1);

   @Support({SQLDialect.POSTGRES})
   void setForUpdateNoWait();

   @Support({SQLDialect.POSTGRES_9_5})
   void setForUpdateSkipLocked();

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   void setForShare(boolean var1);
}
