package org.jooq;

import org.jooq.exception.DataAccessException;

public interface QueryPartInternal extends QueryPart {
   /** @deprecated */
   @Deprecated
   void accept(Context<?> var1);

   /** @deprecated */
   @Deprecated
   void toSQL(RenderContext var1);

   /** @deprecated */
   @Deprecated
   void bind(BindContext var1) throws DataAccessException;

   Clause[] clauses(Context<?> var1);

   boolean declaresFields();

   boolean declaresTables();

   boolean declaresWindows();

   boolean declaresCTE();

   boolean generatesCast();
}
