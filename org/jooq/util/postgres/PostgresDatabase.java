package org.jooq.util.postgres;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.WindowSpecification;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.util.AbstractDatabase;
import org.jooq.util.ArrayDefinition;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DataTypeDefinition;
import org.jooq.util.DefaultCheckConstraintDefinition;
import org.jooq.util.DefaultDataTypeDefinition;
import org.jooq.util.DefaultDomainDefinition;
import org.jooq.util.DefaultEnumDefinition;
import org.jooq.util.DefaultRelations;
import org.jooq.util.DefaultSequenceDefinition;
import org.jooq.util.DomainDefinition;
import org.jooq.util.EnumDefinition;
import org.jooq.util.PackageDefinition;
import org.jooq.util.RoutineDefinition;
import org.jooq.util.SchemaDefinition;
import org.jooq.util.SequenceDefinition;
import org.jooq.util.TableDefinition;
import org.jooq.util.UDTDefinition;
import org.jooq.util.postgres.information_schema.Tables;
import org.jooq.util.postgres.information_schema.tables.CheckConstraints;
import org.jooq.util.postgres.information_schema.tables.Routines;
import org.jooq.util.postgres.information_schema.tables.TableConstraints;
import org.jooq.util.postgres.pg_catalog.tables.PgClass;
import org.jooq.util.postgres.pg_catalog.tables.PgConstraint;
import org.jooq.util.postgres.pg_catalog.tables.PgInherits;
import org.jooq.util.postgres.pg_catalog.tables.PgNamespace;
import org.jooq.util.postgres.pg_catalog.tables.PgType;

public class PostgresDatabase extends AbstractDatabase {
   private static final JooqLogger log = JooqLogger.getLogger(PostgresDatabase.class);
   private static Boolean is84;
   private static Boolean is94;
   private static Boolean canCastToEnumType;

