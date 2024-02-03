package org.jooq.util.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Table;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;

public class JDBCTableDefinition extends AbstractTableDefinition {
   private final Table<?> table;

   public JDBCTableDefinition(SchemaDefinition schema, Table<?> table) {
      super(schema, table.getName(), "");
      this.table = table;
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      int ordinal = 0;
      Field[] var3 = this.table.fields();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field<?> field = var3[var5];
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), field.getDataType().getTypeName(), field.getDataType().length(), field.getDataType().precision(), field.getDataType().scale(), field.getDataType().nullable(), this.create().renderInlined(field.getDataType().defaultValue()), (Name)null);
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), field.getName(), ordinal, type, false, (String)null);
         result.add(column);
         ++ordinal;
      }

      return result;
   }
}
