package org.jooq;

public interface ConstraintForeignKeyReferencesStep2<T1, T2> {
   @Support
   ConstraintForeignKeyOnStep references(String var1, String var2, String var3);

   @Support
   ConstraintForeignKeyOnStep references(Table<?> var1, Field<T1> var2, Field<T2> var3);
}
