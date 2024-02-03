package org.jooq;

import java.util.Collection;

public interface CreateTableConstraintStep extends CreateTableOnCommitStep {
   @Support
   CreateTableConstraintStep constraint(Constraint var1);

   @Support
   CreateTableConstraintStep constraints(Constraint... var1);

   @Support
   CreateTableConstraintStep constraints(Collection<? extends Constraint> var1);
}
