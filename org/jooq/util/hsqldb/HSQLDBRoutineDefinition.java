package org.jooq.util.hsqldb;

import java.sql.SQLException;
import java.util.Iterator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractRoutineDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.DefaultParameterDefinition;
import org.jooq.util.InOutDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.ParameterDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.hsqldb.information_schema.Tables;

public class HSQLDBRoutineDefinition extends AbstractRoutineDefinition {
   private final String specificName;

   public HSQLDBRoutineDefinition(SchemaDefinition schema, String name, String specificName, String dataType, Number precision, Number scale) {
      this(schema, name, specificName, dataType, precision, scale, false);
   }

   public HSQLDBRoutineDefinition(SchemaDefinition schema, String name, String specificName, String dataType, Number precision, Number scale, boolean aggregate) {
      super(schema, (PackageDefinition)null, name, (String)null, (String)null, aggregate);
      if (!StringUtils.isBlank(dataType)) {
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), dataType, precision, precision, scale, (Boolean)null, (String)null);
         this.returnValue = new DefaultParameterDefinition(this, "RETURN_VALUE", -1, type);
      }

      this.specificName = specificName;
   }

   protected void init0() throws SQLException {
      Result<?> result = this.create().select(Tables.PARAMETERS.PARAMETER_MODE, Tables.PARAMETERS.PARAMETER_NAME, DSL.nvl((Field)Tables.ELEMENT_TYPES.COLLECTION_TYPE_IDENTIFIER, (Field)Tables.PARAMETERS.DATA_TYPE).as("datatype"), Tables.PARAMETERS.CHARACTER_MAXIMUM_LENGTH, Tables.PARAMETERS.NUMERIC_PRECISION, Tables.PARAMETERS.NUMERIC_SCALE, Tables.PARAMETERS.ORDINAL_POSITION).from(Tables.PARAMETERS).join((TableLike)Tables.ROUTINES).on(Tables.PARAMETERS.SPECIFIC_SCHEMA.equal(Tables.ROUTINES.SPECIFIC_SCHEMA)).and(Tables.PARAMETERS.SPECIFIC_NAME.equal(Tables.ROUTINES.SPECIFIC_NAME)).leftOuterJoin(Tables.ELEMENT_TYPES).on(new Condition[]{Tables.ROUTINES.ROUTINE_SCHEMA.equal(Tables.ELEMENT_TYPES.OBJECT_SCHEMA)}).and(Tables.ROUTINES.ROUTINE_NAME.equal(Tables.ELEMENT_TYPES.OBJECT_NAME)).and(Tables.PARAMETERS.DTD_IDENTIFIER.equal(Tables.ELEMENT_TYPES.COLLECTION_TYPE_IDENTIFIER)).where(new Condition[]{Tables.PARAMETERS.SPECIFIC_SCHEMA.equal(this.getSchema().getName())}).and(Tables.PARAMETERS.SPECIFIC_NAME.equal(this.specificName)).and(DSL.condition((Field)DSL.val(!this.isAggregate())).or(Tables.PARAMETERS.ORDINAL_POSITION.eq(1L))).orderBy(Tables.PARAMETERS.ORDINAL_POSITION.asc()).fetch();
      Iterator var2 = result.iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         String inOut = (String)record.get((Field)Tables.PARAMETERS.PARAMETER_MODE);
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), (String)record.get("datatype", String.class), (Number)record.get((Field)Tables.PARAMETERS.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.PARAMETERS.NUMERIC_PRECISION), (Number)record.get((Field)Tables.PARAMETERS.NUMERIC_SCALE), (Boolean)null, (String)null);
         ParameterDefinition parameter = new DefaultParameterDefinition(this, ((String)record.get((Field)Tables.PARAMETERS.PARAMETER_NAME)).replaceAll("@", ""), (Integer)record.get((Field)Tables.PARAMETERS.ORDINAL_POSITION, (Class)Integer.TYPE), type);
         this.addParameter(InOutDefinition.getFromString(inOut), parameter);
      }

   }
}
