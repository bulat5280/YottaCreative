package org.jooq;

import java.util.List;

public interface UniqueKey<R extends Record> extends Key<R> {
   List<ForeignKey<?, R>> getReferences();

   boolean isPrimary();
}
