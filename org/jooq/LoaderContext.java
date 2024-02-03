package org.jooq;

import java.util.List;

public interface LoaderContext {
   List<LoaderError> errors();

   int processed();

   int executed();

   int ignored();

   int stored();
}
