package org.jooq.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

public abstract class AbstractElementContainerDefinition<E extends TypedElementDefinition<?>> extends AbstractDefinition {
   protected static final Pattern PRECISION_SCALE = Pattern.compile("\\((\\d+)\\s*(?:,\\s*(\\d+))?\\)");
   private static final JooqLogger log = JooqLogger.getLogger(AbstractElementContainerDefinition.class);
   private List<E> elements;
   private final PackageDefinition pkg;

   public AbstractElementContainerDefinition(SchemaDefinition schema, String name, String comment) {
      this(schema, (PackageDefinition)null, name, comment);
   }

   public AbstractElementContainerDefinition(SchemaDefinition schema, PackageDefinition pkg, String name, String comment) {
      super(schema.getDatabase(), schema, name, comment);
      this.pkg = pkg;
   }

   public final PackageDefinition getPackage() {
      return this.pkg;
   }

   public final List<Definition> getDefinitionPath() {
      List<Definition> result = new ArrayList();
      result.addAll(this.getSchema().getDefinitionPath());
      if (this.pkg != null) {
         result.add(this.pkg);
      }

      result.add(this);
      return result;
   }

   protected final List<E> getElements() {
      if (this.elements == null) {
         this.elements = new ArrayList();

         try {
            Database db = this.getDatabase();
            List<E> e = this.getElements0();
            if (this instanceof TableDefinition) {
               boolean hasIdentity = false;
               Iterator var4 = e.iterator();

               while(var4.hasNext()) {
                  E c = (TypedElementDefinition)var4.next();
                  boolean isIdentity = ((ColumnDefinition)c).isIdentity();
                  if (isIdentity) {
                     if (hasIdentity) {
                        log.warn("Multiple identities", (Object)("Table " + this.getOutputName() + " has multiple identity columns. Only the first one is considered."));
                        break;
                     }

                     hasIdentity = true;
                  }
               }
            }

            if (this instanceof TableDefinition && db.getIncludeExcludeColumns()) {
               this.elements = db.filterExcludeInclude(e);
               log.info("Columns fetched", (Object)AbstractDatabase.fetchedSize(e, this.elements));
            } else {
               this.elements = e;
            }
         } catch (Exception var7) {
            log.error("Error while initialising type", (Throwable)var7);
         }
      }

      return this.elements;
   }

   protected final E getElement(String name) {
      return this.getElement(name, false);
   }

   protected final E getElement(String name, boolean ignoreCase) {
      return (TypedElementDefinition)AbstractDatabase.getDefinition(this.getElements(), name, ignoreCase);
   }

   protected final E getElement(int index) {
      return (TypedElementDefinition)this.getElements().get(index);
   }

   protected abstract List<E> getElements0() throws SQLException;

   protected Number parsePrecision(String typeName) {
      if (typeName.contains("(")) {
         Matcher m = PRECISION_SCALE.matcher(typeName);
         if (m.find() && !StringUtils.isBlank(m.group(1))) {
            return Integer.valueOf(m.group(1));
         }
      }

      return 0;
   }

   protected Number parseScale(String typeName) {
      if (typeName.contains("(")) {
         Matcher m = PRECISION_SCALE.matcher(typeName);
         if (m.find() && !StringUtils.isBlank(m.group(2))) {
            return Integer.valueOf(m.group(2));
         }
      }

      return 0;
   }

   protected String parseTypeName(String typeName) {
      return typeName.replace(" NOT NULL", "");
   }

   protected boolean parseNotNull(String typeName) {
      return typeName.toUpperCase().contains("NOT NULL");
   }
}
