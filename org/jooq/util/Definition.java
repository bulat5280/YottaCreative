package org.jooq.util;

import java.util.List;
import org.jooq.Name;

public interface Definition {
   Database getDatabase();

   CatalogDefinition getCatalog();

   SchemaDefinition getSchema();

   String getName();

   String getInputName();

   String getOutputName();

   String getComment();

   List<Definition> getDefinitionPath();

   String getQualifiedName();

   String getQualifiedInputName();

   String getQualifiedOutputName();

   Name getQualifiedNamePart();

   Name getQualifiedInputNamePart();

   Name getQualifiedOutputNamePart();

   String getOverload();
}
