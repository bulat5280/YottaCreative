package org.jooq;

import java.sql.Connection;
import org.jooq.exception.DataAccessException;

public interface ConnectionProvider {
   Connection acquire() throws DataAccessException;

   void release(Connection var1) throws DataAccessException;
}
