package org.jooq;

public interface ConstraintForeignKeyReferencesStepN {
   ConstraintFinalStep references(String var1, String... var2);

   ConstraintFinalStep references(Table<?> var1, Field<?>... var2);
}
