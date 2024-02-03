package org.jooq.util;

public interface ParameterDefinition extends TypedElementDefinition<RoutineDefinition> {
   boolean isDefaulted();

   boolean isUnnamed();
}
