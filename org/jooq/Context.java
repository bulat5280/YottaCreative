package org.jooq;

import java.sql.PreparedStatement;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;

public interface Context<C extends Context<C>> extends Scope {
   C visit(QueryPart var1) throws DataAccessException;

   C start(Clause var1);

   C end(Clause var1);

   boolean declareFields();

   C declareFields(boolean var1);

   boolean declareTables();

   C declareTables(boolean var1);

   boolean declareAliases();

   C declareAliases(boolean var1);

   boolean declareWindows();

   C declareWindows(boolean var1);

   boolean declareCTE();

   C declareCTE(boolean var1);

   boolean subquery();

   C subquery(boolean var1);

   boolean stringLiteral();

   C stringLiteral(boolean var1);

   int nextIndex();

   int peekIndex();

   PreparedStatement statement();

   BindContext bindValue(Object var1, Field<?> var2) throws DataAccessException;

   String peekAlias();

   String nextAlias();

   String render();

   String render(QueryPart var1);

   C keyword(String var1);

   C sql(String var1);

   C sql(String var1, boolean var2);

   C sql(char var1);

   C sql(int var1);

   C format(boolean var1);

   boolean format();

   C formatNewLine();

   C formatNewLineAfterPrintMargin();

   C formatSeparator();

   C formatIndentStart();

   C formatIndentStart(int var1);

   C formatIndentLockStart();

   C formatIndentEnd();

   C formatIndentEnd(int var1);

   C formatIndentLockEnd();

   C formatPrintMargin(int var1);

   C literal(String var1);

   boolean qualify();

   C qualify(boolean var1);

   boolean qualifySchema();

   C qualifySchema(boolean var1);

   boolean qualifyCatalog();

   C qualifyCatalog(boolean var1);

   ParamType paramType();

   C paramType(ParamType var1);

   RenderContext.CastMode castMode();

   C castMode(RenderContext.CastMode var1);

   /** @deprecated */
   @Deprecated
   Boolean cast();

   /** @deprecated */
   @Deprecated
   C castModeSome(SQLDialect... var1);
}
