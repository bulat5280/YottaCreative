package org.jooq.util.cubrid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableLike;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.cubrid.dba.Tables;

public class CUBRIDTableDefinition extends AbstractTableDefinition {
   public CUBRIDTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.DB_ATTRIBUTE.ATTR_NAME, Tables.DB_ATTRIBUTE.DEF_ORDER, Tables.DB_ATTRIBUTE.DATA_TYPE, Tables.DB_ATTRIBUTE.PREC, Tables.DB_ATTRIBUTE.SCALE, Tables.DB_ATTRIBUTE.IS_NULLABLE, Tables.DB_ATTRIBUTE.DEFAULT_VALUE, Tables.DB_SERIAL.NAME).from(Tables.DB_ATTRIBUTE).leftOuterJoin((TableLike)Tables.DB_SERIAL).on(new Condition[]{Tables.DB_ATTRIBUTE.ATTR_NAME.equal(Tables.DB_SERIAL.ATT_NAME).and(Tables.DB_ATTRIBUTE.CLASS_NAME.equal(Tables.DB_SERIAL.CLASS_NAME))}).where(new Condition[]{Tables.DB_ATTRIBUTE.CLASS_NAME.equal(this.getName())}).orderBy(Tables.DB_ATTRIBUTE.DEF_ORDER).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String dataType = (String)record.get((Field)Tables.DB_ATTRIBUTE.DATA_TYPE);
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), dataType, (Number)record.get((Field)Tables.DB_ATTRIBUTE.PREC), (Number)record.get((Field)Tables.DB_ATTRIBUTE.PREC), (Number)record.get((Field)Tables.DB_ATTRIBUTE.SCALE), (Boolean)record.get((Field)Tables.DB_ATTRIBUTE.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Tables.DB_ATTRIBUTE.DEFAULT_VALUE), this.getName() + "_" + (String)record.get((Field)Tables.DB_ATTRIBUTE.ATTR_NAME));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Tables.DB_ATTRIBUTE.ATTR_NAME), (Integer)record.get((Field)Tables.DB_ATTRIBUTE.DEF_ORDER), type, record.get((Field)Tables.DB_SERIAL.NAME) != null, (String)null);
         result.add(column);
      }

      return result;
   }
}
