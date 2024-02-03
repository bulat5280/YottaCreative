package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.exception.DataAccessException;

abstract class AbstractBindContext extends AbstractContext<BindContext> implements BindContext {
   AbstractBindContext(Configuration configuration, PreparedStatement stmt) {
      super(configuration, stmt);
   }

   /** @deprecated */
   @Deprecated
   public final BindContext bind(Collection<? extends QueryPart> parts) {
      return (BindContext)Tools.visitAll(this, (Collection)parts);
   }

   /** @deprecated */
   @Deprecated
   public final BindContext bind(QueryPart[] parts) {
      return (BindContext)Tools.visitAll(this, (QueryPart[])parts);
   }

   /** @deprecated */
   @Deprecated
   public final BindContext bind(QueryPart part) {
      return (BindContext)this.visit(part);
   }

   protected void visit0(QueryPartInternal internal) {
      this.bindInternal(internal);
   }

   /** @deprecated */
   @Deprecated
   public final BindContext bindValues(Object... values) {
      if (values == null) {
         this.bindValues(null);
      } else {
         Object[] var2 = values;
         int var3 = values.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object value = var2[var4];
            Class<?> type = value == null ? Object.class : value.getClass();
            this.bindValue(value, (Field)DSL.val(value, type));
         }
      }

      return this;
   }

   /** @deprecated */
   @Deprecated
   public final BindContext bindValue(Object value, Class<?> type) {
      try {
         return this.bindValue0(value, DSL.val(value, type));
      } catch (SQLException var4) {
         throw Tools.translate((String)null, var4);
      }
   }

   public final BindContext bindValue(Object value, Field<?> field) throws DataAccessException {
      try {
         return this.bindValue0(value, field);
      } catch (SQLException var4) {
         throw Tools.translate((String)null, var4);
      }
   }

   public final String peekAlias() {
      return null;
   }

   public final String nextAlias() {
      return null;
   }

   public final String render() {
      return null;
   }

   public final String render(QueryPart part) {
      return null;
   }

   public final BindContext keyword(String keyword) {
      return this;
   }

   public final BindContext sql(String sql) {
      return this;
   }

   public final BindContext sql(String sql, boolean literal) {
      return this;
   }

   public final BindContext sql(char sql) {
      return this;
   }

   public final BindContext sql(int sql) {
      return this;
   }

   public final BindContext format(boolean format) {
      return this;
   }

   public final boolean format() {
      return false;
   }

   public final BindContext formatNewLine() {
      return this;
   }

   public final BindContext formatNewLineAfterPrintMargin() {
      return this;
   }

   public final BindContext formatSeparator() {
      return this;
   }

   public final BindContext formatIndentStart() {
      return this;
   }

   public final BindContext formatIndentStart(int indent) {
      return this;
   }

   public final BindContext formatIndentLockStart() {
      return this;
   }

   public final BindContext formatIndentEnd() {
      return this;
   }

   public final BindContext formatIndentEnd(int indent) {
      return this;
   }

   public final BindContext formatIndentLockEnd() {
      return this;
   }

   public final BindContext formatPrintMargin(int margin) {
      return this;
   }

   public final BindContext literal(String literal) {
      return this;
   }

   protected void bindInternal(QueryPartInternal internal) {
      internal.accept(this);
   }

   protected BindContext bindValue0(Object value, Field<?> field) throws SQLException {
      return this;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      this.toString(sb);
      return sb.toString();
   }
}
