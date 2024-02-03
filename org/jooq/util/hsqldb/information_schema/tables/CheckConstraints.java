package org.jooq.util.hsqldb.information_schema.tables;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.hsqldb.information_schema.InformationSchema;

public class CheckConstraints extends TableImpl<Record> {
   private static final long serialVersionUID = -836721390L;
   public static final CheckConstraints CHECK_CONSTRAINTS = new CheckConstraints();
   public final TableField<Record, String> CONSTRAINT_CATALOG;
   public final TableField<Record, String> CONSTRAINT_SCHEMA;
   public final TableField<Record, String> CONSTRAINT_NAME;
   public final TableField<Record, String> CHECK_CLAUSE;

   public Class<Record> getRecordType() {
      return Record.class;
   }

   public CheckConstraints() {
      this("CHECK_CONSTRAINTS", (Table)null);
   }

   public CheckConstraints(String alias) {
      this(alias, CHECK_CONSTRAINTS);
   }

   private CheckConstraints(String alias, Table<Record> aliased) {
      this(alias, aliased, (Field[])null);
   }

   private CheckConstraints(String alias, Table<Record> aliased, Field<?>[] parameters) {
      super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
      this.CONSTRAINT_CATALOG = createField("CONSTRAINT_CATALOG", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_SCHEMA = createField("CONSTRAINT_SCHEMA", SQLDataType.VARCHAR.length(128), this, "");
      this.CONSTRAINT_NAME = createField("CONSTRAINT_NAME", SQLDataType.VARCHAR.length(128), this, "");
      this.CHECK_CLAUSE = createField("CHECK_CLAUSE", SQLDataType.VARCHAR.length(65536), this, "");
   }

   public CheckConstraints as(String alias) {
      return new CheckConstraints(alias, this);
   }

   public CheckConstraints rename(String name) {
      return new CheckConstraints(name, (Table)null);
   }
}
