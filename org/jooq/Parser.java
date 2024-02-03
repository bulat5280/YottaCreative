package org.jooq;

/** @deprecated */
@Deprecated
public interface Parser {
   /** @deprecated */
   @Deprecated
   Queries parse(String var1);

   /** @deprecated */
   @Deprecated
   Query parseQuery(String var1);

   /** @deprecated */
   @Deprecated
   Table<?> parseTable(String var1);

   /** @deprecated */
   @Deprecated
   Field<?> parseField(String var1);

   /** @deprecated */
   @Deprecated
   Condition parseCondition(String var1);

   /** @deprecated */
   @Deprecated
   Name parseName(String var1);
}
