package org.jooq.impl;

import java.sql.SQLException;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;

final class Val<T> extends AbstractParam<T> {
   private static final long serialVersionUID = 6807729087019209084L;

   Val(T value, DataType<T> type) {
      super(value, type);
   }

   Val(T value, DataType<T> type, String paramName) {
      super(value, type, paramName);
   }

   public void accept(Context<?> ctx) {
      if (ctx instanceof RenderContext) {
         ParamType paramType = ctx.paramType();
         if (this.isInline(ctx)) {
            ctx.paramType(ParamType.INLINED);
         }

         try {
            this.getBinding().sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.data(), (RenderContext)ctx, this.value, this.getBindVariable(ctx)));
         } catch (SQLException var4) {
            throw new DataAccessException("Error while generating SQL for Binding", var4);
         }

         ctx.paramType(paramType);
      } else if (!this.isInline(ctx)) {
         ctx.bindValue(this.value, this);
      }

   }

   final String getBindVariable(Context<?> ctx) {
      if (ctx.paramType() != ParamType.NAMED && ctx.paramType() != ParamType.NAMED_OR_INLINED) {
         return "?";
      } else {
         int index = ctx.nextIndex();
         return StringUtils.isBlank(this.getParamName()) ? ":" + index : ":" + this.getParamName();
      }
   }
}
