package org.jooq.util.postgres;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.util.AbstractUDTDefinition;
import org.jooq.util.AttributeDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultAttributeDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.RoutineDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.postgres.information_schema.Tables;

public class PostgresUDTDefinition extends AbstractUDTDefinition {
   public PostgresUDTDefinition(SchemaDefinition schema, String name, String comment) {
      super(schema, (PackageDefinition)null, name, comment);
   }

   protected List<AttributeDefinition> getElements0() throws SQLException {
      List<AttributeDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.ATTRIBUTES.ATTRIBUTE_NAME, Tables.ATTRIBUTES.ORDINAL_POSITION, Tables.ATTRIBUTES.DATA_TYPE, Tables.ATTRIBUTES.CHARACTER_MAXIMUM_LENGTH, Tables.ATTRIBUTES.NUMERIC_PRECISION, Tables.ATTRIBUTES.NUMERIC_SCALE, Tables.ATTRIBUTES.IS_NULLABLE, Tables.ATTRIBUTES.ATTRIBUTE_DEFAULT, Tables.ATTRIBUTES.ATTRIBUTE_UDT_SCHEMA, Tables.ATTRIBUTES.ATTRIBUTE_UDT_NAME).from(Tables.ATTRIBUTES).where(new Condition[]{Tables.ATTRIBUTES.UDT_SCHEMA.equal(this.getSchema().getName())}).and(Tables.ATTRIBUTES.UDT_NAME.equal(this.getName())).orderBy(Tables.ATTRIBUTES.ORDINAL_POSITION).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition typeSchema = null;
         String schemaName = (String)record.get((Field)Tables.ATTRIBUTES.ATTRIBUTE_UDT_SCHEMA);
         if (schemaName != null) {
            typeSchema = this.getDatabase().getSchema(schemaName);
         }

         DataTypeDefinition type = new DefaultDataTypeDefinition(this.getDatabase(), typeSchema == null ? this.getSchema() : typeSchema, (String)record.get((Field)Tables.ATTRIBUTES.DATA_TYPE), (Number)record.get((Field)Tables.ATTRIBUTES.CHARACTER_MAXIMUM_LENGTH), (Number)record.get((Field)Tables.ATTRIBUTES.NUMERIC_PRECISION), (Number)record.get((Field)Tables.ATTRIBUTES.NUMERIC_SCALE), (Boolean)record.get((Field)Tables.ATTRIBUTES.IS_NULLABLE, (Class)Boolean.TYPE), (String)record.get((Field)Tables.ATTRIBUTES.ATTRIBUTE_DEFAULT), DSL.name((String)record.get((Field)Tables.ATTRIBUTES.ATTRIBUTE_UDT_SCHEMA), (String)record.get((Field)Tables.ATTRIBUTES.ATTRIBUTE_UDT_NAME)));
         AttributeDefinition column = new DefaultAttributeDefinition(this, (String)record.get((Field)Tables.ATTRIBUTES.ATTRIBUTE_NAME), (Integer)record.get((Field)Tables.ATTRIBUTES.ORDINAL_POSITION), type);
         result.add(column);
      }

      return result;
   }

   protected List<RoutineDefinition> getRoutines0() {
      return Collections.emptyList();
   }
}
