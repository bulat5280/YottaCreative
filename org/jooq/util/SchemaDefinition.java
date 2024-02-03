package org.jooq.util;

import java.util.Arrays;
import java.util.List;
import org.jooq.tools.StringUtils;

public class SchemaDefinition extends AbstractDefinition {
   private final CatalogDefinition catalog;

   public SchemaDefinition(Database database, String name, String comment) {
      this(database, name, comment, (CatalogDefinition)null);
   }

   public SchemaDefinition(Database database, String name, String comment, CatalogDefinition catalog) {
      super(database, (SchemaDefinition)null, name, comment);
      this.catalog = catalog == null ? new CatalogDefinition(database, "", "") : catalog;
   }

   public final CatalogDefinition getCatalog() {
      return this.catalog;
   }

   public final List<TableDefinition> getTables() {
      return this.getDatabase().getTables(this);
   }

   public final String getOutputName() {
      return this.getDatabase().getOutputSchema(this.getCatalog().getInputName(), this.getInputName());
   }

   public final List<Definition> getDefinitionPath() {
      return StringUtils.isEmpty(this.catalog.getName()) ? Arrays.asList(this) : Arrays.asList(this.catalog, this);
   }

   public boolean isDefaultSchema() {
      return StringUtils.isBlank(this.getOutputName());
   }
}
