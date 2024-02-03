package org.jooq.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public abstract class AbstractDefinition implements Definition {
   private final Database database;
   private final SchemaDefinition schema;
   private final String name;
   private final String comment;
   private final String overload;
   private transient String qualifiedInputName;
   private transient String qualifiedOutputName;
   private transient Name qualifiedInputNamePart;
   private transient Name qualifiedOutputNamePart;
   private transient Integer hashCode;

   public AbstractDefinition(Database database, SchemaDefinition schema, String name) {
      this(database, schema, name, (String)null);
   }

   public AbstractDefinition(Database database, SchemaDefinition schema, String name, String comment) {
      this(database, schema, name, comment, (String)null);
   }

   public AbstractDefinition(Database database, SchemaDefinition schema, String name, String comment, String overload) {
      this.database = database;
      this.schema = schema == null && this instanceof SchemaDefinition ? (SchemaDefinition)this : schema;
      this.name = name;
      this.comment = comment;
      this.overload = overload;
   }

   public final String getOverload() {
      return this.overload;
   }

   public CatalogDefinition getCatalog() {
      return this.getSchema().getCatalog();
   }

   public final SchemaDefinition getSchema() {
      return this.schema;
   }

   public final String getName() {
      return this.name;
   }

   public final String getInputName() {
      return this.name;
   }

   public String getOutputName() {
      return this.getInputName();
   }

   public final String getComment() {
      return this.comment;
   }

   public final String getQualifiedName() {
      return this.getQualifiedInputName();
   }

   public final String getQualifiedInputName() {
      if (this.qualifiedInputName == null) {
         StringBuilder sb = new StringBuilder();
         String separator = "";
         Iterator var3 = this.getDefinitionPath().iterator();

         while(true) {
            Definition part;
            do {
               do {
                  if (!var3.hasNext()) {
                     this.qualifiedInputName = sb.toString();
                     return this.qualifiedInputName;
                  }

                  part = (Definition)var3.next();
               } while(part instanceof CatalogDefinition && ((CatalogDefinition)part).isDefaultCatalog());
            } while(part instanceof SchemaDefinition && ((SchemaDefinition)part).isDefaultSchema());

            sb.append(separator);
            sb.append(part.getInputName());
            separator = ".";
         }
      } else {
         return this.qualifiedInputName;
      }
   }

   public final String getQualifiedOutputName() {
      if (this.qualifiedOutputName == null) {
         StringBuilder sb = new StringBuilder();
         String separator = "";
         Iterator var3 = this.getDefinitionPath().iterator();

         while(true) {
            Definition part;
            do {
               do {
                  if (!var3.hasNext()) {
                     this.qualifiedOutputName = sb.toString();
                     return this.qualifiedOutputName;
                  }

                  part = (Definition)var3.next();
               } while(part instanceof CatalogDefinition && ((CatalogDefinition)part).isDefaultCatalog());
            } while(part instanceof SchemaDefinition && ((SchemaDefinition)part).isDefaultSchema());

            sb.append(separator);
            sb.append(part.getOutputName());
            separator = ".";
         }
      } else {
         return this.qualifiedOutputName;
      }
   }

   public final Name getQualifiedNamePart() {
      return this.getQualifiedInputNamePart();
   }

   public final Name getQualifiedInputNamePart() {
      if (this.qualifiedInputNamePart == null) {
         List<String> list = new ArrayList();
         Iterator var2 = this.getDefinitionPath().iterator();

         while(true) {
            Definition part;
            do {
               do {
                  if (!var2.hasNext()) {
                     this.qualifiedInputNamePart = DSL.name((Collection)list);
                     return this.qualifiedInputNamePart;
                  }

                  part = (Definition)var2.next();
               } while(part instanceof CatalogDefinition && ((CatalogDefinition)part).isDefaultCatalog());
            } while(part instanceof SchemaDefinition && ((SchemaDefinition)part).isDefaultSchema());

            list.add(part.getInputName());
         }
      } else {
         return this.qualifiedInputNamePart;
      }
   }

   public final Name getQualifiedOutputNamePart() {
      if (this.qualifiedOutputNamePart == null) {
         List<String> list = new ArrayList();
         Iterator var2 = this.getDefinitionPath().iterator();

         while(true) {
            Definition part;
            do {
               do {
                  if (!var2.hasNext()) {
                     this.qualifiedOutputNamePart = DSL.name((Collection)list);
                     return this.qualifiedOutputNamePart;
                  }

                  part = (Definition)var2.next();
               } while(part instanceof CatalogDefinition && ((CatalogDefinition)part).isDefaultCatalog());
            } while(part instanceof SchemaDefinition && ((SchemaDefinition)part).isDefaultSchema());

            list.add(part.getOutputName());
         }
      } else {
         return this.qualifiedOutputNamePart;
      }
   }

   public final Database getDatabase() {
      return this.database;
   }

   protected final Connection getConnection() {
      return this.database.getConnection();
   }

   public final String toString() {
      return this.getQualifiedName();
   }

   public final boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof Definition) {
         Definition that = (Definition)obj;
         return that.getQualifiedName().equals(this.getQualifiedName());
      } else {
         return false;
      }
   }

   public final int hashCode() {
      if (this.hashCode == null) {
         this.hashCode = this.getQualifiedName().hashCode();
      }

      return this.hashCode;
   }

   protected final DSLContext create() {
      return this.database.create();
   }

   protected final DSLContext create(boolean muteExceptions) {
      return this.database instanceof AbstractDatabase ? ((AbstractDatabase)this.database).create(muteExceptions) : this.database.create();
   }

   protected final SQLDialect getDialect() {
      return this.database.getDialect();
   }
}
