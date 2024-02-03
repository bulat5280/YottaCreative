package org.jooq.tools.jdbc;

import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public final class Mock {
   public static final MockDataProvider of(int rows) {
      return of(new MockResult(rows, (Result)null));
   }

   public static final MockDataProvider of(Record record) {
      return of(result(record));
   }

   public static final MockDataProvider of(Result<?> result) {
      return of(new MockResult(result.size(), result));
   }

   public static final MockDataProvider of(final MockResult... result) {
      return new MockDataProvider() {
         public MockResult[] execute(MockExecuteContext ctx) {
            return result;
         }
      };
   }

   static final Result<?> result(Record data) {
      Configuration configuration = data instanceof AttachableInternal ? ((AttachableInternal)data).configuration() : new DefaultConfiguration();
      Result<Record> result = DSL.using((Configuration)configuration).newResult(data.fields());
      result.add(data);
      return result;
   }

   private Mock() {
   }
}
