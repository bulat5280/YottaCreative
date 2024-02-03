package org.jooq.util.h2;

import java.sql.SQLException;
import java.util.Iterator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractRoutineDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.DefaultParameterDefinition;
import org.jooq.util.InOutDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.ParameterDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.h2.information_schema.tables.FunctionColumns;

public class H2RoutineDefinition extends AbstractRoutineDefinition {
   public H2RoutineDefinition(SchemaDefinition schema, String name, String comment, String typeName, Number precision, Number scale) {
      super(schema, (PackageDefinition)null, name, comment, (String)null);
      if (!StringUtils.isBlank(typeName)) {
         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), schema, typeName, precision, precision, scale, (Boolean)null, (String)null);
         this.returnValue = new DefaultParameterDefinition(this, "RETURN_VALUE", -1, type);
      }

   }

   protected void init0() throws SQLException {
      Iterator var1 = this.create().select(FunctionColumns.COLUMN_NAME, FunctionColumns.TYPE_NAME, FunctionColumns.PRECISION, FunctionColumns.SCALE, FunctionColumns.POS, FunctionColumns.NULLABLE, FunctionColumns.COLUMN_DEFAULT).from(FunctionColumns.FUNCTION_COLUMNS).where(new Condition[]{FunctionColumns.ALIAS_SCHEMA.equal(this.getSchema().getName())}).and(FunctionColumns.ALIAS_NAME.equal(this.getName())).and(FunctionColumns.POS.gt(0)).orderBy(FunctionColumns.POS.asc()).fetch().iterator();

      while(true) {
         String paramName;
         String typeName;
         Integer precision;
         Short scale;
         int position;
         boolean nullable;
         String defaultValue;
         do {
            if (!var1.hasNext()) {
               return;
            }

            Record record = (Record)var1.next();
            paramName = (String)record.get((Field)FunctionColumns.COLUMN_NAME);
            typeName = (String)record.get((Field)FunctionColumns.TYPE_NAME);
            precision = (Integer)record.get((Field)FunctionColumns.PRECISION);
            scale = (Short)record.get((Field)FunctionColumns.SCALE);
            position = (Integer)record.get((Field)FunctionColumns.POS);
            nullable = (Boolean)record.get((Field)FunctionColumns.NULLABLE, (Class)Boolean.TYPE);
            defaultValue = (String)record.get((Field)FunctionColumns.COLUMN_DEFAULT);
         } while(position == 0 && H2DataType.OTHER.getTypeName().equalsIgnoreCase(typeName));

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), typeName, precision, precision, scale, nullable, defaultValue);
         ParameterDefinition parameter = new DefaultParameterDefinition(this, paramName, position, type);
         this.addParameter(InOutDefinition.IN, parameter);
      }
   }
}
