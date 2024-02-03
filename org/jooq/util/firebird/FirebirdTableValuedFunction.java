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
import org.jooq.util.ParameterDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.firebird.rdb.Tables;
import org.jooq.util.firebird.rdb.tables.Rdb$fields;
import org.jooq.util.firebird.rdb.tables.Rdb$procedureParameters;

public class FirebirdTableValuedFunction extends AbstractTableDefinition {
   private final FirebirdRoutineDefinition routine;

   public FirebirdTableValuedFunction(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
      this.routine = new FirebirdRoutineDefinition(schema, name);
   }

   protected List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Rdb$procedureParameters p = Tables.RDB$PROCEDURE_PARAMETERS.as("p");
      Rdb$fields f = Tables.RDB$FIELDS.as("f");
      Iterator var4 = this.create().select(p.RDB$PARAMETER_NUMBER, p.RDB$PARAMETER_NAME.trim(), p.RDB$DESCRIPTION, p.RDB$DEFAULT_VALUE, DSL.bitOr(p.RDB$NULL_FLAG.nvl(Short.valueOf((short)0)), f.RDB$NULL_FLAG.nvl(Short.valueOf((short)0))).as((Field)p.RDB$NULL_FLAG), p.RDB$DEFAULT_SOURCE, FirebirdDatabase.CHARACTER_LENGTH(f).as("CHARACTER_LENGTH"), f.RDB$FIELD_PRECISION, FirebirdDatabase.FIELD_SCALE(f).as("FIELD_SCALE"), FirebirdDatabase.FIELD_TYPE(f).as("FIELD_TYPE"), f.RDB$FIELD_SUB_TYPE).from(p).leftOuterJoin((TableLike)f).on(new Condition[]{p.RDB$FIELD_SOURCE.eq(f.RDB$FIELD_NAME)}).where(new Condition[]{p.RDB$PROCEDURE_NAME.eq(this.getName())}).and(p.RDB$PARAMETER_TYPE.eq(Short.valueOf((short)1))).orderBy(p.RDB$PARAMETER_NUMBER).iterator();

      while(var4.hasNext()) {
         Record record = (Record)var4.next();
         DefaultDataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), (String)record.get("FIELD_TYPE", String.class), (Number)record.get("CHARACTER_LENGTH", Short.TYPE), (Number)record.get((Field)f.RDB$FIELD_PRECISION), (Number)record.get("FIELD_SCALE", Integer.class), (Short)record.get((Field)p.RDB$NULL_FLAG) == 0, (String)record.get((Field)p.RDB$DEFAULT_SOURCE));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get(p.RDB$PARAMETER_NAME.trim()), (Short)record.get((Field)p.RDB$PARAMETER_NUMBER), type, false, (String)null);
         result.add(column);
      }

      return result;
   }

   public boolean isTableValuedFunction() {
      return true;
   }

   protected List<ParameterDefinition> getParameters0() {
      return this.routine.getInParameters();
   }
}
