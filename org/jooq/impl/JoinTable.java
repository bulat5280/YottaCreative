package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.JoinType;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnConditionStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.exception.DataAccessException;

final class JoinTable extends AbstractTable<Record> implements TableOptionalOnStep<Record>, TableOnConditionStep<Record> {
   private static final long serialVersionUID = 8377996833996498178L;
   private static final Clause[] CLAUSES;
   private final Table<?> lhs;
   private final Table<?> rhs;
   private final QueryPartList<Field<?>> rhsPartitionBy;
   private final JoinType type;
   private final ConditionProviderImpl condition;
   private final QueryPartList<Field<?>> using;

   JoinTable(TableLike<?> lhs, TableLike<?> rhs, JoinType type) {
      super("join");
      this.lhs = lhs.asTable();
      this.rhs = rhs.asTable();
      this.rhsPartitionBy = new QueryPartList();
      this.type = type;
      this.condition = new ConditionProviderImpl();
      this.using = new QueryPartList();
   }

   public final List<ForeignKey<Record, ?>> getReferences() {
      List<ForeignKey<?, ?>> result = new ArrayList();
      result.addAll(this.lhs.getReferences());
      result.addAll(this.rhs.getReferences());
      return result;
   }

   public final void accept(Context<?> ctx) {
      JoinType translatedType = this.translateType(ctx);
      Clause translatedClause = this.translateClause(translatedType);
      String keyword = translatedType.toSQL();
      if (translatedType == JoinType.CROSS_APPLY && ctx.family() == SQLDialect.POSTGRES) {
         keyword = "cross join lateral";
      } else if (translatedType == JoinType.OUTER_APPLY && ctx.family() == SQLDialect.POSTGRES) {
         keyword = "left outer join lateral";
      }

      this.toSQLTable(ctx, this.lhs);
      switch(translatedType) {
      case LEFT_SEMI_JOIN:
      case LEFT_ANTI_JOIN:
         if (ctx.data(Tools.DataKey.DATA_COLLECT_SEMI_ANTI_JOIN) != null) {
            List<Condition> semiAntiJoinPredicates = (List)ctx.data(Tools.DataKey.DATA_COLLECTED_SEMI_ANTI_JOIN);
            if (semiAntiJoinPredicates == null) {
               semiAntiJoinPredicates = new ArrayList();
               ctx.data(Tools.DataKey.DATA_COLLECTED_SEMI_ANTI_JOIN, semiAntiJoinPredicates);
            }

            switch(translatedType) {
            case LEFT_SEMI_JOIN:
               ((List)semiAntiJoinPredicates).add(DSL.exists(DSL.selectOne().from(this.rhs).where(new Condition[]{this.condition})));
               break;
            case LEFT_ANTI_JOIN:
               ((List)semiAntiJoinPredicates).add(DSL.notExists(DSL.selectOne().from(this.rhs).where(new Condition[]{this.condition})));
            }

            return;
         }
      default:
         ctx.formatIndentStart().formatSeparator().start(translatedClause).keyword(keyword).sql(' ');
         this.toSQLTable(ctx, this.rhs);
         if (!this.rhsPartitionBy.isEmpty()) {
            ctx.formatSeparator().start(Clause.TABLE_JOIN_PARTITION_BY).keyword("partition by").sql(" (").visit(this.rhsPartitionBy).sql(')').end(Clause.TABLE_JOIN_PARTITION_BY);
         }

         if (!Arrays.asList(JoinType.CROSS_JOIN, JoinType.NATURAL_JOIN, JoinType.NATURAL_LEFT_OUTER_JOIN, JoinType.NATURAL_RIGHT_OUTER_JOIN, JoinType.CROSS_APPLY, JoinType.OUTER_APPLY).contains(translatedType)) {
            this.toSQLJoinCondition(ctx);
         } else if (JoinType.OUTER_APPLY == translatedType && ctx.family() == SQLDialect.POSTGRES) {
            ctx.formatSeparator().start(Clause.TABLE_JOIN_ON).keyword("on").sql(" true").end(Clause.TABLE_JOIN_ON);
         }

         ctx.end(translatedClause).formatIndentEnd();
      }
   }

