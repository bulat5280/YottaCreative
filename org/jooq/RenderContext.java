package org.jooq;

import org.jooq.conf.ParamType;

public interface RenderContext extends Context<RenderContext> {
   String peekAlias();

   String nextAlias();

   String render();

   String render(QueryPart var1);

   RenderContext keyword(String var1);

   RenderContext sql(String var1);

   RenderContext sql(String var1, boolean var2);

   RenderContext sql(char var1);

   RenderContext sql(int var1);

   /** @deprecated */
   @Deprecated
   RenderContext sql(QueryPart var1);

   RenderContext format(boolean var1);

   boolean format();

   RenderContext formatNewLine();

   RenderContext formatNewLineAfterPrintMargin();

   RenderContext formatSeparator();

   RenderContext formatIndentStart();

   RenderContext formatIndentStart(int var1);

   RenderContext formatIndentLockStart();

   RenderContext formatIndentEnd();

   RenderContext formatIndentEnd(int var1);

   RenderContext formatIndentLockEnd();

   RenderContext formatPrintMargin(int var1);

   RenderContext literal(String var1);

   /** @deprecated */
   @Deprecated
   boolean inline();

   /** @deprecated */
   @Deprecated
   RenderContext inline(boolean var1);

   boolean qualify();

   RenderContext qualify(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean namedParams();

   /** @deprecated */
   @Deprecated
   RenderContext namedParams(boolean var1);

   ParamType paramType();

   RenderContext paramType(ParamType var1);

   RenderContext.CastMode castMode();

   RenderContext castMode(RenderContext.CastMode var1);

   /** @deprecated */
   @Deprecated
   Boolean cast();

   /** @deprecated */
   @Deprecated
   RenderContext castModeSome(SQLDialect... var1);

   public static enum CastMode {
      ALWAYS,
      NEVER,
      /** @deprecated */
      @Deprecated
      SOME,
      DEFAULT;
   }
}
