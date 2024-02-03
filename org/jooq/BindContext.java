package org.jooq;

import java.sql.PreparedStatement;
import java.util.Collection;
import org.jooq.exception.DataAccessException;

public interface BindContext extends Context<BindContext> {
   PreparedStatement statement();

   /** @deprecated */
   @Deprecated
   BindContext bind(QueryPart var1) throws DataAccessException;

   /** @deprecated */
   @Deprecated
   BindContext bind(Collection<? extends QueryPart> var1) throws DataAccessException;

   /** @deprecated */
   @Deprecated
   BindContext bind(QueryPart[] var1) throws DataAccessException;

   /** @deprecated */
   @Deprecated
   BindContext bindValue(Object var1, Class<?> var2) throws DataAccessException;

   /** @deprecated */
   @Deprecated
   BindContext bindValues(Object... var1) throws DataAccessException;

   BindContext bindValue(Object var1, Field<?> var2) throws DataAccessException;
}