   private void toSQLTable(Context<?> ctx, Table<?> table) {
      boolean wrap = table instanceof JoinTable && (table == this.rhs || Arrays.asList().contains(ctx.configuration().dialect().family()));
      if (wrap) {
         ctx.sql('(').formatIndentStart().formatNewLine();
      }

      ctx.visit(table);
      if (wrap) {
         ctx.formatIndentEnd().formatNewLine().sql(')');
      }

   }

   final Clause translateClause(JoinType translatedType) {
      switch(translatedType) {
      case LEFT_SEMI_JOIN:
         return Clause.TABLE_JOIN_SEMI_LEFT;
      case LEFT_ANTI_JOIN:
         return Clause.TABLE_JOIN_ANTI_LEFT;
      case JOIN:
         return Clause.TABLE_JOIN_INNER;
      case CROSS_JOIN:
         return Clause.TABLE_JOIN_CROSS;
      case NATURAL_JOIN:
         return Clause.TABLE_JOIN_NATURAL;
      case LEFT_OUTER_JOIN:
         return Clause.TABLE_JOIN_OUTER_LEFT;
      case RIGHT_OUTER_JOIN:
         return Clause.TABLE_JOIN_OUTER_RIGHT;
      case FULL_OUTER_JOIN:
         return Clause.TABLE_JOIN_OUTER_FULL;
      case NATURAL_LEFT_OUTER_JOIN:
         return Clause.TABLE_JOIN_NATURAL_OUTER_LEFT;
      case NATURAL_RIGHT_OUTER_JOIN:
         return Clause.TABLE_JOIN_NATURAL_OUTER_RIGHT;
      case CROSS_APPLY:
         return Clause.TABLE_JOIN_CROSS_APPLY;
      case OUTER_APPLY:
         return Clause.TABLE_JOIN_OUTER_APPLY;
      case STRAIGHT_JOIN:
         return Clause.TABLE_JOIN_STRAIGHT;
      default:
         throw new IllegalArgumentException("Bad join type: " + translatedType);
      }
   }

   final JoinType translateType(Context<?> context) {
      if (this.emulateCrossJoin(context)) {
         return JoinType.JOIN;
      } else if (this.emulateNaturalJoin(context)) {
         return JoinType.JOIN;
      } else if (this.emulateNaturalLeftOuterJoin(context)) {
         return JoinType.LEFT_OUTER_JOIN;
      } else {
         return this.emulateNaturalRightOuterJoin(context) ? JoinType.RIGHT_OUTER_JOIN : this.type;
      }
   }

   private final boolean emulateCrossJoin(Context<?> context) {
      return this.type == JoinType.CROSS_JOIN && Arrays.asList().contains(context.configuration().dialect().family());
   }

   private final boolean emulateNaturalJoin(Context<?> context) {
      return this.type == JoinType.NATURAL_JOIN && Arrays.asList(SQLDialect.CUBRID).contains(context.configuration().dialect().family());
   }

   private final boolean emulateNaturalLeftOuterJoin(Context<?> context) {
      return this.type == JoinType.NATURAL_LEFT_OUTER_JOIN && Arrays.asList(SQLDialect.CUBRID, SQLDialect.H2).contains(context.family());
   }

   private final boolean emulateNaturalRightOuterJoin(Context<?> context) {
      return this.type == JoinType.NATURAL_RIGHT_OUTER_JOIN && Arrays.asList(SQLDialect.CUBRID, SQLDialect.H2).contains(context.family());
   }

