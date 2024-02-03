package org.jooq.util;

import java.util.Collections;
import java.util.List;

public abstract class AbstractUDTDefinition extends AbstractElementContainerDefinition<AttributeDefinition> implements UDTDefinition {
   private List<RoutineDefinition> routines;

   public AbstractUDTDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, (PackageDefinition)null, name, comment);
   }

   public AbstractUDTDefinition(SchemaDefinition schema, PackageDefinition pkg, String name, String comment) {
      super(schema, pkg, name, comment);
   }

   public final List<AttributeDefinition> getAttributes() {
      return this.getElements();
   }

   public final AttributeDefinition getAttribute(String attributeName) {
      return (AttributeDefinition)this.getElement(attributeName);
   }

   public final AttributeDefinition getAttribute(int attributeIndex) {
      return (AttributeDefinition)this.getElement(attributeIndex);
   }

   public final List<RoutineDefinition> getRoutines() {
      if (this.routines == null) {
         this.routines = this.getRoutines0();
      }

      return this.routines;
   }

   protected abstract List<RoutineDefinition> getRoutines0();

   public List<AttributeDefinition> getConstants() {
      return Collections.emptyList();
   }
}