   protected void loadPrimaryKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("PRIMARY KEY").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA));
         String key = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME);
         String tableName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_NAME);
         String columnName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.COLUMN_NAME);
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addPrimaryKey(key, table.getColumn(columnName));
         }
      }

   }

   protected void loadUniqueKeys(DefaultRelations relations) throws SQLException {
      Iterator var2 = this.fetchKeys("UNIQUE").iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA));
         String key = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME);
         String tableName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.TABLE_NAME);
         String columnName = (String)record.get((Field)Tables.KEY_COLUMN_USAGE.COLUMN_NAME);
         TableDefinition table = this.getTable(schema, tableName);
         if (table != null) {
            relations.addUniqueKey(key, table.getColumn(columnName));
         }
      }

   }

   private Result<Record4<String, String, String, String>> fetchKeys(String constraintType) {
      return this.create().select(Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME, Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA, Tables.KEY_COLUMN_USAGE.TABLE_NAME, Tables.KEY_COLUMN_USAGE.COLUMN_NAME).from(Tables.TABLE_CONSTRAINTS).join((TableLike)Tables.KEY_COLUMN_USAGE).on(Tables.TABLE_CONSTRAINTS.CONSTRAINT_SCHEMA.equal(Tables.KEY_COLUMN_USAGE.CONSTRAINT_SCHEMA)).and(Tables.TABLE_CONSTRAINTS.CONSTRAINT_NAME.equal(Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME)).where(new Condition[]{Tables.TABLE_CONSTRAINTS.CONSTRAINT_TYPE.equal(constraintType)}).and(Tables.TABLE_CONSTRAINTS.TABLE_SCHEMA.in(this.getInputSchemata())).orderBy(Tables.KEY_COLUMN_USAGE.TABLE_SCHEMA.asc(), Tables.KEY_COLUMN_USAGE.TABLE_NAME.asc(), Tables.KEY_COLUMN_USAGE.CONSTRAINT_NAME.asc(), Tables.KEY_COLUMN_USAGE.ORDINAL_POSITION.asc()).fetch();
   }

   protected void loadForeignKeys(DefaultRelations relations) throws SQLException {
      Result<Record> result = this.create().fetch(this.getConnection().getMetaData().getExportedKeys((String)null, (String)null, (String)null)).sortAsc("key_seq").sortAsc("fk_name").sortAsc("fktable_name").sortAsc("fktable_schem");
      Iterator var3 = result.iterator();

      while(var3.hasNext()) {
         Record record = (Record)var3.next();
         SchemaDefinition foreignKeySchema = this.getSchema((String)record.get("fktable_schem", String.class));
         SchemaDefinition uniqueKeySchema = this.getSchema((String)record.get("pktable_schem", String.class));
         String foreignKey = (String)record.get("fk_name", String.class);
         String foreignKeyTable = (String)record.get("fktable_name", String.class);
         String foreignKeyColumn = (String)record.get("fkcolumn_name", String.class);
         String uniqueKey = (String)record.get("pk_name", String.class);
         TableDefinition referencingTable = this.getTable(foreignKeySchema, foreignKeyTable);
         if (referencingTable != null) {
            ColumnDefinition referencingColumn = referencingTable.getColumn(foreignKeyColumn);
            relations.addForeignKey(foreignKeyTable + "__" + foreignKey, uniqueKey, referencingColumn, uniqueKeySchema);
         }
      }

   }

   protected void loadCheckConstraints(DefaultRelations relations) throws SQLException {
      TableConstraints tc = Tables.TABLE_CONSTRAINTS.as("tc");
      CheckConstraints cc = Tables.CHECK_CONSTRAINTS.as("cc");
      Iterator var4 = this.create().select(tc.TABLE_SCHEMA, tc.TABLE_NAME, cc.CONSTRAINT_NAME, cc.CHECK_CLAUSE).from(tc).join((TableLike)cc).using(tc.CONSTRAINT_CATALOG, tc.CONSTRAINT_SCHEMA, tc.CONSTRAINT_NAME).where(new Condition[]{tc.TABLE_SCHEMA.in(this.getInputSchemata())}).fetch().iterator();

      while(var4.hasNext()) {
         Record record = (Record)var4.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)tc.TABLE_SCHEMA));
         TableDefinition table = this.getTable(schema, (String)record.get((Field)tc.TABLE_NAME));
         if (table != null) {
            relations.addCheckConstraint(table, new DefaultCheckConstraintDefinition(schema, table, (String)record.get((Field)cc.CONSTRAINT_NAME), (String)record.get((Field)cc.CHECK_CLAUSE)));
         }
      }

   }

   protected List<TableDefinition> getTables0() throws SQLException {
      List<TableDefinition> result = new ArrayList();
      Map<Name, PostgresTableDefinition> map = new HashMap();
      Select<Record6<String, String, String, Boolean, Boolean, String>> empty = DSL.select(DSL.inline(""), DSL.inline(""), DSL.inline(""), DSL.inline(false), DSL.inline(false), DSL.inline("")).where(new Condition[]{DSL.falseCondition()});
      Iterator var4 = this.create().select().from(DSL.select(Tables.TABLES.TABLE_SCHEMA, Tables.TABLES.TABLE_NAME, Tables.TABLES.TABLE_NAME.as("specific_name"), DSL.inline(false).as("table_valued_function"), DSL.inline(false).as("materialized_view"), org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION).from(Tables.TABLES).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(Tables.TABLES.TABLE_SCHEMA.eq(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME)).join(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS).on(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAME.eq(Tables.TABLES.TABLE_NAME)).and(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).leftOuterJoin(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION).on(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJOID.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS))}).and(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJSUBID.eq(0)).where(new Condition[]{Tables.TABLES.TABLE_SCHEMA.in(this.getInputSchemata())}).and(DSL.row((Field)Tables.TABLES.TABLE_SCHEMA, (Field)Tables.TABLES.TABLE_NAME).notIn((Select)DSL.select(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME, org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAME).from(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).where(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELKIND.eq(DSL.inline("m"))}))).unionAll(DSL.select(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME, org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAME, org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAME, DSL.inline(false).as("table_valued_function"), DSL.inline(true).as("materialized_view"), org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION).from(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELNAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).leftOuterJoin(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION).on(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJOID.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS))}).and(org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.OBJSUBID.eq(0)).where(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME.in(this.getInputSchemata())}).and(org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.RELKIND.eq(DSL.inline("m")))).unionAll(this.tableValuedFunctions() ? DSL.select(Tables.ROUTINES.ROUTINE_SCHEMA, Tables.ROUTINES.ROUTINE_NAME, Tables.ROUTINES.SPECIFIC_NAME, DSL.inline(true).as("table_valued_function"), DSL.inline(false).as("materialized_view"), DSL.inline("")).from(Tables.ROUTINES).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(Tables.ROUTINES.SPECIFIC_SCHEMA.eq(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME)).join(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC).on(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PRONAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).and(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PRONAME.concat(new String[]{"_"}).concat(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC)).eq((Field)Tables.ROUTINES.SPECIFIC_NAME)).where(new Condition[]{Tables.ROUTINES.ROUTINE_SCHEMA.in(this.getInputSchemata())}).and((Field)org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PRORETSET) : empty).asTable("tables")).orderBy(new int[]{1, 2}).fetch().iterator();

      while(var4.hasNext()) {
         Record record = (Record)var4.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.TABLES.TABLE_SCHEMA));
         String name = (String)record.get((Field)Tables.TABLES.TABLE_NAME);
         boolean tableValuedFunction = (Boolean)record.get("table_valued_function", Boolean.TYPE);
         boolean materializedView = (Boolean)record.get("materialized_view", Boolean.TYPE);
         String comment = (String)record.get((Field)org.jooq.util.postgres.pg_catalog.Tables.PG_DESCRIPTION.DESCRIPTION, (Class)String.class);
         if (tableValuedFunction) {
            result.add(new PostgresTableValuedFunction(schema, name, (String)record.get((Field)Tables.ROUTINES.SPECIFIC_NAME), comment));
         } else if (materializedView) {
            result.add(new PostgresMaterializedViewDefinition(schema, name, comment));
         } else {
            PostgresTableDefinition t = new PostgresTableDefinition(schema, name, comment);
            result.add(t);
            map.put(DSL.name(schema.getName(), name), t);
         }
      }

      PgClass ct = org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.as("ct");
      PgNamespace cn = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("cn");
      PgInherits i = org.jooq.util.postgres.pg_catalog.Tables.PG_INHERITS.as("i");
      PgClass pt = org.jooq.util.postgres.pg_catalog.Tables.PG_CLASS.as("pt");
      PgNamespace pn = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("pn");
      if (this.is84()) {
         Iterator var20 = this.create().select(cn.NSPNAME, ct.RELNAME, pn.NSPNAME, pt.RELNAME, DSL.max(i.INHSEQNO).over().partitionBy(i.INHRELID).as("m")).from(ct).join((TableLike)cn).on(ct.RELNAMESPACE.eq(PostgresDSL.oid(cn))).join(i).on(i.INHRELID.eq(PostgresDSL.oid(ct))).join(pt).on(i.INHPARENT.eq(PostgresDSL.oid(pt))).join(pn).on(pt.RELNAMESPACE.eq(PostgresDSL.oid(pn))).where(new Condition[]{cn.NSPNAME.in(this.getInputSchemata())}).and(pn.NSPNAME.in(this.getInputSchemata())).fetch().iterator();

         while(var20.hasNext()) {
            Record5<String, String, String, String, Integer> inheritance = (Record5)var20.next();
            Name child = DSL.name((String)inheritance.value1(), (String)inheritance.value2());
            Name parent = DSL.name((String)inheritance.value3(), (String)inheritance.value4());
            if ((Integer)inheritance.value5() > 1) {
               log.info("Multiple inheritance", (Object)("Multiple inheritance is not supported by jOOQ: " + child + " inherits from " + parent));
            } else {
               PostgresTableDefinition childTable = (PostgresTableDefinition)map.get(child);
               PostgresTableDefinition parentTable = (PostgresTableDefinition)map.get(parent);
               if (childTable != null && parentTable != null) {
                  childTable.setParentTable(parentTable);
                  parentTable.getChildTables().add(childTable);
               }
            }
         }
      }

      return result;
   }

   protected List<CatalogDefinition> getCatalogs0() throws SQLException {
      List<CatalogDefinition> result = new ArrayList();
      result.add(new CatalogDefinition(this, "", ""));
      return result;
   }

   protected List<SchemaDefinition> getSchemata0() throws SQLException {
      List<SchemaDefinition> result = new ArrayList();
      Iterator var2 = this.create().select((SelectField)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME).from(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).fetch(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME).iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         result.add(new SchemaDefinition(this, name, ""));
      }

      return result;
   }

   protected List<SequenceDefinition> getSequences0() throws SQLException {
      List<SequenceDefinition> result = new ArrayList();
      Iterator var2 = this.create().select(Tables.SEQUENCES.SEQUENCE_SCHEMA, Tables.SEQUENCES.SEQUENCE_NAME, Tables.SEQUENCES.DATA_TYPE, Tables.SEQUENCES.NUMERIC_PRECISION, Tables.SEQUENCES.NUMERIC_SCALE).from(Tables.SEQUENCES).where(new Condition[]{Tables.SEQUENCES.SEQUENCE_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.SEQUENCES.SEQUENCE_SCHEMA, Tables.SEQUENCES.SEQUENCE_NAME).fetch().iterator();

      while(var2.hasNext()) {
         Record record = (Record)var2.next();
         SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.SEQUENCES.SEQUENCE_SCHEMA));
         DataTypeDefinition type = new DefaultDataTypeDefinition(this, schema, (String)record.get((Field)Tables.SEQUENCES.DATA_TYPE), 0, (Number)record.get((Field)Tables.SEQUENCES.NUMERIC_PRECISION), (Number)record.get((Field)Tables.SEQUENCES.NUMERIC_SCALE), false, (String)null);
         result.add(new DefaultSequenceDefinition(schema, (String)record.get((Field)Tables.SEQUENCES.SEQUENCE_NAME), type));
      }

      return result;
   }

   protected List<EnumDefinition> getEnums0() throws SQLException {
      List<EnumDefinition> result = new ArrayList();
      if (this.exists(org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM)) {
         Result<Record2<String, String>> types = this.create().select(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME, org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.TYPNAME).from(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.TYPNAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).where(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME.in(this.getInputSchemata())}).and(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE).in((Select)DSL.select((SelectField)org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM.ENUMTYPID).from(org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM))).orderBy(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME, org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.TYPNAME).fetch();
         Iterator var3 = types.iterator();

         while(var3.hasNext()) {
            Record2<String, String> type = (Record2)var3.next();
            String nspname = (String)type.get(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME);
            String typname = (String)type.get(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.TYPNAME);
            DefaultEnumDefinition definition = null;

            String label;
            for(Iterator var8 = this.enumLabels(nspname, typname).iterator(); var8.hasNext(); definition.addLiteral(label)) {
               label = (String)var8.next();
               SchemaDefinition schema = this.getSchema(nspname);
               String typeName = String.valueOf(typname);
               if (definition == null || !definition.getName().equals(typeName)) {
                  definition = new DefaultEnumDefinition(schema, typeName, (String)null);
                  result.add(definition);
               }
            }
         }
      }

      return result;
   }

   protected List<DomainDefinition> getDomains0() throws SQLException {
      List<DomainDefinition> result = new ArrayList();
      if (this.existAll(new Table[]{org.jooq.util.postgres.pg_catalog.Tables.PG_CONSTRAINT, org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE})) {
         PgNamespace n = org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.as("n");
         PgConstraint c = org.jooq.util.postgres.pg_catalog.Tables.PG_CONSTRAINT.as("c");
         PgType d = org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.as("d");
         PgType b = org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.as("b");
         Field<String[]> src = DSL.field(DSL.name("domains", "src"), String[].class);
         Iterator var7 = this.create().withRecursive("domains", "domain_id", "base_id", "typbasetype", "src").as(DSL.select(PostgresDSL.oid(d), PostgresDSL.oid(d), d.TYPBASETYPE, DSL.array(c.CONSRC)).from(d).join((TableLike)n).on(PostgresDSL.oid(n).eq((Field)d.TYPNAMESPACE)).leftJoin(c).on(new Condition[]{PostgresDSL.oid(d).eq((Field)c.CONTYPID)}).where(new Condition[]{d.TYPTYPE.eq("d")}).and(n.NSPNAME.in(this.getInputSchemata())).unionAll(DSL.select(DSL.field(DSL.name("domains", "domain_id"), Long.class), PostgresDSL.oid(d), d.TYPBASETYPE, DSL.decode().when(c.CONSRC.isNull(), src).otherwise(PostgresDSL.arrayAppend((Field)src, (Field)c.CONSRC))).from(DSL.name("domains")).join((TableLike)d).on(DSL.field(DSL.name("domains", d.TYPBASETYPE.getName())).eq((Object)PostgresDSL.oid(d))).leftJoin(c).on(new Condition[]{PostgresDSL.oid(d).eq((Field)c.CONTYPID)}))).select(n.NSPNAME, d.TYPNAME, d.TYPNOTNULL, d.TYPDEFAULT, b.TYPNAME, b.TYPLEN, src).from(d).join(DSL.name("domains")).on(DSL.field(DSL.name("domains", "typbasetype")).eq((int)0)).and(DSL.field(DSL.name("domains", "domain_id")).eq((Object)PostgresDSL.oid(d))).join(b).on(DSL.field(DSL.name("domains", "base_id")).eq((Object)PostgresDSL.oid(b))).join(n).on(PostgresDSL.oid(n).eq((Field)d.TYPNAMESPACE)).where(new Condition[]{d.TYPTYPE.eq("d")}).and(n.NSPNAME.in(this.getInputSchemata())).iterator();

         while(var7.hasNext()) {
            Record record = (Record)var7.next();
            SchemaDefinition schema = this.getSchema((String)record.get((Field)n.NSPNAME));
            DataTypeDefinition baseType = new DefaultDataTypeDefinition(this, schema, (String)record.get((Field)b.TYPNAME), (Number)record.get((Field)b.TYPLEN), (Number)record.get((Field)b.TYPLEN), 0, !(Boolean)record.get((Field)d.TYPNOTNULL, (Class)Boolean.TYPE), (String)record.get((Field)d.TYPDEFAULT), DSL.name((String)record.get((Field)n.NSPNAME), (String)record.get((Field)b.TYPNAME)));
            DefaultDomainDefinition domain = new DefaultDomainDefinition(schema, (String)record.get((Field)d.TYPNAME), baseType);
            domain.addCheckClause((String[])record.get(src));
            result.add(domain);
         }
      }

      return result;
   }

   protected List<UDTDefinition> getUDTs0() throws SQLException {
      List<UDTDefinition> result = new ArrayList();
      if (this.exists(Tables.ATTRIBUTES)) {
         Iterator var2 = this.create().selectDistinct(Tables.ATTRIBUTES.UDT_SCHEMA, Tables.ATTRIBUTES.UDT_NAME).from(Tables.ATTRIBUTES).where(new Condition[]{Tables.ATTRIBUTES.UDT_SCHEMA.in(this.getInputSchemata())}).orderBy(Tables.ATTRIBUTES.UDT_SCHEMA, Tables.ATTRIBUTES.UDT_NAME).fetch().iterator();

         while(var2.hasNext()) {
            Record record = (Record)var2.next();
            SchemaDefinition schema = this.getSchema((String)record.get((Field)Tables.ATTRIBUTES.UDT_SCHEMA));
            String name = (String)record.get((Field)Tables.ATTRIBUTES.UDT_NAME);
            result.add(new PostgresUDTDefinition(schema, name, (String)null));
         }
      }

      return result;
   }

   protected List<ArrayDefinition> getArrays0() throws SQLException {
      List<ArrayDefinition> result = new ArrayList();
      return result;
   }

   protected List<RoutineDefinition> getRoutines0() throws SQLException {
      List<RoutineDefinition> result = new ArrayList();
      Routines r1 = Tables.ROUTINES.as("r1");
      Iterator var3 = this.create().select(r1.ROUTINE_SCHEMA, r1.ROUTINE_NAME, r1.SPECIFIC_NAME, DSL.when(DSL.condition("{0} && ARRAY['o','b']::\"char\"[]", org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PROARGMODES), (Field)DSL.inline("void")).otherwise((Field)r1.DATA_TYPE).as("data_type"), r1.CHARACTER_MAXIMUM_LENGTH, r1.NUMERIC_PRECISION, r1.NUMERIC_SCALE, r1.TYPE_UDT_SCHEMA, r1.TYPE_UDT_NAME, DSL.when(DSL.count().over(DSL.partitionBy(r1.ROUTINE_SCHEMA, r1.ROUTINE_NAME)).gt(DSL.one()), (Field)DSL.rowNumber().over((WindowSpecification)DSL.partitionBy(r1.ROUTINE_SCHEMA, r1.ROUTINE_NAME).orderBy(r1.SPECIFIC_NAME))).as("overload"), org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PROISAGG).from(r1).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME.eq(r1.SPECIFIC_SCHEMA)).join(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC).on(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PRONAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).and(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PRONAME.concat(new String[]{"_"}).concat(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_PROC)).eq((Field)r1.SPECIFIC_NAME)).where(new Condition[]{r1.ROUTINE_SCHEMA.in(this.getInputSchemata())}).and(this.tableValuedFunctions() ? DSL.condition(DSL.not((Field)org.jooq.util.postgres.pg_catalog.Tables.PG_PROC.PRORETSET)) : DSL.trueCondition()).orderBy(r1.ROUTINE_SCHEMA.asc(), r1.ROUTINE_NAME.asc(), DSL.field(DSL.name("overload")).asc()).fetch().iterator();

      while(var3.hasNext()) {
         Record record = (Record)var3.next();
         result.add(new PostgresRoutineDefinition(this, record));
      }

      return result;
   }

   protected List<PackageDefinition> getPackages0() throws SQLException {
      List<PackageDefinition> result = new ArrayList();
      return result;
   }

   protected DSLContext create0() {
      return DSL.using(this.getConnection(), SQLDialect.POSTGRES);
   }

   boolean is84() {
      if (is84 == null) {
         try {
            this.create(true).select((SelectField)DSL.count().over()).fetch();
            is84 = true;
         } catch (DataAccessException var2) {
            is84 = false;
         }
      }

      return is84;
   }

   boolean is94() {
      if (is94 == null) {
         try {
            this.create(true).select((SelectField)Tables.PARAMETERS.PARAMETER_DEFAULT).from(Tables.PARAMETERS).where(new Condition[]{DSL.falseCondition()}).fetch();
            is94 = true;
         } catch (DataAccessException var2) {
            is94 = false;
         }
      }

      return is94;
   }

   private List<String> enumLabels(String nspname, String typname) {
      Field<Object> cast = DSL.field("{0}::{1}", org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM.ENUMLABEL, DSL.name(nspname, typname));
      if (canCastToEnumType == null) {
         try {
            canCastToEnumType = true;
            return this.enumLabels(nspname, typname, cast);
         } catch (DataAccessException var5) {
            canCastToEnumType = false;
         }
      }

      return canCastToEnumType ? this.enumLabels(nspname, typname, cast) : this.enumLabels(nspname, typname, org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM.ENUMLABEL);
   }

   private List<String> enumLabels(String nspname, String typname, Field<?> orderBy) {
      return this.create().select((SelectField)org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM.ENUMLABEL).from(org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM).join((TableLike)org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE).on(org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM.ENUMTYPID.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE))).join(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE).on(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.TYPNAMESPACE.eq(PostgresDSL.oid(org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE))).where(new Condition[]{org.jooq.util.postgres.pg_catalog.Tables.PG_NAMESPACE.NSPNAME.eq(nspname)}).and(org.jooq.util.postgres.pg_catalog.Tables.PG_TYPE.TYPNAME.eq(typname)).orderBy(orderBy).fetch(org.jooq.util.postgres.pg_catalog.Tables.PG_ENUM.ENUMLABEL);
   }
}
