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

public class PostgresTableDefinition extends AbstractTableDefinition {
   public PostgresTableDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, name, comment);
   }

   public List<ColumnDefinition> getElements0() throws SQLException {
      List<ColumnDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.COLUMNS.COLUMN_NAME, Tables.COLUMNS.ORDINAL_POSITION, Tables.COLUMNS.DATA_TYPE, Tables.COLUMNS.CHARACTER_MAXIMUM_LENGTH, Tables.COLUMNS.NUMERIC_PRECISION, Tables.COLUMNS.NUMERIC_SCALE, Tables.COLUMNS.IS_NULLABLE, Tables.COLUMNS.COLUMN_DEFAULT, Tables.COLUMNS.UDT_SCHEMA, Tables.COLUMNS.UDT_NAME, org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION).from(Tables.COLUMNS).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(Tables.COLUMNS.TABLE_SCHEMA.eq(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME)).join(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS).on(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAME.eq(Tables.COLUMNS.TABLE_NAME)).and(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).leftOuterJoin(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION).on(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJOID.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS))}).and(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJSUBID.eq(Tables.COLUMNS.ORDINAL_POSITION)).where(new Condition[]{Tables.COLUMNS.TABLE_SCHEMA.equal(this.getSchema().getName())}).and(Tables.COLUMNS.TABLE_NAME.equal(this.getName())).orderBy(Tables.COLUMNS.ORDINAL_POSITION).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition typeSchema = null;
         String schemaName = (String)record.get((Field)Tables.COLUMNS.UDT_SCHEMA);
         if (schemaName != null) {
            typeSchema = this.getDatabase().getSchema(schemaName);
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), typeSchema, (String)record.get((Field)Tables.COLUMNS.DATA_TYPE), (Number)record.get((Field)Tables.COLUMNS.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.COLUMNS.NUMERIC_PRECISION), (Number)record.get((Field)Tables.COLUMNS.NUMERIC_SCALE), (Boolean)record.get((Field)Tables.COLUMNS.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Tables.COLUMNS.COLUMN_DEFAULT), DSL.name((String)record.get((Field)Tables.COLUMNS.UDT_SCHEMA), (String)record.get((Field)Tables.COLUMNS.UDT_NAME)));
         ColumnDefinition column = new DefaultColumnDefinition(this.getDatabase().getTable(this.getSchema(), this.getName()), (String)record.get((Field)Tables.COLUMNS.COLUMN_NAME), (Integer)record.get((Field)Tables.COLUMNS.ORDINAL_POSITION, (Class)Integer.TYPE), type, StringUtils.defaultString((String)record.get((Field)Tables.COLUMNS.COLUMN_DEFAULT)).trim().toLowerCase().startsWith("nextval"), (String)record.get((Field)org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION));
         result.add(column);
      }

      return result;
   }
}
