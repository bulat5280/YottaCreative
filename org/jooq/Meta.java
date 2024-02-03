package org.jooq;

import java.util.List;
import org.jooq.exception.DataAccessException;

public interface Meta {
   @Support
   List<Catalog> getCatalogs() throws DataAccessException;

   @Support
   List<Schema> getSchemas() throws DataAccessException;

   @Support
   List<Table<?>> getTables() throws DataAccessException;

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   List<Sequence<?>> getSequences() throws DataAccessException;

   @Support
   List<UniqueKey<?>> getPrimaryKeys() throws DataAccessException;
}
