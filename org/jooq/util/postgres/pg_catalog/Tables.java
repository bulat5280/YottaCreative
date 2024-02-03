package org.jooq.util.postgres.pg_catalog;

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

public class Tables {
   public static final PgAttrdef PG_ATTRDEF;
   public static final PgAttribute PG_ATTRIBUTE;
   public static final PgClass PG_CLASS;
   public static final PgCollation PG_COLLATION;
   public static final PgConstraint PG_CONSTRAINT;
   public static final PgCursor PG_CURSOR;
   public static final PgDescription PG_DESCRIPTION;
   public static final PgEnum PG_ENUM;
   public static final PgInherits PG_INHERITS;
   public static final PgNamespace PG_NAMESPACE;
   public static final PgProc PG_PROC;
   public static final PgType PG_TYPE;

   public static PgCursor PG_CURSOR() {
      return PgCursor.PG_CURSOR.call();
   }

   static {
      PG_ATTRDEF = PgAttrdef.PG_ATTRDEF;
      PG_ATTRIBUTE = PgAttribute.PG_ATTRIBUTE;
      PG_CLASS = PgClass.PG_CLASS;
      PG_COLLATION = PgCollation.PG_COLLATION;
      PG_CONSTRAINT = PgConstraint.PG_CONSTRAINT;
      PG_CURSOR = PgCursor.PG_CURSOR;
      PG_DESCRIPTION = PgDescription.PG_DESCRIPTION;
      PG_ENUM = PgEnum.PG_ENUM;
      PG_INHERITS = PgInherits.PG_INHERITS;
      PG_NAMESPACE = PgNamespace.PG_NAMESPACE;
      PG_PROC = PgProc.PG_PROC;
      PG_TYPE = PgType.PG_TYPE;
   }
}
