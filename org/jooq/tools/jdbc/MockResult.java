package org.jooq.tools.jdbc;

import org.jooq.Record;
import org.jooq.Result;

public class MockResult {
   public final int rows;
   public final Result<?> data;

   public MockResult(Record data) {
      this(1, Mock.result(data));
   }

   public MockResult(int rows, Result<?> data) {
      this.rows = rows;
      this.data = data;
   }

   public String toString() {
      return this.data != null ? this.data.toString() : "" + this.rows;
   }
}
