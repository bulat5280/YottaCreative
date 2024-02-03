package org.jooq;

import java.util.List;
import org.jooq.exception.DataAccessException;

public interface Select<R extends Record> extends ResultQuery<R>, TableLike<R>, FieldLike {
   @Support
   Select<R> union(Select<? extends R> var1);

   @Support
   Select<R> unionAll(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Select<R> except(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   Select<R> exceptAll(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   Select<R> intersect(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   Select<R> intersectAll(Select<? extends R> var1);

   List<Field<?>> getSelect();

   /** @deprecated */
   @Deprecated
   int fetchCount() throws DataAccessException;
}
