package org.jooq.util.postgres;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectField;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractRoutineDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.Database;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.DefaultParameterDefinition;
import org.jooq.util.InOutDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.ParameterDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.postgres.information_schema.Tables;

public class PostgresRoutineDefinition extends AbstractRoutineDefinition {
   private final String specificName;

   public PostgresRoutineDefinition(Database database, Record record) {
      super(database.getSchema((String)record.get((Field)Tables.ROUTINES.ROUTINE_SCHEMA)), (PackageDefinition)null, (String)record.get((Field)Tables.ROUTINES.ROUTINE_NAME), (String)null, (String)record.get("overload", String.class), (Boolean)record.get((Field)org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PROISAGG, (Class)Boolean.TYPE));
      if (!Arrays.asList("void", "record").contains(record.get("data_type"))) {
         SchemaDefinition typeSchema = null;
         String schemaName = (String)record.get((Field)Tables.ROUTINES.TYPE_UDT_SCHEMA);
         if (schemaName != null) {
            typeSchema = this.getDatabase().getSchema(schemaName);
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), typeSchema == null ? database.getSchema((String)record.get((Field)Tables.ROUTINES.ROUTINE_SCHEMA)) : typeSchema, (String)record.get("data_type", String.class), (Number)record.get((Field)Tables.ROUTINES.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.ROUTINES.NUMERIC_PRECISION), (Number)record.get((Field)Tables.ROUTINES.NUMERIC_SCALE), (Boolean)null, (String)null, DSL.name((String)record.get((Field)Tables.ROUTINES.TYPE_UDT_SCHEMA), (String)record.get((Field)Tables.ROUTINES.TYPE_UDT_NAME)));
         this.returnValue = new DefaultParameterDefinition(this, "RETURN_VALUE", -1, type);
      }

      this.specificName = (String)record.get((Field)Tables.ROUTINES.SPECIFIC_NAME);
   }

   PostgresRoutineDefinition(Database database, String schema, String name, String specificName) {
      super(database.getSchema(schema), (PackageDefinition)null, name, (String)null, (String)null);
      this.specificName = specificName;
   }

   protected void init0() throws SQLException {
      Iterator var1 = this.create().select(Tables.PARAMETERS.PARAMETER_NAME, Tables.PARAMETERS.DATA_TYPE, Tables.PARAMETERS.CHARACTER_MAXIMUM_LENGTH, Tables.PARAMETERS.NUMERIC_PRECISION, Tables.PARAMETERS.NUMERIC_SCALE, Tables.PARAMETERS.UDT_SCHEMA, Tables.PARAMETERS.UDT_NAME, Tables.PARAMETERS.ORDINAL_POSITION, Tables.PARAMETERS.PARAMETER_MODE, (SelectField)(((PostgresDatabase)this.getDatabase()).is94() ? Tables.PARAMETERS.PARAMETER_DEFAULT : DSL.inline((String)null).as(Tables.PARAMETERS.PARAMETER_DEFAULT))).from(Tables.PARAMETERS).where(new Condition[]{Tables.PARAMETERS.SPECIFIC_SCHEMA.equal(this.getSchema().getName())}).and(Tables.PARAMETERS.SPECIFIC_NAME.equal(this.specificName)).orderBy(Tables.PARAMETERS.ORDINAL_POSITION.asc()).fetch().iterator();

      while(var1.hasNext()) {
         Record record = (Record)var1.next();
         String inOut = (String)record.get((Field)Tables.PARAMETERS.PARAMETER_MODE);
         SchemaDefinition typeSchema = null;
         String schemaName = (String)record.get((Field)Tables.PARAMETERS.UDT_SCHEMA);
         if (schemaName != null) {
            typeSchema = this.getDatabase().getSchema(schemaName);
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), typeSchema, (String)record.get((Field)Tables.PARAMETERS.DATA_TYPE), (Number)record.get((Field)Tables.PARAMETERS.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.PARAMETERS.NUMERIC_PRECISION), (Number)record.get((Field)Tables.PARAMETERS.NUMERIC_SCALE), (Boolean)null, (String)record.get((Field)Tables.PARAMETERS.PARAMETER_DEFAULT), DSL.name((String)record.get((Field)Tables.PARAMETERS.UDT_SCHEMA), (String)record.get((Field)Tables.PARAMETERS.UDT_NAME)));
         ParameterDefinition parameter = new DefaultParameterDefinition(this, (String)record.get((Field)Tables.PARAMETERS.PARAMETER_NAME), (Integer)record.get((Field)Tables.PARAMETERS.ORDINAL_POSITION), type, record.get((Field)Tables.PARAMETERS.PARAMETER_DEFAULT) != null, StringUtils.isBlank((String)record.get((Field)Tables.PARAMETERS.PARAMETER_NAME)));
         this.addParameter(InOutDefinition.getFromString(inOut), parameter);
      }

   }
}
