package org.jooq;

import java.util.List;

public interface Loader<R extends Record> {
   List<LoaderError> errors();

   int processed();

   int executed();

   int ignored();

   int stored();

   LoaderContext result();
}
