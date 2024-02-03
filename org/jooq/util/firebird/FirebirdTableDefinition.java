package org.jooq.util.firebird;

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
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.firebird.rdb.Tables;
import org.jooq.util.firebird.rdb.tables.Rdb$fields;
import org.jooq.util.firebird.rdb.tables.Rdb$relationFields;

public class FirebirdTableDefinition extends AbstractTableDefinition {
   public FirebirdTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   protected List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Rdb$relationFields r = Tables.RDB$RELATION_FIELDS.as("r");
      Rdb$fields f = Tables.RDB$FIELDS.as("f");
      Iterator var4 = this.create().select(r.RDB$FIELD_NAME.trim(), r.RDB$DESCRIPTION, r.RDB$DEFAULT_VALUE, DSL.bitOr(r.RDB$NULL_FLAG.nvl(Short.valueOf((short)0)), f.RDB$NULL_FLAG.nvl(Short.valueOf((short)0))).as((Field)r.RDB$NULL_FLAG), r.RDB$DEFAULT_SOURCE, r.RDB$FIELD_POSITION, FirebirdDatabase.CHARACTER_LENGTH(f).as("CHARACTER_LENGTH"), f.RDB$FIELD_PRECISION, FirebirdDatabase.FIELD_SCALE(f).as("FIELD_SCALE"), FirebirdDatabase.FIELD_TYPE(f).as("FIELD_TYPE"), f.RDB$FIELD_SUB_TYPE).from(r).leftOuterJoin((TableLike)f).on(new Condition[]{r.RDB$FIELD_SOURCE.eq(f.RDB$FIELD_NAME)}).where(new Condition[]{r.RDB$RELATION_NAME.eq(this.getName())}).orderBy(r.RDB$FIELD_POSITION).fetch().iterator();

      while(var4.hasNext()) {
         Record record = (Record)var4.next();
         DefaultDataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), (String)record.get("FIELD_TYPE", String.class), (Number)record.get("CHARACTER_LENGTH", Short.TYPE), (Number)record.get((Field)f.RDB$FIELD_PRECISION), (Number)record.get("FIELD_SCALE", Integer.class), (Short)record.get((Field)r.RDB$NULL_FLAG) == 0, (String)record.get((Field)r.RDB$DEFAULT_SOURCE));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get(r.RDB$FIELD_NAME.trim()), (Short)record.get((Field)r.RDB$FIELD_POSITION), type, false, (String)null);
         result.add(column);
      }

      return result;
   }
}
