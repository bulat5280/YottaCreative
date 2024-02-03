package org.jooq;

import java.util.Collection;

public interface SelectSelectStep<R extends Record> extends SelectDistinctOnStep<R> {
   @Support
   SelectSelectStep<Record> select(SelectField<?>... var1);

   @Support
   SelectSelectStep<Record> select(Collection<? extends SelectField<?>> var1);
}
