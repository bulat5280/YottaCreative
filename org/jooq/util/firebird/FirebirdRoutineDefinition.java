package org.jooq.util.firebird;

import java.sql.SQLException;
import java.util.Iterator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractRoutineDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.DefaultParameterDefinition;
import org.jooq.util.InOutDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.ParameterDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.firebird.rdb.Tables;
import org.jooq.util.firebird.rdb.tables.Rdb$fields;
import org.jooq.util.firebird.rdb.tables.Rdb$procedureParameters;

public class FirebirdRoutineDefinition extends AbstractRoutineDefinition {
   public FirebirdRoutineDefinition(SchemaDefinition schema, String name) {
      super(schema, (PackageDefinition)null, name, (String)null, (String)null, false);
   }

   protected void init0() throws SQLException {
      Rdb$procedureParameters p = Tables.RDB$PROCEDURE_PARAMETERS.as("p");
      Rdb$fields f = Tables.RDB$FIELDS.as("f");
      int i = 0;
      Iterator var4 = this.create().select(p.RDB$PARAMETER_NUMBER, p.RDB$PARAMETER_TYPE, p.RDB$PARAMETER_NAME.trim().as((Field)p.RDB$PARAMETER_NAME), FirebirdDatabase.FIELD_TYPE(f).as("FIELD_TYPE"), FirebirdDatabase.CHARACTER_LENGTH(f).as("CHARACTER_LENGTH"), f.RDB$FIELD_PRECISION, FirebirdDatabase.FIELD_SCALE(f).as("FIELD_SCALE"), DSL.bitOr(p.RDB$NULL_FLAG.nvl(Short.valueOf((short)0)), f.RDB$NULL_FLAG.nvl(Short.valueOf((short)0))).as((Field)p.RDB$NULL_FLAG), p.RDB$DEFAULT_SOURCE).from(p).leftOuterJoin((TableLike)f).on(new Condition[]{p.RDB$FIELD_SOURCE.eq(f.RDB$FIELD_NAME)}).where(new Condition[]{p.RDB$PROCEDURE_NAME.eq(this.getName())}).orderBy(p.RDB$PARAMETER_TYPE.desc(), p.RDB$PARAMETER_NUMBER.asc()).iterator();

      while(var4.hasNext()) {
         Record record = (Record)var4.next();
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), (String)record.get("FIELD_TYPE", String.class), (Number)record.get("CHARACTER_LENGTH", Short.TYPE), (Number)record.get((Field)f.RDB$FIELD_PRECISION), (Number)record.get("FIELD_SCALE", Integer.class), (Short)record.get((Field)p.RDB$NULL_FLAG) == 0, (String)record.get((Field)p.RDB$DEFAULT_SOURCE));
         ParameterDefinition parameter = new DefaultParameterDefinition(this, (String)record.get((Field)p.RDB$PARAMETER_NAME), i++, type);
         this.addParameter(((Integer)record.get((Field)p.RDB$PARAMETER_TYPE, (Class)Integer.TYPE)).equals(0) ? InOutDefinition.IN : InOutDefinition.OUT, parameter);
      }

   }
}
