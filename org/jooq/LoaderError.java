package org.jooq;

import org.jooq.exception.DataAccessException;

public interface LoaderError {
   DataAccessException exception();

   int rowIndex();

   String[] row();

   Query query();
}
