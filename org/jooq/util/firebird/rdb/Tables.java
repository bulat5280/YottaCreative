package org.jooq.util.firebird.rdb;

import org.jooq.util.firebird.rdb.tables.Rdb$fields;
import org.jooq.util.firebird.rdb.tables.Rdb$generators;
import org.jooq.util.firebird.rdb.tables.Rdb$indexSegments;
import org.jooq.util.firebird.rdb.tables.Rdb$procedureParameters;
import org.jooq.util.firebird.rdb.tables.Rdb$procedures;
import org.jooq.util.firebird.rdb.tables.Rdb$refConstraints;
import org.jooq.util.firebird.rdb.tables.Rdb$relationConstraints;
import org.jooq.util.firebird.rdb.tables.Rdb$relationFields;
import org.jooq.util.firebird.rdb.tables.Rdb$relations;

public class Tables {
   public static final Rdb$fields RDB$FIELDS;
   public static final Rdb$generators RDB$GENERATORS;
   public static final Rdb$indexSegments RDB$INDEX_SEGMENTS;
   public static final Rdb$procedures RDB$PROCEDURES;
   public static final Rdb$procedureParameters RDB$PROCEDURE_PARAMETERS;
   public static final Rdb$refConstraints RDB$REF_CONSTRAINTS;
   public static final Rdb$relations RDB$RELATIONS;
   public static final Rdb$relationConstraints RDB$RELATION_CONSTRAINTS;
   public static final Rdb$relationFields RDB$RELATION_FIELDS;

   static {
      RDB$FIELDS = Rdb$fields.RDB$FIELDS;
      RDB$GENERATORS = Rdb$generators.RDB$GENERATORS;
      RDB$INDEX_SEGMENTS = Rdb$indexSegments.RDB$INDEX_SEGMENTS;
      RDB$PROCEDURES = Rdb$procedures.RDB$PROCEDURES;
      RDB$PROCEDURE_PARAMETERS = Rdb$procedureParameters.RDB$PROCEDURE_PARAMETERS;
      RDB$REF_CONSTRAINTS = Rdb$refConstraints.RDB$REF_CONSTRAINTS;
      RDB$RELATIONS = Rdb$relations.RDB$RELATIONS;
      RDB$RELATION_CONSTRAINTS = Rdb$relationConstraints.RDB$RELATION_CONSTRAINTS;
      RDB$RELATION_FIELDS = Rdb$relationFields.RDB$RELATION_FIELDS;
   }
}
