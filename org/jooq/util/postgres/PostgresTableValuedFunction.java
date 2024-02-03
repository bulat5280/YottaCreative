package org.jooq.util.postgres;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.WindowSpecification;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.ParameterDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.postgres.information_schema.Tables;
import org.jooq.util.postgres.information_schema.tables.Columns;
import org.jooq.util.postgres.information_schema.tables.Parameters;
import org.jooq.util.postgres.information_schema.tables.Routines;
import org.jooq.util.postgres.pg_catalog.tables.PgNamespace;
import org.jooq.util.postgres.pg_catalog.tables.PgProc;

public class PostgresTableValuedFunction extends AbstractTableDefinition {
   private final PostgresRoutineDefinition routine;
   private final String specificName;

   public PostgresTableValuedFunction(SchemaDefinition schema, String name, String specificName, String comment) {
      super(schema, name, comment);
      this.routine = new PostgresRoutineDefinition(schema.getDatabase(), schema.getInputName(), name, specificName);
      this.specificName = specificName;
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Routines r = Tables.ROUTINES;
      Parameters p = Tables.PARAMETERS;
      PgNamespace pg_n = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE;
      PgProc pg_p = org.jooq.util.postgres.pg_catalog.Tables.PG_PROC;
      Columns c = Tables.COLUMNS;
      Iterator var7 = this.create().select(p.PARAMETER_NAME, DSL.rowNumber().over((WindowSpecification)DSL.partitionBy(p.SPECIFIC_NAME).orderBy(p.ORDINAL_POSITION)).as(p.ORDINAL_POSITION), p.DATA_TYPE, p.CHARACTER_MAXIMUM_LENGTH, p.NUMERIC_PRECISION, p.NUMERIC_SCALE, DSL.inline("true").as(c.IS_NULLABLE), ((Field)(((PostgresDatabase)this.getDatabase()).is94() ? Tables.PARAMETERS.PARAMETER_DEFAULT : DSL.inline((String)null))).as((Field)c.COLUMN_DEFAULT), p.UDT_SCHEMA, p.UDT_NAME).from(r).join((TableLike)p).on(DSL.row((Field)r.SPECIFIC_CATALOG, (Field)r.SPECIFIC_SCHEMA, (Field)r.SPECIFIC_NAME).eq((Field)p.SPECIFIC_CATALOG, (Field)p.SPECIFIC_SCHEMA, (Field)p.SPECIFIC_NAME)).join(pg_n).on(r.SPECIFIC_SCHEMA.eq(pg_n.NSPNAME)).join(pg_p).on(pg_p.PRONAMESPACE.eq(PostgresDSL.oid(pg_n))).and(pg_p.PRONAME.eq(r.ROUTINE_NAME)).where(new Condition[]{r.SPECIFIC_NAME.eq(this.specificName)}).and(p.PARAMETER_MODE.ne("IN")).and((Field)pg_p.PRORETSET).unionAll(DSL.select(DSL.nvl((Field)c.COLUMN_NAME, (Object)this.getName()).as((Field)c.COLUMN_NAME), DSL.nvl((Field)c.ORDINAL_POSITION, (Field)DSL.inline((int)1)).as((Field)c.ORDINAL_POSITION), DSL.nvl((Field)c.DATA_TYPE, (Field)r.DATA_TYPE).as((Field)c.DATA_TYPE), DSL.nvl((Field)c.CHARACTER_MAXIMUM_LENGTH, (Field)r.CHARACTER_MAXIMUM_LENGTH).as((Field)c.CHARACTER_MAXIMUM_LENGTH), DSL.nvl((Field)c.NUMERIC_PRECISION, (Field)r.NUMERIC_PRECISION).as((Field)c.NUMERIC_PRECISION), DSL.nvl((Field)c.NUMERIC_SCALE, (Field)r.NUMERIC_SCALE).as((Field)c.NUMERIC_SCALE), DSL.nvl((Field)c.IS_NULLABLE, (Object)"true").as((Field)c.IS_NULLABLE), DSL.nvl((Field)c.COLUMN_DEFAULT, (Field)DSL.inline((String)null)).as((Field)c.COLUMN_DEFAULT), DSL.nvl((Field)c.UDT_SCHEMA, (Field)DSL.inline((String)null)).as((Field)c.UDT_SCHEMA), DSL.nvl((Field)c.UDT_NAME, (Field)r.UDT_NAME).as((Field)c.UDT_NAME)).from(r).leftOuterJoin((TableLike)c).on(new Condition[]{DSL.row((Field)r.TYPE_UDT_CATALOG, (Field)r.TYPE_UDT_SCHEMA, (Field)r.TYPE_UDT_NAME).eq((Field)c.TABLE_CATALOG, (Field)c.TABLE_SCHEMA, (Field)c.TABLE_NAME)}).join(pg_n).on(r.SPECIFIC_SCHEMA.eq(pg_n.NSPNAME)).join(pg_p).on(pg_p.PRONAMESPACE.eq(PostgresDSL.oid(pg_n))).and(pg_p.PRONAME.concat(new String[]{"_"}).concat(PostgresDSL.oid(pg_p)).eq((Field)r.SPECIFIC_NAME)).where(new Condition[]{r.SPECIFIC_NAME.eq(this.specificName)}).and(DSL.row((Field)r.SPECIFIC_CATALOG, (Field)r.SPECIFIC_SCHEMA, (Field)r.SPECIFIC_NAME).notIn((Select)DSL.select(p.SPECIFIC_CATALOG, p.SPECIFIC_SCHEMA, p.SPECIFIC_NAME).from(p).where(new Condition[]{p.PARAMETER_MODE.eq("OUT")}))).and((Field)pg_p.PRORETSET)).orderBy(2).iterator();

      while(var7.hasNext()) {
         Record record = (Record)var7.next();
         SchemaDefinition typeSchema = null;
         String schemaName = (String)record.get((Field)p.UDT_SCHEMA);
         if (schemaName != null) {
            typeSchema = this.getDatabase().getSchema(schemaName);
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), typeSchema, (String)record.get((Field)p.DATA_TYPE), (Number)record.get((Field)p.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)p.NUMERIC_PRECISION), (Number)record.get((Field)p.NUMERIC_SCALE), (Boolean)record.get((Field)c.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)c.COLUMN_DEFAULT), DSL.name((String)record.get((Field)p.UDT_SCHEMA), (String)record.get((Field)p.UDT_NAME)));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)p.PARAMETER_NAME), (Integer)record.get((Field)p.ORDINAL_POSITION, (Class)Integer.TYPE), type, StringUtils.defaultString((String)record.get((Field)c.COLUMN_DEFAULT)).startsWith("nextval"), (String)null);
         result.add(column);
      }

      return result;
   }

   protected List<ParameterDefinition> getParameters0() {
      return this.routine.getInParameters();
   }

   public boolean isTableValuedFunction() {
      return true;
   }
}
