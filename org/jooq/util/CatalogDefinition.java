package org.jooq.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jooq.tools.StringUtils;

public class CatalogDefinition extends AbstractDefinition {
   public CatalogDefinition(Database database, String name, String comment) {
      super(database, (SchemaDefinition)null, name, comment);
   }

   public final CatalogDefinition getCatalog() {
      return this;
   }

   public final List<SchemaDefinition> getSchemata() {
      return this.getDatabase().getSchemata(this);
   }

   public final SchemaDefinition getSchema(String name) {
      Iterator var2 = this.getSchemata().iterator();

      SchemaDefinition schema;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         schema = (SchemaDefinition)var2.next();
      } while(!schema.getInputName().equals(name));

      return schema;
   }

   public final String getOutputName() {
      return this.getDatabase().getOutputCatalog(this.getInputName());
   }

   public final List<Definition> getDefinitionPath() {
      return Arrays.asList(this);
   }

   public boolean isDefaultCatalog() {
      return StringUtils.isBlank(this.getOutputName());
   }
}
