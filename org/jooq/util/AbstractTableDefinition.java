package org.jooq.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

public abstract class AbstractTableDefinition extends AbstractElementContainerDefinition<ColumnDefinition> implements TableDefinition {
   private List<ParameterDefinition> parameters;
   private TableDefinition parentTable = null;
   private List<TableDefinition> childTables = new ArrayList();

   public AbstractTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public final UniqueKeyDefinition getPrimaryKey() {
      UniqueKeyDefinition primaryKey = null;
      Iterator var2 = this.getColumns().iterator();

      ColumnDefinition column;
      do {
         if (!var2.hasNext()) {
            return primaryKey;
         }

         column = (ColumnDefinition)var2.next();
      } while(column.getPrimaryKey() == null);

      primaryKey = column.getPrimaryKey();
      return primaryKey;
   }

   public final List<UniqueKeyDefinition> getUniqueKeys() {
      return this.getDatabase().getRelations().getUniqueKeys((TableDefinition)this);
   }

   public final List<ForeignKeyDefinition> getForeignKeys() {
      return this.getDatabase().getRelations().getForeignKeys((TableDefinition)this);
   }

   public final List<CheckConstraintDefinition> getCheckConstraints() {
      return this.getDatabase().getRelations().getCheckConstraints(this);
   }

   public final IdentityDefinition getIdentity() {
      IdentityDefinition identity = null;
      Iterator var2 = this.getColumns().iterator();

      while(var2.hasNext()) {
         ColumnDefinition column = (ColumnDefinition)var2.next();
         if (column.isIdentity()) {
            identity = new DefaultIdentityDefinition(column);
            break;
         }
      }

      return identity;
   }

   public final void setParentTable(TableDefinition parentTable) {
      this.parentTable = parentTable;
   }

   public final TableDefinition getParentTable() {
      return this.parentTable;
   }

   public final List<TableDefinition> getChildTables() {
      return this.childTables;
   }

   public final Table<Record> getTable() {
      return DSL.table(this.getQualifiedName());
   }

   public final List<ColumnDefinition> getColumns() {
      return this.getElements();
   }

   public final ColumnDefinition getColumn(String columnName) {
      return (ColumnDefinition)this.getElement(columnName);
   }

   public final ColumnDefinition getColumn(String columnName, boolean ignoreCase) {
      return (ColumnDefinition)this.getElement(columnName, ignoreCase);
   }

   public final ColumnDefinition getColumn(int columnIndex) {
      return (ColumnDefinition)this.getElement(columnIndex);
   }

   public final List<ParameterDefinition> getParameters() {
      if (this.parameters == null) {
         this.parameters = this.getParameters0();
      }

      return this.parameters;
   }

   public boolean isTableValuedFunction() {
      return false;
   }

   protected List<ColumnDefinition> getElements0() throws SQLException {
      return null;
   }

   protected List<ParameterDefinition> getParameters0() {
      return Collections.emptyList();
   }
}
