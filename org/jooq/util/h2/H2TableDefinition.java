package org.jooq.util.h2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.h2.information_schema.tables.Columns;

public class H2TableDefinition extends AbstractTableDefinition {
   public H2TableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Columns.COLUMN_NAME, Columns.ORDINAL_POSITION, Columns.TYPE_NAME, Columns.CHARACTER_MAXIMUM_LENGTH, Columns.NUMERIC_PRECISION, Columns.NUMERIC_SCALE, Columns.IS_NULLABLE, Columns.COLUMN_DEFAULT, Columns.REMARKS, Columns.SEQUENCE_NAME).from(Columns.COLUMNS).where(new Condition[]{Columns.TABLE_SCHEMA.equal(this.getSchema().getName())}).and(Columns.TABLE_NAME.equal(this.getName())).orderBy(Columns.ORDINAL_POSITION).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), (String)record.get((Field)Columns.TYPE_NAME), (Number)record.get((Field)Columns.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Columns.NUMERIC_PRECISION), (Number)record.get((Field)Columns.NUMERIC_SCALE), (Boolean)record.get((Field)Columns.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Columns.COLUMN_DEFAULT));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Columns.COLUMN_NAME), (Integer)record.get((Field)Columns.ORDINAL_POSITION), type, null != record.get((Field)Columns.SEQUENCE_NAME) || StringUtils.defaultString((String)record.get((Field)Columns.COLUMN_DEFAULT)).trim().toLowerCase().startsWith("nextval"), (String)record.get((Field)Columns.REMARKS));
         result.add(column);
      }

      return result;
   }
}
