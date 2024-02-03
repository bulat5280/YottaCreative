package org.jooq.impl;

import java.sql.ResultSetMetaData;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.Record;

final class SQLResultQuery extends AbstractResultQuery<Record> {
   private static final long serialVersionUID = 1740879770879469220L;
   private final QueryPart delegate;

   SQLResultQuery(Configuration configuration, QueryPart delegate) {
      super(configuration);
      this.delegate = delegate;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return this.delegate instanceof QueryPartInternal ? ((QueryPartInternal)this.delegate).clauses(ctx) : null;
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   protected final Field<?>[] getFields(ResultSetMetaData meta) {
      Configuration configuration = this.configuration();
      return (new MetaDataFieldProvider(configuration, meta)).getFields();
   }

   final boolean isForUpdate() {
      return false;
   }
}
