package org.jooq;

import java.util.Collection;

public interface SelectDistinctOnStep<R extends Record> extends SelectIntoStep<R> {
   @Support({SQLDialect.POSTGRES})
   SelectIntoStep<R> on(SelectField<?>... var1);

   @Support({SQLDialect.POSTGRES})
   SelectIntoStep<R> on(Collection<? extends SelectField<?>> var1);

   @Support({SQLDialect.POSTGRES})
   SelectIntoStep<R> distinctOn(SelectField<?>... var1);

   @Support({SQLDialect.POSTGRES})
   SelectIntoStep<R> distinctOn(Collection<? extends SelectField<?>> var1);
}
