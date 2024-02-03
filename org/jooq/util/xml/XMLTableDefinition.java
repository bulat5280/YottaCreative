package org.jooq.util.xml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.Table;

public class XMLTableDefinition extends AbstractTableDefinition {
   private final InformationSchema info;
   private final Table table;

   public XMLTableDefinition(SchemaDefinition schema, InformationSchema info, Table table) {
      super(schema, table.getTableName(), "");
      this.info = info;
      this.table = table;
   }

   protected List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.info.getColumns().iterator();

      while(var2.hasNext()) {
         Column column = (Column)var2.next();
         if (StringUtils.equals(this.table.getTableCatalog(), column.getTableCatalog()) && StringUtils.equals(this.table.getTableSchema(), column.getTableSchema()) && StringUtils.equals(this.table.getTableName(), column.getTableName())) {
            SchemaDefinition schema = this.getDatabase().getSchema(column.getTableSchema());
            DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), schema, column.getDataType(), XMLDatabase.unbox(column.getCharacterMaximumLength()), XMLDatabase.unbox(column.getNumericPrecision()), XMLDatabase.unbox(column.getNumericScale()), column.isIsNullable(), column.getColumnDefault());
            result.add(new DefaultColumnDefinition(this, column.getColumnName(), XMLDatabase.unbox(column.getOrdinalPosition()), type, column.getIdentityGeneration() != null, ""));
         }
      }

      return result;
   }
}
