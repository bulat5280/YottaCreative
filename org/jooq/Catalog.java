package org.jooq;

import java.util.List;
import java.util.stream.Stream;

public interface Catalog extends QueryPart {
   String getName();

   List<Schema> getSchemas();

   Schema getSchema(String var1);

   Stream<Schema> schemaStream();
}
