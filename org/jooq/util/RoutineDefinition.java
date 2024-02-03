package org.jooq.util;

import java.util.List;

public interface RoutineDefinition extends Definition {
   PackageDefinition getPackage();

   List<ParameterDefinition> getInParameters();

   List<ParameterDefinition> getOutParameters();

   List<ParameterDefinition> getAllParameters();

   ParameterDefinition getReturnValue();

   DataTypeDefinition getReturnType();

   boolean isSQLUsable();

   boolean isAggregate();
}
