package org.jooq;

public interface ConstraintForeignKeyReferencesStep1<T1> {
   @Support
   ConstraintForeignKeyOnStep references(String var1, String var2);

   @Support
   ConstraintForeignKeyOnStep references(Table<?> var1, Field<T1> var2);
}
