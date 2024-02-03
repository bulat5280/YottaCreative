package org.jooq.util.postgres.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.information_schema.InformationSchema;

public class ReferentialConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = 1798397642L;
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
      this("referential_constraints", (Table)null);
   }

   public ReferentialConstraints(String alias) {
      this(alias, REFERENTIAL_CONSTRAINTS);
   }

   private ReferentialConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private ReferentialConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CONSTRAINT_CATALOG = createField("constraint_catalog", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_SCHEMA = createField("constraint_schema", SQLDataType.VARCHAR, this, "");
      this.CONSTRAINT_NAME = createField("constraint_name", SQLDataType.VARCHAR, this, "");
      this.UNIQUE_CONSTRAINT_CATALOG = createField("unique_constraint_catalog", SQLDataType.VARCHAR, this, "");
      this.UNIQUE_CONSTRAINT_SCHEMA = createField("unique_constraint_schema", SQLDataType.VARCHAR, this, "");
      this.UNIQUE_CONSTRAINT_NAME = createField("unique_constraint_name", SQLDataType.VARCHAR, this, "");
      this.MATCH_OPTION = createField("match_option", SQLDataType.VARCHAR, this, "");
      this.UPDATE_RULE = createField("update_rule", SQLDataType.VARCHAR, this, "");
      this.DELETE_RULE = createField("delete_rule", SQLDataType.VARCHAR, this, "");
   }

   public ReferentialConstraints as(String alias) {
      return new ReferentialConstraints(alias, this);
   }

   public ReferentialConstraints rename(String name) {
      return new ReferentialConstraints(name, (Table)null);
   }
}
