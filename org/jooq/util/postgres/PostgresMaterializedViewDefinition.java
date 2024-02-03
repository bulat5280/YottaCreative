package org.jooq.util.postgres;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.util.AbstractTableDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultColumnDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.postgres.information_schema.Tables;
import org.jooq.util.postgres.information_schema.tables.Columns;
import org.jooq.util.postgres.pg_catalog.tables.PgAttrdef;
import org.jooq.util.postgres.pg_catalog.tables.PgAttribute;
import org.jooq.util.postgres.pg_catalog.tables.PgClass;
import org.jooq.util.postgres.pg_catalog.tables.PgCollation;
import org.jooq.util.postgres.pg_catalog.tables.PgNamespace;
import org.jooq.util.postgres.pg_catalog.tables.PgType;

public class PostgresMaterializedViewDefinition extends AbstractTableDefinition {
   public PostgresMaterializedViewDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Columns col = Tables.COLUMNS;
      PgAttribute a = org.jooq.util.postgres.pg_catalog.Tables.PG_ATTRIBUTE.as("a");
      PgAttrdef ad = org.jooq.util.postgres.pg_catalog.Tables.PG_ATTRDEF.as("ad");
      PgType t = org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.as("t");
      PgType bt = org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.as("bt");
      PgClass c = org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.as("c");
      PgCollation co = org.jooq.util.postgres.pg_catalog.Tables.PG_COLLATION.as("co");
      PgNamespace nt = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("nt");
      PgNamespace nc = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("nc");
      PgNamespace nbt = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("nbt");
      PgNamespace nco = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("nco");
      Iterator var13 = this.create().select(DSL.field("({0})::information_schema.sql_identifier", col.COLUMN_NAME.getDataType(), a.ATTNAME).as((Field)col.COLUMN_NAME), DSL.field("({0})::information_schema.cardinal_number", col.ORDINAL_POSITION.getDataType(), a.ATTNUM).as((Field)col.ORDINAL_POSITION), DSL.field("({0})::information_schema.character_data", col.DATA_TYPE.getDataType(), DSL.when(t.TYPTYPE.eq(DSL.inline("d")), DSL.when(bt.TYPELEM.ne(DSL.inline(0L)).and(bt.TYPLEN.eq(DSL.inline((short)-1))), (Field)DSL.inline("ARRAY")).when(nbt.NSPNAME.eq(DSL.inline("pg_catalog")), DSL.field("format_type({0}, NULL::integer)", String.class, t.TYPBASETYPE)).otherwise((Field)DSL.inline("USER-DEFINED"))).otherwise(DSL.when(t.TYPELEM.ne(DSL.inline(0L)).and(t.TYPLEN.eq(DSL.inline((short)-1))), (Field)DSL.inline("ARRAY")).when(nt.NSPNAME.eq(DSL.inline("pg_catalog")), DSL.field("format_type({0}, NULL::integer)", String.class, a.ATTTYPID)).otherwise((Field)DSL.inline("USER-DEFINED")))).as((Field)col.DATA_TYPE), DSL.field("(information_schema._pg_char_max_length(information_schema._pg_truetypid(a.*, t.*), information_schema._pg_truetypmod(a.*, t.*)))::information_schema.cardinal_number", col.CHARACTER_MAXIMUM_LENGTH.getDataType()).as((Field)col.CHARACTER_MAXIMUM_LENGTH), DSL.field("(information_schema._pg_numeric_precision(information_schema._pg_truetypid(a.*, t.*), information_schema._pg_truetypmod(a.*, t.*)))::information_schema.cardinal_number", col.NUMERIC_PRECISION.getDataType()).as((Field)col.NUMERIC_PRECISION), DSL.field("(information_schema._pg_numeric_scale(information_schema._pg_truetypid(a.*, t.*), information_schema._pg_truetypmod(a.*, t.*)))::information_schema.cardinal_number", col.NUMERIC_SCALE.getDataType()).as((Field)col.NUMERIC_SCALE), DSL.field("({0})::information_schema.yes_or_no", col.IS_NULLABLE.getDataType(), DSL.when(DSL.condition((Field)a.ATTNOTNULL).or(t.TYPTYPE.eq(DSL.inline("d")).and((Field)t.TYPNOTNULL)), (Field)DSL.inline("NO")).otherwise((Field)DSL.inline("YES"))).as((Field)col.IS_NULLABLE), DSL.field("(pg_get_expr({0}, {1}))::information_schema.character_data", col.COLUMN_DEFAULT.getDataType(), ad.ADBIN, ad.ADRELID).as((Field)col.COLUMN_DEFAULT), DSL.field("({0})::information_schema.sql_identifier", col.UDT_SCHEMA.getDataType(), DSL.nvl((Field)nbt.NSPNAME, (Field)nt.NSPNAME)).as((Field)col.UDT_SCHEMA), DSL.field("({0})::information_schema.sql_identifier", col.UDT_NAME.getDataType(), DSL.nvl((Field)bt.TYPNAME, (Field)t.TYPNAME)).as((Field)col.UDT_NAME), org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION).from(a.leftJoin(ad).on(new Condition[]{a.ATTRELID.eq(ad.ADRELID)}).and(a.ATTNUM.eq(ad.ADNUM)).join(c.join(nc).on(c.RELNAMESPACE.eq(PostgresDSL.oid(nc)))).on(a.ATTRELID.eq(PostgresDSL.oid(c))).join(t.join(nt).on(t.TYPNAMESPACE.eq(PostgresDSL.oid(nt)))).on(a.ATTTYPID.eq(PostgresDSL.oid(t)))).leftJoin((TableLike)bt.join(nbt).on(bt.TYPNAMESPACE.eq(PostgresDSL.oid(nbt)))).on(new Condition[]{t.TYPTYPE.eq(DSL.inline("d")).and(t.TYPBASETYPE.eq(PostgresDSL.oid(bt)))}).leftJoin(co.join(nco).on(co.COLLNAMESPACE.eq(PostgresDSL.oid(nco)))).on(new Condition[]{a.ATTCOLLATION.eq(PostgresDSL.oid(co)).and(nco.NSPNAME.ne(DSL.inline("pg_catalog")).or(co.COLLNAME.ne(DSL.inline("default"))))}).leftJoin(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION).on(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJOID.eq(PostgresDSL.oid(c))}).and(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJSUBID.eq(a.ATTNUM.coerce(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJSUBID))).where(new Condition[]{DSL.not(DSL.condition("pg_is_other_temp_schema({0})", PostgresDSL.oid(nc))).and(a.ATTNUM.gt(DSL.inline((short)0))).and(DSL.not((Field)a.ATTISDROPPED)).and(c.RELKIND.eq(DSL.inline("m"))).and(nc.NSPNAME.in(new String[]{this.getSchema().getName()})).and(c.RELNAME.eq(this.getName()))}).orderBy(a.ATTNUM).iterator();

      while(var13.hasNext()) {
         Record record = (Record)var13.next();
         SchemaDefinition typeSchema = null;
         String schemaName = (String)record.get((Field)Tables.COLUMNS.UDT_SCHEMA);
         if (schemaName != null) {
            typeSchema = this.getDatabase().getSchema(schemaName);
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), typeSchema, (String)record.get((Field)Tables.COLUMNS.DATA_TYPE), (Number)record.get((Field)Tables.COLUMNS.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.COLUMNS.NUMERIC_PRECISION), (Number)record.get((Field)Tables.COLUMNS.NUMERIC_SCALE), (Boolean)record.get((Field)Tables.COLUMNS.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Tables.COLUMNS.COLUMN_DEFAULT), DSL.name((String)record.get((Field)Tables.COLUMNS.UDT_SCHEMA), (String)record.get((Field)Tables.COLUMNS.UDT_NAME)));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Tables.COLUMNS.COLUMN_NAME), (Integer)record.get((Field)Tables.COLUMNS.ORDINAL_POSITION, (Class)Integer.TYPE), type, StringUtils.defaultString((String)record.get((Field)Tables.COLUMNS.COLUMN_DEFAULT)).startsWith("nextval"), (String)record.get((Field)org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION));
         result.add(column);
      }

      return result;
   }
}
