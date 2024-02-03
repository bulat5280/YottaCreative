package org.jooq;

import java.io.IOException;

public interface LoaderLoadStep<R extends Record> {
   @Support
   Loader<R> execute() throws IOException;
}
