package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;

final class TableAlias<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = -8417114874567698325L;
   final Alias<Table<R>> alias;
   final Fields<R> aliasedFields;

   TableAlias(Table<R> table, String alias) {
      this(table, alias, (String[])null, false);
   }

   TableAlias(Table<R> table, String alias, boolean wrapInParentheses) {
      this(table, alias, (String[])null, wrapInParentheses);
   }

   TableAlias(Table<R> table, String alias, String[] fieldAliases) {
      this(table, alias, fieldAliases, false);
   }

   TableAlias(Table<R> table, String alias, String[] fieldAliases, boolean wrapInParentheses) {
      super(alias, table.getSchema());
      this.alias = new Alias(table, alias, fieldAliases, wrapInParentheses);
      this.aliasedFields = this.init(fieldAliases);
   }

   private final Fields<R> init(String[] fieldAliases) {
      List<Field<?>> result = new ArrayList();
      Row row = ((Table)this.alias.wrapped()).fieldsRow();
      int size = row.size();

      for(int i = 0; i < size; ++i) {
         Field<?> field = row.field(i);
         String name = field.getName();
         if (fieldAliases != null && fieldAliases.length > i) {
            name = fieldAliases[i];
         }

         result.add(new TableFieldImpl(name, field.getDataType(), this, field.getComment(), field.getBinding()));
      }

      return new Fields(result);
   }

   Table<R> getAliasedTable() {
      return this.alias != null ? (Table)this.alias.wrapped() : null;
   }

   public final Identity<R, ?> getIdentity() {
      return ((Table)this.alias.wrapped()).getIdentity();
   }

   public final UniqueKey<R> getPrimaryKey() {
      return ((Table)this.alias.wrapped()).getPrimaryKey();
   }

   public final List<UniqueKey<R>> getKeys() {
      return ((Table)this.alias.wrapped()).getKeys();
   }

   public final List<ForeignKey<R, ?>> getReferences() {
      return ((Table)this.alias.wrapped()).getReferences();
   }

   public final TableField<R, ?> getRecordVersion() {
      return ((Table)this.alias.wrapped()).getRecordVersion();
   }

   public final TableField<R, ?> getRecordTimestamp() {
      return ((Table)this.alias.wrapped()).getRecordTimestamp();
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.alias);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final Table<R> as(String as) {
      return ((Table)this.alias.wrapped()).as(as);
   }

   public final Table<R> as(String as, String... fieldAliases) {
      return ((Table)this.alias.wrapped()).as(as, fieldAliases);
   }

   public final boolean declaresTables() {
      return true;
   }

   final Fields<R> fields0() {
      return this.aliasedFields;
   }

   public Class<? extends R> getRecordType() {
      return ((Table)this.alias.wrapped()).getRecordType();
   }
}
