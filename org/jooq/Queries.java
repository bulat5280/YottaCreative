package org.jooq;

import java.util.stream.Stream;

public interface Queries extends Iterable<Query> {
   Query[] queries();

   Stream<Query> stream();
}
