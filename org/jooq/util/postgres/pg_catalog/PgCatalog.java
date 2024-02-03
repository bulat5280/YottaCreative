package org.jooq.util.postgres.pg_catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.postgres.pg_catalog.tables.PgAttrdef;
import org.jooq.util.postgres.pg_catalog.tables.PgAttribute;
import org.jooq.util.postgres.pg_catalog.tables.PgClass;
import org.jooq.util.postgres.pg_catalog.tables.PgCollation;
import org.jooq.util.postgres.pg_catalog.tables.PgConstraint;
import org.jooq.util.postgres.pg_catalog.tables.PgCursor;
import org.jooq.util.postgres.pg_catalog.tables.PgDescription;
import org.jooq.util.postgres.pg_catalog.tables.PgEnum;
import org.jooq.util.postgres.pg_catalog.tables.PgInherits;
import org.jooq.util.postgres.pg_catalog.tables.PgNamespace;
import org.jooq.util.postgres.pg_catalog.tables.PgProc;
import org.jooq.util.postgres.pg_catalog.tables.PgType;

public class PgCatalog extends SchemaImpl {
   private static final long serialVersionUID = -1493849473L;
   public static final PgCatalog PG_CATALOG = new PgCatalog();

   private PgCatalog() {
      super("pg_catalog", DefaultCatalog.DEFAULT_CATALOG);
   }

   public final List<Table<?>> getTables() {
      List result = new ArrayList();
      result.addAll(this.getTables0());
      return result;
   }

   private final List<Table<?>> getTables0() {
      return Arrays.asList(PgAttrdef.PG_ATTRDEF, PgAttribute.PG_ATTRIBUTE, PgClass.PG_CLASS, PgCollation.PG_COLLATION, PgConstraint.PG_CONSTRAINT, PgCursor.PG_CURSOR, PgDescription.PG_DESCRIPTION, PgEnum.PG_ENUM, PgInherits.PG_INHERITS, PgNamespace.PG_NAMESPACE, PgProc.PG_PROC, PgType.PG_TYPE);
   }
}
