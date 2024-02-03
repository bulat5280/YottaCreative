package org.jooq;

import java.util.concurrent.Future;

/** @deprecated */
@Deprecated
public interface FutureResult<R extends Record> extends Future<Result<R>> {
}
