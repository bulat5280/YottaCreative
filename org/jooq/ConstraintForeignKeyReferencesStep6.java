package org.jooq;

public interface ConstraintForeignKeyReferencesStep6<T1, T2, T3, T4, T5, T6> {
   @Support
   ConstraintForeignKeyOnStep references(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

   @Support
   ConstraintForeignKeyOnStep references(Table<?> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);
}