   private final void toSQLJoinCondition(Context<?> context) {
      boolean first;
      if (!this.using.isEmpty()) {
         if (Arrays.asList(SQLDialect.CUBRID, SQLDialect.H2).contains(context.family())) {
            first = true;

            Field field;
            for(Iterator var3 = this.using.iterator(); var3.hasNext(); context.sql(' ').visit(this.lhs.field(field)).sql(" = ").visit(this.rhs.field(field))) {
               field = (Field)var3.next();
               context.formatSeparator();
               if (first) {
                  first = false;
                  context.start(Clause.TABLE_JOIN_ON).keyword("on");
               } else {
                  context.keyword("and");
               }
            }

            context.end(Clause.TABLE_JOIN_ON);
         } else {
            context.formatSeparator().start(Clause.TABLE_JOIN_USING).keyword("using").sql('(');
            Tools.fieldNames(context, (Collection)this.using);
            context.sql(')').end(Clause.TABLE_JOIN_USING);
         }
      } else if (!this.emulateNaturalJoin(context) && !this.emulateNaturalLeftOuterJoin(context) && !this.emulateNaturalRightOuterJoin(context)) {
         context.formatSeparator().start(Clause.TABLE_JOIN_ON).keyword("on").sql(' ').visit(this.condition).end(Clause.TABLE_JOIN_ON);
      } else {
         first = true;
         Field[] var8 = this.lhs.fields();
         int var9 = var8.length;

         for(int var5 = 0; var5 < var9; ++var5) {
            Field<?> field = var8[var5];
            Field<?> other = this.rhs.field(field);
            if (other != null) {
               context.formatSeparator();
               if (first) {
                  first = false;
                  context.start(Clause.TABLE_JOIN_ON).keyword("on");
               } else {
                  context.keyword("and");
               }

               context.sql(' ').visit(field).sql(" = ").visit(other);
            }
         }

         context.end(Clause.TABLE_JOIN_ON);
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final Table<Record> as(String alias) {
      return new TableAlias(this, alias, true);
   }

   public final Table<Record> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases, true);
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   final Fields<Record> fields0() {
      if (this.type != JoinType.LEFT_SEMI_JOIN && this.type != JoinType.LEFT_ANTI_JOIN) {
         Field<?>[] l = this.lhs.asTable().fields();
         Field<?>[] r = this.rhs.asTable().fields();
         Field<?>[] all = new Field[l.length + r.length];
         System.arraycopy(l, 0, all, 0, l.length);
         System.arraycopy(r, 0, all, l.length, r.length);
         return new Fields(all);
      } else {
         return new Fields(this.lhs.asTable().fields());
      }
   }

   public final boolean declaresTables() {
      return true;
   }

   public final JoinTable on(Condition... conditions) {
      this.condition.addConditions(conditions);
      return this;
   }

   public final JoinTable on(Field<Boolean> c) {
      return this.on(DSL.condition(c));
   }

   public final JoinTable on(Boolean c) {
      return this.on(DSL.condition(c));
   }

   public final JoinTable on(SQL sql) {
      this.and(sql);
      return this;
   }

   public final JoinTable on(String sql) {
      this.and(sql);
      return this;
   }

   public final JoinTable on(String sql, Object... bindings) {
      this.and(sql, bindings);
      return this;
   }

   public final JoinTable on(String sql, QueryPart... parts) {
      this.and(sql, parts);
      return this;
   }

   public final JoinTable onKey() throws DataAccessException {
      List<?> leftToRight = this.lhs.getReferencesTo(this.rhs);
      List<?> rightToLeft = this.rhs.getReferencesTo(this.lhs);
      if (leftToRight.size() == 1 && rightToLeft.size() == 0) {
         return this.onKey((ForeignKey)leftToRight.get(0), this.lhs, this.rhs);
      } else if (rightToLeft.size() == 1 && leftToRight.size() == 0) {
         return this.onKey((ForeignKey)rightToLeft.get(0), this.rhs, this.lhs);
      } else {
         throw this.onKeyException();
      }
   }

   public final JoinTable onKey(TableField<?, ?>... keyFields) throws DataAccessException {
      if (keyFields != null && keyFields.length > 0) {
         Iterator var2;
         ForeignKey key;
         if (this.search(this.lhs, keyFields[0].getTable()) != null) {
            var2 = this.lhs.getReferences().iterator();

            while(var2.hasNext()) {
               key = (ForeignKey)var2.next();
               if (key.getFields().containsAll(Arrays.asList(keyFields))) {
                  return this.onKey(key);
               }
            }
         } else if (this.search(this.rhs, keyFields[0].getTable()) != null) {
            var2 = this.rhs.getReferences().iterator();

            while(var2.hasNext()) {
               key = (ForeignKey)var2.next();
               if (key.getFields().containsAll(Arrays.asList(keyFields))) {
                  return this.onKey(key);
               }
            }
         }
      }

      throw this.onKeyException();
   }

   public final JoinTable onKey(ForeignKey<?, ?> key) {
      if (this.search(this.lhs, key.getTable()) != null) {
         return this.onKey(key, this.lhs, this.rhs);
      } else if (this.search(this.rhs, key.getTable()) != null) {
         return this.onKey(key, this.rhs, this.lhs);
      } else {
         throw this.onKeyException();
      }
   }

   private final Table<?> search(Table<?> tree, Table<?> search) {
      if (tree instanceof TableImpl) {
         TableImpl<?> t = (TableImpl)tree;
         if (t.alias == null && search.equals(t)) {
            return t;
         } else {
            return t.alias != null && search.equals(t.alias.wrapped()) ? t : null;
         }
      } else if (tree instanceof TableAlias) {
         TableAlias<?> t = (TableAlias)tree;
         return search.equals(t.alias.wrapped()) ? t : null;
      } else if (tree instanceof JoinTable) {
         JoinTable t = (JoinTable)tree;
         Table<?> r = this.search(t.lhs, search);
         if (r == null) {
            r = this.search(t.rhs, search);
         }

         return r;
      } else {
         return tree;
      }
   }

   private final JoinTable onKey(ForeignKey<?, ?> key, Table<?> fk, Table<?> pk) {
      JoinTable result = this;
      TableField<?, ?>[] references = key.getFieldsArray();
      TableField<?, ?>[] referenced = key.getKey().getFieldsArray();

      for(int i = 0; i < references.length; ++i) {
         Field f1 = fk.field(references[i]);
         Field f2 = pk.field(referenced[i]);
         result.and(f1.equal(f2));
      }

      return result;
   }

   private final DataAccessException onKeyException() {
      return new DataAccessException("Key ambiguous between tables " + this.lhs + " and " + this.rhs);
   }

   public final JoinTable using(Field<?>... fields) {
      return this.using((Collection)Arrays.asList(fields));
   }

   public final JoinTable using(Collection<? extends Field<?>> fields) {
      this.using.addAll(fields);
      return this;
   }

   public final JoinTable and(Condition c) {
      this.condition.addConditions(c);
      return this;
   }

   public final JoinTable and(Field<Boolean> c) {
      return this.and(DSL.condition(c));
   }

   public final JoinTable and(Boolean c) {
      return this.and(DSL.condition(c));
   }

   public final JoinTable and(SQL sql) {
      return this.and(DSL.condition(sql));
   }

   public final JoinTable and(String sql) {
      return this.and(DSL.condition(sql));
   }

   public final JoinTable and(String sql, Object... bindings) {
      return this.and(DSL.condition(sql, bindings));
   }

   public final JoinTable and(String sql, QueryPart... parts) {
      return this.and(DSL.condition(sql, parts));
   }

   public final JoinTable andNot(Condition c) {
      return this.and(c.not());
   }

   public final JoinTable andNot(Field<Boolean> c) {
      return this.andNot(DSL.condition(c));
   }

   public final JoinTable andNot(Boolean c) {
      return this.andNot(DSL.condition(c));
   }

   public final JoinTable andExists(Select<?> select) {
      return this.and(DSL.exists(select));
   }

   public final JoinTable andNotExists(Select<?> select) {
      return this.and(DSL.notExists(select));
   }

   public final JoinTable or(Condition c) {
      this.condition.addConditions(Operator.OR, c);
      return this;
   }

   public final JoinTable or(Field<Boolean> c) {
      return this.or(DSL.condition(c));
   }

   public final JoinTable or(Boolean c) {
      return this.or(DSL.condition(c));
   }

   public final JoinTable or(SQL sql) {
      return this.or(DSL.condition(sql));
   }

   public final JoinTable or(String sql) {
      return this.or(DSL.condition(sql));
   }

   public final JoinTable or(String sql, Object... bindings) {
      return this.or(DSL.condition(sql, bindings));
   }

   public final JoinTable or(String sql, QueryPart... parts) {
      return this.or(DSL.condition(sql, parts));
   }

   public final JoinTable orNot(Condition c) {
      return this.or(c.not());
   }

   public final JoinTable orNot(Field<Boolean> c) {
      return this.orNot(DSL.condition(c));
   }

   public final JoinTable orNot(Boolean c) {
      return this.orNot(DSL.condition(c));
   }

   public final JoinTable orExists(Select<?> select) {
      return this.or(DSL.exists(select));
   }

   public final JoinTable orNotExists(Select<?> select) {
      return this.or(DSL.notExists(select));
   }

   static {
      CLAUSES = new Clause[]{Clause.TABLE, Clause.TABLE_JOIN};
   }
}
