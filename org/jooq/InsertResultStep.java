package org.jooq;

import java.util.Optional;
import org.jooq.exception.DataAccessException;

public interface InsertResultStep<R extends Record> extends Insert<R> {
   @Support
   Result<R> fetch() throws DataAccessException;

   @Support
   R fetchOne() throws DataAccessException;

   @Support
   Optional<R> fetchOptional() throws DataAccessException;
}
