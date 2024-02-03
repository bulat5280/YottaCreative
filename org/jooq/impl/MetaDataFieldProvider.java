package org.jooq.impl;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.JooqLogger;

final class MetaDataFieldProvider implements Serializable {
   private static final long serialVersionUID = -8482521025536063609L;
   private static final JooqLogger log = JooqLogger.getLogger(MetaDataFieldProvider.class);
   private final Fields<Record> fields;

   MetaDataFieldProvider(Configuration configuration, ResultSetMetaData meta) {
      this.fields = this.init(configuration, meta);
   }

   private Fields<Record> init(Configuration configuration, ResultSetMetaData meta) {
      int columnCount = 0;

      Field[] fields;
      try {
         columnCount = meta.getColumnCount();
         fields = new Field[columnCount];
      } catch (SQLException var16) {
         log.info("Cannot fetch column count for cursor : " + var16.getMessage());
         fields = new Field[]{DSL.field("dummy")};
      }

      try {
         for(int i = 1; i <= columnCount; ++i) {
            String columnLabel = meta.getColumnLabel(i);
            String columnName = meta.getColumnName(i);
            Name name;
            if (columnName.equals(columnLabel)) {
               try {
                  String columnSchema = meta.getSchemaName(i);
                  String columnTable = meta.getTableName(i);
                  name = DSL.name(columnSchema, columnTable, columnName);
               } catch (SQLException var15) {
                  name = DSL.name(columnLabel);
               }
            } else {
               name = DSL.name(columnLabel);
            }

            int precision = meta.getPrecision(i);
            int scale = meta.getScale(i);
            DataType<?> dataType = SQLDataType.OTHER;
            String type = meta.getColumnTypeName(i);

            try {
               dataType = DefaultDataType.getDataType(configuration.family(), type, precision, scale);
               if (dataType.hasPrecision()) {
                  dataType = dataType.precision(precision);
               }

               if (dataType.hasScale()) {
                  dataType = dataType.scale(scale);
               }

               if (dataType.hasLength()) {
                  dataType = dataType.length(precision);
               }
            } catch (SQLDialectNotSupportedException var14) {
               log.debug("Not supported by dialect", (Object)var14.getMessage());
            }

            fields[i - 1] = DSL.field(name, dataType);
         }
      } catch (SQLException var17) {
         throw Tools.translate((String)null, var17);
      }

      return new Fields(fields);
   }

   final Field<?>[] getFields() {
      return this.fields.fields();
   }

   public String toString() {
      return this.fields.toString();
   }
}
