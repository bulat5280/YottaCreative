package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.ConstraintForeignKeyOnStep;
import org.jooq.ConstraintForeignKeyReferencesStep1;
import org.jooq.ConstraintForeignKeyReferencesStep10;
import org.jooq.ConstraintForeignKeyReferencesStep11;
import org.jooq.ConstraintForeignKeyReferencesStep12;
import org.jooq.ConstraintForeignKeyReferencesStep13;
import org.jooq.ConstraintForeignKeyReferencesStep14;
import org.jooq.ConstraintForeignKeyReferencesStep15;
import org.jooq.ConstraintForeignKeyReferencesStep16;
import org.jooq.ConstraintForeignKeyReferencesStep17;
import org.jooq.ConstraintForeignKeyReferencesStep18;
import org.jooq.ConstraintForeignKeyReferencesStep19;
import org.jooq.ConstraintForeignKeyReferencesStep2;
import org.jooq.ConstraintForeignKeyReferencesStep20;
import org.jooq.ConstraintForeignKeyReferencesStep21;
import org.jooq.ConstraintForeignKeyReferencesStep22;
import org.jooq.ConstraintForeignKeyReferencesStep3;
import org.jooq.ConstraintForeignKeyReferencesStep4;
import org.jooq.ConstraintForeignKeyReferencesStep5;
import org.jooq.ConstraintForeignKeyReferencesStep6;
import org.jooq.ConstraintForeignKeyReferencesStep7;
import org.jooq.ConstraintForeignKeyReferencesStep8;
import org.jooq.ConstraintForeignKeyReferencesStep9;
import org.jooq.ConstraintForeignKeyReferencesStepN;
import org.jooq.ConstraintTypeStep;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;

final class ConstraintImpl extends AbstractQueryPart implements ConstraintTypeStep, ConstraintForeignKeyOnStep, ConstraintForeignKeyReferencesStepN, ConstraintForeignKeyReferencesStep1, ConstraintForeignKeyReferencesStep2, ConstraintForeignKeyReferencesStep3, ConstraintForeignKeyReferencesStep4, ConstraintForeignKeyReferencesStep5, ConstraintForeignKeyReferencesStep6, ConstraintForeignKeyReferencesStep7, ConstraintForeignKeyReferencesStep8, ConstraintForeignKeyReferencesStep9, ConstraintForeignKeyReferencesStep10, ConstraintForeignKeyReferencesStep11, ConstraintForeignKeyReferencesStep12, ConstraintForeignKeyReferencesStep13, ConstraintForeignKeyReferencesStep14, ConstraintForeignKeyReferencesStep15, ConstraintForeignKeyReferencesStep16, ConstraintForeignKeyReferencesStep17, ConstraintForeignKeyReferencesStep18, ConstraintForeignKeyReferencesStep19, ConstraintForeignKeyReferencesStep20, ConstraintForeignKeyReferencesStep21, ConstraintForeignKeyReferencesStep22 {
   private static final long serialVersionUID = 1018023703769802616L;
   private static final Clause[] CLAUSES;
   private final Name name;
   private Field<?>[] unique;
   private Field<?>[] primaryKey;
   private Field<?>[] foreignKey;
   private Table<?> referencesTable;
   private Field<?>[] references;
   private ConstraintImpl.Action onDelete;
   private ConstraintImpl.Action onUpdate;
   private Condition check;

   ConstraintImpl() {
      this((Name)null);
   }

