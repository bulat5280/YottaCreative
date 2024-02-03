package org.jooq.util.mysql.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.mysql.information_schema.InformationSchema;

public class ReferentialConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = 1234359031L;
   public static final ReferentialConstraints REFERENTIAL_CONSTRAINTS = new ReferentialConstraints();
   public static final TableField<Record, String> CONSTRAINT_CATALOG;
   public static final TableField<Record, String> CONSTRAINT_SCHEMA;
   public static final TableField<Record, String> CONSTRAINT_NAME;
   public static final TableField<Record, String> UNIQUE_CONSTRAINT_CATALOG;
   public static final TableField<Record, String> UNIQUE_CONSTRAINT_SCHEMA;
   public static final TableField<Record, String> UNIQUE_CONSTRAINT_NAME;
   public static final TableField<Record, String> MATCH_OPTION;
   public static final TableField<Record, String> UPDATE_RULE;
   public static final TableField<Record, String> DELETE_RULE;
   public static final TableField<Record, String> TABLE_NAME;
   public static final TableField<Record, String> REFERENCED_TABLE_NAME;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   private ReferentialConstraints() {
      this("REFERENTIAL_CONSTRAINTS", (Table)null);
   }

   private ReferentialConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private ReferentialConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
   }

   static {
      CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      UNIQUE_CONSTRAINT_CATALOG = createField("UNIQUE_CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      UNIQUE_CONSTRAINT_SCHEMA = createField("UNIQUE_CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      UNIQUE_CONSTRAINT_NAME = createField("UNIQUE_CONSTRAINT_NAME", SQLDataType.VARCHAR.length(64), REFERENTIAL_CONSTRAINTS, "");
      MATCH_OPTION = createField("MATCH_OPTION", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      UPDATE_RULE = createField("UPDATE_RULE", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      DELETE_RULE = createField("DELETE_RULE", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      TABLE_NAME = createField("TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
      REFERENCED_TABLE_NAME = createField("REFERENCED_TABLE_NAME", SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), REFERENTIAL_CONSTRAINTS, "");
   }
}
