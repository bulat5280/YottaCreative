package org.jooq.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jooq.tools.JooqLogger;

public abstract class AbstractPackageDefinition extends AbstractDefinition implements PackageDefinition {
   private static final JooqLogger log = JooqLogger.getLogger(AbstractPackageDefinition.class);
   private List<RoutineDefinition> routines;
   private List<AttributeDefinition> constants;

   public AbstractPackageDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema.getDatabase(), schema, name, comment);
   }

   public List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      result.add(this);
      return result;
   }

   public final List<RoutineDefinition> getRoutines() {
      if (this.routines == null) {
         this.routines = new ArrayList();

         try {
            this.routines = this.getRoutines0();
         } catch (Exception var2) {
            log.error("Error while initialising package", (Throwable)var2);
         }
      }

      return this.routines;
   }

   protected abstract List<RoutineDefinition> getRoutines0() throws SQLException;

   public final List<AttributeDefinition> getConstants() {
      if (this.constants == null) {
         this.constants = new ArrayList();

         try {
            this.constants = this.getConstants0();
         } catch (Exception var2) {
            log.error("Error while initialising package", (Throwable)var2);
         }
      }

      return this.constants;
   }

   protected abstract List<AttributeDefinition> getConstants0() throws SQLException;
}
