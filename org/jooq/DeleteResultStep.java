package org.jooq;

import java.util.Optional;
import org.jooq.exception.DataAccessException;

public interface DeleteResultStep<R extends Record> extends Delete<R> {
   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   Result<R> fetch() throws DataAccessException;

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   R fetchOne() throws DataAccessException;

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   Optional<R> fetchOptional() throws DataAccessException;
}
