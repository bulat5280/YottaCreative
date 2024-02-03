package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class ReferentialConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = 912942764L;
   public static final ReferentialConstraints REFERENTIAL_CONSTRAINTS = new ReferentialConstraints();
   public final TableField<Record, String> CONSTRAINT_CATALOG;
   public final TableField<Record, String> CONSTRAINT_SCHEMA;
   public final TableField<Record, String> CONSTRAINT_NAME;
   public final TableField<Record, String> UNIQUE_CONSTRAINT_CATALOG;
   public final TableField<Record, String> UNIQUE_CONSTRAINT_SCHEMA;
   public final TableField<Record, String> UNIQUE_CONSTRAINT_NAME;
   public final TableField<Record, String> MATCH_OPTION;
   public final TableField<Record, String> UPDATE_RULE;
   public final TableField<Record, String> DELETE_RULE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public ReferentialConstraints() {
      this("REFERENTIAL_CONSTRAINTS", (Table)null);
   }

   public ReferentialConstraints(String alias) {
      this(alias, REFERENTIAL_CONSTRAINTS);
   }

   private ReferentialConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private ReferentialConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.UNIQUE_CONSTRAINT_CATALOG = createField("UNIQUE_CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.UNIQUE_CONSTRAINT_SCHEMA = createField("UNIQUE_CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.UNIQUE_CONSTRAINT_NAME = createField("UNIQUE_CONSTRAINT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.MATCH_OPTION = createField("MATCH_OPTION", SQLDataType.VARCHAR.length(65536), this, "");
      this.UPDATE_RULE = createField("UPDATE_RULE", SQLDataType.VARCHAR.length(65536), this, "");
      this.DELETE_RULE = createField("DELETE_RULE", SQLDataType.VARCHAR.length(65536), this, "");
   }

   public ReferentialConstraints as(String alias) {
      return new ReferentialConstraints(alias, this);
   }

   public ReferentialConstraints rename(String name) {
      return new ReferentialConstraints(name, (Table)null);
   }
}
