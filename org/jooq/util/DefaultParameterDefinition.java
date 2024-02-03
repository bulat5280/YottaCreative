package org.jooq.util;

public class DefaultParameterDefinition extends AbstractTypedElementDefinition<RoutineDefinition> implements ParameterDefinition {
   private final boolean isDefaulted;
   private final boolean isUnnamed;

   public DefaultParameterDefinition(RoutineDefinition routine, String name, int position, DataTypeDefinition type) {
      this(routine, name, position, type, false, false);
   }

   public DefaultParameterDefinition(RoutineDefinition routine, String name, int position, DataTypeDefinition type, boolean isDefaulted) {
      this(routine, name, position, type, isDefaulted, false);
   }

   public DefaultParameterDefinition(RoutineDefinition routine, String name, int position, DataTypeDefinition type, boolean isDefaulted, boolean isUnnamed) {
      super(routine, name, position, type, (String)null);
      this.isDefaulted = isDefaulted;
      this.isUnnamed = isUnnamed;
   }

   public boolean isDefaulted() {
      return this.isDefaulted;
   }

   public boolean isUnnamed() {
      return this.isUnnamed;
   }
}
