package org.jooq.util.hsqldb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.hsqldb.information_schema.Tables;

public class HSQLDBTableDefinition extends AbstractTableDefinition {
   public HSQLDBTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.COLUMNS.COLUMN_NAME, Tables.COLUMNS.ORDINAL_POSITION, DSL.nvl((Field)Tables.ELEMENT_TYPES.COLLECTION_TYPE_IDENTIFIER, (Field)DSL.nvl2(Tables.COLUMNS.INTERVAL_TYPE, (Field)DSL.concat(Tables.COLUMNS.DATA_TYPE, DSL.val(" "), Tables.COLUMNS.INTERVAL_TYPE), (Field)Tables.COLUMNS.DATA_TYPE)).as("datatype"), Tables.COLUMNS.IDENTITY_GENERATION, Tables.COLUMNS.IS_NULLABLE, Tables.COLUMNS.COLUMN_DEFAULT, Tables.COLUMNS.CHARACTER_MAXIMUM_LENGTH, Tables.COLUMNS.NUMERIC_PRECISION, Tables.COLUMNS.NUMERIC_SCALE, Tables.COLUMNS.UDT_NAME).from(Tables.COLUMNS).leftOuterJoin((TableLike)Tables.ELEMENT_TYPES).on(new Condition[]{Tables.COLUMNS.TABLE_SCHEMA.equal(Tables.ELEMENT_TYPES.OBJECT_SCHEMA)}).and(Tables.COLUMNS.TABLE_NAME.equal(Tables.ELEMENT_TYPES.OBJECT_NAME)).and(Tables.COLUMNS.DTD_IDENTIFIER.equal(Tables.ELEMENT_TYPES.COLLECTION_TYPE_IDENTIFIER)).where(new Condition[]{Tables.COLUMNS.TABLE_SCHEMA.equal(this.getSchema().getName())}).and(Tables.COLUMNS.TABLE_NAME.equal(this.getName())).orderBy(Tables.COLUMNS.ORDINAL_POSITION).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), (String)record.get("datatype", String.class), (Number)record.get((Field)Tables.COLUMNS.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.COLUMNS.NUMERIC_PRECISION), (Number)record.get((Field)Tables.COLUMNS.NUMERIC_SCALE), (Boolean)record.get((Field)Tables.COLUMNS.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Tables.COLUMNS.COLUMN_DEFAULT), (String)record.get((Field)Tables.COLUMNS.UDT_NAME));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Tables.COLUMNS.COLUMN_NAME), (Integer)record.get((Field)Tables.COLUMNS.ORDINAL_POSITION, (Class)Integer.TYPE), type, null != record.get((Field)Tables.COLUMNS.IDENTITY_GENERATION), (String)null);
         result.add(column);
      }

      return result;
   }
}
