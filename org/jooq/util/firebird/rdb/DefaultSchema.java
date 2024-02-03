package org.jooq.util.firebird.rdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.jooq.util.firebird.rdb.tables.Rdb$fields;
import org.jooq.util.firebird.rdb.tables.Rdb$generators;
import org.jooq.util.firebird.rdb.tables.Rdb$indexSegments;
import org.jooq.util.firebird.rdb.tables.Rdb$procedureParameters;
import org.jooq.util.firebird.rdb.tables.Rdb$procedures;
import org.jooq.util.firebird.rdb.tables.Rdb$refConstraints;
import org.jooq.util.firebird.rdb.tables.Rdb$relationConstraints;
import org.jooq.util.firebird.rdb.tables.Rdb$relationFields;
import org.jooq.util.firebird.rdb.tables.Rdb$relations;

public class DefaultSchema extends SchemaImpl {
   private static final long serialVersionUID = 500280502L;
   public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

   private DefaultSchema() {
      super("");
   }

   public final List<Sequence<?>> getSequences() {
      List result = new ArrayList();
      result.addAll(this.getSequences0());
      return result;
   }

   private final List<Sequence<?>> getSequences0() {
      return Arrays.asList(Sequences.RDB$PROCEDURES);
   }

   public final List<Table<?>> getTables() {
      List result = new ArrayList();
      result.addAll(this.getTables0());
      return result;
   }

   private final List<Table<?>> getTables0() {
      return Arrays.asList(Rdb$fields.RDB$FIELDS, Rdb$generators.RDB$GENERATORS, Rdb$indexSegments.RDB$INDEX_SEGMENTS, Rdb$procedures.RDB$PROCEDURES, Rdb$procedureParameters.RDB$PROCEDURE_PARAMETERS, Rdb$refConstraints.RDB$REF_CONSTRAINTS, Rdb$relations.RDB$RELATIONS, Rdb$relationConstraints.RDB$RELATION_CONSTRAINTS, Rdb$relationFields.RDB$RELATION_FIELDS);
   }
}
