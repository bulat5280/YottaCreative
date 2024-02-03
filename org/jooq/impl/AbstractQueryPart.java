package org.jooq.impl;

import java.sql.SQLException;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLDialectNotSupportedException;

abstract class AbstractQueryPart implements QueryPartInternal {
   private static final long serialVersionUID = 2078114876079493107L;

   Configuration configuration() {
      return new DefaultConfiguration();
   }

   /** @deprecated */
   @Deprecated
   public final void toSQL(RenderContext context) {
   }

   /** @deprecated */
   @Deprecated
   public final void bind(BindContext context) throws DataAccessException {
   }

   public boolean declaresFields() {
      return false;
   }

   public boolean declaresTables() {
      return false;
   }

   public boolean declaresWindows() {
      return false;
   }

   public boolean declaresCTE() {
      return false;
   }

   public boolean generatesCast() {
      return false;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (that instanceof QueryPart) {
         String sql1 = this.create().renderInlined(this);
         String sql2 = this.create().renderInlined((QueryPart)that);
         return sql1.equals(sql2);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.create().renderInlined(this).hashCode();
   }

   public String toString() {
      try {
         return this.create(this.configuration().derive(SettingsTools.clone(this.configuration().settings()).withRenderFormatted(true))).renderInlined(this);
      } catch (SQLDialectNotSupportedException var2) {
         return "[ ... " + var2.getMessage() + " ... ]";
      }
   }

   protected final DSLContext create() {
      return this.create(this.configuration());
   }

   protected final DSLContext create(Configuration configuration) {
      return DSL.using(configuration);
   }

   protected final DSLContext create(Context<?> ctx) {
      return DSL.using(ctx.configuration());
   }

   protected final DataAccessException translate(String sql, SQLException e) {
      return Tools.translate(sql, e);
   }
}
