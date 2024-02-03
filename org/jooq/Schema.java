package org.jooq;

import java.util.List;
import java.util.stream.Stream;

public interface Schema extends QueryPart {
   Catalog getCatalog();

   String getName();

   Stream<Table<?>> tableStream();

   List<Table<?>> getTables();

   Table<?> getTable(String var1);

   Stream<UDT<?>> udtStream();

   List<UDT<?>> getUDTs();

   UDT<?> getUDT(String var1);

   Stream<Sequence<?>> sequenceStream();

   List<Sequence<?>> getSequences();

   Sequence<?> getSequence(String var1);
}
