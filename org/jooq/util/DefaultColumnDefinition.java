package org.jooq.util;

import java.util.Collections;
import java.util.List;
import org.jooq.tools.JooqLogger;

public class DefaultColumnDefinition extends AbstractTypedElementDefinition<TableDefinition> implements ColumnDefinition {
   private static final JooqLogger log = JooqLogger.getLogger(DefaultColumnDefinition.class);
   private final int position;
   private final boolean isIdentity;

   public DefaultColumnDefinition(TableDefinition table, String name, int position, DataTypeDefinition type, boolean isIdentity, String comment) {
      super(table, name, position, type, comment);
      this.position = position;
      this.isIdentity = isIdentity || isSyntheticIdentity(this);
   }

   private static boolean isSyntheticIdentity(DefaultColumnDefinition column) {
      AbstractDatabase db = (AbstractDatabase)column.getDatabase();
      String[] syntheticIdentities = db.getSyntheticIdentities();
      boolean match = !db.filterExcludeInclude(Collections.singletonList(column), (String[])null, syntheticIdentities, db.getFilters()).isEmpty();
      if (match) {
         log.info("Synthetic Identity: " + column.getQualifiedName());
      }

      return match;
   }

   public final int getPosition() {
      return this.position;
   }

   public final UniqueKeyDefinition getPrimaryKey() {
      return this.getDatabase().getRelations().getPrimaryKey(this);
   }

   public List<UniqueKeyDefinition> getUniqueKeys() {
      return this.getDatabase().getRelations().getUniqueKeys((ColumnDefinition)this);
   }

   public final List<ForeignKeyDefinition> getForeignKeys() {
      return this.getDatabase().getRelations().getForeignKeys((ColumnDefinition)this);
   }

   public final boolean isIdentity() {
      return this.isIdentity;
   }

   public final boolean isNullable() {
      return this.getType().isNullable();
   }
}
