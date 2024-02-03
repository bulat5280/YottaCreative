package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;

final class HintedTable<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = -3905775637768497535L;
   private final AbstractTable<R> delegate;
   private final String keywords;
   private final QueryPartList<Name> arguments;

   HintedTable(AbstractTable<R> delegate, String keywords, String... arguments) {
      this(delegate, keywords, new QueryPartList(Tools.names(arguments)));
   }

   HintedTable(AbstractTable<R> delegate, String keywords, QueryPartList<Name> arguments) {
      super(delegate.getName(), delegate.getSchema());
      this.delegate = delegate;
      this.keywords = keywords;
      this.arguments = arguments;
   }

   public final boolean declaresTables() {
      return true;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate).sql(' ').keyword(this.keywords).sql(" (").visit(this.arguments).sql(')');
   }

   public final Class<? extends R> getRecordType() {
      return this.delegate.getRecordType();
   }

   public final Table<R> as(String alias) {
      return new HintedTable(new TableAlias(this.delegate, alias), this.keywords, this.arguments);
   }

   public final Table<R> as(String alias, String... fieldAliases) {
      return new HintedTable(new TableAlias(this.delegate, alias, fieldAliases), this.keywords, this.arguments);
   }

   final Fields<R> fields0() {
      return this.delegate.fields0();
   }
}
