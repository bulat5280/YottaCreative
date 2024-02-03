package org.jooq;

import java.util.List;
import org.jooq.exception.DataAccessException;

public interface Routine<T> extends QueryPart {
   Catalog getCatalog();

   Schema getSchema();

   String getName();

   Package getPackage();

   Parameter<T> getReturnParameter();

   List<Parameter<?>> getOutParameters();

   List<Parameter<?>> getInParameters();

   List<Parameter<?>> getParameters();

   int execute(Configuration var1) throws DataAccessException;

   int execute() throws DataAccessException;

   <Z> void setValue(Parameter<Z> var1, Z var2);

   <Z> void set(Parameter<Z> var1, Z var2);

   <Z> Z getValue(Parameter<Z> var1);

   <Z> Z get(Parameter<Z> var1);

   T getReturnValue();

   Results getResults();
}
