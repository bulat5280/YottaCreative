package org.jooq;

import java.util.Collection;

public interface GroupConcatOrderByStep extends GroupConcatSeparatorStep {
   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   GroupConcatSeparatorStep orderBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   GroupConcatSeparatorStep orderBy(SortField<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   GroupConcatSeparatorStep orderBy(Collection<? extends SortField<?>> var1);
}