   ConstraintImpl(Name name) {
      this.name = name;
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final void accept(Context<?> ctx) {
      if (ctx.data(Tools.DataKey.DATA_CONSTRAINT_REFERENCE) != null) {
         if (this.name == null) {
            throw new DataAccessException("Cannot ALTER or DROP CONSTRAINT without name");
         }

         ctx.visit(this.name);
      } else {
         boolean qualify = ctx.qualify();
         if (this.name != null) {
            ctx.keyword("constraint").sql(' ').visit(this.name).formatIndentStart().formatSeparator();
         }

         if (this.unique != null) {
            ctx.keyword("unique").sql(" (").qualify(false).visit(new QueryPartList(this.unique)).qualify(qualify).sql(')');
         } else if (this.primaryKey != null) {
            ctx.keyword("primary key").sql(" (").qualify(false).visit(new QueryPartList(this.primaryKey)).qualify(qualify).sql(')');
         } else if (this.foreignKey != null) {
            ctx.keyword("foreign key").sql(" (").qualify(false).visit(new QueryPartList(this.foreignKey)).qualify(qualify).sql(')').formatSeparator().keyword("references").sql(' ').visit(this.referencesTable).sql(" (").qualify(false).visit(new QueryPartList(this.references)).qualify(qualify).sql(')');
            if (this.onDelete != null) {
               ctx.sql(' ').keyword("on delete").sql(' ').keyword(this.onDelete.sql);
            }

            if (this.onUpdate != null) {
               ctx.sql(' ').keyword("on update").sql(' ').keyword(this.onUpdate.sql);
            }
         } else if (this.check != null) {
            ctx.keyword("check").sql(" (").qualify(false).visit(this.check).qualify(qualify).sql(')');
         }

         ctx.formatIndentEnd();
      }

   }

   public final ConstraintImpl unique(String... fields) {
      return this.unique(Tools.fieldsByName(fields));
   }

   public final ConstraintImpl unique(Field<?>... fields) {
      this.unique = fields;
      return this;
   }

   public final ConstraintImpl check(Condition condition) {
      this.check = condition;
      return this;
   }

   public final ConstraintImpl primaryKey(String... fields) {
      return this.primaryKey(Tools.fieldsByName(fields));
   }

   public final ConstraintImpl primaryKey(Field<?>... fields) {
      this.primaryKey = fields;
      return this;
   }

   public final ConstraintImpl foreignKey(String... fields) {
      return this.foreignKey(Tools.fieldsByName(fields));
   }

   public final ConstraintImpl foreignKey(Field<?>... fields) {
      this.foreignKey = fields;
      return this;
   }

   public final ConstraintImpl references(String table, String... fields) {
      return this.references(DSL.table(DSL.name(table)), Tools.fieldsByName(fields));
   }

   public final ConstraintImpl references(Table<?> table, Field<?>... fields) {
      this.referencesTable = table;
      this.references = fields;
      return this;
   }

   public final ConstraintImpl onDeleteNoAction() {
      this.onDelete = ConstraintImpl.Action.NO_ACTION;
      return this;
   }

   public final ConstraintImpl onDeleteRestrict() {
      this.onDelete = ConstraintImpl.Action.RESTRICT;
      return this;
   }

   public final ConstraintImpl onDeleteCascade() {
      this.onDelete = ConstraintImpl.Action.CASCADE;
      return this;
   }

   public final ConstraintImpl onDeleteSetNull() {
      this.onDelete = ConstraintImpl.Action.SET_NULL;
      return this;
   }

   public final ConstraintImpl onDeleteSetDefault() {
      this.onDelete = ConstraintImpl.Action.SET_DEFAULT;
      return this;
   }

   public final ConstraintImpl onUpdateNoAction() {
      this.onUpdate = ConstraintImpl.Action.NO_ACTION;
      return this;
   }

   public final ConstraintImpl onUpdateRestrict() {
      this.onUpdate = ConstraintImpl.Action.RESTRICT;
      return this;
   }

   public final ConstraintImpl onUpdateCascade() {
      this.onUpdate = ConstraintImpl.Action.CASCADE;
      return this;
   }

   public final ConstraintImpl onUpdateSetNull() {
      this.onUpdate = ConstraintImpl.Action.SET_NULL;
      return this;
   }

   public final ConstraintImpl onUpdateSetDefault() {
      this.onUpdate = ConstraintImpl.Action.SET_DEFAULT;
      return this;
   }

   public final <T1> ConstraintImpl foreignKey(Field<T1> field1) {
      return this.foreignKey(field1);
   }

   public final <T1, T2> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2) {
      return this.foreignKey(field1, field2);
   }

   public final <T1, T2, T3> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return this.foreignKey(field1, field2, field3);
   }

   public final <T1, T2, T3, T4> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return this.foreignKey(field1, field2, field3, field4);
   }

   public final <T1, T2, T3, T4, T5> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return this.foreignKey(field1, field2, field3, field4, field5);
   }

   public final <T1, T2, T3, T4, T5, T6> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6);
   }

   public final <T1, T2, T3, T4, T5, T6, T7> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public final ConstraintImpl foreignKey(String field1) {
      return this.foreignKey(field1);
   }

   public final ConstraintImpl foreignKey(String field1, String field2) {
      return this.foreignKey(field1, field2);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3) {
      return this.foreignKey(field1, field2, field3);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4) {
      return this.foreignKey(field1, field2, field3, field4);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5) {
      return this.foreignKey(field1, field2, field3, field4, field5);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21, String field22) {
      return this.foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public final ConstraintImpl references(Table table, Field t1) {
      return this.references(table, t1);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2) {
      return this.references(table, t1, t2);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3) {
      return this.references(table, t1, t2, t3);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4) {
      return this.references(table, t1, t2, t3, t4);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5) {
      return this.references(table, t1, t2, t3, t4, t5);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
      return this.references(table, t1, t2, t3, t4, t5, t6);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
      return this.references(table, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final ConstraintImpl references(String table, String field1) {
      return this.references(table, field1);
   }

   public final ConstraintImpl references(String table, String field1, String field2) {
      return this.references(table, field1, field2);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3) {
      return this.references(table, field1, field2, field3);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4) {
      return this.references(table, field1, field2, field3, field4);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5) {
      return this.references(table, field1, field2, field3, field4, field5);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6) {
      return this.references(table, field1, field2, field3, field4, field5, field6);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21, String field22) {
      return this.references(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   static {
      CLAUSES = new Clause[]{Clause.CONSTRAINT};
   }

   static enum Action {
      NO_ACTION("no action"),
      RESTRICT("restrict"),
      CASCADE("cascade"),
      SET_NULL("set null"),
      SET_DEFAULT("set default");

      String sql;

      private Action(String sql) {
         this.sql = sql;
      }
   }
}
