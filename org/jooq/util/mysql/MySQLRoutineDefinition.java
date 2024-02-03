package org.jooq.util.mysql;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
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
import org.jooq.util.hsqldb.information_schema.Tables;
import org.jooq.util.mysql.information_schema.tables.Parameters;
import org.jooq.util.mysql.mysql.enums.ProcType;

public class MySQLRoutineDefinition extends AbstractRoutineDefinition {
   private Boolean is55;
   private final String params;
   private final String returns;
   private final ProcType procType;

   /** @deprecated */
   @Deprecated
   public MySQLRoutineDefinition(SchemaDefinition schema, String name, String comment, String params, String returns) {
      this(schema, name, comment, params, returns, (ProcType)null, (String)null);
   }

   public MySQLRoutineDefinition(SchemaDefinition schema, String name, String comment, String params, String returns, ProcType procType, String overload) {
      super(schema, (PackageDefinition)null, name, comment, overload);
      this.params = params;
      this.returns = returns;
      this.procType = procType;
   }

   protected void init0() {
      if (this.is55()) {
         this.init55();
      } else {
         this.init54();
      }

   }

   private void init55() {
      Iterator var1 = this.create().select(Parameters.ORDINAL_POSITION, Parameters.PARAMETER_NAME, Parameters.PARAMETER_MODE, Parameters.DATA_TYPE, Parameters.DTD_IDENTIFIER, Parameters.CHARACTER_MAXIMUM_LENGTH, Parameters.NUMERIC_PRECISION, Parameters.NUMERIC_SCALE).from(Tables.PARAMETERS).where(new Condition[]{Parameters.SPECIFIC_SCHEMA.eq(this.getSchema().getInputName())}).and(Parameters.SPECIFIC_NAME.eq(this.getInputName())).and(Parameters.ROUTINE_TYPE.eq(this.procType.name())).orderBy(Parameters.ORDINAL_POSITION.asc()).fetch().iterator();

      while(var1.hasNext()) {
         Record record = (Record)var1.next();
         String inOut = (String)record.get((Field)Parameters.PARAMETER_MODE);
         String dataType = (String)record.get((Field)Parameters.DATA_TYPE);
         if (this.getDatabase().supportsUnsignedTypes() && Arrays.asList("tinyint", "smallint", "mediumint", "int", "bigint").contains(dataType.toLowerCase()) && ((String)record.get((Field)Parameters.DTD_IDENTIFIER)).toLowerCase().contains("unsigned")) {
            dataType = dataType + "unsigned";
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), dataType, (Number)record.get((Field)Parameters.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Parameters.NUMERIC_PRECISION), (Number)record.get((Field)Parameters.NUMERIC_SCALE), (Boolean)null, (String)null);
         if (inOut == null) {
            this.addParameter(InOutDefinition.RETURN, new DefaultParameterDefinition(this, "RETURN_VALUE", -1, type));
         } else {
            ParameterDefinition parameter = new DefaultParameterDefinition(this, ((String)record.get((Field)Parameters.PARAMETER_NAME)).replaceAll("@", ""), (Integer)record.get((Field)Parameters.ORDINAL_POSITION, (Class)Integer.TYPE), type);
            this.addParameter(InOutDefinition.getFromString(inOut), parameter);
         }
      }

   }

   private void init54() {
      String[] split = this.params.split(",(?!\\s*\\d+\\s*\\))");
      Matcher matcher = TYPE_PATTERN.matcher(this.returns);
      if (matcher.find()) {
         this.addParameter(InOutDefinition.RETURN, this.createParameter(matcher, 0, -1, "RETURN_VALUE"));
      }

      for(int i = 0; i < split.length; ++i) {
         String param = split[i];
         param = param.trim();
         matcher = PARAMETER_PATTERN.matcher(param);

         while(matcher.find()) {
            InOutDefinition inOut = InOutDefinition.getFromString(matcher.group(2));
            this.addParameter(inOut, this.createParameter(matcher, 3, i + 1));
         }
      }

   }

   private ParameterDefinition createParameter(Matcher matcher, int group, int columnIndex) {
      return this.createParameter(matcher, group, columnIndex, matcher.group(group));
   }

   private ParameterDefinition createParameter(Matcher matcher, int group, int columnIndex, String paramName) {
      String paramType = matcher.group(group + 1);
      Number precision = 0;
      Number scale = 0;
      if (!StringUtils.isBlank(matcher.group(group + 2))) {
         precision = Integer.valueOf(matcher.group(group + 2));
      }

      if (!StringUtils.isBlank(matcher.group(group + 3))) {
         scale = Integer.valueOf(matcher.group(group + 3));
      }

      DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), this.getSchema(), paramType, precision, precision, scale, (Boolean)null, (String)null);
      return new DefaultParameterDefinition(this, paramName, columnIndex, type);
   }

   private boolean is55() {
      if (this.is55 == null) {
         try {
            this.create().selectOne().from(Tables.PARAMETERS).limit(1).fetchOne();
            this.is55 = true;
         } catch (Exception var2) {
            this.is55 = false;
         }
      }

      return this.is55;
   }
}
