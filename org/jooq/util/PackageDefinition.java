package org.jooq.util;

import java.util.List;

public interface PackageDefinition extends Definition {
   List<RoutineDefinition> getRoutines();

   List<AttributeDefinition> getConstants();
}
