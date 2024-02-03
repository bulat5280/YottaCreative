package org.jooq.util.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.mysql.information_schema.tables.Columns;

public class MySQLTableDefinition extends AbstractTableDefinition {
   public MySQLTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Columns.ORDINAL_POSITION, Columns.COLUMN_NAME, Columns.COLUMN_COMMENT, Columns.COLUMN_TYPE, Columns.DATA_TYPE, Columns.IS_NULLABLE, Columns.COLUMN_DEFAULT, Columns.CHARACTER_MAXIMUM_LENGTH, Columns.NUMERIC_PRECISION, Columns.NUMERIC_SCALE, Columns.EXTRA).from(Columns.COLUMNS).where(new Condition[]{Columns.TABLE_SCHEMA.equal(this.getSchema().getName())}).and(Columns.TABLE_NAME.equal(this.getName())).orderBy(Columns.ORDINAL_POSITION).iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String dataType = (String)record.get((Field)Columns.DATA_TYPE);
         if (this.getDatabase().supportsUnsignedTypes() && Arrays.asList("tinyint", "smallint", "mediumint", "int", "bigint").contains(dataType.toLowerCase()) && ((String)record.get((Field)Columns.COLUMN_TYPE)).toLowerCase().contains("unsigned")) {
            dataType = dataType + "unsigned";
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), dataType, (Number)record.get((Field)Columns.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Columns.NUMERIC_PRECISION), (Number)record.get((Field)Columns.NUMERIC_SCALE), (Boolean)record.get((Field)Columns.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Columns.COLUMN_DEFAULT), DSL.name(this.getSchema().getName(), this.getName() + "_" + (String)record.get((Field)Columns.COLUMN_NAME)));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Columns.COLUMN_NAME), (Integer)record.get((Field)Columns.ORDINAL_POSITION, (Class)Integer.TYPE), type, "auto_increment".equalsIgnoreCase((String)record.get((Field)Columns.EXTRA)), (String)record.get((Field)Columns.COLUMN_COMMENT));
         result.add(column);
      }

      return result;
   }
}
