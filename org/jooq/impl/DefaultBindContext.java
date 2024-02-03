package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jooq.BindContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Field;

final class DefaultBindContext extends AbstractBindContext {
   DefaultBindContext(Configuration configuration, PreparedStatement stmt) {
      super(configuration, stmt);
   }

   protected final BindContext bindValue0(Object value, Field<?> field) throws SQLException {
      field.getBinding().set((BindingSetStatementContext)(new DefaultBindingSetStatementContext(this.configuration(), this.data(), this.stmt, this.nextIndex(), value)));
      return this;
   }
}
