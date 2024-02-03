package org.jooq.impl;

import org.jooq.BindContext;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.RenderContext;
import org.jooq.UDTRecord;
import org.jooq.exception.SQLDialectNotSupportedException;

final class UDTConstant<R extends UDTRecord<R>> extends AbstractParam<R> {
   private static final long serialVersionUID = 6807729087019209084L;

   UDTConstant(R value) {
      super(value, value.getUDT().getDataType());
   }

   public void accept(Context<?> ctx) {
      if (ctx instanceof RenderContext) {
         this.toSQL0((RenderContext)ctx);
      } else {
         this.bind0((BindContext)ctx);
      }

   }

   final void toSQL0(RenderContext context) {
      switch(context.family()) {
      case POSTGRES:
         this.toSQLInline(context);
         return;
      default:
         this.toSQLInline(context);
      }
   }

   private final void toSQLInline(RenderContext context) {
      switch(context.family()) {
      case POSTGRES:
         context.keyword("row");
         break;
      default:
         context.visit(((UDTRecord)this.value).getUDT());
      }

      context.sql('(');
      String separator = "";
      Field[] var3 = ((UDTRecord)this.value).fields();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field<?> field = var3[var5];
         context.sql(separator);
         context.visit(DSL.val(((UDTRecord)this.value).get(field), field));
         separator = ", ";
      }

      context.sql(')');
   }

   /** @deprecated */
   @Deprecated
   private final String getInlineConstructor(RenderContext context) {
      switch(context.family()) {
      case POSTGRES:
         return "ROW";
      default:
         return Tools.getMappedUDTName(context.configuration(), (UDTRecord)this.value);
      }
   }

   final void bind0(BindContext context) {
      switch(context.family()) {
      case POSTGRES:
         Field[] var2 = ((UDTRecord)this.value).fields();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Field<?> field = var2[var4];
            context.visit(DSL.val(((UDTRecord)this.value).get(field)));
         }

         return;
      default:
         throw new SQLDialectNotSupportedException("UDTs not supported in dialect " + context.dialect());
      }
   }
}
