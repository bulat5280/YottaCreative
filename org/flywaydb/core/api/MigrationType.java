package org.flywaydb.core.api;

public enum MigrationType {
   SCHEMA(true, false),
   BASELINE(true, false),
   SQL(false, false),
   UNDO_SQL(false, true),
   JDBC(false, false),
   UNDO_JDBC(false, true),
   SPRING_JDBC(false, false),
   UNDO_SPRING_JDBC(false, true),
   CUSTOM(false, false),
   UNDO_CUSTOM(false, true);

   private final boolean synthetic;
   private final boolean undo;

   private MigrationType(boolean synthetic, boolean undo) {
      this.synthetic = synthetic;
      this.undo = undo;
   }

   public boolean isSynthetic() {
      return this.synthetic;
   }

   public boolean isUndo() {
      return this.undo;
   }
}
