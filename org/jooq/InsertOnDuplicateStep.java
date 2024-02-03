package org.jooq;

import java.util.Collection;

public interface InsertOnDuplicateStep<R extends Record> extends InsertReturningStep<R> {
   @Support({SQLDialect.POSTGRES_9_5})
   InsertOnConflictDoUpdateStep<R> onConflict(Field<?>... var1);

   @Support({SQLDialect.POSTGRES_9_5})
   InsertOnConflictDoUpdateStep<R> onConflict(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.POSTGRES_9_5})
   InsertFinalStep<R> onConflictDoNothing();

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   InsertOnDuplicateSetStep<R> onDuplicateKeyUpdate();

   @Support
   InsertFinalStep<R> onDuplicateKeyIgnore();
}
