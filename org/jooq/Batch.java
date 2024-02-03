package org.jooq;

import java.io.Serializable;
import org.jooq.exception.DataAccessException;

public interface Batch extends Serializable {
   int[] execute() throws DataAccessException;

   int size();
}
