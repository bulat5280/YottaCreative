package org.jooq.util;

import java.util.List;

public interface UDTDefinition extends PackageDefinition {
   PackageDefinition getPackage();

   List<AttributeDefinition> getAttributes();

   AttributeDefinition getAttribute(String var1);

   AttributeDefinition getAttribute(int var1);

   List<RoutineDefinition> getRoutines();
}
